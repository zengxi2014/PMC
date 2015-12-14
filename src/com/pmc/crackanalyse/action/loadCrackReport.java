package com.pmc.crackanalyse.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.List;
import scut.zengxi.CrashAnalyze.CrashAnalysis;

import com.pmc.crackanalyse.model.CrackReport;
import com.pmc.crackanalyse.service.CrackReportBasicService;
import org.json.JSONObject;
import org.apache.struts2.ServletActionContext;

public class loadCrackReport {
	private CrackReportBasicService crackReportBasicService = new CrackReportBasicService();
	private CrashAnalysis crashAnalysis = new CrashAnalysis();

	public String hashCode;

	public String appId;

	public String getHashCode() {
		return hashCode;
	}

	public void setHashCode(String hashCode) {
		this.hashCode = hashCode;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public void loadCrackReportById() throws UnknownHostException, ParseException{

		System.out.println("~~~~~~");
		CrackReport report =  crackReportBasicService.getCrackReportById("549a35ac9e48edc06770f6a3");
		if(report==null){
			System.out.println("找不到该数据");
		}else{
		System.out.println(report.getCrackInfo());
		}
	}

	public void loadCrashTypeReportById() throws Exception {
		JSONObject resultObject = crackReportBasicService.findAllCrashTypeReportByAppId(getAppId());
		ServletActionContext.getResponse().addHeader("Access-Control-Allow-Origin", "*");
		ServletActionContext.getResponse().setCharacterEncoding("gb2312");
		PrintWriter writer = ServletActionContext.getResponse().getWriter();
		writer.print(resultObject.toString());
	}

	public void loadCrashReportAnalysis() throws Exception {
		JSONObject resultObject = crashAnalysis.getCrashAnalysis(getAppId(), getHashCode());
		ServletActionContext.getResponse().addHeader("Access-Control-Allow-Origin", "*");
		ServletActionContext.getResponse().setCharacterEncoding("gb2312");
		PrintWriter writer = ServletActionContext.getResponse().getWriter();
		writer.print(resultObject.toString());
	}

	public void loadCrashTypeReportByHashCode() throws IOException {
		JSONObject obj = crackReportBasicService.findAllCrashTypeReportByHashCode(getHashCode());
		PrintWriter out = ServletActionContext.getResponse().getWriter();
		out.print(obj.toString());
	}
	
	public void loadAllCrackReport()throws UnknownHostException, ParseException{
//		List<CrackReport> resultlist = crackReportBasicService.loadCrackReport();
//		for(CrackReport report : resultlist){
//			System.out.println("-------------------------------------");
//			System.out.println(report.getId());
//			System.out.println(report.getCrackTime().toString());
//			System.out.println(report.getCrackType());
//			System.out.println(report.getCrackInfo());
//			System.out.println("-------------------------------------");
//		}
		System.out.println("Hello, world");
		System.out.println(getHashCode());
	}
}
