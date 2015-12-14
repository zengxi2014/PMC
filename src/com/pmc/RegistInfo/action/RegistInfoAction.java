package com.pmc.RegistInfo.action;

import com.pmc.RegistInfo.service.RegistInfoBasicService;
import org.apache.struts2.ServletActionContext;
import org.json.JSONObject;

import java.io.PrintWriter;

/**
 * Created by luluteam on 2015/9/12.
 */
public class RegistInfoAction {
    private String appId;

    private RegistInfoBasicService registInfoBasicService = new RegistInfoBasicService();
    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public void getRegistInfo() throws Exception {
        JSONObject retObject = registInfoBasicService.getRegistInfo(getAppId());
        ServletActionContext.getResponse().addHeader("Access-Control-Allow-Origin", "*");
        ServletActionContext.getResponse().setCharacterEncoding("utf-8");
        PrintWriter writer = ServletActionContext.getResponse().getWriter();
        writer.print(retObject.toString());
    }

    public void getWayInfo() throws Exception {
        JSONObject retObject = registInfoBasicService.getWayInfo(getAppId());
        ServletActionContext.getResponse().addHeader("Access-Control-Allow-Origin", "*");
        ServletActionContext.getResponse().setCharacterEncoding("utf-8");
        PrintWriter writer = ServletActionContext.getResponse().getWriter();
        writer.print(retObject.toString());
    }
}
