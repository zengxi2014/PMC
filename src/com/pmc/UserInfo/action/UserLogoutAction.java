package com.pmc.UserInfo.action;
import org.json.JSONObject;



import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.Enumeration;

/**
 * Created by luluteam on 2015/8/23.
 */
public class UserLogoutAction {
    public void execute() throws Exception {
        ServletActionContext.getContext().getSession().remove("USER_NAME");

        ServletActionContext.getResponse().addHeader("Access-Control-Allow-Origin", "*");

        JSONObject resultObject = new JSONObject();
        PrintWriter writer = ServletActionContext.getResponse().getWriter();

        resultObject.put("result", "OK");
        writer.print(resultObject.toString());

    }
}
