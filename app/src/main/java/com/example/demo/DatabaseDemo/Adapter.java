package com.example.demo.DatabaseDemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.demo.R;

import java.util.List;


public class Adapter extends BaseAdapter {

    private Context context;
    private List<User> List;
    private final LayoutInflater mlayoutInflater;

    public Adapter(Context context, List<User> List) {
        this.context = context;
        this.List = List;
        mlayoutInflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return List.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    static class ViewHolder {
        public TextView mId, mName, mAge, mSalary;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mlayoutInflater.inflate(R.layout.item, null);
            holder.mId = convertView.findViewById(R.id.tv_1);
            holder.mName = convertView.findViewById(R.id.tv_2);
            holder.mAge = convertView.findViewById(R.id.tv_3);
            holder.mSalary = convertView.findViewById(R.id.tv_4);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //这里报了个错，因为TextView.setText时参数只能是字符/字符串 加个空串就好
        holder.mId.setText((" " + List.get(position).getId()));
        holder.mName.setText(List.get(position).getName());
        holder.mAge.setText(" " + List.get(position).getAge());
        holder.mSalary.setText(" " + List.get(position).getSalary());
        return convertView;
    }
}
