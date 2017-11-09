/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gbc.dominos.controller;

import com.gbc.dominos.common.AppConst;
import com.gbc.dominos.common.CommonModel;
import com.gbc.dominos.common.DefineMessage;
import com.gbc.dominos.common.DefineName;
import com.gbc.dominos.hmacutil.HMACUtil;
import com.gbc.dominos.info.ClientSessionInfo;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

/**
 *
 * @author diepth
 */
@WebSocket
public class NotifyController {
    
    private static final Logger logger = Logger.getLogger(NotifyController.class);
    private static final Gson gson = new Gson();
    private static final Map<String, ClientSessionInfo> _clientSessionMap = Collections.synchronizedMap(new LinkedHashMap<String, ClientSessionInfo>());
    
    @OnWebSocketConnect
    public void onConnect(Session session) {
        
        Map<String, List<String>> params = session.getUpgradeRequest().getParameterMap();
        
        String appUser = "";
        List<String> appUserList = params.get(DefineName.APP_USER);
        if (appUserList == null || appUserList.isEmpty()) {
            session.close();
            return;
        } else {
            appUser = appUserList.get(0);
        }
        
        String deviceId = "";
        List<String> deviceIdList = params.get(DefineName.DEVICE_ID);
        if (deviceIdList == null || deviceIdList.isEmpty()) {
            session.close();
            return;
        } else {
            deviceId = deviceIdList.get(0);
        }
        
        String sessionId = "";
        List<String> sessionIdList = params.get(DefineName.WS_SESSION);
        if (sessionIdList == null || sessionIdList.isEmpty()) {
            session.close();
            return;
        } else {
            sessionId = sessionIdList.get(0);
        }
        
        String token = "";
        List<String> tokenList = params.get(DefineName.TOKEN);
        if (tokenList == null || tokenList.isEmpty()) {
            session.close();
            return;
        } else {
            token = tokenList.get(0);
        }
        
        String datax = appUser + deviceId + sessionId;
        String vrfToken = HMACUtil.HMacHexStringEncode("HmacSHA256", AppConst.HMAC_SHA256_KEY, datax);
        if (!vrfToken.equals(token)) {
            session.close();
            return;
        }
        
        List<HttpCookie> listCookie = new ArrayList<>();
        listCookie.add(new HttpCookie(DefineName.APP_USER, appUser));
        listCookie.add(new HttpCookie(DefineName.DEVICE_ID, deviceId));
        session.getUpgradeRequest().setCookies(listCookie);
        
        logger.info("NotifyController.onConnect: client deviceId = " + deviceId);
        
        ClientSessionInfo oldClient = _clientSessionMap.get(deviceId);
        if (oldClient != null) {
            logger.info("NotifyController.onConnect: close old client deviceId = " + deviceId);
            oldClient.getSession().close();
        }
        
        _clientSessionMap.put(deviceId, new ClientSessionInfo(session, deviceId, appUser));
        session.setIdleTimeout(10*60*1000);
        return;
    }
    
    @OnWebSocketClose
    public void onClose(Session session, int status, String reason) {
        session.close();
        removeSession(session);
    }

    @OnWebSocketMessage
    public void onText(Session session, String message) {
        
    }
    
    public void removeSession(Session session) {
        List<HttpCookie> listCookie = session.getUpgradeRequest().getCookies();
        for (HttpCookie cookie : listCookie) {
            if (cookie.getName().compareToIgnoreCase(DefineName.DEVICE_ID) == 0) {
                _clientSessionMap.remove(cookie.getValue());
                break;
            }
        }
    }
    
    public static boolean sendMessageToClient(String deviceId, String appUser, Object dataObject) {
        return sendMessageToClient(deviceId, appUser, gson.toJson(dataObject));
    }
    
    public static boolean sendMessageToClient(String deviceId, String appUser, String data) {
        if (_clientSessionMap.containsKey(deviceId)) {
            ClientSessionInfo clientSession = _clientSessionMap.get(deviceId);
            if (clientSession != null) {
                try {
                    logger.info("NotifyController.sendMessageToClient: response to client deviceId = " + deviceId + ", data = " + data);
                    clientSession.getSession().getRemote().sendString(data);
                    return true;
                } catch (IOException ex) {
                    logger.error("NotifyController.sendMessageToClient: " + ex.getMessage(), ex);
                }
            }
        }
        
        return false;
    }
    
    public static boolean sendMessageByAppUser(String appUser, String data) {    
        _clientSessionMap.forEach( (devId, clientSession) -> {
            if (appUser.compareToIgnoreCase(clientSession.getAppUser()) == 0) {
                try {
                    logger.info("NotifyController.sendMessageToClient: notify to client deviceId = " + devId + ", data = " + data);
                    clientSession.getSession().getRemote().sendString(data);
                } catch (IOException ex) {
                    logger.error("NotifyController.sendMessageByAppUser: " + ex.getMessage(), ex);
                } 
            }
        });
        
        return true;
    }
    
    public static boolean sendMessageToClientUpdateItem(String appUser, long itemId) {
        JsonObject respClientJobj = new JsonObject();
        respClientJobj.addProperty("item_id", itemId);
        String responseData = CommonModel.FormatResponseEx(DefineMessage.MsgType.UPDATE_ITEM,
                DefineMessage.MsgName.UPDATE_ITEM, respClientJobj);
        return sendMessageByAppUser(appUser, responseData);
    }
}
