/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gbc.dominos.common;

/**
 *
 * @author haint3
 */
public class DefineMessage {
    public static class MsgType {
        public static String PROPERTY_NAME          = "type";
        public static int  INVOICE_COMPLETE         = 1;
        public static int  UPDATE_ITEM              = 2;
    }
    
    public static class MsgName {
        public static String PROPERTY_NAME          = "msg";
        public static String PAYMENT_SUCCESS        = "payment_success";
        public static String PAYMENT_FAIL           = "payment_fail";
        public static String UPDATE_ITEM            = "update_item";
    }
}
