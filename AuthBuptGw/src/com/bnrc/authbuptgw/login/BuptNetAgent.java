/**
 * 
 */
package com.bnrc.authbuptgw.login;

import com.bnrc.authbuptgw.AuthUtil;
import com.bnrc.authbuptgw.util.HttpRequest;
import com.bnrc.authbuptgw.util.HttpResponse;
import com.bnrc.authbuptgw.util.HttpUtil;

/**
 * @author wangyq
 *  用于北邮校园网的登录功能。
 */
public class BuptNetAgent  extends LoginEngine implements ILoginAgent{

	final static String URL_LOGIN = "http://gw.bupt.edu.cn";
	final static String URL_LOGOUT = "http://gw.bupt.edu.cn/F.htm";
	final static String URL_RELOGIN = "http://gw.bupt.edu.cn/a11.htm";
	
	/**
	 * 
	 * @param user
	 * @param passwd
	 */
	protected BuptNetAgent(){

	}
	/* 
	 * 生成登录请求发送到服务器:
	 *  curl --silent -d "DDDDD=$USERNAME&upass=$upass&R1=0&R2=1&para=00&0MKKey=123456"	 "$URL_LOGIN
	 * @see com.bnrc.authbuptgw.login.ILoginAgent#login()
	 */
	@Override
	public boolean login() {
		// TODO Auto-generated method stub
		HttpRequest request = new HttpRequest(URL_LOGIN, "POST");  //POST 方法
		String[] keys = new String[]{"DDDDD","upass", "R1", "R2", "para", "0MKKey"};
		String[] values = new String[]{username, genPassword(), "0","1", "00", "123456"};
		
		request.addParam(keys, values);  //添加参数
		request.addHeaderFieldDefault(); //默认报头

		//HttpResponse response = HttpUtil.sendAndGetContent(request);
		HttpResponse response = HttpUtil.send(request);
		
		//response.print();
		
		return "200".equals(response.getStatusCode());
	}

	/* (non-Javadoc)
	 * @see com.bnrc.authbuptgw.login.ILoginAgent#relogin()
	 */
	@Override
	public boolean relogin() {
		// TODO Auto-generated method stub
		HttpRequest request = new HttpRequest(URL_RELOGIN, "POST");  //POST 方法
		String[] keys = new String[]{"DDDDD","upass",  "AMKKey"};
		String[] values = new String[]{username, password, ""};
		
		request.addParam(keys, values);  //添加参数
		request.addHeaderFieldDefault(); //默认报头
		
		request.addHeaderFieldDefault();

		//HttpResponse response = HttpUtil.sendAndGetContent(request);
		HttpResponse response = HttpUtil.send(request);
		
		//response.print();
		
		return "200".equals(response.getStatusCode());
	}

	/* (non-Javadoc)
	 * @see com.bnrc.authbuptgw.login.ILoginAgent#logout()
	 */
	@Override
	public boolean logout() {
		// TODO Auto-generated method stub
		HttpRequest request = new HttpRequest(URL_LOGOUT, "GET");  //GET 方法

		request.addHeaderFieldDefault();

		//HttpResponse response = HttpUtil.sendAndGetContent(request);
		HttpResponse response = HttpUtil.send(request);
		
		//response.print();
		
		return "200".equals(response.getStatusCode());
	}

	protected String genPassword(){
		String s1 = "1" + password + "12345678";
		String res = "";
		try {
			res = AuthUtil.getMD5(s1);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return res + "123456781";
	}
}
