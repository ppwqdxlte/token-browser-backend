package com.laowang.tokenbrowserbackend.service;

import com.laowang.tokenbrowserbackend.entity.BaseToken;
import com.laowang.tokenbrowserbackend.entity.KCT;
import com.laowang.tokenbrowserbackend.entity.Result;
import com.laowang.tokenbrowserbackend.entity.TCC;
import com.laowang.tokenbrowserbackend.enumeration.TokenType;
import com.laowang.tokenbrowserbackend.util.SQLServerConnector;
import org.apache.ibatis.ognl.NumericExpression;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TokenOperation<T> {

    private static final TokenOperation INSTANCE = new TokenOperation();
    private final SQLServerConnector connector = new SQLServerConnector();

    private TokenOperation() {
    }

    public static TokenOperation getINSTANCE() {
        return INSTANCE;
    }

    /**
     * @return all token records
     */
    public Result<List<BaseToken>> queryAll() {
        Result<List<BaseToken>> result = new Result<>();
        List<BaseToken> allTokens = new ArrayList<>();
        result.setData(allTokens);
        allTokens.addAll(queryAllTCC().getData());
        allTokens.addAll(queryAllKCT().getData());
        result.setMsg("There are " + allTokens.size() + " records~~~");
        return result;
    }

    public Result<List<BaseToken>> queryAllTCC() {
        Result<List<BaseToken>> result = new Result<>();
        List<BaseToken> allTokens = new ArrayList<>();
        result.setData(allTokens);
        Connection connection = connector.getConnectionOfThisProject();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement("select * from TCCList");
            rs = ps.executeQuery();
            while (rs.next()) {
                TCC tcc = new TCC();
                tcc.setBatchNo(rs.getString(1));
                tcc.setMeterNo(rs.getString(2));
                tcc.setToken(rs.getString(3));
                tcc.setDate(rs.getDate(4));
                allTokens.add(tcc);
            }
            result.setMsg("There are " + allTokens.size() + " records~");
        } catch (SQLException e) {
            result.setMsg("Something wrong with database...");
            result.setData(null);
            result.setThrowable(e);
        } finally {
            SQLServerConnector.closeAll(connection, ps, rs);
        }
        return result;
    }

    public Result<List<BaseToken>> queryAllKCT() {
        Result<List<BaseToken>> result = new Result<>();
        List<BaseToken> allTokens = new ArrayList<>();
        result.setData(allTokens);
        Connection connection = connector.getConnectionOfThisProject();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement("select * from KCTList");
            rs = ps.executeQuery();
            while (rs.next()) {
                KCT kct = new KCT();
                kct.setBatchNo(rs.getString(1));
                kct.setMeterNo(rs.getString(2));
                kct.setToken(rs.getString(3));
                kct.setDate(rs.getDate(4));
                allTokens.add(kct);
            }
            result.setMsg("There are " + allTokens.size() + " records~");
        } catch (SQLException e) {
            result.setMsg("Something wrong with database...");
            result.setData(null);
            result.setThrowable(e);
        } finally {
            SQLServerConnector.closeAll(connection, ps, rs);
        }
        return result;
    }

    /**
     * query TCC list by condition of meter-number string
     *
     * @param meterStr single-meter:XXXXXX multi-meter: XXXXX,XXXXX,XXXX.... range-meter:XXXXX-XXXXX
     * @return token list
     */
    public Result<List<T>> queryTokenListbyMeterStr(TokenType tokenType, String meterStr) {
        Result<List<T>> result = new Result<>();
        List<T> allTokens = new ArrayList<>();
        Connection connection = connector.getConnectionOfThisProject();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String tokenTable = (tokenType == TokenType.TCC) ? "TCCList" : "KCTList";
        try {
            if (meterStr.contains(",")) {   // multiple meter numbers seperated by comma
                StringBuffer queryText = new StringBuffer("select * from " + tokenTable + " where Meter_No ='1'");
                String[] split = meterStr.split(",");
                for (String s : split) {
                    queryText.append(" or Meter_No ='"+s+"'");
                }
                ps = connection.prepareStatement(queryText.toString());
            } else if (meterStr.contains("-")) {
                // multiple meter numbers ranging from '-' left to right
                StringBuffer queryText = new StringBuffer("select * from " + tokenTable + " where cast(Meter_No as decimal(13,0)) between ");
                String[] split = meterStr.split("-");
                String first = "0";
                String second = "0";
                if (isDecimal(split[0])){
                    first = split[0];
                }
                if (isDecimal(split[1])){
                    second = split[1];
                }
                queryText.append(first).append(" and ").append(second);
                ps = connection.prepareStatement(queryText.toString());
            } else {
                ps = connection.prepareStatement("select * from " + tokenTable + " where Meter_No=?");
                ps.setString(1, meterStr.trim());
            }
            rs = ps.executeQuery();
            while (rs.next()){
                if (tokenTable.contains("TCC")){
                    TCC tcc = new TCC();
                    tcc.setBatchNo(rs.getString(1));
                    tcc.setMeterNo(rs.getString(2));
                    tcc.setToken(rs.getString(3));
                    tcc.setDate(rs.getDate(4));
                    allTokens.add((T)tcc);
                }else {
                    KCT kct = new KCT();
                    kct.setBatchNo(rs.getString(1));
                    kct.setMeterNo(rs.getString(2));
                    kct.setToken(rs.getString(3));
                    kct.setDate(rs.getDate(4));
                    allTokens.add((T) kct);
                }
            }
        } catch (SQLException e) {
            result.setMsg("Something wrong with database...Query failed!");
            result.setData(null);
            result.setThrowable(e);
        } finally {
            SQLServerConnector.closeAll(connection, ps, rs);
        }
        result.setData(allTokens);
        return result;
    }

    private boolean isDecimal(String s) {
        try {
            BigDecimal bigDecimal = new BigDecimal(s);
            return true;
        } catch (Exception e){
            return false;
        }
    }
}
