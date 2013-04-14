package com.bnrc.authbuptgw;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
	static final int LOGIN_NUM = 3;
	static final int UPDATE_UI = 0;
	static final int TASK_DELAY = 1 * 1000;   // Seconds;
	
	private Timer mTimer;
    private TimerTask mTimerTask;
    private Handler mHandler;
    
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
	 * 
	 */
	protected void initTimerAndHandler(){
		
		mTimer = new Timer();
		mTimerTask = new TimerTask() {

            @Override
            public void run() {
            	
                mHandler.sendEmptyMessage(UPDATE_UI);
/*              
                // It doesn't work updating the UI inside a timer.
                Calendar cal = Calendar.getInstance();
                mButton.setText(cal.toString());
*/          
                }
        };
        
		mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                case UPDATE_UI:

                    break;
                }
            }
        };
	}
	
	protected void destroyTimer(){
		if( mTimerTask !=null ){
			mTimerTask.cancel();
			mTimerTask = null;
		}
		if( mTimer!= null ){
			mTimer.cancel();
			mTimer = null;
		}
	}
	
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

	/**
	 * 初始化
	 */
	protected void init() {
		m_tgbtn = (ToggleButton) this.findViewById(R.id.btn_enable_disable);
		m_chkbx = (CheckBox) this.findViewById(R.id.chkbtn_enable);
		m_msg = (TextView) this.findViewById(R.id.login_msg);
		m_username = (EditText) this.findViewById(R.id.txt_username);
		m_password = (EditText) this.findViewById(R.id.txt_passwd);

		initEvent(); // 初始化事件处理
		initTimerAndHandler(); //初始化定时器
		
		bEnable = true;  //自动登录处理
		m_tgbtn.setChecked(bEnable);
		m_chkbx.setChecked(bEnable);
		
	}
	/**
	 * 
	 */
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
		checkNetwork();  //检查网络是否正常!
		
		doLogin(); //自动登录处理
		
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		destroyTimer();
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

		mTimer.schedule(mTimerTask, TASK_DELAY);
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
	 * 检查网络是否可用
	 */
	protected void checkNetwork(){

		boolean bWifiEnable = AuthUtil.isWifiEnable(this);
		
		if( !bWifiEnable ){
			m_msg.setText(R.string.msg_wifi_fail); //wifi不可用
			
		} else  {//进行网络检查
			String strChkUrl = this.getString(R.string.URL_CHECK_NETWORK);
	
			//bNetOK = false;
			
			for( int i=0; i<LOGIN_NUM; i++ ){
				if( AuthUtil.checkUrl(strChkUrl)) { //测试网络是否连通
					bNetOK = true;
					break;
				}
				
			}
			//启用定时器检查结果
			if( bNetOK ){
				m_msg.setText(R.string.msg_network_ok);
			} else {
				m_msg.setText(R.string.msg_network_fail);
			}
		}// end of if
	}
	/**
	 * 自动登录处理
	 */
	protected void doLogin() {
		
		boolean bWifiEnable = AuthUtil.isWifiEnable(this);
		
		if( !bWifiEnable ){
			m_msg.setText(R.string.msg_wifi_fail); //wifi不可用
			
		} else if ( bEnable && (!bNetOK) ) {//进行登录处理
			m_msg.setText(R.string.msg_login);
			
			String userName = m_username.getText().toString();
			String passWord = m_password.getText().toString();
			String strUrl = this.getString(R.string.URL_LOGIN);
			String strChkUrl = this.getString(R.string.URL_CHECK_NETWORK);
			
			//bNetOK = false;
			
			for( int i=0; i<LOGIN_NUM; i++ ){
				AuthUtil.login(strUrl, userName, passWord);  //发送登录请求到服务器
				if( AuthUtil.checkUrl(strChkUrl)) { //测试网络是否连通
					bNetOK = true;
					break;
				}
				
			}
			//启用定时器检查结果
			if( bNetOK ){
				m_msg.setText(R.string.msg_login_ok);
			} else {
				m_msg.setText(R.string.msg_login_fail);
			}
		}// end of if
		
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
