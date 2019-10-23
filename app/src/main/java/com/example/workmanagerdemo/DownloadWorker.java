package com.example.workmanagerdemo;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class DownloadWorker extends Worker {
    public DownloadWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        String input = getInputData().getString("task");
        System.out.println("hl---------第二个任务执行了 input=" + input);
        Data outputData = new Data.Builder().putString("worker", "DownloadWorker").build();
        return Result.success(outputData);
    }
}
