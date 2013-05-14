/**
 * 
 */
package com.bnrc.authbuptgw.login;

/**
 * @author wangyq
 *
 */
public interface ILoginAgent {

	/**
	 * 进行登录功能
	 * @return
	 */
	boolean login();
	
	/**
	 * 断开并重新登录
	 * @return
	 */
	boolean relogin();
	
	/**
	 * 注销功能
	 * @return
	 */
	boolean logout();
}
