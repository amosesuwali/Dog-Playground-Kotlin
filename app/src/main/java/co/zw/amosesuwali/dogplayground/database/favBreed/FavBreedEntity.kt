package co.zw.amosesuwali.dogplayground.database.favBreed

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.random.Random

@Entity
data class FavBreedEntity(
    @PrimaryKey(autoGenerate = true,) val id: Int= Random(9).nextInt(),
    @NonNull @ColumnInfo(name = "breedName") val breedName: String,
    @NonNull @ColumnInfo(name = "breedImageURL") val breedImageURL: String
){

}
