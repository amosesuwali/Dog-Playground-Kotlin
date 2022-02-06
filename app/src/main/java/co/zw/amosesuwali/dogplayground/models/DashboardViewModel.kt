package co.zw.amosesuwali.dogplayground.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.zw.amosesuwali.dogplayground.data.FavBreedIconListAdapter

class DashboardViewModel : ViewModel() {

    private var _favouriteDogBreeds = MutableLiveData<List<BreedDetailModel>>()
    val favouriteDogBreeds = _favouriteDogBreeds
    val favBreedsListAdapter= FavBreedIconListAdapter()
    init {
        getSavedFavouriteBreeds()
    }

    private fun getSavedFavouriteBreeds(){
        val tempUrl="https://images.dog.ceo/breeds/terrier-irish/n02093991_403.jpg"
        _favouriteDogBreeds.value =  mutableListOf(
            BreedDetailModel("Pitbull",tempUrl),
            BreedDetailModel("Rotwiller",tempUrl),
            BreedDetailModel("Africa",tempUrl),
            BreedDetailModel("Chihuahua",tempUrl),
        )
    }

}