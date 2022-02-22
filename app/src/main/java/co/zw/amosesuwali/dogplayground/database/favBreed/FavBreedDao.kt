package co.zw.amosesuwali.dogplayground.database.favBreed

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import co.zw.amosesuwali.dogplayground.models.BreedDetailModel
import kotlinx.coroutines.flow.Flow

@Dao
interface FavBreedDao {

    @Query("SELECT * FROM FavBreedEntity ORDER BY breedName ASC")
      fun getSavedFavBreeds(): Flow<List<BreedDetailModel>>
    @Insert
     fun insertAll(vararg favEntity: FavBreedEntity)

    @Query("DELETE FROM FavBreedEntity")
     fun deleteAll()

    @Query("SELECT * FROM FavBreedEntity ORDER BY breedName ASC")
    fun getAll(): Flow<List<BreedDetailModel>>


//    @Query("SELECT * FROM schedule WHERE stop_name = :stopName ORDER BY arrival_time ASC")
//    fun getByStopName(stopName: String): List<Schedule>
}