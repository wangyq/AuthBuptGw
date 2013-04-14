package com.bnrc.authbuptgw;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import com.bnrc.authbuptgw.util.HttpRequest;
import com.bnrc.authbuptgw.util.HttpResponse;
import com.bnrc.authbuptgw.util.HttpUtil;
import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

/**
 * 自动登录工具类
 * 
 * @author wangyq
 * 
 */
public class AuthUtil {
	private static char md5Chars[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	/**
	 * 生成登录请求发送到服务器 curl --silent -d
	 * "DDDDD=$USERNAME&upass=$upass&R1=0&R2=1&para=00&0MKKey=123456"
	 * "$URL_LOGIN"`
	 * 
	 * @param user
	 * @param passwd
	 * @return
	 */
	public static boolean login(String strUrl, String user, String passwd) {
		if ((strUrl.length() == 0) || (user.length() == 0) || (passwd.length() == 0)) {
			return false;
		}
		
		HttpRequest request = new HttpRequest(strUrl, "POST");  //POST 方法
		
		request.addParam("DDDDD", user);
		request.addParam("upass", genPasswd(passwd));
		request.addParam("R1", "0");
		request.addParam("R2", "1");
		request.addParam("para", "00");
		request.addParam("0MKKey", "123456");
		
		request.addHeaderField("Cache-Control: no-cache");
		request.addHeaderField("Connection: keep-alive");
		request.addHeaderField("Accept-Language: zh-CN");
		request.addHeaderField("Accept-Charset: GBK,utf-8");

		//HttpResponse response = HttpUtil.sendAndGetContent(request);
		HttpResponse response = HttpUtil.send(request);
		
		//response.print();
		
		return "200".equals(response.getStatusCode());
	}

	// public static boolean login(String strUrl, String user, String passwd) {
	// boolean bOK = false;
	// HttpURLConnection conn = null;
	//
	// // Error check!
	// if ((strUrl.length() == 0) || (user.length() == 0) || (passwd.length() ==
	// 0)){
	// return bOK;
	// }
	//
	// Map<String, String> params = new HashMap<String, String>();
	// params.put("DDDDD", user);
	// params.put("upass", genPasswd(passwd));
	// params.put("R1", "0");
	// params.put("R2", "0");
	// params.put("para", "00");
	// params.put("0MKKey", "123456");
	//
	// try {
	// conn = (HttpURLConnection) HttpRequestUtil.sendPostRequest(strUrl,
	// params, null);
	// int code = conn.getResponseCode();
	// bOK = (code == 200);
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } finally {
	// conn.disconnect();
	// }
	//
	// return bOK;
	//
	// }

	/**
	 * 生成登出请求发送到服务器
	 * 
	 * @param user
	 * @param passwd
	 * @return
	 */
	public static boolean logout(String strUrl, String user, String passwd) {
		if ((strUrl.length() == 0) || (user.length() == 0) || (passwd.length() == 0)) {
			return false;
		}
		
		HttpRequest request = new HttpRequest(strUrl, "GET");  //GET 方法
		
		request.addHeaderField("Cache-Control: no-cache");
		request.addHeaderField("Connection: keep-alive");
		request.addHeaderField("Accept-Language: zh-CN");
		request.addHeaderField("Accept-Charset: GBK,utf-8");

		//HttpResponse response = HttpUtil.sendAndGetContent(request);
		HttpResponse response = HttpUtil.send(request);
		
		//response.print();
		
		return "200".equals(response.getStatusCode());
	}
	// public static boolean logout(String strUrl, String user, String passwd) {
	// boolean bOK = false;
	// HttpURLConnection conn = null;
	//
	// if ((strUrl.length() == 0) || (user.length() == 0) || (passwd.length() ==
	// 0))
	// return bOK;
	// try {
	// conn = (HttpURLConnection) HttpRequestUtil.sendGetRequest(strUrl, null,
	// null);
	// int code = conn.getResponseCode();
	// bOK = (code == 200);
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } finally{
	// conn.disconnect();
	// }
	//
	// return bOK;
	//
	// }

	/**
	 * 连接url, 根据返回的内容检查测试网络连通性 p = new
	 * ProcessBuilder("sh").redirectErrorStream(true).start();
	 * 
	 * DataOutputStream os = new DataOutputStream(p.getOutputStream());
	 * os.writeBytes("ping -c 10 " + host + '\n'); os.flush();
	 * 
	 * // Close the terminal os.writeBytes("exit\n"); os.flush();
	 * 
	 * // read ping replys BufferedReader reader = new BufferedReader(new
	 * InputStreamReader(p.getInputStream())); String line;
	 * 
	 * while ((line = reader.readLine()) != null) { System.out.println(line); }
	 * 
	 * @param strUrl
	 * @return
	 */
	public static boolean checkUrl(String strUrl, String content) {
		HttpRequest request = new HttpRequest(strUrl, "GET");  //GET 方法
		
		request.addHeaderField("Cache-Control: no-cache");
		request.addHeaderField("Connection: keep-alive");
		request.addHeaderField("Accept-Language: zh-CN");
		request.addHeaderField("Accept-Charset: GBK,utf-8");

		//HttpResponse response = HttpUtil.sendAndGetContent(request);
		HttpResponse response = HttpUtil.send(request);
		return "200".equals(response.getStatusCode());
	}

	// public static boolean checkUrl(String strUrl, String content) {
	// boolean bOK = false;
	//
	// if ((strUrl.length() == 0))
	// return bOK;
	// try {
	// HttpRequester request = new HttpRequester();
	// HttpResponser hr = request.sendGet(strUrl);
	// if( content.length() == 0 ){
	// bOK = (hr.getResponseCode()==HttpURLConnection.HTTP_OK);
	// } else {
	// bOK = (hr.getContent().indexOf(content) !=-1 );
	// }
	//
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } finally {
	//
	// }
	// return bOK;
	// }

	// public static boolean checkUrl(String strUrl, String content){
	// boolean bOK = false;
	// Socket socket = null;
	// StringBuffer req = new StringBuffer();
	// String code = "";
	// HashMap<String, String> heads = new HashMap<String, String>();
	//
	// try {
	// URL url = new URL(strUrl);
	// int port = url.getPort()==-1?80:url.getPort();
	// socket = new Socket(InetAddress.getByName(url.getHost()), port);
	// OutputStream outStream = socket.getOutputStream();
	//
	// if( url.getPath().length() ==0 ){//请求的第一行
	// req.append("GET / HTTP/1.1\r\n");
	// } else {
	// req.append("GET "+ url.getPath() + " HTTP/1.1\r\n");
	// }
	// req.append("Host: " + url.getHost() );
	// if( url.getPort() != -1 ){
	// req.append(":" + url.getPort() );
	// }
	// req.append("\r\n");
	// req.append("Connection: keep-alive\r\n");
	// req.append("Cache-Control: no-cache\r\n");
	//
	// req.append("Accept-Language: zh-CN\r\n");
	// req.append("Accept-Charset: GBK,utf-8\r\n");
	// req.append("\r\n\r\n");
	//
	// System.out.println(req.toString());
	//
	// outStream.write(req.toString().getBytes()); //写入HTTP 请求头部
	//
	// InputStream in = socket.getInputStream();
	// BufferedReader bufferedReader = new BufferedReader(new
	// InputStreamReader(in));
	//
	// String line = bufferedReader.readLine(); //HTTP/1.1 200 OK, HTTP/1.1 302
	// Moved Temporarily
	// if( line.indexOf("HTTP" ) ==0 ){
	// code = line.substring(9,12);
	// }
	// //read header
	// while( null != (line=bufferedReader.readLine()) ){
	// if( line.length() == 0 ) break; //header end
	// int mid = line.indexOf(':');
	// if( mid !=-1 ){
	// String name = line.substring(0,mid);
	// String value = line.substring(mid+1, line.length());
	// heads.put(name, value);
	// }
	// }
	// outStream.close();
	// bufferedReader.close();
	//
	// bOK = (code.equals("200"));
	//
	// } catch (Exception e) {
	// // TODO: handle exception
	// e.printStackTrace();
	// } finally {
	// if( socket!=null ){
	// try {
	// socket.close();
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
	// }
	// return bOK;
	// }

	/**
	 * 检查Wifi网络是否可用
	 * 
	 * @param activitiy
	 * @return true-Wifi网络可用, false-Wifi网络不可用
	 */
	public static boolean isWifiEnable(Activity activitiy) {

		boolean bOK = false;

		WifiManager wm = (WifiManager) activitiy.getSystemService(Context.WIFI_SERVICE);
		bOK = (wm.getWifiState() == WifiManager.WIFI_STATE_ENABLED);

		// WifiInfo wifiInfo = mWifiManager.getConnectionInfo();
		// int ipAddress = (wifiInfo == null ? 0 : wifiInfo.getIpAddress());
		// if ( mWifiManager.isWifiEnabled() && ipAddress != 0 ) {
		// // System.out.println("**** WIFI is on");
		// bOK = true;
		// } else {
		// // System.out.println("**** WIFI is off");
		//
		// }
		//return true;
		
		return bOK;
	}

	/**
	 * 开启关闭 Wifi功能
	 * @param activitiy
	 * @param isOn
	 */
	public static void changeWifiState(Activity activitiy,boolean isOn){
		WifiManager wm = (WifiManager) activitiy.getSystemService(Context.WIFI_SERVICE);
		wm.setWifiEnabled(isOn);
	}
	/**
	 * 返回加密的字符串
	 * 
	 * @return
	 */
	protected static String genPasswd(String passwd) {
		String s1 = "1" + passwd + "12345678";
		String res = "";
		try {
			res = getMD5(s1);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return res + "123456781";
	}

	/**
	 * 返回给定字符串的MD5值
	 * 
	 * @param val
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static String getMD5(String val) throws NoSuchAlgorithmException {

		MessageDigest md5 = MessageDigest.getInstance("MD5");
		md5.update(val.getBytes());
		byte[] m = md5.digest();// 加密

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < m.length; i++) {
			byte b = m[i];
			char c0 = md5Chars[(b & 0xf0) >> 4];
			char c1 = md5Chars[b & 0xf];
			sb.append(c0).append(c1);
		}

		return sb.toString();
	}

}
