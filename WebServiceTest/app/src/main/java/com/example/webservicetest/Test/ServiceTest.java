package com.example.webservicetest.Test;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.webservicetest.Business.ServiceCollection;
import com.example.webservicetest.Model.BoolResultModel;
import com.example.webservicetest.Model.ListResultModel;
import com.example.webservicetest.Model.ObjectResultModel;
import com.example.webservicetest.R;
import com.example.webservicetest.UserModel;

import java.util.List;

public class ServiceTest extends AppCompatActivity implements View.OnClickListener {

    private EditText deleteId;
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;

    public final int GET=0x0003;
    public final int ADD=0x0004;
    public final int UPDATE=0x0005;
    public final int GETLIST=0x0006;
    public final int DELETE=0x0007;

    private int operateId;

    private BoolResultModel result1;
    private ListResultModel<UserModel> result2;
    private ObjectResultModel<UserModel> result3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_test);
        deleteId = (EditText)findViewById(R.id.deleteId);
        btn1 = (Button)findViewById(R.id.getUser);
        btn2 = (Button)findViewById(R.id.getUserList);
        btn3 =(Button)findViewById(R.id.deleteUser);
        btn4 = (Button)findViewById(R.id.addUser);
        btn5 = (Button)findViewById(R.id.updateUser);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);

    }

    //定义一个Handler用来更新页面：
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GET:
                    Toast.makeText(ServiceTest.this, result3.Flag + result3.Message, Toast.LENGTH_SHORT).show();
                    if(result3.Flag){
                        Toast.makeText(ServiceTest.this, result3.Data.getUserName(), Toast.LENGTH_SHORT).show();
                    }
                    break;
                case ADD:
                    Toast.makeText(ServiceTest.this, result1.Flag + result1.Message, Toast.LENGTH_SHORT).show();
                    break;
                case DELETE:
                    Toast.makeText(ServiceTest.this, result1.Flag + result1.Message, Toast.LENGTH_SHORT).show();
                    break;
                case GETLIST:
                    Toast.makeText(ServiceTest.this, result2.Flag + result2.Message, Toast.LENGTH_SHORT).show();
                    if(result2.Flag && result2.Data.size()>0){
                        List<UserModel> _list = result2.Data;
                        Toast.makeText(ServiceTest.this, _list.get(1).userName, Toast.LENGTH_SHORT).show();
                    }
                    break;
                case UPDATE:
                    Toast.makeText(ServiceTest.this, result1.Flag + result1.Message, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.getUser:
                try{
                    operateId = Integer.parseInt(deleteId.getText().toString());
                }
                catch(Exception ex){
                    Toast.makeText(ServiceTest.this, "序号问题", Toast.LENGTH_SHORT).show();
                    return ;
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ServiceCollection collection = new ServiceCollection();
                        result3 = collection.GetUser(operateId);
                        handler.sendEmptyMessage(GET);
                    }
                }).start();
                break;
            case R.id.addUser:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ServiceCollection collection = new ServiceCollection();
                        result1 = collection.AddUser("小张", "18868197153");
                        handler.sendEmptyMessage(ADD);
                    }
                }).start();
                break;
            case R.id.deleteUser:
                try{
                    operateId = Integer.parseInt(deleteId.getText().toString());
                }
                catch(Exception ex){
                    Toast.makeText(ServiceTest.this, "序号问题", Toast.LENGTH_SHORT).show();
                    return ;
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ServiceCollection collection = new ServiceCollection();
                        result1 = collection.DeleteUser(operateId);
                        handler.sendEmptyMessage(DELETE);
                    }
                }).start();
                break;
            case R.id.getUserList:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ServiceCollection collection = new ServiceCollection();
                        result2 = collection.GetUserList();
                        handler.sendEmptyMessage(GETLIST);
                    }
                }).start();
                break;
            case R.id.updateUser:
                try{
                    operateId = Integer.parseInt(deleteId.getText().toString());
                }
                catch(Exception ex){
                    Toast.makeText(ServiceTest.this, "序号问题", Toast.LENGTH_SHORT).show();
                    return ;
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ServiceCollection collection = new ServiceCollection();
                        result1 = collection.UpdateUser(operateId, "小名", "156546514");
                        handler.sendEmptyMessage(UPDATE);
                    }
                }).start();
                break;
        }
    }
}
