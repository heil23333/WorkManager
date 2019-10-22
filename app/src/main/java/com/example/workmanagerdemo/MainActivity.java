package com.example.workmanagerdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.os.Bundle;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        如果设置了多个约束条件，那需要全部满足才能完成
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)//网络连接时执行
                .setTriggerContentUpdateDelay(20, TimeUnit.SECONDS)
                .build();

        Data data = new  Data.Builder().putString("图片", "石原里美写真").build();

//        单次任务
        OneTimeWorkRequest uploadWorkRequest = new OneTimeWorkRequest.Builder(UploadWorker.class)
//                .setConstraints(constraints)//设置约束条件
                .setInitialDelay(20, TimeUnit.SECONDS)//20秒后开始执行
                .setInputData(data)//设置数据
                .build();

//        多次任务
//        PeriodicWorkRequest uploadWorkRequest = new PeriodicWorkRequest.Builder(UploadWorker.class, 10, TimeUnit.SECONDS).build();

        WorkManager.getInstance(this).enqueue(uploadWorkRequest);//如果没有设置延时和约束条件，则会立刻执行
        System.out.println("hl-----------enqueue");
    }
}
