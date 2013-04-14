package com.bnrc.authbuptgw;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * 响应对象
 */
public class HttpResponser {
 
	Map<String, String> headFieldsMap = new HashMap<String, String>();
	
	String urlString;
 
	int defaultPort;
 
	String file;
 
	String host;
 
	String path;
 
	int port;
 
	String protocol;
 
	String query;
 
	String ref;
 
	String userInfo;
 
	String contentEncoding;
 
	String content;
 
	String contentType;
 
	int code;
 
	String message;
 
	String method;
 
	int connectTimeout;
 
	int readTimeout;
 
	Vector<String> contentCollection;
 
	public Map<String, String> getHeaderFields(){
		return headFieldsMap;
	}
	
	/**
	 * 返回头部字段为name的值. 如果不存在, 返回null
	 * @param name - 头部域的名
	 * @return 
	 */
	public String getHeaderField(String name){
		return headFieldsMap.get(name);
	}
	
	public String getContent() {
		return content;
	}
 
	public String getContentType() {
		return contentType;
	}
 
	public int getResponseCode() {
		return code;
	}
 
	public String getMessage() {
		return message;
	}
 
	public Vector<String> getContentCollection() {
		return contentCollection;
	}
 
	public String getContentEncoding() {
		return contentEncoding;
	}
 
	public String getMethod() {
		return method;
	}
 
	public int getConnectTimeout() {
		return connectTimeout;
	}
 
	public int getReadTimeout() {
		return readTimeout;
	}
 
	public String getUrlString() {
		return urlString;
	}
 
	public int getDefaultPort() {
		return defaultPort;
	}
 
	public String getFile() {
		return file;
	}
 
	public String getHost() {
		return host;
	}
 
	public String getPath() {
		return path;
	}
 
	public int getPort() {
		return port;
	}
 
	public String getProtocol() {
		return protocol;
	}
 
	public String getQuery() {
		return query;
	}
 
	public String getRef() {
		return ref;
	}
 
	public String getUserInfo() {
		return userInfo;
	}
 
}
