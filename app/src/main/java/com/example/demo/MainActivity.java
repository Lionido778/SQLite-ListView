package com.example.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.demo.DatabaseDemo.DatabaseActivity;
import com.example.demo.ListViewDemo.ListViewActivity;
import com.example.demo.StorageDemo.StorageActivity;

public class MainActivity extends AppCompatActivity {

    private Button mBtnListView;
    private Button mBtnSQLite;
    private Button mBtnStorage;

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
        mBtnStorage.setOnClickListener(myClickListener);
    }

    private void initView() {
        mBtnListView = findViewById(R.id.btn_listview);
        mBtnSQLite = findViewById(R.id.btn_SQLite);
        mBtnStorage = findViewById(R.id.btn_Storage);
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
                    intent = new Intent(MainActivity.this, DatabaseActivity.class);
                    startActivity(intent);
                    break;
                case R.id.btn_Storage:
                    intent = new Intent(MainActivity.this, StorageActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    }

}
