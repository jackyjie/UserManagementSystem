package com.example.webservicetest;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.webservicetest.Business.ServiceCollection;
import com.example.webservicetest.Model.ListResultModel;

import java.util.ArrayList;
import java.util.List;

public class UserActivity extends AppCompatActivity {

    public List<UserModel> list = new ArrayList<>();

    public ListResultModel result;

    private int userId;
    UserAdapter adapter;

    private final int GETLIST=0x002;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Intent intent = getIntent();
        userId = intent.getIntExtra("userId", 0);
        initUsers();
        adapter = new UserAdapter(UserActivity.this, R.layout.user_item, list);
        ListView listView = (ListView)findViewById(R.id.userList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserModel user = list.get(position);
//                Toast.makeText(UserActivity.this, "Item点击" + user.getUserId(), Toast.LENGTH_SHORT ).show();
                Intent intent = new Intent(UserActivity.this, com.example.webservicetest.UserInfoActivity2.class);
                intent.putExtra("UserInfo", user);
                intent.putExtra("Action", "Edit");
                startActivityForResult(intent, 1);
            }
        });

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreatePanelMenu(int featureId, Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_normal,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.addUser:
                Intent intent = new Intent(UserActivity.this, com.example.webservicetest.UserInfoActivity2.class);
                intent.putExtra("UserInfo", new UserModel());
                intent.putExtra("Action", "Add");
                startActivityForResult(intent, 1);
                break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 1:
                //if(resultCode == RESULT_OK){
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            result = ServiceCollection.GetUserList();
                            handler.sendEmptyMessage(GETLIST);
                        }
                    }).start();
                //}
        }
    }

    //定义一个Handler用来更新页面：
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GETLIST:
                    if(!result.Flag){
                        Toast.makeText(UserActivity.this, result.Message, Toast.LENGTH_SHORT).show();
                    }
                    else {
                        list.clear();
                        list.addAll(result.Data);
                        adapter.notifyDataSetChanged();
                    }
                    break;
            }
        }
    };

    public static void actionStart(Context context, int userId){
        Intent intent  = new Intent(context, UserActivity.class);
        intent.putExtra("userId", userId);
        context.startActivity(intent);
    }

    private void initUsers(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                ServiceCollection collection = new ServiceCollection();
                result = collection.GetUserList();
                handler.sendEmptyMessage(GETLIST);
            }
        }).start();
    }
}
