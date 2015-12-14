package com.pmc.UserInfo.service;

import com.mongodb.*;
import com.mongodb.util.JSON;
import com.pmc.CommonDBManager.CommonDBManager;
import org.json.*;

import java.util.UUID;
import java.net.UnknownHostException;
import java.util.List;

/**
 * Created by luluteam on 2015/8/16.
 */
public class UserInfoBasicService {
    static final int PMC_REGISTERFAIL = -1;
    static final int PMC_LOGINFAIL = -1;
    static final int PMC_OK = 0;
    static final int PMC_FAIL = -1;
    public int userRegisterInfoByJSON(String userRegisterInfo) throws UnknownHostException {
        // TODO Auto-generated method stub
        DBCollection activityRouteInfoCollection = CommonDBManager.MongoDBConnect("UserInfo");
        DBObject dbObecjt = (DBObject)JSON.parse(userRegisterInfo);
        DBObject queryObject = new BasicDBObject();
        queryObject.put("userName",(String)dbObecjt.get("userName"));
        DBCursor cursor = activityRouteInfoCollection.find(queryObject);
        List<DBObject> resultList = cursor.toArray();
        if (resultList.size() == 0) {
            activityRouteInfoCollection.insert(dbObecjt);
            return PMC_OK;
        } else {
            System.out.println("FAIL");
            return PMC_REGISTERFAIL;
        }
    }

    public int userLoginInfo(String userLoginInfo) throws UnknownHostException {
        DBCollection activityRouteInfoCollection = CommonDBManager.MongoDBConnect("UserInfo");
        DBObject dbObecjt = (DBObject)JSON.parse(userLoginInfo);
        DBObject queryObject = new BasicDBObject();
        queryObject.put("userName", (String)dbObecjt.get("userName"));
        queryObject.put("userPassword", (String)dbObecjt.get("userPassword"));
        DBCursor cursor = activityRouteInfoCollection.find(queryObject);
        List<DBObject> resultList = cursor.toArray();
        if (resultList.size()==0) {
            return PMC_LOGINFAIL;
        } else {
            return PMC_OK;
        }
    }

    public JSONObject addAppInfo(String appInfoStr) throws Exception{
        DBCollection appInfoCollection = CommonDBManager.MongoDBConnect("AppInfo");
        DBObject dbObecjt = (DBObject) JSON.parse(appInfoStr);
        DBObject queryObject = new BasicDBObject();
        queryObject.put("userName", dbObecjt.get("userName"));
        queryObject.put("appName", dbObecjt.get("appName"));
        DBCursor cursor = appInfoCollection.find(queryObject);
        List<DBObject> resultList = cursor.toArray();

        JSONObject retObject = new JSONObject();


        if (resultList.size() > 0 ) {
            retObject.put("ret", PMC_FAIL);
        } else {
            DBObject insertObject = dbObecjt;
            UUID uuid = UUID.randomUUID();
            insertObject.put("appId", uuid);
            appInfoCollection.insert(insertObject);
            retObject.put("ret",(Integer)PMC_OK);
            retObject.put("appId", uuid.toString());
        }
        return retObject;
    }

    public JSONObject appList(String appInfoStr) throws Exception {
        DBCollection appInfoCollection = CommonDBManager.MongoDBConnect("AppInfo");
        DBObject dbObject = (DBObject) JSON.parse(appInfoStr);
        DBObject queryObject = new BasicDBObject();
        queryObject.put("userName", dbObject.get("userName"));

        DBCursor cursor = appInfoCollection.find(queryObject);
        List<DBObject> resultList = cursor.toArray();
        JSONObject retObject = new JSONObject();
        JSONArray dataArray = new JSONArray();
        for (DBObject listObject : resultList) {
            JSONObject appObject = new JSONObject();
            appObject.put("userName", listObject.get("userName").toString());
            appObject.put("appName", listObject.get("appName").toString());
            appObject.put("appId", listObject.get("appId").toString());
            dataArray.put(appObject);
        }
        retObject.put("data", dataArray);
        return retObject;
    }
}
