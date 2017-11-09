/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gbc.dominos.controller;

import com.gbc.dominos.common.AppConst;
import com.gbc.dominos.common.CommonModel;
import com.gbc.dominos.data.HistoryEmailObj;
import com.gbc.dominos.model.HistorySendEmailModel;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author tamvh
 */
public class HistorySendEmailController extends HttpServlet {
    protected final Logger logger = Logger.getLogger(this.getClass());
    private static final Gson _gson = new Gson();
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
        } catch (IOException ex) {
            logger.error(getClass().getSimpleName() + ".handle: " + ex.getMessage(), ex);
        }
    }

    private void processs(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String cmd = req.getParameter("cm") != null ? req.getParameter("cm") : "";
        String data = req.getParameter("dt") != null ? req.getParameter("dt") : "";
        String content = "";
            
        CommonModel.prepareHeader(resp, CommonModel.HEADER_JS);
        
        switch (cmd) {            
            case "get_history":
                content = getHistorySendEmail(req, data);
                break;
        }
        
        CommonModel.out(content, resp);
    }

    private String getHistorySendEmail(HttpServletRequest req, String data) {
        String content;
        int ret = AppConst.ERROR_GENERIC;
        try {
            List<HistoryEmailObj> history_send_email = new ArrayList<>();
            ret = HistorySendEmailModel.getInstance().getHistorySendEmail(history_send_email);
            if (ret == 0) {
                content = CommonModel.FormatResponse(AppConst.NO_ERROR, "", history_send_email);
            } else {
                content = CommonModel.FormatResponse(AppConst.ERROR_GET_LIST_HISTORY_SEND_EMAIL, "");
            }
        } catch (IOException ex) {
            logger.error(getClass().getSimpleName() + ".getHistorySendEmail: " + ex.getMessage(), ex);
            content = CommonModel.FormatResponse(ret, ex.getMessage());
        }
        return content;
    }
}
