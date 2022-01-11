package co.zw.amosesuwali.dogplayground.models

import com.squareup.moshi.Json

data class DogBreeds(
    @Json(name = "message") val breedType: BreedType,
    val status: String
)