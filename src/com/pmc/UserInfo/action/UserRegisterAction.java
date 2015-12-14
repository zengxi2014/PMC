package com.pmc.UserInfo.action;

import com.pmc.UserInfo.service.UserInfoBasicService;
import com.pmc.utils.PMCJSONUtils;
import org.apache.struts2.ServletActionContext;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by luluteam on 2015/8/16.
 */
public class UserRegisterAction {
    private UserInfoBasicService userInfoBasicService = new UserInfoBasicService();
    static final int PMC_REGISTERFAIL = -1;
    static final int PMC_OK = 0;

    private String userName;
    private String userPassword;
    private String email;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void execute()throws JSONException, IOException {
//        String jsonStr = PMCJSONUtils.readJSONStringFromHttpRequest(ServletActionContext.getRequest());
//        System.out.println("jsonString:" + jsonStr);
//        JSONObject object = new JSONObject(jsonStr);
//        if(jsonStr == null)
//            return;
        JSONObject userRegisterInfo = new JSONObject();
        userRegisterInfo.put("userName",getUserName());
        userRegisterInfo.put("userPassword", getUserPassword());
        userRegisterInfo.put("email", getEmail());
        System.out.println(userRegisterInfo.toString());
        int restultStatus = userInfoBasicService.userRegisterInfoByJSON(userRegisterInfo.toString());
        System.out.println("result:" + restultStatus);
        PrintWriter writer = ServletActionContext.getResponse().getWriter();
        JSONObject resultJSON = new JSONObject();
        ServletActionContext.getResponse().addHeader("Access-Control-Allow-Origin", "*");
        if (restultStatus == PMC_REGISTERFAIL) {
            resultJSON.put("result", "FAIL");
            writer.write(resultJSON.toString());
        } else if (restultStatus==PMC_OK) {
            resultJSON.put("result", "OK");
            writer.write(resultJSON.toString());
        }
        // WriteResult result = activityInfoBasicService.saveActivityInfoByJSON(jsonStr);
    }
}
