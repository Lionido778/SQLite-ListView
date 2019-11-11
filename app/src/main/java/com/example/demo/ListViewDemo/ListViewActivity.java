package com.example.demo.ListViewDemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.demo.R;

public class ListViewActivity extends AppCompatActivity {


    private ListView mlistView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        mlistView = findViewById(R.id.lv_1);
        mlistView.setAdapter(new ListAdapter(ListViewActivity.this));
    }
}
