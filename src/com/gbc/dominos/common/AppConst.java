/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gbc.dominos.common;

/**
 *
 * @author diepth
 */
public class AppConst {
    
    public static final int INFO_GENERIC = 1;
    
    public static final int ERROR_GENERIC = -1;
    public static final int NO_ERROR = 0;
    public static final int ERROR_INVALID_USER = 100;
    public static final int ERROR_NOT_LOGIN = 101;
    public static final int HAVE_NOT_PERMISSION = 102;
    public static final int ERROR_INVALID_PASSWORD = 103;
    public static final int ERROR_INVALID_USER_OR_PW = 104;
    
    // config email
    public static final int ERROR_GET_LIST_EMAIL = 200;
    public static final int ERROR_INSERT_EMAIL = 201;
    public static final int ERROR_DELETE_EMAIL = 202;
    public static final int ERROR_SEND_EMAIL = 203;
    public static final int ERROR_GET_LIST_HISTORY_SEND_EMAIL = 204;
    
    //username & password email:
    public static String GMAIL_HOST;
    public static String GMAIL_PORT;
    public static String GMAIL_USER;
    public static String GMAIL_PWD;
    
    public static final String MD5_KEY                      = "nuTdPPedhzNj3BRR";
    public static final String HMAC_SHA256_KEY              = "7VShsAFE3S4pS3lijpCkIxCDpzi7ljdS";
    public static final String HMAC_SHA256_KEY_2            = "yvJNXhn7lMr8SOD3ltTdzn2hldWYPri9";
}
