package org.gmfbilu.superapp.module_kotlin.sunflower.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.orhanobut.logger.Logger
import kotlinx.coroutines.coroutineScope
import org.gmfbilu.superapp.module_kotlin.sunflower.data.AppDatabase
import org.gmfbilu.superapp.module_kotlin.sunflower.data.Plant
import org.gmfbilu.superapp.module_kotlin.sunflower.utilities.PLANT_DATA_FILENAME

class SeedDatabaseWorker(context: Context, workerParams: WorkerParameters) : CoroutineWorker(context, workerParams)  {


    private val TAG by lazy { SeedDatabaseWorker::class.java.simpleName }

    /**
     * TODO 这个方法只会在app首次安装进入程序的时候调用，而且是子线程
     */
    override suspend fun doWork(): Result = coroutineScope {
        try {
            applicationContext.assets.open(PLANT_DATA_FILENAME).use { inputStream ->
                JsonReader(inputStream.reader()).use { jsonReader ->
                    val plantType = object : TypeToken<List<Plant>>() {}.type
                    val plantList: List<Plant> = Gson().fromJson(jsonReader, plantType)
                    Logger.d(plantList.toString())
                    val database = AppDatabase.getInstance(applicationContext)
                    database.plantDao().insertAll(plantList)
                    Result.success()
                }
            }
        } catch (ex: Exception) {
            Log.e(TAG, "Error seeding database", ex)
            Result.failure()
        }
    }
}