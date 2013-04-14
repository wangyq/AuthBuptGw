package com.bnrc.authbuptgw.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class HttpResponse {
	/**
	 * 响应头部域
	 */
	Map<String, String> headFieldsMap = new HashMap<String, String>();

	/**
	 * Status-Line = HTTP-Version SP Status-Code SP Reason-Phrase CRLF
	 */
	String StatusLine;

	/**
	 * Status-Line = HTTP-Version SP Status-Code SP Reason-Phrase CRLF
	 */
	String HttpVersion;

	/**
	 * Status-Line = HTTP-Version SP Status-Code SP Reason-Phrase CRLF
	 */
	String statusCode;
	/**
	 * Status-Line = HTTP-Version SP Status-Code SP Reason-Phrase CRLF
	 */
	String ReasonPhrase;

	/**
	 * HTTP的内容
	 */
	String content;

	/**
	 * 返回响应的第一行: Status-Line
	 * 
	 * @return
	 */
	public String getStatusLine() {
		return StatusLine;
	}

	public String getStatusCode(){
		return statusCode;
	}
	
	public Map<String, String> getHeaderFields() {
		return headFieldsMap;
	}

	/**
	 * 返回头部字段为name的值. 如果不存在, 返回null
	 * 
	 * @param name
	 *            - 头部域的名
	 * @return
	 */
	public String getHeaderField(String name) {
		return headFieldsMap.get(name);
	}

	/**
	 * 
	 * @param in
	 */
	public void receiveHeader(InputStream in) {
		try {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));

			String line = bufferedReader.readLine(); // HTTP/1.1 200 OK, HTTP/1.1 302 Moved Temporarily
			if (line.indexOf("HTTP") == 0) {
				StatusLine = line;
				HttpVersion = line.substring(0,8);
				statusCode = line.substring(9, 12);
				ReasonPhrase = line.substring(13,line.length());
			}
			// read header
			while (null != (line = bufferedReader.readLine())) {
				if (line.length() == 0)
					break; // header end
				int mid = line.indexOf(':');
				if (mid != -1) {
					String name = line.substring(0, mid);
					String value = line.substring(mid + 1, line.length());
					headFieldsMap.put(name, value);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/**
	 * Before call this function, You MUST call receiveHeader() first
	 * 
	 * @param in
	 */
	public void receiveContent(InputStream in) {
		try {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
			StringBuffer sbBuffer = new StringBuffer();
			String line = "";
			// read header
			while (null != (line = bufferedReader.readLine())) {
				sbBuffer.append(line);
			}
			content = sbBuffer.toString();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void printHeader(){
		System.out.println(StatusLine);
        
        Set<String> keys = headFieldsMap.keySet();
        Iterator<String> it = keys.iterator();
        while(it.hasNext()){
        	String name = it.next();
        	String value = headFieldsMap.get(name);
        	System.out.println(name + ": " + value);
        }
	}
	public void print() {
		printHeader();
		System.out.println(content);
	}
}
