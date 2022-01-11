package co.zw.amosesuwali.dogplayground.models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.zw.amosesuwali.dogplayground.network.DogCeoApi
import kotlinx.coroutines.launch

class SelectFavBreedViewModel : ViewModel() {
    enum class DogCeoApiStatus { LOADING, ERROR, DONE }
    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<DogCeoApiStatus>()

    // The external immutable LiveData for the request status
    val status: LiveData<DogCeoApiStatus> = _status

    // Internally, we use a MutableLiveData, because we will be updating the List of MarsPhoto
    // with new values
    private val _dogBreeds = MutableLiveData<BreedType>()

    // The external LiveData interface to the property is immutable, so only this class can modify
    val photos: LiveData<BreedType> = _dogBreeds

    /**
     * Call getMarsPhotos() on init so we can display status immediately.
     */
    init {
        getDogBreed()
    }

    /**
     * Gets Mars photos information from the Mars API Retrofit service and updates the
     * [MarsPhoto] [List] [LiveData].
     */
    fun getDogBreed() {
        Log.d("__________________", "__________________ We are doing so,___________")
        viewModelScope.launch {
            _status.value = DogCeoApiStatus.LOADING
            try {
                var dogBreedsResponse: DogBreeds = DogCeoApi.retrofitService.getDogBreedList()
                _dogBreeds.value = dogBreedsResponse.breedType
                _status.value = DogCeoApiStatus.DONE
                Log.d("__________________", "__________________ SERVER RESPONSE SUCCESS___________")
                Log.d("__________________",_dogBreeds.value.toString())
            } catch (e: Exception) {
                Log.d("__________________","__________________ FAILED RESPONSE___________")
                Log.d("________________value__",_dogBreeds.value.toString())
                Log.d("__________________",e.message.toString())
                _status.value = DogCeoApiStatus.ERROR
                _dogBreeds.value = null
            }
        }
    }
}