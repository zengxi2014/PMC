package com.pmc.UserInfo.action;

import com.mongodb.util.JSON;
import com.pmc.UserInfo.service.UserInfoBasicService;
import com.pmc.utils.PMCJSONUtils;
import org.apache.struts2.ServletActionContext;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * Created by luluteam on 2015/8/16.
 */
public class appInfo {

    static final int PMC_OK = 1;

        private String userName;
        private String appName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    private UserInfoBasicService userInfoBasicService = new UserInfoBasicService();
        public void addApp() throws Exception {
            JSONObject appInfo = new JSONObject();
            appInfo.put("userName", getUserName());
            appInfo.put("appName", getAppName());

            JSONObject retObject = userInfoBasicService.addAppInfo(appInfo.toString());

            PrintWriter writer = ServletActionContext.getResponse().getWriter();
            ServletActionContext.getResponse().addHeader("Access-Control-Allow-Origin", "*");
            writer.print(retObject.toString());
        }


        public void appList() throws Exception {
            JSONObject appListInfo = new JSONObject();
            appListInfo.put("userName", getUserName());

            JSONObject retObject = userInfoBasicService.appList(appListInfo.toString());

            PrintWriter writer = ServletActionContext.getResponse().getWriter();
            ServletActionContext.getResponse().addHeader("Access-Control-Allow-Origin", "*");
            writer.print(retObject.toString());

        }
}
