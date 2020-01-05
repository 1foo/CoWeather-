package com.ifoo.weather;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SearchActivity extends AppCompatActivity {
    private EditText cityname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);




    }

    public void search(View view) {
        cityname = (EditText) findViewById(R.id.search_city);
        String city =  cityname.getText().toString();
        Log.i("TGA",city);
        Intent intent = new Intent(SearchActivity.this,MainActivity.class);
        intent.putExtra("city_name",city);
        startActivity(intent);
    }
}
