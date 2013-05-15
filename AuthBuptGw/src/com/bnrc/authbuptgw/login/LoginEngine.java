package com.bnrc.authbuptgw.login;

public class LoginEngine {
	/**
	 * 用户名
	 */
	protected static String username;
	
	/**
	 * 密码
	 */
	protected static String password;
	
	/**
	 * 
	 */
	protected static BuptWLanAgent wlAgent = new BuptWLanAgent();
	
	/**
	 * 
	 */
	protected static BuptNetAgent netAgent = new BuptNetAgent();
	
	/**
	 * 生成登录代理进行各种登录操作。
	 * @param user
	 * @param passwd
	 * @param ssid
	 * @return
	 */
	public static ILoginAgent getLoginAgent(String user, String passwd, String ssid){
		username = user;
		password = passwd;
		
		if( ssid.startsWith("BUPT-")) {
			return wlAgent;
		} else if( (ssid == null) || (ssid.length()==0) ){
			return null;
		}
		return netAgent;
	}
}
