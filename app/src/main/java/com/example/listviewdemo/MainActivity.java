package com.example.listviewdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.listviewdemo.DatabaseDemo.DatabaseActivity;

public class MainActivity extends AppCompatActivity {

    private Button mBtnListView;
    private Button mBtnSQLite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initLisenter();
    }

    private void initLisenter() {
        MyClickListener myClickListener = new MyClickListener();
        mBtnListView.setOnClickListener(myClickListener);
        mBtnSQLite.setOnClickListener(myClickListener);
    }

    private void initView() {
        mBtnListView = findViewById(R.id.btn_listview);
        mBtnSQLite = findViewById(R.id.btn_SQLite);
    }

    class MyClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = null;
            switch (v.getId()) {
                case R.id.btn_listview:
                    intent = new Intent(MainActivity.this, ListViewActivity.class);
                    startActivity(intent);
                    break;
                case R.id.btn_SQLite:
                    intent = new Intent(MainActivity.this, DatabaseActivity.class );
                    startActivity(intent);
            }
        }
    }

}
