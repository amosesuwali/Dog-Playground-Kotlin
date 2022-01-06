package co.zw.amosesuwali.dogplayground.models

import com.squareup.moshi.Json

data class Pictures(
    @Json(name = "message") val imageList: List<String>,
    val status: String) {
}
