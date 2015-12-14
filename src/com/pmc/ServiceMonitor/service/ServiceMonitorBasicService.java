package com.pmc.ServiceMonitor.service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import com.pmc.CommonDBManager.CommonDBManager;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by luluteam on 2015/8/22.
 */
public class ServiceMonitorBasicService {
    static final int PMC_OK = 1;
    public int saveServiceMonitorWithJSONStr(String jsonStr) throws Exception{
        DBCollection serviceMonitorCollection = CommonDBManager.MongoDBConnect("ServiceMonitor");
        DBObject dbObject = (DBObject) JSON.parse(jsonStr);
        serviceMonitorCollection.insert(dbObject);
        return PMC_OK;
    }

    public JSONObject loadServiceMonitorWithClassName(String jsonStr) throws Exception{
        DBCollection serviceMonitorCollection = CommonDBManager.MongoDBConnect("ServiceMonitor");
        DBObject dbObject = (DBObject) JSON.parse(jsonStr);
        DBObject queryObject = new BasicDBObject();
        queryObject.put("userName" ,dbObject.get("userName"));
        queryObject.put("className", dbObject.get("className"));
        DBCursor cursor = serviceMonitorCollection.find(queryObject);
        List<DBObject> dbList = cursor.toArray();
        JSONObject retObject = new JSONObject();
        JSONArray retArray = new JSONArray();
        for (DBObject object : dbList) {
            retArray.put(object);
        }
        retObject.put("data", retArray);
        return retObject;
    }
}
