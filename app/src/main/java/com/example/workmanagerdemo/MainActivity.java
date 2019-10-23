package com.example.workmanagerdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.ArrayCreatingInputMerger;
import androidx.work.BackoffPolicy;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.OverwritingInputMerger;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkContinuation;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.os.Bundle;

import java.time.Duration;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private WorkManager workManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        workManager = WorkManager.getInstance(this);
//        如果设置了多个约束条件，那需要全部满足才能完成
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)//网络连接时执行
//                .setTriggerContentUpdateDelay(20, TimeUnit.SECONDS)
                .build();

        Data data = new  Data.Builder().putString("图片", "石原里美写真").build();

//        单次任务1
        OneTimeWorkRequest uploadWork = new OneTimeWorkRequest.Builder(UploadWorker.class)
//                .setConstraints(constraints)//设置约束条件
//                .setBackoffCriteria(BackoffPolicy.LINEAR, 1, TimeUnit.MINUTES)//重试补偿方式，线性和指数型递增，默认为LINEAR
//                .setInitialDelay(20, TimeUnit.SECONDS)//20秒后开始执行
                .setInputData(data)//设置数据
                .build();

//        单次任务2
        OneTimeWorkRequest downloadWork = new OneTimeWorkRequest.Builder(DownloadWorker.class)
//                使用链式任务时父任务的输出会作为子任务的输入
//                .setInputMerger(OverwritingInputMerger.class)//将所有输入的键添加到输出中，当键冲突时，会覆盖之前的键
//                .setInputMerger(ArrayCreatingInputMerger.class)//冲突时，使用数组保存
                .build();

//        单次任务3
        OneTimeWorkRequest mergeWork = new OneTimeWorkRequest.Builder(MergeWorker.class)
//                OverwritingInputMerger会将所有输入的键添加到输出中，当键冲突时，会覆盖之前的键
                .setInputMerger(ArrayCreatingInputMerger.class)//使用链式任务时父任务的输出会作为子任务的输入
                .build();

//        多次任务 第二、第三个参数为间隔时间
//        PeriodicWorkRequest uploadWork = new PeriodicWorkRequest.Builder(UploadWorker.class, 10, TimeUnit.MINUTES).build();

//        链式任务
//        只适用于单次任务
//        WorkContinuation continuation = workManager.beginWith(uploadWork);//以该任务开始，依次执行(前提是任务返回success)
        WorkContinuation continuation = workManager.beginWith(Arrays.asList(uploadWork, downloadWork));//list集合里的任务会同时开始
        continuation.then(mergeWork).enqueue();

//        任务入队
//        如果没有设置延时和约束条件，则会立刻执行
//        workManager.enqueue(uploadWork);
//        System.out.println("hl-----------enqueue");

//        workManager.getWorkInfoByIdLiveData(uploadWork.getId()).observe(this, new Observer<WorkInfo>() {
//            @Override
//            public void onChanged(WorkInfo workInfo) {
//                System.out.println("hl-------WorkInfo=" + workInfo);
//            }
//        });
    }
}
