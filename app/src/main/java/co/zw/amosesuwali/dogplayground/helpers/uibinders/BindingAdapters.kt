package co.zw.amosesuwali.dogplayground.helpers.uibinders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import co.zw.amosesuwali.dogplayground.R
import co.zw.amosesuwali.dogplayground.models.BreedDetailModel
import co.zw.amosesuwali.dogplayground.models.BreedRandomResponse
import co.zw.amosesuwali.dogplayground.models.viewmodels.DogCeoApiStatus
import coil.load

/**
 * Updates the data shown in the [RecyclerView].
 */
@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<String>?) {
    val adapter = recyclerView.adapter as PhotoGridAdapter
//recyclerView.layoutParams=(RecyclerView.LayoutParams(recyclerView.layoutParams.width+ 20, recyclerView.layoutParams.height))

    adapter.submitList(data)
}

@BindingAdapter("breedListData")
fun bindBreedRecyclerView(recyclerView: RecyclerView, data: List<BreedDetailModel>?) {
    val adapter = recyclerView.adapter as BreedListAdapter
    adapter.submitList(data)
}


@BindingAdapter("selectedFavBreedsCount")
fun bindSelectedFavBreeds(selectedFavBreedsCountView: TextView, data: String?) {
    selectedFavBreedsCountView.text=data.toString()
}
@BindingAdapter("totalFavBreedsCount")
fun bindTotalFavBreedsCount(totalFavBreedsCount: TextView, data: String?) {
    totalFavBreedsCount.text=" out of "+data.toString()
}

/**
 * Uses the Coil library to load an image by URL into an [ImageView]
 */
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

@BindingAdapter("breedImageUrl")
fun bindBreedImage(imgView: ImageView, imgUrl: BreedRandomResponse?) {
    imgUrl?.let {
        val imgUri = imgUrl.message.toUri().buildUpon().scheme("https").build()
        imgView.load(imgUri) {
            placeholder(R.drawable.loading_animation)
            error(R.drawable.ic_broken_image)
        }
    }
}
/**
 * This binding adapter displays the [DogCeoApiStatus] of the network request in an image view.  When
 * the request is loading, it displays a loading_animation.  If the request has an error, it
 * displays a broken image to reflect the connection error.  When the request is finished, it
 * hides the image view.
 */
@BindingAdapter("marsApiStatus")
fun bindStatus(statusImageView: ImageView, status: DogCeoApiStatus?) {
    when (status) {
        DogCeoApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        DogCeoApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_broken_image)
        }
        DogCeoApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}


//Favourite Breed Icons Binding Adapter
@BindingAdapter("favBreedListData")
fun bindFavBreedIconRecyclerView(recyclerView: RecyclerView, data: List<BreedDetailModel>?) {
    val adapter = recyclerView.adapter as FavBreedIconListAdapter
    adapter.submitList(data)
}
