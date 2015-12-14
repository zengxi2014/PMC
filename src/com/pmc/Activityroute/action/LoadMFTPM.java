package com.pmc.Activityroute.action;

import org.json.*;
import org.apache.struts2.ServletActionContext;
import scut.zengxi.FPSTree.MFTPM;
import scut.zengxi.Funnel.Funnel;

import java.io.PrintWriter;

/**
 * Created by luluteam on 2015/9/3.
 */
public class LoadMFTPM {
    private Funnel funnel = new Funnel();

    String appId;
    String minSup;
    String criticalPath;

    public String getCriticalPath() {
        return criticalPath;
    }

    public void setCriticalPath(String criticalPath) {
        this.criticalPath = criticalPath;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getMinSup() {
        return minSup;
    }

    public void setMinSup(String minSup) {
        this.minSup = minSup;
    }

    public void loadMFTPM() throws Exception {
        System.out.println("appId:" + getAppId() + "\n" + "minSup:" + getMinSup());
        JSONArray resultArray = MFTPM.getMFSResults(getAppId(), getMinSup());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data", resultArray);
        ServletActionContext.getResponse().addHeader("Access-Control-Allow-Origin", "*");
        ServletActionContext.getResponse().setCharacterEncoding("gb2312");
        PrintWriter writer = ServletActionContext.getResponse().getWriter();
        writer.print(jsonObject.toString());
    }

    public void loadFunnel() throws Exception {
//        org.json.JSONArray jsonArray = Funnel.
        Funnel funnel = new Funnel();
        org.json.JSONObject jsonObject = funnel.getFunnelResult(getAppId(), getCriticalPath());
        PrintWriter writer = ServletActionContext.getResponse().getWriter();
        ServletActionContext.getResponse().addHeader("Access-Control-Allow-Origin", "*");
        ServletActionContext.getResponse().setCharacterEncoding("gb2312");

        writer.print(jsonObject.toString());
    }
}
