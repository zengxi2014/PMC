package com.pmc.fragmentInfo.action;

import com.pmc.fragmentInfo.service.FragmentInfoBasicService;
import org.apache.struts2.ServletActionContext;
import org.json.JSONObject;

import java.io.PrintWriter;

/**
 * Created by luluteam on 2015/8/29.
 */
public class loadFragmentInfoAction {
    private FragmentInfoBasicService fragmentInfoBasicService = new FragmentInfoBasicService();
    private String appId;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public void getFragementAverageLoadTime() throws Exception {
        JSONObject resultObject =fragmentInfoBasicService.loadFragmentAverageUseTime(getAppId());
        ServletActionContext.getResponse().addHeader("Access-Control-Allow-Origin", "*");
        ServletActionContext.getResponse().setCharacterEncoding("gb2312");
        PrintWriter writer = ServletActionContext.getResponse().getWriter();
        writer.print(resultObject.toString());
    }

    public void getFragmentVisitTimes() throws Exception {
        JSONObject resultObject =fragmentInfoBasicService.loadFragmentVisitTimes(getAppId());
        ServletActionContext.getResponse().addHeader("Access-Control-Allow-Origin", "*");
        ServletActionContext.getResponse().setCharacterEncoding("gb2312");
        PrintWriter writer = ServletActionContext.getResponse().getWriter();
        writer.print(resultObject.toString());
    }
}
