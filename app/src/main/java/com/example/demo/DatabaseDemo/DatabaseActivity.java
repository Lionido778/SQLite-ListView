package com.example.demo.DatabaseDemo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.demo.R;

import java.util.List;

public class DatabaseActivity extends AppCompatActivity {

    private static final String TAG = "DatabaseActivity";
    private Button mBtnInsert;
    private Button mBtnUpdate;
    private Button mBtnDelete;
    private Button mBtnQuery;
    private List<User> result;
    private ListView mListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_main);

        initView();
        initListener();
    }

    private void initListener() {
        myOnClickListener onClickListener = new myOnClickListener();
        mBtnInsert.setOnClickListener(onClickListener);
        mBtnDelete.setOnClickListener(onClickListener);
        mBtnUpdate.setOnClickListener(onClickListener);
        mBtnQuery.setOnClickListener(onClickListener);
    }

    private void initView() {
        mBtnInsert = findViewById(R.id.btn_insert);
        mBtnUpdate = findViewById(R.id.btn_update);
        mBtnDelete = findViewById(R.id.btn_delete);
        mBtnQuery = findViewById(R.id.btn_query);
        mListView = findViewById(R.id.listview);
    }


    class myOnClickListener implements View.OnClickListener {

        Dao userDao = new Dao(DatabaseActivity.this);

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_insert:
                    for (int i = 0; i < 10; i++) {
                        userDao.insert(new User(i, "user" + i, 18 + i, 10000 + i));
                    }
                    Log.d(TAG, "数据插入成功");
                    break;
                case R.id.btn_delete:
                    userDao.delete();
                    Log.d(TAG, "数据删除成功");
                    break;
                case R.id.btn_update:
                    userDao.update();
                    Log.d(TAG, "数据更新成功");
                    break;
                case R.id.btn_query:
                    result = userDao.query();
                    Log.d(TAG, "数据查询成功");
                    for (User user : result) {
                        Log.d(TAG, "user --> " + user.toString());
                    }

                    mListView.setAdapter(new Adapter(DatabaseActivity.this, result));
                    break;
            }
        }
    }

}
