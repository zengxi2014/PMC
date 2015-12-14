package com.pmc.fragmentInfo.service;

import java.net.UnknownHostException;
import java.util.List;

import com.mongodb.*;
import com.mongodb.util.JSON;
import com.pmc.CommonDBManager.CommonDBManager;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

public class FragmentInfoBasicService {
	public WriteResult saveFragmentInfoByJSON(String JSONStr) throws UnknownHostException {
		// TODO Auto-generated method stub
		
		DBCollection fragmentInfoCollection = CommonDBManager.MongoDBConnect("FragmentInfo");
		DBObject dbObecjt = (DBObject)JSON.parse(JSONStr);
		WriteResult result =   fragmentInfoCollection.insert(dbObecjt);
		
		return result;
	}

	public JSONObject loadFragmentAverageUseTime(String appId) throws Exception {
		DBCollection activityRouteInfoCollection = CommonDBManager.MongoDBConnect("FragmentInfo");

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

	public JSONObject loadFragmentVisitTimes(String appId) throws Exception {
		DBCollection activityRouteInfoCollection = CommonDBManager.MongoDBConnect("FragmentInfo");

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
		return resultObject;
	}
	@Test
	public void testLoadFragmentVisitTime() throws Exception {
		loadFragmentVisitTimes("85d4a553-ee8d-4136-80ab-2469adcae44d");
	}

}


