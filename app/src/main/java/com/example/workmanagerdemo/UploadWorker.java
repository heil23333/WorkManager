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
        Data data = getInputData();
        String s = data.getString("图片");
        uploadImages(s);
        return Result.success();
    }

    private void uploadImages(String s) {
        System.out.println(String.format("hl-----------%s上传成功", (s == null ? "图片" : s)));
    }
}
