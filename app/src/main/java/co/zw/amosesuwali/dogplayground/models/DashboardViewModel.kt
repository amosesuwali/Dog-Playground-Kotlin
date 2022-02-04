package co.zw.amosesuwali.dogplayground.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.zw.amosesuwali.dogplayground.data.BreedListAdapter
import co.zw.amosesuwali.dogplayground.network.DogCeoApi

class DashboardViewModel : ViewModel() {

    private var _favouriteDogBreeds = MutableLiveData<List<BreedDetailModel>>()
    val favouriteDogBreeds = _favouriteDogBreeds
    val favBreedsListAdapter= BreedListAdapter()
    init {
        getSavedFavouriteBreeds()
    }

    private fun getSavedFavouriteBreeds(){
        _favouriteDogBreeds.value =  mutableListOf<BreedDetailModel>(
            BreedDetailModel("Pitbull","https://images.dog.ceo/breeds/terrier-irish/n02093991_403.jpg"),
            BreedDetailModel("Rotwiller","https://images.dog.ceo/breeds/terrier-irish/n02093991_403.jpg"),
            BreedDetailModel("Africa","https://images.dog.ceo/breeds/terrier-irish/n02093991_403.jpg"),
            BreedDetailModel("Chihuahua","https://images.dog.ceo/breeds/terrier-irish/n02093991_403.jpg"),
        )
    }

}