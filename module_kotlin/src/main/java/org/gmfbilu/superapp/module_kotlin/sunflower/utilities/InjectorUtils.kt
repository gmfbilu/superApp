package org.gmfbilu.superapp.module_kotlin.sunflower.utilities

import android.content.Context
import com.orhanobut.logger.Logger
import org.gmfbilu.superapp.module_kotlin.sunflower.data.AppDatabase
import org.gmfbilu.superapp.module_kotlin.sunflower.data.PlantDao
import org.gmfbilu.superapp.module_kotlin.sunflower.data.PlantRepository
import org.gmfbilu.superapp.module_kotlin.sunflower.viewmodels.PlantListViewModelFactory

/**
 * Static methods used to inject classes needed for various Activities and Fragments.
 */
object InjectorUtils {

    private fun getPlantRepository(context: Context): PlantRepository {
        val appDatabase: AppDatabase = AppDatabase.getInstance(context.applicationContext)
        val plantDao: PlantDao = appDatabase.plantDao()
        Logger.d("InjectorUtils getPlantRepository")
        return PlantRepository.getInstance(plantDao)
    }

/*    private fun getGardenPlantingRepository(context: Context): GardenPlantingRepository {
        return GardenPlantingRepository.getInstance(AppDatabase.getInstance(context.applicationContext).gardenPlantingDao())
    }*/

    /*   fun provideGardenPlantingListViewModelFactory(context: Context): GardenPlantingListViewModelFactory {
           val repository = getGardenPlantingRepository(context)
           return GardenPlantingListViewModelFactory(repository)
       }*/

    fun providePlantListViewModelFactory(context: Context): PlantListViewModelFactory {
        val repository = getPlantRepository(context)
        Logger.d("InjectorUtils providePlantListViewModelFactory")
        return PlantListViewModelFactory(repository)
    }

    /*  fun providePlantDetailViewModelFactory(context: Context, plantId: String): PlantDetailViewModelFactory {
          return PlantDetailViewModelFactory(getPlantRepository(context), getGardenPlantingRepository(context), plantId)
      }*/
}