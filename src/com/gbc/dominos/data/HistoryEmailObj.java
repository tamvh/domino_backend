/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gbc.dominos.data;

/**
 *
 * @author tamvh
 */
public class HistoryEmailObj {
    private int id;
    private String email;
    private String subject;
    private String content;
    private String time_send_email;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime_send_email() {
        return time_send_email;
    }

    public void setTime_send_email(String time_send_email) {
        this.time_send_email = time_send_email;
    }
}
