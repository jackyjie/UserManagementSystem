package com.example.webservicetest;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.webservicetest.Business.ServiceCollection;
import com.example.webservicetest.Model.BoolResultModel;
import com.example.webservicetest.Test.ServiceTest;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText code_text;
    private EditText pwd_text;
    private Button send_button;
    private Button exit_button;
    private Button interfaceTest;
    private DrawerLayout drawerLayout;

    private final int LOGIN=0x001;

    private String codeText;
    private int iCodeText;
    private String pwdText;

    private BoolResultModel result;
    private UserModel user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
//        ActionBar bar = getSupportActionBar();
//        if(bar != null){
//            bar.setDisplayHomeAsUpEnabled(true);
//            bar.setHomeAsUpIndicator(R.drawable.home_img);
//        }

        send_button = (Button)findViewById(R.id.send_button);
        exit_button = (Button)findViewById(R.id.exit_button);
        code_text = (EditText) findViewById(R.id.code_edit);
        pwd_text = (EditText)findViewById(R.id.pwd_edit);
        send_button.setOnClickListener(this);
        exit_button.setOnClickListener(this);

        interfaceTest = (Button)findViewById(R.id.interface_test);
        interfaceTest.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete:
                Toast.makeText(this, "Delete Item Selected", Toast.LENGTH_SHORT).show();
                break;
            case R.id.backup:
                Toast.makeText(this, "Backup Item Selected", Toast.LENGTH_SHORT).show();
                break;
            case R.id.settings:
                Toast.makeText(this, "Settings Item Selected", Toast.LENGTH_SHORT).show();
                break;
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
        }
        return true;
    }

    //定义一个Handler用来更新页面：
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LOGIN:
                    if(result.Flag){
                        Toast.makeText(MainActivity.this, result.Message, Toast.LENGTH_SHORT).show();
                        UserActivity.actionStart(MainActivity.this, iCodeText);
                    }
                    else {
                        Toast.makeText(MainActivity.this, result.Message, Toast.LENGTH_SHORT).show();
                    }
                    break;
            }

        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_button:
                codeText = code_text.getText().toString().trim();
                pwdText = pwd_text.getText().toString().trim();
                if(codeText.equals("")){
                    Toast.makeText(MainActivity.this, "用户编号不能为空", Toast.LENGTH_SHORT).show();
                    return ;
                }
                try{
                    iCodeText = Integer.parseInt(codeText);
                }
                catch(Exception ex){
                    Toast.makeText(MainActivity.this, "用户编号不能为非数字", Toast.LENGTH_SHORT).show();
                }
                if(pwdText.equals("")){
                    Toast.makeText(MainActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                    return ;
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        login();
                    }
                }).start();
                break;
            case R.id.exit_button:
                finish();
                break;
            case R.id.interface_test:
                Intent intent = new Intent(MainActivity.this, ServiceTest.class);
                startActivity(intent);
                break;
        }
    }

    private void login(){
        ServiceCollection collection = new ServiceCollection();
        result = collection.Login(iCodeText, pwdText);
        handler.sendEmptyMessage(LOGIN);
    }
}
