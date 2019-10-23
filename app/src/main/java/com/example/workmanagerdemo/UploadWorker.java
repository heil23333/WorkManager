package com.example.workmanagerdemo;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class UploadWorker extends Worker {
    private Context context;
    public UploadWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
    }

    @NonNull
    @Override
    public Result doWork() {
        //这里不是主线程哦
        Data inputData = getInputData();
        String s = inputData.getString("图片");
        Data outputData = new Data.Builder()
                .putString("result", (s == null ? "失败" : "成功"))
                .putString("worker", "UploadWorker")
                .build();
        uploadImages(s);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Result.success(outputData);
//        return Result.retry();//返回retry的话会尝试重试
    }

    private void uploadImages(String s) {
        System.out.println(String.format("hl-----------%s上传成功", (s == null ? "图片" : s)));
    }
}
