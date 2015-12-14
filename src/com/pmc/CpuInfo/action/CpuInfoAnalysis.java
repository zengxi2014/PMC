package com.pmc.CpuInfo.action;

import com.pmc.CpuInfo.service.CpuService;
import org.apache.struts2.ServletActionContext;
import org.json.JSONObject;

import java.io.PrintWriter;

/**
 * Created by luluteam on 2015/9/5.
 */
public class CpuInfoAnalysis {
    private CpuService cpuService = new CpuService();

    private  String appId;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public void cpuAveragetRatio() throws Exception {
        JSONObject jsonObject = cpuService.getCPUAverageRatio(getAppId());
        ServletActionContext.getResponse().addHeader("Access-Control-Allow-Origin", "*");
        ServletActionContext.getResponse().setCharacterEncoding("utf-8");
        PrintWriter writer = ServletActionContext.getResponse().getWriter();
        writer.print(jsonObject.toString());
    }

//    public void
}
