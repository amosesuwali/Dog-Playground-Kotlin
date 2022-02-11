package co.zw.amosesuwali.dogplayground.database.favBreed

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import co.zw.amosesuwali.dogplayground.models.BreedDetailModel

@Dao
interface FavBreedDao {

    @Query("SELECT * FROM FavBreedEntity ORDER BY breedName ASC")
     fun getSavedFavBreeds(): MutableList<BreedDetailModel>
    @Insert
     fun insertAll(vararg FavBreedEntity: FavBreedEntity)

    @Query("DELETE FROM FavBreedEntity")
     fun deleteAll()
//    @Query("SELECT * FROM schedule WHERE stop_name = :stopName ORDER BY arrival_time ASC")
//    fun getByStopName(stopName: String): List<Schedule>
}