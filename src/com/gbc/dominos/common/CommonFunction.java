/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gbc.dominos.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.logging.Level;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author diepth
 */
public class CommonFunction {
    
    private static String publicIP = "";
    //private static final Map<String, Long> mapLastInvoiceNo = Collections.synchronizedMap(new LinkedHashMap<String, Long>());
    public static final String KEY_TOKEN_LOGIN = "token_login";
    public static final String KEY_USERNAME = "username";
    
    public static String genBarCodeFromInvoiceCode(String invoiceCode) {
        
        int len = invoiceCode.length();
        int i, j, x = 0, y = 0, c;
        
        if (len < 10)
            return "";
        
        j = 1;
        for (i = 0; i < len; i++) {
            c = (invoiceCode.charAt(i))*j;
            x = x + c;
            j += 3;
        }
        x = (x % 9 + 1) + '0';
        
        j = 1;
        for (i = len - 1; i >= 0; i--) {
            c = (invoiceCode.charAt(i))*j;
            y = y + c;
            j += 1;
        }
        y = (y % 9 + 1) + '0';
        
        char[] temp = new char[len+2];
        char[] barCode = new char[len+2];
        
        invoiceCode.getChars(0, 6, temp, 0);
        temp[6] = (char)x;
        temp[7] = (char)y;
        invoiceCode.getChars(6, len, temp, 8);
        
        Encoder.encode(temp, len + 2, barCode);
        
        return new String(barCode);
    }
    
    public static String getInvoiceCodeFromBarCode(String barCode) {
        
        int len = barCode.length();
        char[] temp = new char[len];
        char[] invoiceCode = new char[len];
        
        if (len < 12)
            return "";
         
        barCode.getChars(0, len, temp, 0);
        Encoder.decode(temp, len, invoiceCode);
        
        String out = String.valueOf(invoiceCode, 0, 6) + String.valueOf(invoiceCode, 8, len - 8);
        return out;
    }
    
    public static String getCurrentDate() {
        
        /*
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        
        cal.setTime(date);*/
        /*
        Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("GMT+7"));
        long time = cal.getTimeInMillis();
        Date date = new Date(time);
        String currDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
        
        return currDate;
        */
        Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("GMT+7"));
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        fmt.setCalendar(cal);
        String currDate = fmt.format(cal.getTimeInMillis());
        
        return currDate;
    }
    
    public static String getCurrentTime() {
        
        /*
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        
        cal.setTime(date);
        */
        /*
        Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("GMT+7"));
        long time = cal.getTimeInMillis();
        Date date = new Date(time);
        String currTime = new SimpleDateFormat("HH:mm:ss").format(date);
        
        return currTime;
        */
        Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("GMT+7"));
        SimpleDateFormat fmt = new SimpleDateFormat("HH:mm:ss");
        fmt.setCalendar(cal);
        String currTime = fmt.format(cal.getTimeInMillis());
        
        return currTime;
    }
    
    public static String getCurrentDateTime() {
        
        /*
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        
        cal.setTime(date);
        */
        /*
        Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("GMT+7"));
        long time = cal.getTimeInMillis();
        Date date = new Date(time);
        String currDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        
        return currDateTime;
        */
        Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("GMT+7"));
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        fmt.setCalendar(cal);
        String currDateTime = fmt.format(cal.getTimeInMillis());
        
        return currDateTime;
    }
    
        public static String getCurrentDateTime(long time) {
        
        Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("GMT+7"));
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        fmt.setCalendar(cal);
        String currDateTime = fmt.format(time);
        
        return currDateTime;
    }
    public static String formatDateFromString (String date){
        if (date == null || date.isEmpty()) {
            return "";
        }
         //format thành dạng dd-MM-yyyy từ chuỗi input (dạng: yyyy-MM-dd)
        String result = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date d = sdf.parse(date);
            result = sdf2.format(d);            
        } catch (ParseException ex) {
            java.util.logging.Logger.getLogger(CommonFunction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;        
    }
    
    public static String formatDateTimeFromString(String date){
        if (date == null || date.isEmpty()) {
            return "";
        }
         //format thành dạng yyyy-MM-dd HH:mm:ss từ chuỗi input (dạng: dd-MM-yyyy HH:mm:ss)
        String result = "";
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date d = sdf.parse(date);
            result = sdf2.format(d);            
        } catch (ParseException ex) {
            java.util.logging.Logger.getLogger(CommonFunction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;        
    }
    
    
    public static String getStringCurrentTimeMillis() {
        
        Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("GMT+7"));
        return Long.toString(cal.getTimeInMillis());
    }
    
    public static long getCurrentTimeMillis() {
        
        Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("GMT+7"));
        return cal.getTimeInMillis();
    }
    
    public static long getTimeMillis( String strDateTime) {
       
       Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("GMT+7"));
       SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       fmt.setCalendar(cal);
       
       try {
           Date date = fmt.parse(strDateTime);
           return date.getTime();
       } catch (Exception ex) {
           
       }
       
       return 0;
   }
    
    public static String getIPUtil() throws IOException {
        
        if (publicIP.isEmpty()) {
            URL whatismyip = new URL("http://checkip.amazonaws.com");
            BufferedReader in = null;
            try {
                in = new BufferedReader(new InputStreamReader(
                        whatismyip.openStream()));
                publicIP = in.readLine();
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                    }
                }
            }
        }
        
        return publicIP;
    }
    
    public static void setSession(HttpServletRequest req, String username, String sid) {
        
        try {
            HttpSession session = req.getSession(true);
            
            session.setAttribute(KEY_TOKEN_LOGIN, sid);
            session.setAttribute(sid, username);
        } catch (Exception ex) {
            
        }
    }
    
    public static void setSessionWithPrefix(HttpServletRequest req, String username, String key) {
        
        try {
            HttpSession session = req.getSession(true);
            session.setAttribute(key, username);
            
        } catch (Exception ex) {
            
        }
    }
    
    public static Boolean checkSession(HttpServletRequest req) {
        
        Boolean ret = false;
        try {
            HttpSession session = req.getSession(false);
            if (session != null) {
                String referer = req.getHeader("Referer");
                String key = null;
                
                if (key != null) {
                    String value = (String)session.getAttribute(key);
                    if (value != null) {
                        return true;
                    }
                }
            }
        } catch (Exception ex) {
            
        }
        
        return ret;
    }
    
    public static String getUserSession(HttpServletRequest req) {
        
        String userName = null;        
        try {
            HttpSession session = req.getSession(false);
            if (session != null) {
                String referer = req.getHeader("Referer");
                String key = null;

                if (key != null) {
                    String value = (String)session.getAttribute(key);
                    if (value != null) {
                        userName = value;
                    }
                }
            }
        } catch (Exception ex) {
            
        }
        
        return userName;
    }
    
    public static void setInvoiceSession(HttpServletRequest req, String invoiceSession) {
        
        try {
            HttpSession session = req.getSession(true);
            session.setAttribute(DefineName.INVOICE_SESSION, invoiceSession);
            session.setMaxInactiveInterval(5);
        } catch (Exception ex) {
            
        }
    }
    
    public static String getInvoiceSession(HttpServletRequest req) {
        
        String ret = null;      
        try {
            HttpSession session = req.getSession(true);
            ret = (String)session.getAttribute(DefineName.INVOICE_SESSION);
        } catch (Exception ex) {
            
        }
        
        return ret;
    }
    
    public static void deleteSession(HttpServletRequest req) {
        
        try {
            HttpSession session = req.getSession(false);
            if (session != null) {
                /*
                String referer = req.getHeader("Referer");
                String key = null;
                if(referer.indexOf("/" + AppConst.PREFIX_USER_SESSION_ACCOUNTANT) > 0){
                    key = AppConst.PREFIX_USER_SESSION_ACCOUNTANT;
                } else if(referer.indexOf("/" + AppConst.PREFIX_USER_SESSION_SUPER) > 0){
                    key = AppConst.PREFIX_USER_SESSION_SUPER;
                } else if(referer.indexOf("/" + AppConst.PREFIX_USER_SESSION_ADMIN) > 0){
                    key = AppConst.PREFIX_USER_SESSION_ADMIN;
                } 

                if (key != null) {
                    String sid = (String)session.getAttribute(key);
                    if(sid != null){
                        session.removeAttribute(sid);
                    }
                }
                */
                session.invalidate();
            }
        } catch (Exception ex) {
            
        }
    }
    
    public static String toMD5(String data) {
        
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(data.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (Exception ex) {
            return null;
        }   
    }
    
    public static String genClientSession() {
        String timestamp = getCurrentDateTime();
        String tok = CommonFunction.toMD5(timestamp + AppConst.MD5_KEY);

        return tok;
    }
}
