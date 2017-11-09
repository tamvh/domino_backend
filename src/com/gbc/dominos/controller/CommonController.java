/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gbc.dominos.controller;

import com.gbc.dominos.common.CommonFunction;
import com.gbc.dominos.common.CommonModel;
import com.gbc.dominos.common.DefineName;
import com.gbc.dominos.common.JsonParserUtil;
import com.google.gson.JsonObject;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author haint3
 */
public class CommonController extends HttpServlet {
    protected static final Logger logger = Logger.getLogger(CommonController.class);
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handle(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handle(req, resp);
    }
    
    private void handle(HttpServletRequest req, HttpServletResponse resp) {
        try {
            processs(req, resp);
        } catch (Exception ex) {
            logger.error(getClass().getSimpleName() + ".handle: " + ex.getMessage(), ex);
        }
    }
    
    private void processs(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        
        String pathInfo = (req.getPathInfo() == null) ? "" : req.getPathInfo();
        String cmd = req.getParameter("cm") != null ? req.getParameter("cm") : "";
        String data = req.getParameter("dt") != null ? req.getParameter("dt") : "";
        String content = "";
       
        CommonModel.prepareHeader(resp, CommonModel.HEADER_JS);
        
        switch (cmd) {
            case "getip":
                content = getIP();
                break;
            case "get_ws_session":
                content = genWSSession(data);
                break;
        }
        
        CommonModel.out(content, resp);
    }
    
    private String getIP() {
        String content = "";
        JsonObject jobject = new JsonObject();
        try {
            String ip = CommonFunction.getIPUtil();
            jobject.addProperty("ip", ip);
        } catch (IOException ex) {
            logger.error(getClass().getSimpleName() + ".getIP: " + ex.getMessage(), ex);
            content = CommonModel.FormatResponse(-1, "get ip error", "");
            return content;
        }
        
        content = CommonModel.FormatResponse(0, "", jobject);
        return content;
    }
    
    private String genWSSession(String data) {
        String content = "";
        
        try {
            JsonObject jsonObject = JsonParserUtil.parseJsonObject(data);
            
            if (jsonObject == null) {
                content = CommonModel.FormatResponse(-1, "Invalid request"); 
            } else {
                String merchantCode = jsonObject.get(DefineName.MERCHANT_CODE).getAsString();
                String deviceId = jsonObject.get(DefineName.DEVICE_ID).getAsString();
                String session;
             
                session = CommonFunction.genClientSession();
                JsonObject jsonRes = new JsonObject();
                jsonRes.addProperty(DefineName.WS_SESSION, session);
                content = CommonModel.FormatResponse(0, "", jsonRes);
            }
        } catch (Exception ex) {
            logger.error(getClass().getSimpleName() + ".genWSSession: " + ex.getMessage(), ex);
            content = CommonModel.FormatResponse(-1, ex.getMessage());
        }
        
        return content;
    }
}
