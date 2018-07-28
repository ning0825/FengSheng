package com.tanhuan.fengsheng.activity;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.transition.Transition;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.tanhuan.fengsheng.R;
import com.tanhuan.fengsheng.entity.WeatherMain;
import com.tanhuan.fengsheng.entity.WeatherOther;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeatherFragment extends Fragment {

    public WeatherFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.weather_pager, container, false);

        WeatherMain weatherMain = (WeatherMain)getArguments().getSerializable("weather_main");
        final ViewHolder viewHolder = new ViewHolder(rootView);
        viewHolder.tmp.setText(weatherMain.getTmp());
        viewHolder.cond.setText(weatherMain.getCond());
        viewHolder.aq.setText(weatherMain.getAirQlty());
        viewHolder.date1.setText(weatherMain.getDate1());
        viewHolder.date2.setText(weatherMain.getDate2());
        viewHolder.date3.setText(weatherMain.getDate3());
        viewHolder.date4.setText(weatherMain.getDate4());
        viewHolder.date5.setText(weatherMain.getDate5());
        viewHolder.tmp1.setText(weatherMain.getTmp1() + "°");
        viewHolder.tmp2.setText(weatherMain.getTmp2()+ "°");
        viewHolder.tmp3.setText(weatherMain.getTmp3() + "°");
        viewHolder.tmp4.setText(weatherMain.getTmp4() + "°");
        viewHolder.tmp5.setText(weatherMain.getTmp5() + "°");
        viewHolder.cond1.setText(weatherMain.getCond1());
        viewHolder.cond2.setText(weatherMain.getCond2());
        viewHolder.cond3.setText(weatherMain.getCond3());
        viewHolder.cond4.setText(weatherMain.getCond4());
        viewHolder.cond5.setText(weatherMain.getCond5());

        final WeatherOther weatherOther = new WeatherOther();
        weatherOther.setCityName(weatherMain.getCityName());
        weatherOther.setTmp(weatherMain.getTmp());
        weatherOther.setHasDate(weatherMain.isHasDate());
        weatherOther.setWindDir(weatherMain.getWindDir());
        weatherOther.setWindSc(weatherMain.getWindSc());
        weatherOther.setWindSpd(weatherMain.getWindSpd());
        weatherOther.setAqi(weatherMain.getAqi());
        weatherOther.setPm10(weatherMain.getPm10());
        weatherOther.setPm25(weatherMain.getPm25());
        weatherOther.setLifestyleBeans(weatherMain.getLifestyleBeans());

        viewHolder.tmp.setClickable(true);
        viewHolder.tmp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), WeatherOtherActivity.class);
                intent.putExtra("weather_other", weatherOther);
                startActivity(intent);
            }
        });


        return rootView;
    }

    public static class ViewHolder {

        //上半部分控件
        @Nullable
        @BindView(R.id.city_name)
        public TextView cityName;
        @BindView(R.id.tmp)
        public TextView tmp;
        @BindView(R.id.cond)
        public TextView cond;
        //空气质量
        @BindView(R.id.qlty)
        public TextView aq;

        //下半部分控件
        @BindView(R.id.date0)
        public TextView date1;
        @BindView(R.id.date1)
        public TextView date2;
        @BindView(R.id.date2)
        public TextView date3;
        @BindView(R.id.date3)
        public TextView date4;
        @BindView(R.id.date4)
        public TextView date5;
        @BindView(R.id.temp0)
        public TextView tmp1;
        @BindView(R.id.temp1)
        public TextView tmp2;
        @BindView(R.id.temp2)
        public TextView tmp3;
        @BindView(R.id.temp3)
        public TextView tmp4;
        @BindView(R.id.temp4)
        public TextView tmp5;
        @BindView(R.id.wea0)
        public TextView cond1;
        @BindView(R.id.wea1)
        public TextView cond2;
        @BindView(R.id.wea2)
        public TextView cond3;
        @BindView(R.id.wea3)
        public TextView cond4;
        @BindView(R.id.wea4)
        public TextView cond5;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
