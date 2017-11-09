/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gbc.dominos.model;

import java.io.IOException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.log4j.Logger;
import com.gbc.dominos.data.EmailInfo;
import com.gbc.dominos.database.MySqlFactory;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 *
 * @author tamvh
 */
public class EmailModel {
    private static EmailModel _instance = null;
    private static final Lock createLock_ = new ReentrantLock();
    protected final Logger logger = Logger.getLogger(this.getClass());

    public static EmailModel getInstance() throws IOException {
        if (_instance == null) {
            createLock_.lock();
            try {
                if (_instance == null) {
                    _instance = new EmailModel();
                }
            } finally {
                createLock_.unlock();
            }
        }
        return _instance;
    }
    
    public int insertEmail(EmailInfo emailInfo) {
        int ret = -1;
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            String queryStr;
            String tabletName = "tb_email_list";
            connection = MySqlFactory.getConnection();
            stmt = connection.createStatement();
            queryStr = String.format("INSERT INTO %1$s (email, name, phone) VALUES ('%2$s', '%3$s', %4$s)",
                    tabletName, emailInfo.getEmail(), emailInfo.getName(), emailInfo.getPhone());
            System.out.println("Query insertEmail: " + queryStr);
            int result = stmt.executeUpdate(queryStr);
            if(result > 0) {
                ret = 0;
            }
            if(ret == 0) {
                queryStr = String.format("SELECT `id` FROM %1$s WHERE `email` = '%2$s' ORDER BY `id` DESC LIMIT 0,1", tabletName, emailInfo.getEmail());
                if (stmt.execute(queryStr)) {
                    rs = stmt.getResultSet();
                    if (rs != null) {
                         if (rs.next()) {
                            emailInfo.setId(rs.getInt("id"));
                            System.out.println("get email id success");
                         } 
                    }
                }
            }
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(EmailModel.class.getName()).log(Level.SEVERE, null, ex);
            ret = -1;
        } finally {
            MySqlFactory.safeClose(rs);
            MySqlFactory.safeClose(stmt);
            MySqlFactory.safeClose(connection);
        }
        return ret;
    }
    
    public int deleteEmail(int id) {
        int ret = -1;
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            String queryStr;
            String tabletName = "tb_email_list";
            connection = MySqlFactory.getConnection();
            stmt = connection.createStatement();
            queryStr = String.format("DELETE FROM %1$s WHERE `id` = %2$d",
                    tabletName, id);
            System.out.println("Query deleteEmail: " + queryStr);
            int result = stmt.executeUpdate(queryStr);
            if(result > 0) {
                ret = 0;
            }
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(EmailModel.class.getName()).log(Level.SEVERE, null, ex);
            ret = -1;
        } finally {
            MySqlFactory.safeClose(rs);
            MySqlFactory.safeClose(stmt);
            MySqlFactory.safeClose(connection);
        }
        return ret;
    }
    
    public int getListEmail(List<EmailInfo> list_email) {
        int ret = -1;
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            String queryStr;
            String tableName = "tb_email_list";
            connection = MySqlFactory.getConnection();
            stmt = connection.createStatement();
            EmailInfo emailInfo;
            queryStr = String.format("SELECT `id`, `email`, `name`, `phone` FROM %1$s", tableName);
            System.out.println("Query getListEmail: " + queryStr);
            stmt.execute(queryStr);
            rs = stmt.getResultSet();
            if (rs != null) {
                while (rs.next()) {
                    emailInfo = new EmailInfo();
                    emailInfo.setId(rs.getInt("id"));
                    emailInfo.setEmail(rs.getString("email"));
                    emailInfo.setName(rs.getString("name"));
                    emailInfo.setPhone(rs.getString("phone"));
                    list_email.add(emailInfo);
                    ret = 0;
                }
            }
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(EmailModel.class.getName()).log(Level.SEVERE, null, ex);
            ret = -1;
        } finally {
            MySqlFactory.safeClose(rs);
            MySqlFactory.safeClose(stmt);
            MySqlFactory.safeClose(connection);
        }
        
        return ret;
    }
    
}
