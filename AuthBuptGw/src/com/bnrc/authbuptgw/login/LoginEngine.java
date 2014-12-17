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
	 * 
	 */
	protected static String[] wlanNames = new String[]{"BUPT-1","BUPT-2","BUPT-3"};
	
	/**
	 * 判断当前是否学校的无线LAN接入点 
	 * @param ssid
	 * @return
	 */
	public static boolean isWLanLogin(String ssid){
		boolean bOk = false;
		
		for( String str: wlanNames){
			if( ssid.startsWith(str) ){
				bOk = true;
				break;
			}
		}
		
		return bOk;
	}
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
		
		if( isWLanLogin(ssid) ) {//学校的无线LAN接入点
			return wlAgent;
		} else if( (ssid == null) || (ssid.length()==0) ){
			return null;
		}
		return netAgent;  //默认的, 有线接入点或者自定义的接入点
	}
}
