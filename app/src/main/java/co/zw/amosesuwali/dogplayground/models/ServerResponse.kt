package co.zw.amosesuwali.dogplayground.models

import com.squareup.moshi.Json

data class ServerResponse(
    @Json(name = "message") val message: BreedType,
    val status: String
)