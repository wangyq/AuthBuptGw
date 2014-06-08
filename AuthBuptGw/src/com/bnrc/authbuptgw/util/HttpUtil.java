package com.bnrc.authbuptgw.util;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;

/**
 * 
 * @author wang
 *
 */
public class HttpUtil {

	/**
	 * 
	 */
	static final int CONN_TIMEOUT = 2;   //Socket 连接超时, 单位秒
	
	static final int SOCKET_TIMEOUT = 2; //Socket 读/写超时，单位秒 
	/**
	 * 
	 * @param req
	 * @return
	 * @throws Exception
	 */
	protected static Socket sendInternal(HttpRequest req) throws Exception {
		Socket socket = null;
		URL url = new URL(req.getUrlString());
		int port = url.getPort() == -1 ? 80 : url.getPort();
		InetAddress addr = InetAddress.getByName(url.getHost());
		
		//socket = new Socket(InetAddress.getByName(url.getHost()), port);
		socket = new Socket();
		socket.connect(new InetSocketAddress(addr, port), CONN_TIMEOUT*1000);  //指定连接超时
		socket.setSoTimeout(SOCKET_TIMEOUT * 1000);   //指定读写超时
		
		OutputStream outStream = socket.getOutputStream();
		String reqStr = req.getRequestHeader();

		//Just for debug now!
		//System.out.println("HTTP REQUEST : \r" + reqStr );
		
		outStream.write(reqStr.getBytes()); // 写入HTTP 请求头部
		return socket;
	}

	/**
	 * Just send request and get response header only!
	 * @param req
	 * @return
	 */
	public static HttpResponse send(HttpRequest req) {
		Socket socket = null;
		HttpResponse response = new HttpResponse();

		try {
			socket = sendInternal(req);
			response.receiveHeader(socket.getInputStream());
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			 if( socket!=null ){
				try {
					socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return response;
	}
	
	/**
	 * Just send request and don't get any response!
	 */
	public static void sendSimple( HttpRequest req ){
		Socket socket = null;
		try {
			socket = sendInternal(req);			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			 if( socket!=null ){
				try {
					socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * send request and get response header and response content
	 * @param req
	 * @return
	 */
	public static HttpResponse sendAndGetContent(HttpRequest req){
		Socket socket = null;
		HttpResponse response = new HttpResponse();

		try {
			socket = sendInternal(req);
			response.receiveHeader(socket.getInputStream());
			response.receiveContent(socket.getInputStream());
			
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			 if( socket!=null ){
				try {
					socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return response;
	}
}



