package com.pmc.LoginInfo;

import com.mongodb.*;
import com.mongodb.util.JSON;
import com.pmc.CommonDBManager.CommonDBManager;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import java.net.UnknownHostException;
import java.util.List;

/**
 * Created by luluteam on 2015/7/8.
 */
public class LoginInfoService {
    public WriteResult saveLoginInfoByJSON(String JSONStr) throws UnknownHostException {
        // TODO Auto-generated method stub

        DBCollection activityRouteInfoCollection = CommonDBManager.MongoDBConnect("LoginInfo");
        DBObject dbObecjt = (DBObject) JSON.parse(JSONStr);
        WriteResult result =  activityRouteInfoCollection.insert(dbObecjt);

        return result;
    }

    public JSONObject getLoginInfo(String appId) throws Exception {
        DBCollection loginInfoCollcetion = CommonDBManager.MongoDBConnect("LoginInfo");

        DBObject query = new BasicDBObject();
        query.put("appId", appId);
        DBObject result = loginInfoCollcetion.findOne(query);
        String appName = (String)result.get("appName");

        BasicDBObject key = new BasicDBObject();
        key.put("date", true);
        BasicDBObject condition = new BasicDBObject();
        condition.put("appId", appId);
        BasicDBObject initial = new BasicDBObject();
        int count = 0;
        initial.put("value", count);
        String reduceString = "function(obj, prev){prev.value += 1}";
        DBObject dbo = loginInfoCollcetion.group(key, condition, initial, reduceString);
        JSONArray groupList = new JSONArray(dbo.toString());

        JSONArray namesArray = new JSONArray();
        JSONArray valuesArray = new JSONArray();
        for (int i = 0; i < groupList.length(); i++) {
            JSONObject obj = (JSONObject)groupList.get(i);
            namesArray.put(obj.get("date"));
            valuesArray.put(obj.get("value"));
        }
        JSONObject retObject = new JSONObject();
        JSONArray dataArray = new JSONArray();
        JSONObject dataObject = new JSONObject();
        retObject.put("appName", appName);
        dataObject.put("appVersion","所有版本信息");
        dataObject.put("names", namesArray);
        dataObject.put("values",valuesArray);
        dataArray.put(dataObject);
        retObject.put("data", dataArray);
        return retObject;
    }

    @Test
    public void testGetLoginInfo() throws Exception {
        getLoginInfo("85d4a553-ee8d-4136-80ab-2469adcae44d-abcdefg");
    }
}
