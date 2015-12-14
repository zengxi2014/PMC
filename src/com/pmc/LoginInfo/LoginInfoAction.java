package com.pmc.LoginInfo;

import org.apache.struts2.ServletActionContext;
import org.json.JSONObject;

import java.io.PrintWriter;

/**
 * Created by luluteam on 2015/9/12.
 */
public class LoginInfoAction {
    private String appId;

    private LoginInfoService loginInfoService = new LoginInfoService();

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public void getLoginInfo() throws Exception {
        JSONObject retObject = loginInfoService.getLoginInfo(getAppId());
        ServletActionContext.getResponse().addHeader("Access-Control-Allow-Origin", "*");
        ServletActionContext.getResponse().setCharacterEncoding("utf-8");
        PrintWriter writer = ServletActionContext.getResponse().getWriter();
        writer.print(retObject.toString());
    }
}
