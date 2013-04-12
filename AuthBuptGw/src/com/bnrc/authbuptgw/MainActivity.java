package com.bnrc.authbuptgw;

import android.os.Bundle;
import android.app.Activity;
//import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.ToggleButton;

/**
 * 
 * @author yinqingwang@163.com
 *
 */
public class MainActivity extends Activity {

	/**
	 * 是否开启自动登录功能, true为开启, false为不开启。
	 */
	boolean bEnable = false;
	/**
	 * 
	 */
	CheckBox m_chkbx = null;
	/**
	 * 
	 */
	ToggleButton m_tgbtn = null;
	
	/**
	 * 
	 */
	TextView m_msg = null;
	
	/**
	 * 初始化函数, 在onCreate()方法中调用
	 */
	protected void init(){
		Button btn = (Button) this.findViewById(R.id.btn_enable_disable);
		btn.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onClickEnableDisable(v);
			}
			
		});
		
		btn = (Button) this.findViewById(R.id.btn_settings);
		btn.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onClickSettings(v);
			}
			
		});
		
		btn = (Button) this.findViewById(R.id.btn_quit);
		btn.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onClickQuit(v);
			}
			
		});
		
		btn = (Button) this.findViewById(R.id.chkbtn_enable);
		btn.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onClickCheckEnable(v);
			}
			
		});
	}
	/**
	 * 
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//初始化
		init();
		m_tgbtn = (ToggleButton) this.findViewById(R.id.btn_enable_disable);
		m_chkbx = (CheckBox)this.findViewById(R.id.chkbtn_enable);
		m_msg = (TextView)this.findViewById(R.id.login_msg);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * 
	 * @param v
	 */
	protected void onClickEnableDisable(View v){
		//ToggleButton btn = (ToggleButton)v;
		bEnable = !bEnable;  //切换状态
		
		m_chkbx.setChecked(bEnable);
		
		doLogin();
	}
	/**
	 * 
	 * @param v
	 */
	protected void onClickCheckEnable(View v){
		bEnable = !bEnable;  //切换状态
		m_tgbtn.setChecked(bEnable);
		
		doLogin();
	}
	/**
	 * 
	 * @param v
	 */
	protected void onClickSettings(View v){
		
	}
	
	/**
	 * 
	 * @param v
	 */
	protected void onClickQuit(View v){
		
	}
	/**
	 * 自动登录处理
	 */
	protected void doLogin(){
		if( bEnable ){
			m_msg.setText(R.string.msg_login_ok);
		} else {
			m_msg.setText(R.string.msg_login_fail);
		}
	}
}
