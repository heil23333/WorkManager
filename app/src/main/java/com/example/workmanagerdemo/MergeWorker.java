package com.example.workmanagerdemo;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.Arrays;

public class MergeWorker extends Worker {
    public MergeWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        System.out.println("hl----------MergeWorker doWork");
        Data inputData = getInputData();
        System.out.println("hl----------worker=" + Arrays.asList(inputData.getStringArray("worker")));
        return Result.success();
    }
}
