package com.pmc.UserInfo.action;

import com.mongodb.WriteResult;
import com.pmc.UserInfo.service.UserInfoBasicService;
import com.pmc.utils.PMCJSONUtils;
import org.apache.struts2.ServletActionContext;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * Created by luluteam on 2015/8/16.
 */
public class UserLoginAction {
    private UserInfoBasicService userInfoBasicService = new UserInfoBasicService();
    static final int PMC_LOGINFAIL = -1;
    static final int PMC_OK = 0;

    private String userName;
    private String userPassword;

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

    public void execute()throws JSONException, IOException {
//        String jsonStr = PMCJSONUtils.readJSONStringFromHttpRequest(ServletActionContext.getRequest());
//        JSONObject object = new JSONObject(jsonStr);
//        if(jsonStr == null)
//            return;
        JSONObject userLoginInfo = new JSONObject();
        userLoginInfo.put("userName", getUserName());
        userLoginInfo.put("userPassword", getUserPassword());

        int resultStatus = userInfoBasicService.userLoginInfo(userLoginInfo.toString());

        PrintWriter writer = ServletActionContext.getResponse().getWriter();
        ServletActionContext.getResponse().addHeader("Access-Control-Allow-Origin", "*");
        JSONObject resultObject = new JSONObject();

        if (resultStatus == PMC_LOGINFAIL) {
            resultObject.put("result","FAIL");
            writer.print(resultObject.toString());
        } else {
            resultObject.put("result", "OK");
           //HttpServletSession session =  ServletActionContext.getActionContext(ServletActionContext.getRequest()).getSession();
            HttpSession session =  ServletActionContext.getRequest().getSession();
            session.setAttribute("USER_NAME", userLoginInfo.get("userName"));
            System.out.println(session.getAttribute("USER_NAME"));
            writer.print(resultObject.toString());
        }
    }
}
