package co.zw.amosesuwali.dogplayground.network

import com.squareup.moshi.Json

data class DogPicture( @Json(name = "message") val pictureUrl: String) {
}