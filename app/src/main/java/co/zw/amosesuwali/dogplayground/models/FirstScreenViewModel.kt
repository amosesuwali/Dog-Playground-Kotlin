

package co.zw.amosesuwali.dogplayground.models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.zw.amosesuwali.dogplayground.network.DogCeoApi
import kotlinx.coroutines.launch

enum class DogCeoApiStatus { LOADING, ERROR, DONE }

/**
 * The [ViewModel] that is attached to the [OverviewFragment].
 */
class FirstScreenViewModel : ViewModel() {

    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<DogCeoApiStatus>()

    // The external immutable LiveData for the request status
    val status: LiveData<DogCeoApiStatus> = _status

    // Internally, we use a MutableLiveData, because we will be updating the List of MarsPhoto
    // with new values
    private val _photos = MutableLiveData<List<Pictures>>()

    // The external LiveData interface to the property is immutable, so only this class can modify
    val photos: LiveData<List<Pictures>> = _photos

    /**
     * Call getMarsPhotos() on init so we can display status immediately.
     */
    init {
        getMarsPhotos()
    }

    /**
     * Gets Mars photos information from the Mars API Retrofit service and updates the
     * [MarsPhoto] [List] [LiveData].
     */
    public fun getMarsPhotos() {
        Log.d("__________________", "__________________ We are doing so,___________")
        viewModelScope.launch {
            _status.value = DogCeoApiStatus.LOADING
            try {
                _photos.value = DogCeoApi.retrofitService.getPhotos()
                _status.value = DogCeoApiStatus.DONE
                Log.d("__________________", "__________________ SERVER RESPONSE___________")
                Log.d("__________________",_photos.value.toString())
            } catch (e: Exception) {
                Log.d("__________________","__________________ SERVER RESPONSE___________")
                Log.d("__________________",e.message.toString())
                _status.value = DogCeoApiStatus.ERROR
                _photos.value = listOf()
            }
        }
    }
}
