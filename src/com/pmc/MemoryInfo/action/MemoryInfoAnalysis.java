package com.pmc.MemoryInfo.action;

import com.pmc.MemoryInfo.service.MemoryInfoService;
import org.apache.struts2.ServletActionContext;
import org.json.JSONObject;

import java.io.PrintWriter;

/**
 * Created by luluteam on 2015/9/5.
 */
public class MemoryInfoAnalysis {
    private MemoryInfoService memoryInfoService = new MemoryInfoService();

    private String appId;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public void memoryAverageRatio() throws Exception {
        JSONObject jsonObject = memoryInfoService.getMemoryAverageRatio(getAppId());
        ServletActionContext.getResponse().addHeader("Access-Control-Allow-Origin", "*");
        ServletActionContext.getResponse().setCharacterEncoding("gb2312");
        PrintWriter writer = ServletActionContext.getResponse().getWriter();
        writer.print(jsonObject.toString());
    }
}
