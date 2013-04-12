package com.bnrc.authbuptgw;

import android.os.Bundle;
import android.app.Activity;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

/**
 * 
 * @author yinqingwang@163.com
 * 
 */
public class MainActivity extends Activity {

	static final String PREF_NAME = "AuthBuptGW";
	static final String USERNAME = "USERNAME";
	static final String PASSWORD = "PASSWORD";

	/**
	 * 是否开启自动登录功能, true为开启, false为不开启。
	 */
	boolean bEnable = false;
	
	/**
	 * 网络是否畅通
	 */
	boolean bNetOK = false;
	
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

	EditText m_username = null;
	EditText m_password = null;

	/**
	 * 初始化函数, 在onCreate()方法中调用
	 */
	protected void initEvent() {

		((ToggleButton) this.findViewById(R.id.btn_enable_disable)).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onClickEnableDisable(v);
			}

		});

		((Button) this.findViewById(R.id.btn_settings)).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onClickSettings(v);
			}

		});

		((Button) this.findViewById(R.id.btn_quit)).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onClickQuit(v);
			}

		});

		((CheckBox) this.findViewById(R.id.chkbtn_enable)).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onClickCheckEnable(v);
			}

		});
		// 用户-输入框
		((EditText) this.findViewById(R.id.txt_username)).addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				onTextChangedUserNamePassword(s);
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});
		// 密码输入框
		((EditText) this.findViewById(R.id.txt_passwd)).addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				onTextChangedUserNamePassword(s);
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});
	}

	protected void init() {
		m_tgbtn = (ToggleButton) this.findViewById(R.id.btn_enable_disable);
		m_chkbx = (CheckBox) this.findViewById(R.id.chkbtn_enable);
		m_msg = (TextView) this.findViewById(R.id.login_msg);
		m_username = (EditText) this.findViewById(R.id.txt_username);
		m_password = (EditText) this.findViewById(R.id.txt_passwd);

		initEvent(); // 初始化事件处理
	}
	/**
	 * 
	 */
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}
	/**
	 * 
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// 初始化
		init();

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
	protected void onClickEnableDisable(View v) {
		// ToggleButton btn = (ToggleButton)v;
		bEnable = !bEnable; // 切换状态

		m_chkbx.setChecked(bEnable);

		doLogin();
	}

	/**
	 * 
	 * @param v
	 */
	protected void onClickCheckEnable(View v) {
		bEnable = !bEnable; // 切换状态
		m_tgbtn.setChecked(bEnable);

		doLogin();
	}

	/**
	 * 
	 * @param v
	 */
	protected void onClickSettings(View v) {

	}

	/**
	 * 
	 * @param v
	 */
	protected void onClickQuit(View v) {

	}

	/**
	 * 输入的用户名/密码有变化时调用
	 */
	protected void onTextChangedUserNamePassword(CharSequence s) {
		if( bEnable ){//如果开启了自动登录, 则暂时关闭
			bEnable = !bEnable;
			
			m_chkbx.setChecked(bEnable); //设置状态为不启用
			m_tgbtn.setChecked(bEnable); //设置状态为不启用
		}
	}

	/**
	 * 自动登录处理
	 */
	protected void doLogin() {
		if (bEnable) {//进行登录处理
			String userName = m_username.getText().toString();
			String passWord = m_password.getText().toString();
			String strUrl = this.getString(R.string.URL_LOGIN);
			
			m_msg.setText(R.string.msg_login_ok);
		} else {
			m_msg.setText(R.string.msg_login_fail);
		}
	}

	/**
	 * 从存储中读取出数据并显示到界面上面
	 */
	protected void loadData() {
		SharedPreferences myPref = getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);
		String userName = myPref.getString(USERNAME, "");
		String passWord = myPref.getString(PASSWORD, "");

		m_username.setText(userName);
		m_password.setText(passWord);
	}

	/**
	 * 存储数据到应用程序中
	 */
	protected void saveData() {
		String userName = m_username.getText().toString();
		String passWord = m_password.getText().toString();

		SharedPreferences myPref = getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = myPref.edit();
		editor.putString(USERNAME, userName);
		editor.putString(PASSWORD, passWord);
		// editor.apply();
		editor.commit();
	}
}
