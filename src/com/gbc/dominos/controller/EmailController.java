/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gbc.dominos.controller;

import com.gbc.dominos.common.AppConst;
import com.gbc.dominos.common.CommonModel;
import com.gbc.dominos.common.GoogleEmail;
import com.gbc.dominos.common.JsonParserUtil;
import com.gbc.dominos.common.RenderModel;
import com.gbc.dominos.data.EmailInfo;
import com.gbc.dominos.model.EmailModel;
import com.gbc.dominos.model.HistorySendEmailModel;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import hapax.Template;
import hapax.TemplateDataDictionary;
import hapax.TemplateDictionary;
import hapax.TemplateException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author tamvh
 */
public class EmailController extends HttpServlet {

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
            case "insert":
                content = insertEmail(req, data);
                break;
            case "delete":
                content = deleteEmail(req, data);
                break;
            case "getlist":
                content = getListEmail(req);
                break;
            case "sendemail":
                content = sendEmail(req, data);
                break;
        }

        CommonModel.out(content, resp);
    }

    private String insertEmail(HttpServletRequest req, String data) {
        String content;
        int ret = AppConst.ERROR_GENERIC;
        try {
            JsonObject jsonObject = JsonParserUtil.parseJsonObject(data);
            if (jsonObject == null) {
                content = CommonModel.FormatResponse(ret, "Invalid parameter");
            } else {
                EmailInfo email = _gson.fromJson(jsonObject.get("email").getAsJsonObject(), EmailInfo.class);
                if (email == null) {
                    content = CommonModel.FormatResponse(ret, "Invalid parameter");
                } else {
                    ret = EmailModel.getInstance().insertEmail(email);
                    if (ret == 0) {
                        content = CommonModel.FormatResponse(AppConst.NO_ERROR, "insert email success", email);
                    } else {
                        content = CommonModel.FormatResponse(AppConst.ERROR_INSERT_EMAIL, "insert email failed");
                    }
                }
            }
        } catch (IOException ex) {
            logger.error(getClass().getSimpleName() + ".insertEmail: " + ex.getMessage(), ex);
            content = CommonModel.FormatResponse(ret, ex.getMessage());
        }
        return content;
    }

    private String deleteEmail(HttpServletRequest req, String data) {
        String content;
        int ret = AppConst.ERROR_GENERIC;
        try {
            JsonObject jsonObject = JsonParserUtil.parseJsonObject(data);
            if (jsonObject == null) {
                content = CommonModel.FormatResponse(ret, "Invalid parameter");
            } else {
                int email_id = 0;
                if (jsonObject.has("id")) {
                    email_id = jsonObject.get("id").getAsInt();
                }

                if (email_id <= 0) {
                    content = CommonModel.FormatResponse(ret, "Invalid parameter");
                } else {
                    ret = EmailModel.getInstance().deleteEmail(email_id);
                    if (ret == 0) {
                        content = CommonModel.FormatResponse(AppConst.NO_ERROR, "delete email success");
                    } else {
                        content = CommonModel.FormatResponse(AppConst.ERROR_DELETE_EMAIL, "delete email failed");
                    }
                }
            }
        } catch (IOException ex) {
            logger.error(getClass().getSimpleName() + ".deleteEmail: " + ex.getMessage(), ex);
            content = CommonModel.FormatResponse(ret, ex.getMessage());
        }
        return content;
    }

    private String getListEmail(HttpServletRequest req) {
        String content;
        int ret = AppConst.ERROR_GENERIC;
        try {
            List<EmailInfo> list_email = new ArrayList<>();
            ret = EmailModel.getInstance().getListEmail(list_email);
            if (ret == 0) {
                content = CommonModel.FormatResponse(AppConst.NO_ERROR, "", list_email);
            } else {
                content = CommonModel.FormatResponse(AppConst.ERROR_GET_LIST_EMAIL, "");
            }
        } catch (IOException ex) {
            logger.error(getClass().getSimpleName() + ".getListEmail: " + ex.getMessage(), ex);
            content = CommonModel.FormatResponse(ret, ex.getMessage());
        }
        return content;
    }

    private String sendEmail(HttpServletRequest req, String data) {
        String content = "";
        int ret = AppConst.ERROR_GENERIC;
        JsonObject jsonObj = JsonParserUtil.parseJsonObject(data);
        try {
            if (jsonObj == null) {
                content = CommonModel.FormatResponse(ret, "Invalid parameter");
            } else {
                String branch = null;
                String host = null;
                int port = 0;
                if (jsonObj.has("branch")) {
                    branch = jsonObj.get("branch").getAsString();
                }
                if (jsonObj.has("host")) {
                    host = jsonObj.get("host").getAsString();
                }
                if (jsonObj.has("port")) {
                    port = jsonObj.get("port").getAsInt();
                }
                if (branch == null || host == null || port <= 0) {
                    content = CommonModel.FormatResponse(ret, "item null");
                    return content;
                }
                String temp = this.renderEmailContent(branch, host, port);
                String subj = "[DOMINOS PIZZA] THÔNG BÁO";
                String listEmailCC = "";
                String listEmailReceive = "";
                List<EmailInfo> list_email = new ArrayList<>();
                int resulet_getlist_email = EmailModel.getInstance().getListEmail(list_email);
                if(resulet_getlist_email == 0) {
                    for(int i = 0; i< list_email.size(); i ++){
                        if(i == list_email.size() - 1) {
                            listEmailReceive = listEmailReceive + list_email.get(i).getEmail();
                        } else {
                            listEmailReceive = listEmailReceive + list_email.get(i).getEmail() + ", ";
                        }
                        //write log
                        String content_log = "telnet " + host + " " + String.valueOf(port)+  " -> no response. Branch: " + branch;
                        HistorySendEmailModel.getInstance().insertHistorySendEmail(list_email.get(i).getEmail(), subj, content_log);
                    }
                }
                if(!listEmailReceive.isEmpty()) {
                    ret = GoogleEmail.sendEmail(listEmailReceive, listEmailCC, subj, temp);
                }
                if (ret == 0) {
                    content = CommonModel.FormatResponse(ret, "Send mail success");
                } else {
                    content = CommonModel.FormatResponse(ret, "Send mail failed");
                }
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(EmailController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return content;
    }

    private String renderEmailContent(String branch, String host, int port) throws Exception {
        String content = "";
        TemplateDataDictionary dic = TemplateDictionary.create();
        try {
            dic.setVariable("BRANCH", branch);
            dic.setVariable("HOST", host);
            dic.setVariable("PORT", String.valueOf(port));

            Template template = RenderModel.getCTemplate("email");
            content = template.renderToString(dic);
        } catch (TemplateException ex) {
            java.util.logging.Logger.getLogger(EmailController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return content;
    }
}
