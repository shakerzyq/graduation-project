package com.example.demo.util;

import java.util.HashMap;
import java.util.Map;
import http.HttpInvoker;
import org.springframework.stereotype.Component;

@Component
public class ApiClient_HmacMD5 {
	private  HttpInvoker httpInvoker; 
	//验证方式 apicode或appkey 默认apicode
	private String authoration = "apicode";
	
	//测试api地址
	private String testUrl = "https://api.yonyoucloud.com/apis/dst/APILinkHashEncode/HmacMD5";
	//请求方法类型
	private String methodType = "POST";
	//线程池参数文件路
	private static final String propertyUrl = "src/main/resources/HttpClient.properties";
	
	public ApiClient_HmacMD5() throws Exception{
		httpInvoker = new HttpInvoker(propertyUrl);
	}
	
	public String getMD5(String message){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("pwd", "123");
		params.put("text", message);
	
		Map<String,String> header = new HashMap<String,String>();
		header.put("authoration", authoration);
		if(authoration.equals("apicode"))
		{//验证方式为apicode时需要添加apicode
			header.put("apicode","a3557f20f98d4a6cb63cf45163d482dc");
		}else
		{//验证方式为appkey时需要添加appkey值和appsecret
			header.put("appkey","******");
			header.put("appsecret","******");
		}
		header.put("Content-Type", "application/json");

		String result = httpInvoker.invoker(testUrl, params, methodType, header);
		System.out.println("结果："+result);
		return result;
	}
	
	//关闭线程
	public void destoy(){
		httpInvoker.destoy();
	}
	public String makeMD5(String message){
		ApiClient_HmacMD5 apiClient;
		String md5Code=null;
		try {
			apiClient = new ApiClient_HmacMD5();
			md5Code = apiClient.getMD5(message);
			apiClient.destoy();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return md5Code;
	}
}