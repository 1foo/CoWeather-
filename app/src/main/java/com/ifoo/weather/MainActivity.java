package com.ifoo.weather;


import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import com.ifoo.weather.gson.Future;
import com.ifoo.weather.gson.FutureAdapter;
import com.ifoo.weather.util.Utilty;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {
    private ScrollView weatherLayout;
    private TextView titlecity;
    private TextView titleUpdateTime;
    private TextView degreeTExt;
    private TextView weatherInfoText;
    private ListView futureLayout;
    private ImageView bingPicImg;
    private TextView tips;
    private TextView windDirection;
    private TextView uvIndex;
    private TextView humidity;
    private String cityname = "重庆";

    private List<Future> futureDays = new ArrayList<>();


    private static String responseData = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化各控件
        weatherLayout = (ScrollView) findViewById(R.id.weather_layout);
        titlecity = (TextView) findViewById(R.id.title_city);
        titleUpdateTime = (TextView) findViewById(R.id.title_update_time);
        degreeTExt = (TextView) findViewById(R.id.now_Temperature);
        weatherInfoText = (TextView) findViewById(R.id.now_Overview);
        futureLayout = (ListView) findViewById(R.id.future_layout);
        bingPicImg = (ImageView) findViewById(R.id.bing_pic_img);
        tips = (TextView)findViewById(R.id.tips_Text);
        windDirection =(TextView)findViewById(R.id.wind_direction);
        uvIndex = (TextView)findViewById(R.id.uv_index);
        humidity = (TextView)findViewById(R.id.humidity);
        titlecity.setFocusable(true);
        titlecity.setFocusableInTouchMode(true);
        titlecity.requestFocus();
        try {
            Intent intent = getIntent();
            String city = intent.getStringExtra("city_name").toString();
            sendRequest(city);
        }catch (Exception e){
            sendRequest("重庆");
        }






    }

    private void sendRequest(String cityname) {

        final String weatherURL = "https://v.juhe.cn/weather/index?format=2" +
                "&cityname=" +
                cityname +
                /**
                 * 填入自己的聚合数据key
                 */
                "&key=XXXXXXXXXXXXXXXXXXX";


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url(weatherURL).build();

                    Response response = client.newCall(request).execute();
                    responseData = response.body().string();
                    Log.i("TAG", responseData);

                    parseNow(responseData);

                    parseFuture(responseData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //解析天气
    public void parseNow(String jsonData) {

        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            jsonObject = jsonObject.getJSONObject("result");
            JSONObject sk = jsonObject.getJSONObject("sk");
            JSONObject today = jsonObject.getJSONObject("today");


            String cityname = today.getString("city");
            String tempNow = sk.getString("temp")+"°";
            String time = sk.getString("time");
            String weatherNow = today.getString("weather");
            String tipsNow = today.getString("dressing_advice");
            String uvNow = today.getString("uv_index");
            String windDNow = sk.getString("wind_direction");
            String humNow = sk.getString("humidity");

            titlecity.setText(cityname);
            titleUpdateTime.setText(time);
            degreeTExt.setText(tempNow);
            weatherInfoText.setText(weatherNow);
            tips.setText(tipsNow);
            uvIndex.setText("紫外线 "+uvNow);
            windDirection.setText(windDNow);
            humidity.setText("湿度" + humNow);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }



    private void parseFuture(String jsonData){
        try{
            JSONObject jsonObject = new JSONObject(jsonData);
            jsonObject = jsonObject.getJSONObject("result");
            String future = jsonObject.getString("future");
            JSONArray days = new JSONArray(future);
            for (int i = 0; i < days.length(); i++){
                JSONObject day = days.getJSONObject(i);
                String temp = day.getString("temperature");
                String wind = day.getString("wind");
                String week = day.getString("week");
                String weather = day.getString("weather");
                Future futureDay = new Future(temp, wind, week, weather);
                futureDays.add(futureDay);
            }
            FutureAdapter adapter = new FutureAdapter(
                    MainActivity.this, R.layout.future_item, futureDays);
            ListView listView = (ListView)findViewById(R.id.future_layout);
            listView.setAdapter(adapter);
            Utilty.setListViewHeightBasedOnChildren(listView);
        }catch (Exception e){

        }

    }

    public void exit(View view) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(MainActivity.this).edit();
        editor.putBoolean("isLogin", false);
        editor.apply();
        Intent intent = new Intent(MainActivity.this, SplashActivity.class);
        startActivity(intent);
    }

    public void add(View view){
        Intent intent = new Intent(MainActivity.this, SearchActivity.class);

        startActivity(intent);

    }
}

