package com.pmc.ClassTrace.action;

import com.mongodb.util.JSON;
import com.pmc.ClassTrace.service.ClassTraceBasicService;
import com.pmc.utils.PMCJSONUtils;
import org.apache.struts2.ServletActionContext;
import org.json.JSONObject;

import javax.print.attribute.standard.Severity;
import java.io.PrintWriter;

/**
 * Created by luluteam on 2015/8/22.
 */
public class ClassTraceAction {
    private ClassTraceBasicService classTraceBasicService = new ClassTraceBasicService();
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void receiveClassTrace() throws Exception {
        String jsonStr = PMCJSONUtils.readJSONStringFromHttpRequest(ServletActionContext.getRequest());
        JSONObject object = new JSONObject(jsonStr);
        System.out.println("jsonStr:" + jsonStr);
        if(jsonStr == null)
            return;
        System.out.println(jsonStr);
        classTraceBasicService.saveClassTraceWithJSONStr(jsonStr);
    }

    public void loadClassTrace() throws Exception {
//        String jsonStr = PMCJSONUtils.readJSONStringFromHttpRequest(ServletActionContext.getRequest());
//        JSONObject object = new JSONObject(jsonStr);
//        System.out.println("jsonStr:" + jsonStr);
//        if(jsonStr == null)
//            return;
        JSONObject userInfo = new JSONObject();
        userInfo.put("userName", getUserName());
       JSONObject retObject =  classTraceBasicService.loadAllClassTrace(userInfo.toString());
        ServletActionContext.getResponse().addHeader("Access-Control-Allow-Origin", "*");
        PrintWriter writer = ServletActionContext.getResponse().getWriter();
        writer.print(retObject.toString());
    }
}
