package com.example.webservicetest;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.BoolRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.webservicetest.Business.ServiceCollection;
import com.example.webservicetest.Model.BoolResultModel;
import com.example.webservicetest.Model.ObjectResultModel;

public class UserInfoActivity2 extends AppCompatActivity implements View.OnClickListener{

    private UserModel userModel;
    private String Action;

    private final int Delete = 0x0003;
    private final int Edit = 0x0004;

    private EditText user_code;
    private EditText user_name;
    private EditText user_telphone;
    private Button btn_save;
    private Button btn_delete;
    private Button btn_back;
    private TextView text_view;

    private String user_code_text;
    private String user_name_text;
    private String user_telphone_text;

    private BoolResultModel result ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info2);
        Intent intent = getIntent();
        userModel = (UserModel)intent.getSerializableExtra("UserInfo");
        Action = intent.getStringExtra("Action");
        user_code = (EditText)findViewById(R.id.userCode);
        user_name = (EditText)findViewById(R.id.userName);
        user_telphone = (EditText)findViewById(R.id.telPhone);
        btn_back = (Button)findViewById(R.id.btnBack);
        btn_delete = (Button)findViewById(R.id.btnDelete);
        btn_save = (Button)findViewById(R.id.btnSave);
        text_view = (TextView) findViewById(R.id.user_view);

        btn_back.setOnClickListener(this);
        btn_delete.setOnClickListener(this);
        btn_save.setOnClickListener(this);

        if(Action.equals( "Add")){
            btn_delete.setVisibility(View.GONE);
            user_code.setVisibility(View.GONE);
            text_view.setVisibility(View.GONE);
        }
        else {
            user_telphone.setText(userModel.getTelPhone());
            user_code.setText(Integer.toString(userModel.getUserId()));
            user_name.setText(userModel.getUserName());
        }
    }

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Delete:
                    if(result.Flag){
                        Toast.makeText(UserInfoActivity2.this, "删除成功", Toast.LENGTH_SHORT).show();
                        setResult(RESULT_OK);
                        finish();
                    }
                    else {
                        Toast.makeText(UserInfoActivity2.this, result.Message, Toast.LENGTH_SHORT).show();
                    }
                    break;
                case Edit:
                    if(result.Flag){
                        Toast.makeText(UserInfoActivity2.this, "保存成功", Toast.LENGTH_SHORT).show();
                        setResult(RESULT_OK);
                        finish();
                    }
                    else {
                        Toast.makeText(UserInfoActivity2.this, result.Message, Toast.LENGTH_SHORT).show();
                    }
                    break;
            }

        }
    };

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnBack:
                setResult(RESULT_CANCELED);
                finish();
                break;
            case R.id.btnDelete:
                user_code_text = user_code.getText().toString();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        result = ServiceCollection.DeleteUser(Integer.parseInt(user_code_text));
                        handler.sendEmptyMessage(Delete);
                    }
                }).start();
                break;
            case R.id.btnSave:
                user_code_text = user_code.getText().toString();
                user_name_text = user_name.getText().toString();
                user_telphone_text = user_telphone.getText().toString();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if(Action.equals( "Add")){
                            result = ServiceCollection.AddUser(user_name_text, user_telphone_text);
                        }
                        else {
                            result = ServiceCollection.UpdateUser(Integer.parseInt(user_code_text), user_name_text, user_telphone_text);
                        }
                        handler.sendEmptyMessage(Edit);
                    }
                }).start();
                break;
        }
    }

    public static void actionStart(Context context, UserModel userModel, Boolean IsAdd){
        Intent intent = new Intent(context, com.example.webservicetest.UserInfoActivity2.class);
        intent.putExtra("UserInfo", userModel);
        if(IsAdd){
            intent.putExtra("Action", "Add");
        }
        else {
            intent.putExtra("Action", "Edit");
        }
        // context.startActivtiyForResult(intent, 1);
    }
}
