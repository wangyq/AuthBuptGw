package com.bnrc.authbuptgw;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 自动登录工具类
 * @author wangyq
 *
 */
public class AuthUtil {
	String user = "";
	String passwd = "";

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	/**
	 * 返回加密的字符串
	 * @return
	 */
	protected String genPasswd(){
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
