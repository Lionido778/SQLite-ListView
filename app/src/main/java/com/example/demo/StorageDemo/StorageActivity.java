package com.example.demo.StorageDemo;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.demo.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class StorageActivity extends AppCompatActivity {

    private EditText mAccount;
    private EditText mPassword;
    private Button mlogin;
    private String TAG = "StorageActivity";
    private TextView msharePreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);

        //设置动态权限Dynamic privilege
        initDynamicPrivilege();
        //第一步，找到控件
        initView();
        //第二步，给登录按钮设置点击事件
        initListener();


    }

    private void initDynamicPrivilege() {
        /**
         * 动态获取权限，Android 6.0 新特性，一些保护权限，除了要在AndroidManifest中声明权限，还要使用如下代码动态获取
         */
        if (Build.VERSION.SDK_INT >= 23) {
            int REQUEST_CODE_CONTACT = 101;
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            //验证是否许可权限
            for (String str : permissions) {
                if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    //申请权限
                    this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                    return;
                }
            }
        }

    }

    private void initListener() {
        mlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "登录按钮被点击了...");
                Toast.makeText(StorageActivity.this, "登录成功...", Toast.LENGTH_SHORT).show();
                handlerLoginEvents();
            }
        });

        msharePreference.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StorageActivity.this, PreferenceDemoActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * 数据回显—通过内部存储里的文件
     */
    @Override
    protected void onResume() {
        super.onResume();
        //首先要拿到数据保存的文件 第一种方法如下。 但是第二种方法Android studio集成了一个API openFileInput() 比第一种方法更方便
        /*File filesDir = this.getFilesDir();
        File saveFile = new File(filesDir,"info.txt");*/

        try {
            FileInputStream fileInputStream = this.openFileInput("info.txt");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            String info = bufferedReader.readLine();
            //以 “账户名+***+密码” 的格式将数据存储
            //fos.write((accountText+"***"+passwordText).getBytes());
            //上面这行代码是我们之前保存的数据形式，也就是说，我们拿到数据后，要对数据进行切割

            String[] split = info.split("\\*\\*\\*");
            String account = split[0];
            String password = split[1];
            mAccount.setText(account);
            mPassword.setText(password);

            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //处理登陆事件
    private void handlerLoginEvents() {
        //第三步，拿到界面上的内容: 账号，密码
        //账号,密码
        String accountText = mAccount.getText().toString().trim();
        String passwordText = mPassword.getText().toString().trim();

        //对帐号进行检查。一般来说在实际开发中，我们需要对用户的账号进行合法性检查，比如说账号的长度，账号有没有敏感词
        //密码的检查也是一样，一般来说是对密码的复杂度进行检查。

        //这里我们只对这个密码和账号进行判空
        //第一种判空方法 但通常不用

        /*if (accountText.length() == 0) {
            Toast.makeText(this,"账号不能为空...",Toast.LENGTH_SHORT).show();
            return;
        }
        if (passwordText.length() == 0) {
            Toast.makeText(this,"密码不能为空...",Toast.LENGTH_SHORT).show();
            return;
        }*/

        //第二种判空方法 利用Text 里面的工具类 TextUtil

        if (TextUtils.isEmpty(accountText)) {
            Toast.makeText(this, "账号不能为空...", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(passwordText)) {
            Toast.makeText(this, "密码不能为空...", Toast.LENGTH_SHORT).show();
            return;
        }

        //把账号和密码保存到 内部存储空间
        saveUserInfo(accountText, passwordText);
        //把账号和密码保存到 SD卡
        savaUserInfoBySDCard(accountText, passwordText);
    }

    /**
     * 这里会报一个异常: java.io.FileNotFoundException: /storage/sdcard/info.txt: open failed: EACCES (Permission denied)
     * 是因为没有往SD卡写入数据的权限  在清单文件AndroidManifest.xml <application></application> 标签外边加上以下读写权限就OK了
     * <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
     * <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
     *
     * @param accountText
     * @param passwordText
     */
    private void savaUserInfoBySDCard(String accountText, String passwordText) {
        Log.d(TAG, "保存数据到SD卡...");

        //第一种方法
        /*File filePath = new File("/storage/sdcard");
        File saveFile = new File(filePath,"info.txt");

        try {
            FileOutputStream fos = new FileOutputStream(saveFile);
            fos.write((accountText+"***"+passwordText).getBytes());
            fos.close();
            Log.d(TAG,"数据保存成功");
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        //第二种方法 SD卡的路径获取和前面一样，也是有API获取的

        /**
         * 为什么要这样获取呢？ 就是因为不同的手机厂商，他们的扩展卡的名字是不一样的，
         * 通过这个API,就可以获取到他们的的扩展卡的路径
         *
         * 但是我们在实际的开发当中，会遇到这样的问题，怎样知道这个手机有没有SD卡？
         * 我们同样可以通过一个API, Environment.getExternalStorageState() 来判断SD卡是否已经挂载
         *
         * 同时在准备写入数据的时候 可以判断一下SD卡可用空间的大小
         * 通过API getFreeSpace()
         */

        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            Log.d(TAG, "SD卡已经挂载...");
        } else if (state.equals(Environment.MEDIA_UNMOUNTED)) {
            Log.d(TAG, "SD卡已经卸载...");
        }

        File filePath = Environment.getExternalStorageDirectory();
        File saveFile = new File(filePath, "info.txt");
        Log.d(TAG, "extral-Path ==" + filePath);

        //判断SD卡可用空间大小
        long freeSpace = filePath.getFreeSpace();
        //把long 类型转换为直观的空间大小，例如: KB MB GB  注意重载的类型 Formatter(android.text.format.Formatter)
        String size = Formatter.formatFileSize(this, freeSpace);
        Log.d(TAG, "free size == " + size);

        try {
            FileOutputStream fos = new FileOutputStream(saveFile);
            fos.write((accountText + "***" + passwordText).getBytes());
            fos.close();
            Log.d(TAG, "数据保存成功");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**  java 原生的方法来实现写入数据
     *
     * File file = new File("info.txt");  这种直接写一个文件名，去写文件，报出的异常是read-only;
     * 其实在Android系统中，每一个应用就是一个用户，每个用户的权限是特定的，不可以操作其他应用的内容
     * Android系统 是Linux系统 文件系统是以 / 为根目录
     * Android 应用数据都会保存在 /data/data/包名 下
     *
     */

    /*private void saveUserInfo(String accountText, String passwordText) {
        Log.d(TAG,"保存数据...");

        try {
        //File file = new File("info.txt"); //错误写法
        File file = new File("/data/data/com.example.androiddatapersistentstorage/info.txt");
        if ( !file.exists()) {
            file.createNewFile();
        }
            FileOutputStream fos = new FileOutputStream(file);
            //以 “账户名+***+密码” 的格式将数据存储
            fos.write((accountText+"***"+passwordText).getBytes());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }*/


    /**
     * Android studio 自带的方法API写入数据
     * <p>
     * 怎么获取到文件保存的路径呢？ "/data/data/com.example.androiddatapersistentstorage/files"
     * getFilesDir() 的输出结果: file path == /data/data/com.example.androiddatapersistentstorage/files
     * 这也就是说，这个getFilesDir()方法拿到的路径是 "/data/data/包名/files"
     * getCacheDir() 的输出结果: cache path == /data/data/com.example.androiddatapersistentstorage/cache
     * 上面这个路径是一个缓存路径，用于保存缓存文件，这个目录下的文件，会由系统根据存储情况进行清理
     * 假设说不够用了，那么就会清理
     * 下面这个路径我们用于保存文件的，我们怎样才能清理它呢？我们可以用代码删除，也可以在手机应用管理设置里边进行删除
     * files path == /data/data/com.example.androiddatapersistentstorage/files
     */
    private void saveUserInfo(String accountText, String passwordText) {
        Log.d(TAG, "保存数据到内部存储空间...");

        //获取缓存文件存储路径
        File cachePath = this.getCacheDir();
        Log.d(TAG, "cache path == " + cachePath);

        File filesPath = this.getFilesDir();
        File saveFile = new File(filesPath, "info.txt");
        Log.d(TAG, "file path == " + filesPath.toString());

        try {
            if (!saveFile.exists()) {
                saveFile.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(saveFile);
            //以 “账户名+***+密码” 的格式将数据存储
            fos.write((accountText + "***" + passwordText).getBytes());
            fos.close();
            Log.d(TAG, "数据保存成功");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initView() {
        mAccount = (EditText) findViewById(R.id.et_account);
        mPassword = (EditText) findViewById(R.id.et_password);
        mlogin = (Button) findViewById(R.id.btn_login);
        msharePreference = (TextView) findViewById(R.id.tv_share);
    }
}
