package co.zw.amosesuwali.dogplayground.models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.zw.amosesuwali.dogplayground.network.BASE_URL
import co.zw.amosesuwali.dogplayground.network.DogCeoApi
import kotlinx.coroutines.launch
import kotlin.reflect.full.memberProperties

class SelectFavBreedViewModel : ViewModel() {
    enum class DogCeoApiStatus { LOADING, ERROR, DONE }
    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<DogCeoApiStatus>()

    // The external immutable LiveData for the request status
    val status: LiveData<DogCeoApiStatus> = _status

    // Internally, we use a MutableLiveData, because we will be updating the List of MarsPhoto
    // with new values
    private val _dogBreeds = MutableLiveData<List<BreedDetailModel>>()

    // The external LiveData interface to the property is immutable, so only this class can modify
    val dogBreeds: LiveData<List<BreedDetailModel>> = _dogBreeds

    /**
     * Call getMarsPhotos() on init so we can display status immediately.
     */
    init {
        getDogBreeds()
    }

    /**
     * Gets Mars photos information from the Mars API Retrofit service and updates the
     * [MarsPhoto] [List] [LiveData].
     */
    private fun getDogBreeds() {
        Log.d("__________________", "__________________ We fetching breeds doing so,___________")
        viewModelScope.launch {
            _status.value = DogCeoApiStatus.LOADING
            try {
                var dogBreedsResponse: DogBreeds = DogCeoApi.retrofitService.getDogBreedList()
                Log.d("__________________", "__________________ SERVER RESPONSE SUCCESS___________")
                val tempList : MutableList<BreedDetailModel> = mutableListOf<BreedDetailModel>()
                for (dogBreed in dogBreedsResponse.breedType::class.memberProperties) {
                    tempList.add(BreedDetailModel(dogBreed.name, "https://images.dog.ceo/breeds/buhund-norwegian/hakon3.jpg"))
                }
                _dogBreeds.value =  tempList
                _status.value = DogCeoApiStatus.DONE

            } catch (e: Exception) {
                Log.d("__________________","__________________ FAILED RESPONSE___________")
                Log.d("__________________",e.message.toString())
                _status.value = DogCeoApiStatus.ERROR
                _dogBreeds.value = null
            }
        }
    }

    public fun getDogBreedImage(dogBreed: String) : String{
        Log.d("__________________", "GETTING_RANDOM_BREED")
        var breedImageURL:String =""
        viewModelScope.launch {
            try {
                var breedRandomImageResponse: BreedRandomResponse = DogCeoApi.retrofitService.getDogBreedRandomImage(dogBreed)
                breedImageURL =  breedRandomImageResponse.message
            } catch (e: Exception) {
                Log.d("__________________","FAILED_GETTING_RANDOM_BREED_IMAGE_RESPONSE")
                Log.d("__________________",e.message.toString())
            }

        }
        return breedImageURL

    }
}