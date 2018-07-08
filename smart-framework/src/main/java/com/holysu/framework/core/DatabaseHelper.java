package com.holysu.framework.core;

import com.holysu.framework.util.DBUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by holysu on 2018/7/8.
 */
public class DatabaseHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseHelper.class);
    public static void beginTransaction(){
        Connection conn = DBUtil.getConnection();
        if(conn != null){
            try{
                conn.setAutoCommit(false);
            }catch (SQLException e){
                LOGGER.error("begin transaction failure", e);
                throw new RuntimeException(e);
            }finally {

            }
        }
    }
}
