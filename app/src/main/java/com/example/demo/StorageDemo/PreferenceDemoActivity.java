package com.example.demo.StorageDemo;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.annotation.Nullable;

import com.example.demo.R;

public class PreferenceDemoActivity extends Activity implements CompoundButton.OnCheckedChangeListener {

    private static final String TAG = "PreferenceDemoActivity";
    private Switch mSwitch;
    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference_demo);
        //找到控件
        mSwitch = (Switch) findViewById(R.id.switch_bar);
        mSwitch.setOnCheckedChangeListener(this);

        //第一步，拿到这个Sharepreference
        mSharedPreferences = this.getSharedPreferences("setting_language", MODE_PRIVATE);

        //回显state
        boolean state = mSharedPreferences.getBoolean("state", false);
        mSwitch.setChecked(state);
    }

    /**
     * sharepreference 存储也是属于内部存储，它跟files/cache 是一样的，在/data/data/包名/shared_prefs 以xml文件形式保存起来
     * 它有一个特点，内部保存是以键值对的方式进行保存
     *
     * @param buttonView
     * @param isChecked
     */

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        //我们在这里进行数据保存
        Log.d(TAG, "current state == " + isChecked);
        //第二步，进入编辑模式
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        //第三步，保存数据
        //保存的数据类型有 String float int StringSet...
        editor.putBoolean("state", isChecked);
        //第四步，提交编辑器
        editor.commit();
    }
}

