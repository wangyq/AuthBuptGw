package com.bnrc.authbuptgw;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

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

	/**
	 * 生成登录请求发送到服务器 curl --silent -d
	 * "DDDDD=$USERNAME&upass=$upass&R1=0&R2=0&para=00&0MKKey=123456"
	 * "$URL_LOGIN"`
	 * 
	 * @param user
	 * @param passwd
	 * @return
	 */
	public static boolean login(String strUrl, String user, String passwd) {
		boolean bOK = false;
		// Error check!
		if ((strUrl.length() == 0) || (user.length() == 0) || (passwd.length() == 0))
			return bOK;
		Map<String, String> params = new HashMap<String, String>();
		params.put("DDDDD", user);
		params.put("upass", genPasswd(passwd));
		params.put("R1", "0");
		params.put("R2", "0");
		params.put("para", "00");
		params.put("0MKKey", "123456");

		try {
			HttpURLConnection conn = (HttpURLConnection) HttpRequestUtil.sendPostRequest(strUrl, params, null);
			int code = conn.getResponseCode();
			bOK = (code == 200);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return bOK;

	}

	/**
	 * 生成登出请求发送到服务器
	 * 
	 * @param user
	 * @param passwd
	 * @return
	 */
	public static boolean logout(String strUrl, String user, String passwd) {
		boolean bOK = false;
		if ((strUrl.length() == 0) || (user.length() == 0) || (passwd.length() == 0))
			return bOK;
		try {
			HttpURLConnection conn = (HttpURLConnection) HttpRequestUtil.sendGetRequest(strUrl, null, null);
			int code = conn.getResponseCode();
			bOK = (code == 200);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return bOK;

	}

	/**
	 * 连接url测试网络连通性
	 * 
	 * @param strUrl
	 * @return
	 */
	public static boolean checkUrl(String strUrl) {
		boolean bOK = false;
		if ((strUrl.length() == 0))
			return bOK;
		try {
			HttpURLConnection conn = (HttpURLConnection) HttpRequestUtil.sendGetRequest(strUrl, null, null);
			int code = conn.getResponseCode();
			bOK = (code == 200);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return bOK;
	}

	/**
	 * 检查Wifi网络是否可用
	 * 
	 * @param activitiy
	 * @return true-Wifi网络可用, false-Wifi网络不可用
	 */
	public static boolean isWifiEnable(Activity activitiy) {
		
		boolean bOK = false;

		WifiManager wm = (WifiManager) activitiy.getSystemService(Context.WIFI_SERVICE);
		bOK = (wm.getWifiState() == WifiManager.WIFI_STATE_ENABLED );
		
//		WifiInfo wifiInfo = mWifiManager.getConnectionInfo();
//		int ipAddress = (wifiInfo == null ? 0 : wifiInfo.getIpAddress());
//		if ( mWifiManager.isWifiEnabled() && ipAddress != 0 ) {
//			// System.out.println("**** WIFI is on");
//			bOK = true;
//		} else {
//			// System.out.println("**** WIFI is off");
//
//		}
		
		return bOK;
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
		return getString(m);
	}

	/**
	 * 将字节转换为字符串
	 * 
	 * @param b
	 * @return
	 */
	private static String getString(byte[] b) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			sb.append(b[i]);
		}
		return sb.toString();
	}
}
