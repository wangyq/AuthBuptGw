package com.bnrc.authbuptgw.login;

import com.bnrc.authbuptgw.AuthUtil;
import com.bnrc.authbuptgw.util.HttpRequest;
import com.bnrc.authbuptgw.util.HttpResponse;
import com.bnrc.authbuptgw.util.HttpUtil;

/**
 * 北邮无线热点的登录功能
 * @author wang
 *
 */
public class BuptWLanAgent extends LoginEngine implements ILoginAgent {

	final static String URL_LOGIN = "http://10.4.1.2";
	final static String URL_LOGOUT = "http://10.4.1.2/F.htm";
	final static String URL_RELOGIN = "http://10.4.1.2/a11.htm";
	
	protected BuptWLanAgent(){
	}
	
	/**
	 * 
	 */
	@Override
	public boolean login() {
		// TODO Auto-generated method stub
		HttpRequest request = new HttpRequest(URL_LOGIN, "POST");  //POST 方法
		String[] keys = new String[]{"DDDDD","upass", "R1", "R2", "para", "0MKKey"};
		String[] values = new String[]{username, genPassword(), "0","1", "00", "123456"};
		
		request.addParam(keys, values);  //添加参数
		request.addHeaderFieldDefault(); //默认报头
		request.addHeaderField("Cookie",genCookie());
		
		//HttpResponse response = HttpUtil.sendAndGetContent(request);
		HttpResponse response = HttpUtil.send(request);
		
		//response.print();
		
		return "200".equals(response.getStatusCode());
	}

	/**
	 * 登录格式: DDDDD=xxx&upass=xxx&passplace=密码 Password&AMKKey=
	 */
	@Override
	public boolean relogin() {
		// TODO Auto-generated method stub
		HttpRequest request = new HttpRequest(URL_RELOGIN, "POST");  //POST 方法
		String[] keys = new String[]{"DDDDD", "upass", "passplace",  "AMKKey"};
		String[] values = new String[]{username, password, "密码 Password", ""};
		
		request.addParam(keys, values);  //添加参数
		request.addHeaderFieldDefault(); //默认报头
		
		request.addHeaderField("Cookie",genCookie());

		//HttpResponse response = HttpUtil.sendAndGetContent(request);
		HttpResponse response = HttpUtil.send(request);
		
		//response.print();
		
		return "200".equals(response.getStatusCode());
	}

	@Override
	public boolean logout() {
		// TODO Auto-generated method stub
		HttpRequest request = new HttpRequest(URL_LOGOUT, "GET");  //GET 方法

		request.addHeaderFieldDefault();
		request.addHeaderField("Cookie",genCookie());
		
		//HttpResponse response = HttpUtil.sendAndGetContent(request);
		HttpResponse response = HttpUtil.send(request);
		
		//response.print();
		
		return "200".equals(response.getStatusCode());
	}
	/**
	 * 生成登录请求中的cookie
	 * Cookie: myusername=108247; pwd=xxxxxx; username=108247; smartdot=000000
	 * @return
	 */
	protected String genCookie(){
		String strCookieString = "myusername=" + username + "; pwd=" + password + "; username=" + username + "; smartdot=000000" ;
		
		return strCookieString;
	}
	
	/**
	 * 
	 * @return
	 */
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
