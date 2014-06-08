package com.bnrc.authbuptgw.util;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class HttpRequest {
	public static final String METHOD_GET = "GET";

	public static final String METHOD_POST = "POST";

	private String defaultContentEncoding;

	private String url;
	private boolean isGet = false;
	private boolean isPost = false;

	HashMap<String, String> params = new HashMap<String, String>();
	HashMap<String, String> headRequest = new HashMap<String, String>();

	/**
	 * 
	 * @param strUrl
	 */
	public HttpRequest(String strUrl, String method) throws InvalidParameterException {
		this.defaultContentEncoding = Charset.defaultCharset().name();  //in Android , it is UTF8
		this.url = strUrl;
		if (method.equalsIgnoreCase(METHOD_GET)) {
			this.isGet = true;
		} else if (method.equalsIgnoreCase(METHOD_POST)) {
			this.isPost = true;
		} else {
			throw new InvalidParameterException("Invalid method parameter : " + method);
		}
	}

	public String getRequestHeader() throws Exception {
		URL Url = new URL(url);

		StringBuffer reqStr = new StringBuffer();

		String urlPath = Url.getPath();
		if (urlPath.length() == 0) {
			urlPath = "/";
		}
		String strParam = getParams();
		String strHeadField = getHeaderFields();

		if (isGet) {// GET情况,参数放在HTTP路径部分，形如/add?name=aaa&age=10
			if (strParam.length() > 0) {
				reqStr.append("GET " + urlPath + "?" + strParam + " HTTP/1.1\r\n");
			} else {
				reqStr.append("GET " + urlPath + " HTTP/1.1\r\n");
			}
			reqStr.append("Host: " + Url.getHost());
			if (Url.getPort() != -1) {
				reqStr.append(":" + Url.getPort());
			}
			reqStr.append("\r\n");

			reqStr.append(strHeadField);

		} else if (isPost) { // POST情况, 参数放在HTTP请求体，形如name=aaa&age=10
			reqStr.append("POST " + urlPath + " HTTP/1.1\r\n");
			reqStr.append("Host: " + Url.getHost());
			if (Url.getPort() != -1) {
				reqStr.append(":" + Url.getPort());
			}
			reqStr.append("\r\n");
			
			if( headRequest.get("Content-Type") == null ){
				reqStr.append("Content-Type: application/x-www-form-urlencoded\r\n");  //添加内容类型
			}
			
			reqStr.append("Content-Length: " + strParam.getBytes().length + "\r\n");
			reqStr.append(strHeadField);

			reqStr.append(strParam);
		}
		//System.out.println(reqStr.toString());
		return reqStr.toString();
	}

	/**
	 * 
	 * @return
	 */
	public String getUrlString() {
		return url;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isGet() {
		return isGet;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isPost() {
		return isPost;
	}

	/**
	 * 添加参数
	 * 
	 * @param name
	 * @param value
	 * @return
	 */
	public boolean addParam(String name, String value) {
		name = name.trim();
		if (name.length() == 0 ) {
			return false;
		}
		value = value.trim();
		if( value == null ){
			value = "";
		}
		params.put(name, value);
		return true;
	}
	/**
	 * 添加参数
	 * 
	 * @param names
	 * @param values
	 * @return
	 */
	public boolean addParam(String[] names, String[] values) {
		if (names.length == 0 || (values.length == 0)) {
			return false;
		}
		if( names.length != values.length ) return false;
		for(int i=0; i<names.length;i++){
			addParam(names[i], values[i]);
		}
		return true;
	}
	public boolean addParam(Map<String, String> p ){
		if( p != null ){
			params.putAll(p);
		}
		return true;
	}
	/**
	 * 获得请求的字符串, 例如 name=micke&age=12
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String getParams() throws UnsupportedEncodingException {
		StringBuffer sBuffer = new StringBuffer();
		Set<String> keys = params.keySet();
		Iterator<String> it = keys.iterator();

		while (it.hasNext()) {
			String name = it.next();
			String value = params.get(name);
			if( value.length() > 0 ){
				sBuffer.append(name + "=" + URLEncoder.encode(value, defaultContentEncoding) + "&");
			}
			else{
				sBuffer.append(name + "=" +  "&");
			}
		}
		if( sBuffer.length()>0) {
			sBuffer.deleteCharAt(sBuffer.length() - 1);
		}
		return sBuffer.toString();
	}

	/**
	 * 获取报文头部属性字符串, 不包含"Host: xxx" , "Content-Length: xxx", 和第一行请求行
	 * 
	 * @return
	 */
	public String getHeaderFields() {
		StringBuffer sBuffer = new StringBuffer();
		Set<String> keys = headRequest.keySet();
		Iterator<String> it = keys.iterator();

		while (it.hasNext()) {
			String name = it.next();
			String value = headRequest.get(name);
			sBuffer.append(name + ": " + value + "\r\n");
		}
		sBuffer.append("\r\n");

		return sBuffer.toString();

	}

	/**
	 * 添加报文头部属性
	 * 
	 * @param nameAndValue
	 * @return
	 */
	public boolean addHeaderField(String nameAndValue) {
		if (nameAndValue.length() == 0)
			return false;
		int mid = nameAndValue.indexOf(':');
		if (mid == -1)
			return false;
		String name = nameAndValue.substring(0, mid);
		String value = nameAndValue.substring(mid + 1, nameAndValue.length());
		return addHeaderField(name, value);
	}

	/**
	 * 添加报文头部属性
	 * 
	 * @param name
	 * @param value
	 * @return
	 */
	public boolean addHeaderField(String name, String value) {
		if (name.length() == 0 || (value.length() == 0)) {
			return false;
		}
		headRequest.put(name.trim(), value.trim());
		return true;

	}
	
	/**
	 * 添加默认的头部域。
	 * @return
	 */
	public void addHeaderFieldDefault(){
		addHeaderField("Cache-Control: no-cache");
		addHeaderField("Connection: keep-alive");
		addHeaderField("Accept-Language: zh-CN");
		addHeaderField("Accept-Charset: GBK,utf-8");
	}
}
