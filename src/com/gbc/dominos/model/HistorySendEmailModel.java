/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gbc.dominos.model;

import com.gbc.dominos.data.HistoryEmailObj;
import com.gbc.dominos.database.MySqlFactory;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author tamvh
 */
public class HistorySendEmailModel {
    private static HistorySendEmailModel _instance = null;
    private static final Lock createLock_ = new ReentrantLock();
    protected final Logger logger = Logger.getLogger(this.getClass());

    public static HistorySendEmailModel getInstance() throws IOException {
        if (_instance == null) {
            createLock_.lock();
            try {
                if (_instance == null) {
                    _instance = new HistorySendEmailModel();
                }
            } finally {
                createLock_.unlock();
            }
        }
        return _instance;
    }
    
    public int insertHistorySendEmail(String email, String sub, String content) {
        int ret = -1;
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            String queryStr;
            String tabletName = "tb_email_log";
            connection = MySqlFactory.getConnection();
            stmt = connection.createStatement();
            queryStr = String.format("INSERT INTO %1$s (email, subject, content) VALUES ('%2$s', '%3$s', '%4$s')",
                    tabletName, email, sub, content);
            System.out.println("Query insertHistorySendEmail: " + queryStr);
            int result = stmt.executeUpdate(queryStr);
            if(result > 0) {
                ret = 0;
            }
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(HistorySendEmailModel.class.getName()).log(Level.SEVERE, null, ex);
            ret = -1;
        } finally {
            MySqlFactory.safeClose(rs);
            MySqlFactory.safeClose(stmt);
            MySqlFactory.safeClose(connection);
        }
        return ret;
    }
    
    public int getHistorySendEmail(List<HistoryEmailObj> list_history) {
        int ret = -1;
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            String queryStr;
            String tableName = "tb_email_log";
            connection = MySqlFactory.getConnection();
            stmt = connection.createStatement();
            HistoryEmailObj history_item;
            queryStr = String.format("SELECT `id`, `email`, `subject`, `content`, `time_send_email` FROM %1$s ORDER BY `id` DESC", tableName);
            System.out.println("Query getHistorySendEmail: " + queryStr);
            stmt.execute(queryStr);
            rs = stmt.getResultSet();
            if (rs != null) {
                while (rs.next()) {
                    history_item = new HistoryEmailObj();
                    history_item.setId(rs.getInt("id"));
                    history_item.setEmail(rs.getString("email"));
                    history_item.setSubject(rs.getString("subject"));
                    history_item.setContent(rs.getString("content"));
                    history_item.setTime_send_email(rs.getString("time_send_email"));
                    list_history.add(history_item);
                    ret = 0;
                }
            }
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(HistorySendEmailModel.class.getName()).log(Level.SEVERE, null, ex);
            ret = -1;
        } finally {
            MySqlFactory.safeClose(rs);
            MySqlFactory.safeClose(stmt);
            MySqlFactory.safeClose(connection);
        }
        
        return ret;
    }
}
