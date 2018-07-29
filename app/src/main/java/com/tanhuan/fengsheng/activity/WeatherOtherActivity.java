package com.tanhuan.fengsheng.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.tanhuan.fengsheng.R;
import com.tanhuan.fengsheng.adapter.LifeListViewAdapter;
import com.tanhuan.fengsheng.adapter.MyListViewAdapter;
import com.tanhuan.fengsheng.entity.WeatherOther;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeatherOtherActivity extends AppCompatActivity {

    @BindView(R.id.cv_wind)
    CardView cvWind;
    @BindView(R.id.cv_air)
    CardView cvAir;
    @BindView(R.id.cv_life)
    CardView cvLife;
    @BindView(R.id.tb_other)
    Toolbar toolbar;
    @BindView(R.id.tv_air)
    TextView tvAir;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);
        ButterKnife.bind(this);

        cvWind.setElevation(10);
        cvAir.setElevation(10);
        cvLife.setElevation(10);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setElevation(10);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        WeatherOther weatherOther = (WeatherOther) getIntent().getSerializableExtra("weather_other");
        ViewHolder viewHolder = new ViewHolder();

        viewHolder.tvCityName = findViewById(R.id.city_name_other);
        viewHolder.tvTmp = findViewById(R.id.tmp_other);
        viewHolder.cvAir = findViewById(R.id.cv_air);
        viewHolder.tvWindDir = findViewById(R.id.tv_wind_dir);
        viewHolder.tvWindSc = findViewById(R.id.tv_wind_sc);
        viewHolder.tvWindSpd = findViewById(R.id.tv_wind_spd);
        viewHolder.tvAqi = findViewById(R.id.tv_aqi);
        viewHolder.tvPm25 = findViewById(R.id.tv_pm25);
        viewHolder.tvPm10 = findViewById(R.id.tv_pm10);
        viewHolder.lvLife = findViewById(R.id.lv_life);

        viewHolder.tvCityName.setText(weatherOther.getCityName());
        viewHolder.tvTmp.setText(weatherOther.getTmp() + "°");
        viewHolder.tvWindDir.setText(weatherOther.getWindDir());
        viewHolder.tvWindSc.setText(weatherOther.getWindSc());
        viewHolder.tvWindSpd.setText(weatherOther.getWindSpd());
        if (weatherOther.isHasDate()) {
            viewHolder.tvAqi.setText(weatherOther.getAqi());
            viewHolder.tvPm25.setText(weatherOther.getPm25());
            viewHolder.tvPm10.setText(weatherOther.getPm10());
        } else {
            viewHolder.cvAir.setVisibility(View.GONE);
            tvAir.setVisibility(View.GONE);
        }

        viewHolder.lvLife.setAdapter(new LifeListViewAdapter(this, R.layout.item_life_style, weatherOther.getLifestyleBeans()));

    }

    public class ViewHolder {

        TextView tvCityName;

        TextView tvTmp;

        TextView tvWindDir;
        TextView tvWindSc;
        TextView tvWindSpd;

        CardView cvAir;
        TextView tvAqi;
        TextView tvPm25;
        TextView tvPm10;

        ListView lvLife;


    }


}
