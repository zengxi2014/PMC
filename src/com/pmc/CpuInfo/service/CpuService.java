package com.pmc.CpuInfo.service;

import com.mongodb.*;
import com.mongodb.util.JSON;
import com.pmc.CommonDBManager.CommonDBManager;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import java.net.UnknownHostException;

/**
 * Created by luluteam on 2015/6/17.
 */
public class CpuService {
    public WriteResult savCPUInfoByJSON(String JSONStr) throws UnknownHostException {
        // TODO Auto-generated method stub

        DBCollection activityRouteInfoCollection = CommonDBManager.MongoDBConnect("CpuInfo");
        DBObject dbObecjt = (DBObject) JSON.parse(JSONStr);
        WriteResult result =  activityRouteInfoCollection.insert(dbObecjt);
        return result;
    }

    public JSONObject getCPUAverageRatio(String appId) throws Exception {
        DBCollection cpuInfoCollection = CommonDBManager.MongoDBConnect("CpuInfo");



        BasicDBObject key = new BasicDBObject();
        key.put("appVersion", true);
        BasicDBObject condition = new BasicDBObject();
        condition.put("appId", appId);
        BasicDBObject initial = new BasicDBObject();
        BasicDBList totalRatio = new BasicDBList();
        for (int i = 0; i < 6; i++)
            totalRatio.add(0);
        initial.put("values", totalRatio);
        String reduceString = "function(obj, prev){for(var i = 0; i < obj.data.length; i++){prev.values[i] += obj.data[i]}}";
        DBObject dbo = cpuInfoCollection.group(key, condition, initial, reduceString);
        JSONArray groupList = new JSONArray(dbo.toString());

        for (int i = 0; i < groupList.length(); i++) {
            //String appVersion = groupList.get("appVersion");
            JSONObject groupObject = (JSONObject)groupList.get(i);
            String appVersion = (String)groupObject.get("appVersion");
            DBObject queryObject = new BasicDBObject();
            queryObject.put("appVersion", appVersion);
            queryObject.put("appId", appId);
            DBCursor cursor = cpuInfoCollection.find(queryObject);
            int size = cursor.size();
            JSONArray list = (JSONArray)groupObject.get("values");
            JSONArray nameArray = new JSONArray();
            for (int j = 0; j < 6; j++) {
                list.put(j,(double)list.get(j)/size);
                nameArray.put(j, (new Integer(j + 1)).toString());
            }
            groupObject.put("names", nameArray);
        }

        JSONObject retObject = new JSONObject();
        retObject.put("data", groupList);
        System.out.println(retObject);
        return retObject;
    }

    @Test
    public void testCPUAverageRato() throws Exception {
        getCPUAverageRatio("85d4a553-ee8d-4136-80ab-2469adcae44d-abcdefg");
    }
}
