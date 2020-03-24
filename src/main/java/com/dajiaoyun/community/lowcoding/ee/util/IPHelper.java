package com.dajiaoyun.community.lowcoding.ee.util;

import javax.servlet.http.HttpServletRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

public class IPHelper {
	public static String getIpAddr(HttpServletRequest request) throws Exception {  
	    String ip = request.getHeader("x-forwarded-for");  
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("Proxy-Client-IP");  
	    }  
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("WL-Proxy-Client-IP");  
	    }  
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getRemoteAddr();  
	    }  
	    if(ip.equals("127.0.0.1")||ip.contains("192.")){
	    	ip=getIP();
	    }
	    return ip;  
	} 
	public static String getRequestPath(HttpServletRequest req) {
		String reqFullURL=req.getRequestURL().toString();//http://127.0.0.1:8080/mall/synctaobaoorder.action
		String path=reqFullURL.substring(0,reqFullURL.lastIndexOf("/"));
		return path;
	}
	
	public static boolean ipIsReachable(String hostname) throws IOException{
		InetAddress ia = InetAddress.getByName (hostname);
		return ia.isReachable(5000);
	}
	public static String getIP() throws Exception {
		URL whatismyip= new URL("http://checkip.amazonaws.com");
		BufferedReader in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
		String ip = in.readLine();
		in.close();
		return ip;
	}
	public static void main(String[] args) {
		try {
			System.out.println(ipIsReachable("demo.20518dgd.com"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}

