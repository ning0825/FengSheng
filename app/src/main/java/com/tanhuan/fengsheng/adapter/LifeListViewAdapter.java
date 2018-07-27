package com.tanhuan.fengsheng.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tanhuan.fengsheng.R;
import com.tanhuan.fengsheng.bean.LifeStyle;

import java.util.List;

public class LifeListViewAdapter extends ArrayAdapter {

    int resourceId;
    List<LifeStyle.HeWeather6Bean.LifestyleBean> lifeStyleBeans;

    public LifeListViewAdapter(@NonNull Context context, int resource, @NonNull List<LifeStyle.HeWeather6Bean.LifestyleBean> objects) {
        super(context, resource, objects);
        resourceId = resource;
        lifeStyleBeans = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewHolder viewHolder = null;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();

            viewHolder.tvType = convertView.findViewById(R.id.tv_type);
            viewHolder.tvBrf = convertView.findViewById(R.id.tv_brf);
            viewHolder.tvTxt = convertView.findViewById(R.id.tv_txt);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        LifeStyle.HeWeather6Bean.LifestyleBean lifestyleBean = getItem(position);

        viewHolder.tvType.setText(lifestyleBean.getType());
        viewHolder.tvBrf.setText(lifestyleBean.getBrf());
        viewHolder.tvTxt.setText(lifestyleBean.getTxt());
        return convertView;
    }

    @Override
    public int getCount() {
        return lifeStyleBeans.size();
    }

    @Nullable
    @Override
    public LifeStyle.HeWeather6Bean.LifestyleBean getItem(int position) {
        return lifeStyleBeans.get(position);
    }

    public static class ViewHolder {
        TextView tvType;
        TextView tvBrf;
        TextView tvTxt;
    }
}
