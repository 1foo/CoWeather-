package com.ifoo.weather.gson;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ifoo.weather.R;

import java.util.List;

public class FutureAdapter extends ArrayAdapter<Future> {
    private int resoureId;

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Future futureDay = getItem(position);
        View view ;
        if(convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resoureId, parent, false);

        }else
        {
            view = convertView;
        }
        TextView futureDayTemp = (TextView)view.findViewById(R.id.info_text);
        TextView futureDayWind = (TextView)view.findViewById(R.id.max_text);
        TextView futureDayWeather =(TextView) view.findViewById(R.id.min_text);
        TextView futureDayWeek = (TextView)view.findViewById(R.id.date_text);

        futureDayTemp.setText(futureDay.getTemp());
        futureDayWind.setText(futureDay.getWind());
        futureDayWeek.setText(futureDay.getWeek());
        futureDayWeather.setText(futureDay.getWeather());
        return view;
    }

    public FutureAdapter(Context context, int textViewResourceId, List<Future> objects){
        super(context, textViewResourceId, objects);
        resoureId = textViewResourceId;
    }
}
