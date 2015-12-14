package com.pmc.ActivityInfo.action;

import com.pmc.ActivityInfo.service.ActivityInfoBasicService;
import org.apache.struts2.ServletActionContext;
import org.json.JSONObject;

import java.io.PrintWriter;

/**
 * Created by luluteam on 2015/8/29.
 */
public class loadActivityInfoAction {
    private ActivityInfoBasicService activityInfoBasicService = new ActivityInfoBasicService();

    private String appId;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public void loadAllActivityInfoAction() throws  Exception {

    }

    public void getActivityAverageUseTime() throws Exception {
        JSONObject resultObject = activityInfoBasicService.loadActivityAverageUseTime(getAppId());
        ServletActionContext.getResponse().addHeader("Access-Control-Allow-Origin", "*");
        ServletActionContext.getResponse().setCharacterEncoding("utf-8");
        PrintWriter writer = ServletActionContext.getResponse().getWriter();
        writer.print(resultObject.toString());
    }

    public void getActivityVisitTimes() throws Exception {
        JSONObject resultObject = activityInfoBasicService.loadActivityVisitTimes(getAppId());
        ServletActionContext.getResponse().addHeader("Access-Control-Allow-Origin", "*");
        ServletActionContext.getResponse().setCharacterEncoding("utf-8");
        PrintWriter writer = ServletActionContext.getResponse().getWriter();
        writer.print(resultObject.toString());
    }

    public void getActivityAverageLoadTime() throws Exception {
        JSONObject resultObject = activityInfoBasicService.loadActivityAverageLoadTime(getAppId());
        ServletActionContext.getResponse().addHeader("Access-Control-Allow-Origin", "*");
        ServletActionContext.getResponse().setCharacterEncoding("utf-8");
        PrintWriter writer = ServletActionContext.getResponse().getWriter();
        writer.print(resultObject.toString());
    }


}
