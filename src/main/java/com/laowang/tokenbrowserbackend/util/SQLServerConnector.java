package com.laowang.tokenbrowserbackend.util;

import com.laowang.tokenbrowserbackend.config.SqlServerDatabase;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.sql.*;

@Component
public class SQLServerConnector implements ApplicationContextAware {

    /**
     * @param driverName eg. "com.microsoft.sqlserver.jdbc.SQLServerDriver"
     * @param dburl      eg. "jdbc:sqlserver://localhost:1433;DatabaseName = student"
     * @param dbUserID   eg. "sa";// database user-name
     * @param dbUserPwd    eg. "root";// database user's password
     * @return object of Connection
     */
    private Connection getConnection(String driverName, String dburl, String dbUserID, String dbUserPwd) {
        try {
            Class.forName(driverName);
            System.out.println("Success to load driver!!!");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Fail to load driver...");
        }
        Connection dbcon = null;
        try {
            dbcon = DriverManager.getConnection(dburl, dbUserID, dbUserPwd);
            System.out.println("Successfully connect to SQL-Server!!!");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed connection...");
        }
        return dbcon;
    }

    private SqlServerDatabase database = new SqlServerDatabase();
    public Connection getConnectionOfThisProject(){
        return getConnection(database.getDRIVER_NAME(), database.getDB_URL(), database.getDB_USER_ID(),database.getDB_USER_PWD());
    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void closePrepareStatement(PreparedStatement ps){
        if (ps != null){
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void closeResultSet(ResultSet rs){
        if (rs != null){
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void closeAll(Connection conn,PreparedStatement ps,ResultSet rs){
        closeResultSet(rs);
        closePrepareStatement(ps);
        closeConnection(conn);
    }

    protected static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext app) throws BeansException {
        if (applicationContext == null) {
            applicationContext = app;
        }
    }

    /**
     * 通过类的class从容器中手动获取对象
     */
    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

}
