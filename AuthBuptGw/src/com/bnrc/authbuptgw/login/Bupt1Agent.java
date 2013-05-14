package com.bnrc.authbuptgw.login;

public class Bupt1Agent extends LoginEngine implements ILoginAgent {

	
	protected Bupt1Agent(String user, String passwd){
		this.username = user;
		this.password = passwd;
	}
	@Override
	public boolean login() {
		// TODO Auto-generated method stub
		return false;
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
