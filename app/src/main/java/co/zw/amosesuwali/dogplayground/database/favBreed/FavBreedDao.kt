package co.zw.amosesuwali.dogplayground.database.favBreed

import androidx.room.Dao
import androidx.room.Query

@Dao
interface FavBreedDao {

    @Query("SELECT * FROM FavBreedEntity ORDER BY breedName ASC")
    fun getAll(): List<FavBreedEntity>

//    @Query("SELECT * FROM schedule WHERE stop_name = :stopName ORDER BY arrival_time ASC")
//    fun getByStopName(stopName: String): List<Schedule>
}