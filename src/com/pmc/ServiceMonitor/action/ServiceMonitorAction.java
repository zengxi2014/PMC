package com.pmc.ServiceMonitor.action;


import com.pmc.ServiceMonitor.service.ServiceMonitorBasicService;
import com.pmc.utils.PMCJSONUtils;
import org.apache.struts2.ServletActionContext;
import org.json.JSONObject;

import java.io.PrintWriter;

/**
 * Created by luluteam on 2015/8/22.
 */
public class ServiceMonitorAction {
    private ServiceMonitorBasicService serviceMonitorBasicService = new ServiceMonitorBasicService();

    private String userName;
    private String className;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void receiveServiceMonitor() throws Exception {
        String jsonStr = PMCJSONUtils.readJSONStringFromHttpRequest(ServletActionContext.getRequest());
        JSONObject object = new JSONObject(jsonStr);
        System.out.println("jsonStr:" + jsonStr);
        if(jsonStr == null)
            return;
        serviceMonitorBasicService.saveServiceMonitorWithJSONStr(jsonStr);
    }

    public void loadServiceMonitor() throws Exception {

        JSONObject queryObject = new JSONObject();
        queryObject.put("userName", getUserName());
        queryObject.put("className", getClassName());
        JSONObject retObject =  serviceMonitorBasicService.loadServiceMonitorWithClassName(queryObject.toString());
        PrintWriter writer = ServletActionContext.getResponse().getWriter();
        ServletActionContext.getResponse().addHeader("Access-Control-Allow-Origin", "*");
        System.out.println("returnObject: " + retObject.toString());
        writer.print(retObject.toString());
    }
}
