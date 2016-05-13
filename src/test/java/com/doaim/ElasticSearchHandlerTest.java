package com.doaim;

import com.alibaba.fastjson.JSON;
import com.jfinal.plugin.activerecord.Db;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by Doaim on 16/5/13.
 */
public class ElasticSearchHandlerTest extends SuperTest {

    @Test
    public void testCreateIndex() throws Exception {
        ElasticSearchHandler elasticSearchHandler = new ElasticSearchHandler();
        String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String dbURL = "jdbc:sqlserver://192.168.0.5:1433; DatabaseName=WIND";
        String userName = "sa";   //默认用户名
        String userPwd = "zaq1@WSX1";   //密码
        Connection dbConn = null;
        Statement statement = null;
        try {
            Class.forName(driverName);
            dbConn = DriverManager.getConnection(dbURL, userName, userPwd);
            statement = dbConn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = statement.executeQuery("select S_INFO_WINDCODE,S_INFO_NAME from CBondDescription");
//            List<String> columnNames = null;
            int i = 0;
            while (resultSet.next()) {
                System.out.println(i++);
//                if(columnNames == null) {
//                    columnNames = new ArrayList<String>();
//                    for (int i = 0, j = resultSet.getMetaData().getColumnCount(); i < j; i++) {
//                        columnNames.add(resultSet.getMetaData().getColumnName(i));
//                    }
//                }
                Map<String,String> map = new HashMap<>();
                map.put("S_INFO_WINDCODE",resultSet.getString("S_INFO_WINDCODE"));
                map.put("S_INFO_NAME",resultSet.getString("S_INFO_NAME"));
                elasticSearchHandler.createIndex("lianhe-db","BondInfo",JSON.toJSONString(map));
            }
        }catch(Exception e){
            if(dbConn!=null)
                dbConn.close();
            e.printStackTrace();
        }
    }

    @Test
    public void testSearche() throws Exception {

    }
}