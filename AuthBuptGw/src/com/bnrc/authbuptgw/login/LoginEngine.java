package com.bnrc.authbuptgw.login;

public class LoginEngine {
	/**
	 * 用户名
	 */
	protected String username;
	
	/**
	 * 密码
	 */
	String password;
	
	/**
	 * 生成登录代理进行各种登录操作。
	 * @param user
	 * @param passwd
	 * @param ssid
	 * @return
	 */
	public static ILoginAgent getLoginAgent(String user, String passwd, String ssid){
		if( ssid.equals("BUPT-1") ) {
			return new Bupt1Agent(user,passwd);
		} else if( ssid.equals("BUPT-2") ) {
			return new Bupt2Agent(user,passwd);
		} else if( ssid.equals("BUPT-3") ) {
			return new Bupt3Agent(user,passwd);
		} else if( (ssid == null) || (ssid.length()==0) ){
			return null;
		}
		return new BuptNetAgent(user, passwd);
	}
}
