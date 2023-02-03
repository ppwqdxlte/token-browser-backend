package com.laowang.tokenbrowserbackend.service;

import com.laowang.tokenbrowserbackend.entity.Result;
import com.laowang.tokenbrowserbackend.entity.TokenUser;
import com.laowang.tokenbrowserbackend.util.SQLServerConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Management {

    private Management() {
    }

    private static final Management INSTANCE = new Management();

    public static Management getInstance() {
        return INSTANCE;
    }

    private SQLServerConnector connector = new SQLServerConnector();

    /**
     * modify token user's password
     *
     * @param username token system username
     * @param oldPwd   token system password
     * @param newPwd01 new password
     * @param newPwd02 repeated new password
     * @return result of changing password
     */
    public Result<TokenUser> changePassword(String username, String oldPwd, String newPwd01, String newPwd02) {
        Result<TokenUser> result = Login.getInstance().submit(username, oldPwd);
        if (result.getData() == null) {
            result.setMsg("Wrong username or password, fail to change password!!");
            return result;
        }
        if (StringUtils.isEmpty(newPwd01) || !newPwd01.equals(newPwd02)) {
            result.setData(null);
            result.setMsg("The new password is incorrect,please re-enter!");
            return result;
        }
        Connection connection = connector.getConnectionOfThisProject();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement("UPDATE OperatorList SET Password=? WHERE Op_Name=?");
            ps.setString(1, newPwd01);
            ps.setString(2, username);
            int i = ps.executeUpdate();
            if (i > 0) {
                result.getData().setPassword(newPwd01);
                result.setMsg("Password successfully modified!!!");
            } else {
                result.setData(null);
                result.setMsg("Password modification failed due to unknown reason of database!");
            }
        } catch (SQLException e) {
            result.setData(null);
            result.setMsg(e.getMessage());
            result.setThrowable(e);
        } finally {
            SQLServerConnector.closeAll(connection, ps, rs);
        }
        return result;
    }

    /** 查询用户列表
     * @return 用户列表
     */
    public List<TokenUser> queryUserList() {
        List<TokenUser> result = new ArrayList<>();
        Connection connection = connector.getConnectionOfThisProject();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement("SELECT * FROM OperatorList");
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                TokenUser user = new TokenUser();
                user.setUsername(resultSet.getString(1));
                user.setPassword(resultSet.getString(2));
                user.setLevel(resultSet.getString(3));
                user.setDate(resultSet.getDate(4));
                result.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            SQLServerConnector.closeAll(connection, ps, rs);
        }
        return result;
    }

    public Result<TokenUser> createUser(String username, String password, Integer permisstionIndex) {
        Result<TokenUser> result = new Result<>();
        Connection connection = connector.getConnectionOfThisProject();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement("INSERT INTO OperatorList VALUES(?,?,?,GETDATE())");
            ps.setString(1,username);
            ps.setString(2,password);
            ps.setString(3,permisstionIndex == 0?"1":"0");
            int i = ps.executeUpdate();
            if (i > 0){
                result.setData(new TokenUser(username,password,permisstionIndex == 0?"1":"0",new Date(Calendar.getInstance().getTimeInMillis())));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            SQLServerConnector.closeAll(connection, ps, rs);
        }
        return result;
    }
}
