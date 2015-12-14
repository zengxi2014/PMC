package com.pmc.RegistInfo.service;

import com.mongodb.*;
import com.mongodb.util.JSON;
import com.pmc.CommonDBManager.CommonDBManager;
import com.pmc.utils.MongoDBManage.SpringMongoConfig;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;

import java.net.UnknownHostException;
import java.util.List;

/**
 * Created by luluteam on 2015/6/24.
 */
public class RegistInfoBasicService {
   public boolean checkIfDeviceIdExist(String deviceId){
        ApplicationContext ac = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
        MongoOperations mo = (MongoOperations)ac.getBean("mongoTemplate");
        DBCollection collection =  mo.getCollection("RegistInfo");
        DBObject queryObj = new BasicDBObject();
        queryObj.put("deviceId",deviceId);
        DBCursor cursor =  collection.find(queryObj);
        List<DBObject> list = cursor.toArray();
       if (list.size() > 0 ){
           return true;
       }else
           return false;
    }

    public WriteResult saveRegistInfoByJSON(String JSONStr) throws UnknownHostException {
        // TODO Auto-generated method stub

        DBCollection activityRouteInfoCollection = CommonDBManager.MongoDBConnect("RegistInfo");
        DBObject dbObecjt = (DBObject) JSON.parse(JSONStr);
        WriteResult result =  activityRouteInfoCollection.insert(dbObecjt);
        return result;
    }

    public JSONObject getRegistInfo(String appId) throws Exception {
        DBCollection activityRouteInfoCollection = CommonDBManager.MongoDBConnect("RegistInfo");

        DBObject queryObj = new BasicDBObject();
        queryObj.put("appId", appId);
        DBObject oneObject = activityRouteInfoCollection.findOne(queryObj);
        String appName = (String)oneObject.get("appName");

        BasicDBObject key = new BasicDBObject();
        key.put("date", true);
        BasicDBObject condition = new BasicDBObject();
        condition.put("appId", appId);
        BasicDBObject initial = new BasicDBObject();
        int count = 0;
        initial.put("value", count);
        String reduceString = "function(obj, prev){prev.value += 1}";
        DBObject dbo = activityRouteInfoCollection.group(key, condition, initial, reduceString);
        JSONArray groupList = new JSONArray(dbo.toString());
        System.out.println(groupList.toString());

        JSONArray namesArray = new JSONArray();
        JSONArray valuesArray = new JSONArray();
        for (int i = 0; i < groupList.length(); i++) {
            JSONObject obj = (JSONObject)groupList.get(i);
            namesArray.put(obj.get("date"));
            valuesArray.put(obj.get("value"));
        }
        JSONObject retObject = new JSONObject();
        retObject.put("appName", appName);
        JSONArray dataArray = new JSONArray();
        JSONObject dataObject = new JSONObject();
        dataObject.put("appVersion", "所有版本信息");
        dataObject.put("names", namesArray);
        dataObject.put("values", valuesArray);
        dataArray.put(dataObject);
        retObject.put("data", dataArray);


        return retObject;
    }

    public JSONObject getWayInfo(String appId) throws Exception {
        DBCollection activityRouteInfoCollection = CommonDBManager.MongoDBConnect("RegistInfo");

        DBObject queryObj = new BasicDBObject();
        queryObj.put("appId", appId);
        DBObject oneObject = activityRouteInfoCollection.findOne(queryObj);
        String appName = (String)oneObject.get("appName");

        BasicDBObject key = new BasicDBObject();
        key.put("way", true);
        BasicDBObject condition = new BasicDBObject();
        condition.put("appId", appId);
        BasicDBObject initial = new BasicDBObject();
        int count = 0;
        initial.put("value", count);
        String reduceString = "function(obj, prev){prev.value += 1}";
        DBObject dbo = activityRouteInfoCollection.group(key, condition, initial, reduceString);
        JSONArray groupList = new JSONArray(dbo.toString());
        JSONArray namesArray = new JSONArray();
        JSONArray valueArray = new JSONArray();
        for (int i = 0; i < groupList.length(); i++) {
            JSONObject groupObject = (JSONObject)groupList.get(i);
            namesArray.put(groupObject.get("way"));
            JSONObject obj = new JSONObject();
            obj.put("value", groupObject.get("value"));
            obj.put("name", groupObject.get("way"));
            valueArray.put(obj);
        }
        JSONObject resultObject = new JSONObject();
        resultObject.put("appName", appName);
        resultObject.put("names", namesArray);
        resultObject.put("data", valueArray);
        return resultObject;
    }

    @Test
    public void testGetLoginInfo() throws Exception {
        getWayInfo("85d4a553-ee8d-4136-80ab-2469adcae44d-abcdefg");
    }
}
