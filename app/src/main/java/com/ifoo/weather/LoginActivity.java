package com.ifoo.weather;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ifoo.weather.gson.User;
import com.ifoo.weather.gson.UsersDatabase;
import com.ifoo.weather.gson.UsersOpenHelper;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private EditText userEdit;
    private EditText passwdEdit;
    private Button loginButton;
    private TextView registText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        userEdit = (EditText)findViewById(R.id.userEdit);
        passwdEdit = (EditText)findViewById(R.id.passwdEdit);
        registText = (TextView)findViewById(R.id.registText);
        loginButton = (Button)findViewById(R.id.loginButton);

        registText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegistActivity.class);
                startActivity(intent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = userEdit.getText().toString();
                String password = passwdEdit.getText().toString();
                UsersOpenHelper helper = new UsersOpenHelper(LoginActivity.this, "Users.db", null, 1);
                UsersDatabase udb = new UsersDatabase(helper);
                boolean exist = false;
                List<User> users = udb.getUsers();
                if (users != null){
                    for (User user : users){
                        if (!user.getUsername().equals(username)){
                            continue;
                        }
                        if (!user.getPassword().equals(password)){
                            continue;
                        }
                        exist = true;
                    }
                }
                if (exist){
                    SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this).edit();
                    editor.putBoolean("isLogin", true);
                    editor.apply();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
