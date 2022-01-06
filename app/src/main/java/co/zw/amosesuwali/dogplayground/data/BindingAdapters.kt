package co.zw.amosesuwali.dogplayground.data

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import co.zw.amosesuwali.dogplayground.R
import coil.load

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        imgView.load(imgUri) {
            placeholder(R.drawable.loading_animation)
            error(R.drawable.ic_broken_image)
        }
    }
}