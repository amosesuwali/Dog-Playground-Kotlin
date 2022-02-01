package co.zw.amosesuwali.dogplayground.models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.zw.amosesuwali.dogplayground.data.BreedListAdapter
import co.zw.amosesuwali.dogplayground.network.BASE_URL
import co.zw.amosesuwali.dogplayground.network.DogCeoApi
import kotlinx.coroutines.launch
import kotlin.reflect.full.memberProperties

class SelectFavBreedViewModel : ViewModel() {
    enum class DogCeoApiStatus { LOADING, ERROR, DONE }
    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<DogCeoApiStatus>()

    val dogListAdapter= BreedListAdapter()
    // The external immutable LiveData for the request status
    val status: LiveData<DogCeoApiStatus> = _status
    val selectedBreedsCount: MutableLiveData<String> = dogListAdapter.selectedBreedsCount

    // Internally, we use a MutableLiveData, because we will be updating the List of MarsPhoto
    // with new values
    private val _dogBreeds = MutableLiveData<List<BreedDetailModel>>()

    // The external LiveData interface to the property is immutable, so only this class can modify
    val dogBreeds: LiveData<List<BreedDetailModel>> = _dogBreeds
    val selectedBreeds =MutableLiveData<String>("0")
    /**
     * Call getMarsPhotos() on init so we can display status immediately.
     */
    init {
        getDogBreedsList()
    }

    /**
     * Gets Mars photos information from the Mars API Retrofit service and updates the
     * [MarsPhoto] [List] [LiveData].
     */
    private fun getDogBreedsList() {
        Log.d("__________________", "__________________ We fetching breeds doing so,___________")
        viewModelScope.launch {
            _status.value = DogCeoApiStatus.LOADING
            try {
                var dogBreedsResponse: DogBreeds = DogCeoApi.retrofitService.getDogBreedList()
                Log.d("__________________", "__________________ SERVER RESPONSE SUCCESS___________")
                val tempList = mutableListOf<BreedDetailModel>()
                for (dogBreed in dogBreedsResponse.breedType::class.memberProperties) {
                    tempList.add(BreedDetailModel(dogBreed.name, "https://images.dog.ceo/breeds/buhund-norwegian/hakon3.jpg"))
                }
                selectedBreeds.value=tempList.size.toString()
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

}