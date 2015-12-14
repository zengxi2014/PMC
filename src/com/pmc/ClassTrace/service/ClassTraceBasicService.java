package com.pmc.ClassTrace.service;


import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import com.pmc.CommonDBManager.CommonDBManager;
import org.json.*;
import org.json.JSONArray;

import java.util.List;

/**
 * Created by luluteam on 2015/8/22.
 */
public class ClassTraceBasicService {
    static final int PMC_OK = 1;
   public int saveClassTraceWithJSONStr(String jsonStr) throws Exception{
       DBCollection classTraceCollection = CommonDBManager.MongoDBConnect("ClassTrace");
       DBObject dbObject = (DBObject) JSON.parse(jsonStr);
       classTraceCollection.insert(dbObject);
       return PMC_OK;
   }

    public JSONObject loadAllClassTrace(String jsonStr) throws Exception {
        DBCollection classTraceCollection = CommonDBManager.MongoDBConnect("ClassTrace");
        DBObject dbObject = (DBObject)JSON.parse(jsonStr);
        DBObject queryObject = new BasicDBObject();
        queryObject.put("userName" ,dbObject.get("userName"));
        DBCursor cursor = classTraceCollection.find(queryObject);
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
