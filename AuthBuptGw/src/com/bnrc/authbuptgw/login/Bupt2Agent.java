package com.bnrc.authbuptgw.login;

import com.bnrc.authbuptgw.util.HttpRequest;
import com.bnrc.authbuptgw.util.HttpResponse;
import com.bnrc.authbuptgw.util.HttpUtil;

public class Bupt2Agent extends LoginEngine implements ILoginAgent {

	final static String URL_LOGIN = "http://10.8.128.1/portal/logon.cgi";
	final static String URL_LOGOUT = "";
	final static String URL_RELOGIN = "http://10.8.128.1/portal/logon.cgi";
	
	protected Bupt2Agent(String user, String passwd){
		this.username = user;
		this.password = passwd;
	}
	@Override
	public boolean login() {
		// TODO Auto-generated method stub
		HttpRequest request = new HttpRequest(URL_LOGIN, "POST");  //POST 方法
		String[] keys = new String[]{"PtUser","PtPwd", "PtButton"};
		String[] values = new String[]{this.username, this.password, "登录"};
		
		request.addParam(keys, values);  //添加参数
		request.addHeaderFieldDefault(); //默认报头

		//HttpResponse response = HttpUtil.sendAndGetContent(request);
		HttpResponse response = HttpUtil.send(request);
		
		//response.print();
		
		return "200".equals(response.getStatusCode());
	}

	@Override
	public boolean relogin() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean logout() {
		// TODO Auto-generated method stub
		return false;
	}

}
