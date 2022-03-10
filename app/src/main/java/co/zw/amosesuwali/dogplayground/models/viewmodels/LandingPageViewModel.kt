

package co.zw.amosesuwali.dogplayground.models.viewmodels

import android.util.Log
import androidx.lifecycle.*
import co.zw.amosesuwali.dogplayground.models.Pictures
import co.zw.amosesuwali.dogplayground.api.DogCeoApi
import co.zw.amosesuwali.dogplayground.database.favBreed.FavBreedDao
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

enum class DogCeoApiStatus { LOADING, ERROR, DONE }

/**
 * The [ViewModel] that is attached to the LandingPage.
 */
class LandingPageViewModel(private val favBreedDao: FavBreedDao) : ViewModel() {

    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<DogCeoApiStatus>()

    // The external immutable LiveData for the request status
    val status: LiveData<DogCeoApiStatus> = _status

    // Internally, we use a MutableLiveData, because we will be updating the List of MarsPhoto
    // with new values
    private val _photos = MutableLiveData<List<String>>()

    // The external LiveData interface to the property is immutable, so only this class can modify
    val photos: LiveData<List<String>> = _photos

    /**
     * Call getMarsPhotos() on init so we can display status immediately.
     */
    init {
        getDogPhotos()
    }

    /**
     * Gets Mars photos information from the Mars API Retrofit service and updates the
     *  [List] [LiveData].
     */
    private fun getDogPhotos() {
        viewModelScope.launch {
            _status.value = DogCeoApiStatus.LOADING
            try {
                var pictureResponse: Pictures =DogCeoApi.retrofitService.getPhotos()
                _photos.value = pictureResponse.imageList
                _status.value = DogCeoApiStatus.DONE
            } catch (e: Exception) {
                Log.d("__________________",e.message.toString())
                _status.value = DogCeoApiStatus.ERROR
                _photos.value = listOf()
            }
        }
    }

    suspend fun isFavBreedListEmpty (): Boolean{
        return GlobalScope.async() {
            return@async favBreedDao.getFavouriteBreedsCount() < 1
        }.await()
    }

    class LandingPageViewModelFactory(
        private val favBreedDao: FavBreedDao
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LandingPageViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return LandingPageViewModel(favBreedDao) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}
