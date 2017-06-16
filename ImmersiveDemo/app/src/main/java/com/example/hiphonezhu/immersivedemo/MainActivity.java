package com.example.hiphonezhu.immersivedemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        // 以下两个方法都是 OK 的
//        new KeyboardUtil(this, findViewById(android.R.id.content)).enable();
        AndroidBug5497Workaround.assistActivity(findViewById(android.R.id.content));
    }
}
