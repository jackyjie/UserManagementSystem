package com.example.webservicetest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by JackR on 2018/3/15.
 */

public class UserAdapter extends ArrayAdapter<UserModel> {

    private int resourceId;
    public UserAdapter(Context context, int textViewResouceId, List<UserModel> objects){
        super(context, textViewResouceId, objects);
        resourceId = textViewResouceId;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        UserModel user = getItem(position);
//        View view = LayoutInflater.from(getContext()).inflate(resourceId,parent, false);
        View view = null;
        ViewHolder viewHolder = null;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.text = (TextView) view.findViewById(R.id.user_name);
            view.setTag(viewHolder); // 将viewHolder存储在view中
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.text.setText(user.getUserName());
        return view;
    }

    class ViewHolder {
        ImageView imageView;
        TextView text;
    }
}
