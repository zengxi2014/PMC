package scut.zengxi.CrashAnalyze;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;


import org.json.*;
import org.junit.Test;
import com.pmc.crackanalyse.service.*;
/**
 * Created by zengxi on 2015/8/8.
 */
public class CrashAnalysis {
    /**
     * 获取最近七日的崩溃次数
     * @param hashCode
     * @return
     * @throws ParseException
     * @throws UnknownHostException
     */
    private CrackReportBasicService crackReportBasicService = new CrackReportBasicService();
    public JSONObject getRecentCrashTrend(String appId,String hashCode) throws ParseException, UnknownHostException {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        JSONObject recentCrashTrend = new JSONObject();
        JSONArray recent7Days = new JSONArray();
        JSONArray recent7DaysCrashTimes = new JSONArray();
        try {
            Date today = df.parse(df.format(new Date()));

            for (int i=6;i>=0;i--){
                Calendar c = Calendar.getInstance();
                c.setTime(today);
                c.add(c.DATE, -1*i);
                Date temp_date = c.getTime();
                recent7Days.put(df.format(temp_date));
                recent7DaysCrashTimes.put(0);
            }
            recentCrashTrend.put("recent7Days",recent7Days);
            recentCrashTrend.put("recent7DaysCrashTimes",recent7DaysCrashTimes);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        JSONObject crashReport = crackReportBasicService.findCrashReportByHashCode(appId,hashCode);
        JSONArray data = (JSONArray)crashReport.get("data");
        int len = data.length();
        for (int i =0;i<len;i++){
            JSONObject crashInstance = (JSONObject) JSONObject.wrap(data.get(i));
            String CrashTime = (String) crashInstance.get("CrackTime");
            CrashTime =df.format(df.parse(CrashTime));
            for (int j=0;j<recent7Days.length();j++){
                if(recent7Days.getString(j).equals(CrashTime)){
                    recent7DaysCrashTimes.put(j,recent7DaysCrashTimes.getInt(j)+1);
                }
            }
        }
        return recentCrashTrend;
    }


    /**
     * 获取异常栈信息
     * @param hashCode
     * @return
     * @throws ParseException
     * @throws UnknownHostException
     */
    public JSONObject getCrashStackInfo(String appId,String hashCode) throws ParseException, UnknownHostException {
        JSONObject CrackStackInfo = new JSONObject();
        JSONObject crashReport =  crackReportBasicService.findCrashReportByHashCode(appId,hashCode);
        JSONArray data = (JSONArray)crashReport.get("data");
        if(data.length()!=0){
            JSONObject crashInstance = (JSONObject) JSONObject.wrap(data.get(0));
            CrackStackInfo.put("CrackStackInfo",crashInstance.getString("CrackStackInfo"));
        }
        return CrackStackInfo;
    }


    /**
     * 获取app 版本统计信息
     * @param hashCode
     * @return
     * @throws ParseException
     * @throws UnknownHostException
     */
    public JSONObject getAppVersion(String appId, String hashCode) throws ParseException, UnknownHostException {
        JSONObject appVersion = new JSONObject();
        JSONObject crashReport =  crackReportBasicService.findCrashReportByHashCode(appId,hashCode);
        JSONArray versionType = new JSONArray();
        JSONArray versionInfo = new JSONArray();
        JSONArray data = (JSONArray)crashReport.get("data");
        for(int i=0;i<data.length();i++){
            JSONObject Crash_Device_Info = (JSONObject) ((JSONObject) JSONObject.wrap(data.get(i))).get("Crash_Device_Info");
            String VersionMain = Crash_Device_Info.getString("VersionMain");
            boolean flag =false;
            for(int j=0;j<versionType.length();j++){
                if(versionType.get(j).toString().equals(VersionMain)){
                    flag=true;
                    break;
                }
            }
            if(!flag){
                versionType.put(VersionMain);
            }
        }
        for(int i=0;i<versionType.length();i++){
            JSONObject valueInstance = new JSONObject();
            valueInstance.put("value",0);
            valueInstance.put("name",versionType.get(i).toString());
            versionInfo.put(valueInstance);
        }
        for(int i=0;i<data.length();i++){
            JSONObject Crash_Device_Info = (JSONObject) ((JSONObject) JSONObject.wrap(data.get(i))).get("Crash_Device_Info");
            String VersionMain = Crash_Device_Info.getString("VersionMain");
            int index = 0;
            for(int j=0;j<versionType.length();j++){
                if(versionType.get(j).toString().equals(VersionMain)){
                    index = j;
                    break;
                }
            }
            JSONObject valueInstance = (JSONObject) versionInfo.get(index);
            valueInstance.put("value",valueInstance.getInt("value")+1);
            versionInfo.put(index,valueInstance);
        }
        appVersion.put("versionType", versionType);
        appVersion.put("versionInfo", versionInfo);
        return appVersion;
    }


    /**
     * 获取设备类型统计信息
     * @param hashCode
     * @return
     * @throws ParseException
     * @throws UnknownHostException
     */
    public JSONObject getManufacturerInfo(String appId,String hashCode) throws ParseException, UnknownHostException {
        JSONObject Manufacturer = new JSONObject();
        JSONObject crashReport =  crackReportBasicService.findCrashReportByHashCode(appId,hashCode);
        JSONArray ManufacturerType = new JSONArray();
        JSONArray ManufacturerInfo = new JSONArray();
        JSONArray data = (JSONArray)crashReport.get("data");
        for(int i=0;i<data.length();i++){
            JSONObject Crash_Device_Info = (JSONObject) ((JSONObject) JSONObject.wrap(data.get(i))).get("Crash_Device_Info");
            String ManufacturerAttr = Crash_Device_Info.getString("Manufacturer");
            boolean flag =false;
            for(int j=0;j<ManufacturerType.length();j++){
                if(ManufacturerType.get(j).toString().equals(ManufacturerAttr)){
                    flag=true;
                    break;
                }
            }
            if(!flag){
                ManufacturerType.put(ManufacturerAttr);
            }
        }
        for(int i=0;i<ManufacturerType.length();i++){
            JSONObject valueInstance = new JSONObject();
            valueInstance.put("value",0);
            valueInstance.put("name",ManufacturerType.get(i).toString());
            ManufacturerInfo.put(valueInstance);
        }
        for(int i=0;i<data.length();i++){
            JSONObject Crash_Device_Info = (JSONObject) ((JSONObject) JSONObject.wrap(data.get(i))).get("Crash_Device_Info");
            String VersionMain = Crash_Device_Info.getString("Manufacturer");
            int index = 0;
            for(int j=0;j<ManufacturerType.length();j++){
                if(ManufacturerType.get(j).toString().equals(VersionMain)){
                    index = j;
                    break;
                }
            }
            JSONObject valueInstance = (JSONObject) ManufacturerInfo.get(index);
            valueInstance.put("value",valueInstance.getInt("value")+1);
            ManufacturerInfo.put(index,valueInstance);
        }
        Manufacturer.put("ManufacturerType",ManufacturerType);
        Manufacturer.put("ManufacturerInfo",ManufacturerInfo);
        return Manufacturer;
    }


    /**
     * 获取操作系统类型统计信息
     * @param hashCode
     * @return
     * @throws ParseException
     * @throws UnknownHostException
     */
    public JSONObject getVersionReleaseInfo(String appId,String hashCode) throws ParseException, UnknownHostException {
        JSONObject VersionRelease = new JSONObject();
        JSONObject crashReport =  crackReportBasicService.findCrashReportByHashCode(appId,hashCode);
        JSONArray VersionReleaseType = new JSONArray();
        JSONArray VersionReleaseInfo = new JSONArray();
        JSONArray data = (JSONArray)crashReport.get("data");
        for(int i=0;i<data.length();i++){
            JSONObject Crash_Device_Info = (JSONObject) ((JSONObject) JSONObject.wrap(data.get(i))).get("Crash_Device_Info");
            String VersionReleaseAttr = Crash_Device_Info.getString("VersionRelease");
            boolean flag =false;
            for(int j=0;j<VersionReleaseType.length();j++){
                if(VersionReleaseType.get(j).toString().equals(VersionReleaseAttr)){
                    flag=true;
                    break;
                }
            }
            if(!flag){
                VersionReleaseType.put(VersionReleaseAttr);
            }
        }
        for(int i=0;i<VersionReleaseType.length();i++){
            JSONObject valueInstance = new JSONObject();
            valueInstance.put("value",0);
            valueInstance.put("name",VersionReleaseType.get(i).toString());
            VersionReleaseInfo.put(valueInstance);
        }
        for(int i=0;i<data.length();i++){
            JSONObject Crash_Device_Info = (JSONObject) ((JSONObject) JSONObject.wrap(data.get(i))).get("Crash_Device_Info");
            String VersionMain = Crash_Device_Info.getString("VersionRelease");
            int index = 0;
            for(int j=0;j<VersionReleaseType.length();j++){
                if(VersionReleaseType.get(j).toString().equals(VersionMain)){
                    index = j;
                    break;
                }
            }
            JSONObject valueInstance = (JSONObject) VersionReleaseInfo.get(index);
            valueInstance.put("value",valueInstance.getInt("value")+1);
            VersionReleaseInfo.put(index,valueInstance);
        }
        VersionRelease.put("VersionReleaseType", VersionReleaseType);
        VersionRelease.put("VersionReleaseInfo", VersionReleaseInfo);
        return VersionRelease;
    }


    /**
     * 获取CPU占用率统计信息
     * @param hashCode
     * @return
     * @throws ParseException
     * @throws UnknownHostException
     */
    public JSONObject getCPUusedInfo(String appId,String hashCode) throws ParseException, UnknownHostException {
        JSONObject CPUUsageRate = new JSONObject();
        JSONArray CPUUsageLevel = new JSONArray();
        JSONArray CPUUsageLevelValue = new JSONArray();
        JSONObject crashReport =  crackReportBasicService.findCrashReportByHashCode(appId,hashCode);
        JSONArray data = (JSONArray)crashReport.get("data");
        int levelNum =16;
        float step = (float) (100.0/levelNum);
        for (int i=0;i<levelNum;i++){
            CPUUsageLevel.put(i*step+"%-"+(i+1)*step+"%");
            CPUUsageLevelValue.put(0);
        }
        CPUUsageRate.put("CPUUsageLevel",CPUUsageLevel);
        CPUUsageRate.put("CPUUsageLevelValue",CPUUsageLevelValue);
        for(int i=0;i<data.length();i++){
            JSONObject Crash_Performance_Info = (JSONObject) ((JSONObject) JSONObject.wrap(data.get(i))).get("Crash_Performance_Info");
            String CPUUsage = Crash_Performance_Info.getString("Process Used CPU").substring(0,Crash_Performance_Info.getString("Process Used CPU").length()-1);
            int index = (int) (Double.parseDouble(CPUUsage)/step);
            CPUUsageLevelValue.put(index,(int)CPUUsageLevelValue.get(index)+1);
        }
        for (int i=0;i<CPUUsageLevelValue.length();i++){
            CPUUsageLevelValue.put(i,(int)CPUUsageLevelValue.get(i)*100.0/data.length());
        }
        return CPUUsageRate;
    }


    /**
     * 获取内存统计信息
     * @param hashCode
     * @return
     * @throws ParseException
     * @throws UnknownHostException
     */
    public JSONObject getMemoryUsedInfo(String appId,String hashCode) throws ParseException, UnknownHostException {
        JSONObject MemoryUsageRate = new JSONObject();
        JSONArray MemoryUsageLevel = new JSONArray();
        JSONArray MemoryUsageLevelValue = new JSONArray();
        JSONObject crashReport =  crackReportBasicService.findCrashReportByHashCode(appId,hashCode);
        JSONArray data = (JSONArray)crashReport.get("data");
        int levelNum =16;
        int step = 256/levelNum;
        for (int i=0;i<levelNum-1;i++){
            MemoryUsageLevel.put(i*step+"MB-"+(i+1)*step+"MB");
            MemoryUsageLevelValue.put(0);
        }
        MemoryUsageLevel.put("above 240MB");
        MemoryUsageLevelValue.put(0);
        MemoryUsageRate.put("MemoryUsageLevel",MemoryUsageLevel);
        MemoryUsageRate.put("MemoryUsageLevelValue",MemoryUsageLevelValue);
        for(int i=0;i<data.length();i++){
            JSONObject Crash_Performance_Info = (JSONObject) ((JSONObject) JSONObject.wrap(data.get(i))).get("Crash_Performance_Info");
            String MemoryUsage = Crash_Performance_Info.getString("Process Used Memory").substring(0,Crash_Performance_Info.getString("Process Used Memory").length()-2);
            int index = (int) (Double.parseDouble(MemoryUsage)/step);
            MemoryUsageLevelValue.put(index,(int)MemoryUsageLevelValue.get(index)+1);
        }
        return MemoryUsageRate;
    }


    /**
     * 获取崩溃分析的所有统计信息
     * @param hashCode
     * @return
     * @throws ParseException
     * @throws UnknownHostException
     */
    public JSONObject getCrashAnalysis(String appId, String hashCode) throws ParseException, UnknownHostException {
        JSONObject CrashAnalysisObject = new JSONObject();
        CrashAnalysisObject.put("recentCrashTrend",getRecentCrashTrend(appId,hashCode));
        CrashAnalysisObject.put("CrackStackInfo",getCrashStackInfo(appId,hashCode));
        CrashAnalysisObject.put("appVersion",getAppVersion(appId,hashCode));
        CrashAnalysisObject.put("CPUUsageRate",getCPUusedInfo(appId,hashCode));
        CrashAnalysisObject.put("MemoryUsageRate",getMemoryUsedInfo(appId,hashCode));
        CrashAnalysisObject.put("Manufacturer",getManufacturerInfo(appId,hashCode));
        CrashAnalysisObject.put("VersionRelease",getVersionReleaseInfo(appId,hashCode));
        System.out.println(CrashAnalysisObject);
        return CrashAnalysisObject;
    }
    @Test
    public void testGetCrashAnalysis() throws ParseException, UnknownHostException {
        System.out.println("the crash analysis is:");
        getCrashAnalysis("85d4a553-ee8d-4136-80ab-2469adcae44d","-834561363");
    }
}
