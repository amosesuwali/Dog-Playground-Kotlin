package co.zw.amosesuwali.dogplayground.models

import com.squareup.moshi.Json

data class DogPicture( @Json(name = "message") val pictureUrl: String) {
}