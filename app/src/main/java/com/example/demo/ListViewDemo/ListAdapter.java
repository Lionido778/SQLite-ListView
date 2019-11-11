package com.example.demo.ListViewDemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.demo.R;

public class ListAdapter extends BaseAdapter {

    private int[] imagesID = {R.drawable.music1, R.drawable.music2, R.drawable.music3, R.drawable.music4, R.drawable.music5, R.drawable.music6, R.drawable.music7};
    private String[] titles = {"test1", "test2", "test3", "test4", "test5", "test6", "test7"};
    private String[] contents = {"超级好听1", "超级好听2", "超级好听3", "超级好听4", "超级好听5", "超级好听6", "超级好听7"};

    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public ListAdapter(Context context) {
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return titles.length;
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
        public ImageView image;
        public TextView tvTitle, tvTime, tvContent;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.layout_list_item, null);
            holder.image = convertView.findViewById(R.id.iv_image);
            holder.tvTitle = convertView.findViewById(R.id.tv_title);
            holder.tvTime = convertView.findViewById(R.id.tv_time);
            holder.tvContent = convertView.findViewById(R.id.tv_content);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //给控件赋值
        holder.tvTitle.setText(titles[position]);
        holder.tvTime.setText("2019.11.6");
        holder.tvContent.setText(contents[position]);
        holder.image.setImageResource(imagesID[position]);

        return convertView;
    }
}
