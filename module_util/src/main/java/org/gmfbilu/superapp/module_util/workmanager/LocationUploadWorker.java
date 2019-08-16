package org.gmfbilu.superapp.module_util.workmanager;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class LocationUploadWorker extends Worker {


    public LocationUploadWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    /**
     * 它是执行在一个单独的后台线程里的。所有需要在后台执行的任务都在这个方法里完成
     */
    @NonNull
    @Override
    public Result doWork() {
        return null;
    }
}
