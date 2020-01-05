package com.ifoo.weather;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ifoo.weather.gson.User;
import com.ifoo.weather.gson.UsersDatabase;
import com.ifoo.weather.gson.UsersOpenHelper;

import java.util.List;

public class RegistActivity extends AppCompatActivity {

    private EditText registUserEdit;
    private EditText registPasswdEdit;
    private EditText registConfirmEdit;
    private Button registButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
        setContentView(R.layout.activity_regist);

        registUserEdit = (EditText)findViewById(R.id.registUserEdit);
        registPasswdEdit = (EditText)findViewById(R.id.registPasswdEdit);
        registConfirmEdit = (EditText)findViewById(R.id.registConfirmEdit);

        registButton = (Button)findViewById(R.id.registButton);
        registButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String registUsername = registUserEdit.getText().toString();
                String registPasswd = registPasswdEdit.getText().toString();
                String registConfirm = registConfirmEdit.getText().toString();
                UsersOpenHelper helper = new UsersOpenHelper(RegistActivity.this, "Users.db", null, 1);
                UsersDatabase udb = new UsersDatabase(helper);
                List<User> users = udb.getUsers();
                boolean exist = false;
                if (users != null) {
                    for (User user : users) {
                        if (user.getUsername().equals(registUsername)) {
                            exist = true;
                        }
                    }
                }
                if (!exist && registPasswd.equals(registConfirm)){
                    User user = new User(registUsername, registPasswd);
                    udb.addUser(user);
                    Toast.makeText(RegistActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegistActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(RegistActivity.this, "用户名已存在", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
