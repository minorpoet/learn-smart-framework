package com.holysu.framework.util;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by holysu on 2018/7/8.
 */
public class DBUtil {
    private static final String driver = "com.mysql.jdbc.Driver";
    private static final String url = "jdbc:mysql://localhost:3306/demo";
    private static final String username = "root";
    private static final String password = "root";

    // 用 ThreadLocal 来隔离数据库连接，是为了避免多个数据库事物公用一个连接导致事物失败
    private static ThreadLocal<Connection> connectionContainer = new ThreadLocal<>();

    public static Connection getConnection(){
        Connection conn = connectionContainer.get();
        try{
            Class.forName(driver);
            conn =  DriverManager.getConnection(url, username, password);
        }catch(Exception e){
            e.printStackTrace();
        } finally {
            connectionContainer.set(conn);
        }
        return conn;
    }

    public static void closeConnection(){
        Connection conn = connectionContainer.get();
        try{
            if(conn != null){
                conn.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            // 注意要移除 threadlocal, 否则可能造成oom
            connectionContainer.remove();
        }
    }

}
