package com.pmc.ActivityInfo.service;

import com.mongodb.*;
import com.mongodb.util.JSON;
import com.pmc.CommonDBManager.CommonDBManager;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.net.UnknownHostException;
import java.util.List;

/**
 * Created by luluteam on 2015/7/25.
 */
public class ActivityInfoBasicService {

    public WriteResult saveActivityInfoByJSON(String JSONStr) throws UnknownHostException {
        // TODO Auto-generated method stub
        DBCollection activityRouteInfoCollection = CommonDBManager.MongoDBConnect("ActivityInfo");
        DBObject dbObecjt = (DBObject) JSON.parse(JSONStr);
        WriteResult result =  activityRouteInfoCollection.insert(dbObecjt);
        return result;
    }
    public JSONObject loadActivityAverageUseTime(String appId) throws Exception {
        DBCollection activityRouteInfoCollection = CommonDBManager.MongoDBConnect("ActivityInfo");

        BasicDBObject key = new BasicDBObject();
        key.put("appVersion", true);
        BasicDBObject condition = new BasicDBObject();
        condition.put("appId", appId);
        BasicDBObject initial = new BasicDBObject();
        initial.put("names", new BasicDBList());
        String reduceString = "function(obj, prev){" +
                "if(prev.names.indexOf(obj.name)==-1)"+
                "prev.names.push(obj.name)"+
                "}";
        DBObject dbo = activityRouteInfoCollection.group(key, condition, initial, reduceString);
        JSONArray groupList = new JSONArray(dbo.toString());
        for (int i = 0; i < groupList.length(); i++) {
            JSONObject groupObject = (JSONObject)groupList.get(i);
            String appVersionStr = (String)groupObject.get("appVersion");
            JSONArray namesList = (JSONArray)groupObject.get("names");
            JSONArray useTimeArray = new JSONArray();
            for (int j = 0; j < namesList.length(); j++) {
                DBObject useTimeQuery = new BasicDBObject();
                useTimeQuery.put("appId", appId);
                useTimeQuery.put("appVersion", appVersionStr);
                useTimeQuery.put("name", (String)namesList.get(j));
                DBCursor useTimeCursor = activityRouteInfoCollection.find(useTimeQuery);
                List<DBObject> useTimeList = useTimeCursor.toArray();
                double totalUseTime = 0;
                for (DBObject useTimeObject : useTimeList) {
                    totalUseTime += (int) useTimeObject.get("useTime");
                }
                useTimeArray.put(totalUseTime/useTimeList.size());
            }
            groupObject.put("values", useTimeArray);
        }
        JSONObject resultObject = new JSONObject();
        DBObject queryName = new BasicDBObject();
        queryName.put("appId", appId);
        String appName = (String)activityRouteInfoCollection.findOne(queryName).get("appName");
        resultObject.put("appName", appName);
        resultObject.put("data", groupList);
        return resultObject;
    }

    public JSONObject loadActivityVisitTimes(String appId) throws Exception {
        DBCollection activityRouteInfoCollection = CommonDBManager.MongoDBConnect("ActivityInfo");

        BasicDBObject key = new BasicDBObject();
        key.put("appVersion", true);
        BasicDBObject condition = new BasicDBObject();
        condition.put("appId", appId);
        BasicDBObject initial = new BasicDBObject();
        initial.put("names", new BasicDBList());
        initial.put("values", new BasicDBList());
        String reduceString = "function(obj, prev){" +
                "if(prev.names.indexOf(obj.name)==-1)"+
                "{prev.names.push(obj.name);prev.values.push(1)"+
                "} else {var index = prev.names.indexOf(obj.name); prev.values[index] += 1}}";
        DBObject dbo = activityRouteInfoCollection.group(key, condition, initial, reduceString);
        JSONArray groupList = new JSONArray(dbo.toString());

        JSONObject resultObject = new JSONObject();
        DBObject queryName = new BasicDBObject();
        queryName.put("appId", appId);
        String appName = (String)activityRouteInfoCollection.findOne(queryName).get("appName");
        resultObject.put("appName", appName);
        resultObject.put("data", groupList);
        System.out.println(resultObject.toString());
        return resultObject;
    }

    public JSONObject loadActivityAverageLoadTime(String appId) throws Exception {
        DBCollection activityRouteInfoCollection = CommonDBManager.MongoDBConnect("ActivityInfo");

        BasicDBObject key = new BasicDBObject();
        key.put("appVersion", true);
        BasicDBObject condition = new BasicDBObject();
        condition.put("appId", appId);
        BasicDBObject initial = new BasicDBObject();
        initial.put("names", new BasicDBList());

        String reduceString = "function(obj, prev){" +
                "if(prev.names.indexOf(obj.name)==-1)"+
                "prev.names.push(obj.name)"+
                "}";
        DBObject dbo = activityRouteInfoCollection.group(key, condition, initial, reduceString);
        JSONArray groupList = new JSONArray(dbo.toString());


        for (int i = 0; i < groupList.length(); i++) {
            JSONObject groupObject = (JSONObject)groupList.get(i);
            String appVersionStr = (String)groupObject.get("appVersion");
            JSONArray namesList = (JSONArray)groupObject.get("names");
            JSONArray useTimeArray = new JSONArray();
            for (int j = 0; j < namesList.length(); j++) {
                DBObject useTimeQuery = new BasicDBObject();
                useTimeQuery.put("appId", appId);
                useTimeQuery.put("appVersion", appVersionStr);
                useTimeQuery.put("name", (String)namesList.get(j));
                DBCursor useTimeCursor = activityRouteInfoCollection.find(useTimeQuery);
                List<DBObject> useTimeList = useTimeCursor.toArray();
                double totalUseTime = 0;
                for (DBObject useTimeObject : useTimeList) {
                    totalUseTime += (int) useTimeObject.get("loadTime");
                  //  System.out.println((int) useTimeObject.get("loadTime"));
                }
//                System.out.println("total:\n"+totalUseTime);
//                System.out.println("size:"+useTimeList.size());
                useTimeArray.put(totalUseTime / useTimeList.size());
            }
            groupObject.put("values", useTimeArray);
        }

        System.out.println(groupList.toString());

        JSONObject retObject = new JSONObject();
        retObject.put("data", groupList);
        return retObject;
    }

    @Test
    public void testloadActivityAverageUseTime() throws Exception {
        //loadActivityAverageUseTime("85d4a553-ee8d-4136-80ab-2469adcae44d");
        loadActivityAverageLoadTime("85d4a553-ee8d-4136-80ab-2469adcae44d");
    }
}
