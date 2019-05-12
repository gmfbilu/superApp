package org.gmfbilu.superapp.module_kotlin.sunflower.data

class PlantRepository private constructor(private val plantDao: PlantDao) {


    fun getPlants() = plantDao.getPlants()

    fun getPlant(plantId: String) = plantDao.getPlant(plantId)

    fun getPlantsWithGrowZoneNumber(growZoneNumber: Int) = plantDao.getPlantsWithGrowZoneNumber(growZoneNumber)


    /**
     * TODO 单例的一种写法
     */
    companion object {

        @Volatile
        private var instance: PlantRepository? = null

        fun getInstance(plantDao: PlantDao) = instance ?: synchronized(this) {
            instance ?: PlantRepository(plantDao).also { instance = it }
        }

    }
}