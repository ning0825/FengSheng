package com.tanhuan.fengsheng.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.tanhuan.fengsheng.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EmptyCityActivity extends AppCompatActivity {

    @BindView(R.id.bt_to_search_city)
    Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);
        ButterKnife.bind(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmptyCityActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

    }
}
