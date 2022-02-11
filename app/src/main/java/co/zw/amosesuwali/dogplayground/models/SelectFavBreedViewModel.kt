package co.zw.amosesuwali.dogplayground.models

import android.util.Log
import androidx.lifecycle.*
import co.zw.amosesuwali.dogplayground.data.BreedListAdapter
import co.zw.amosesuwali.dogplayground.database.favBreed.FavBreedDao
import co.zw.amosesuwali.dogplayground.database.favBreed.FavBreedEntity
import co.zw.amosesuwali.dogplayground.network.DogCeoApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.reflect.full.memberProperties

class SelectFavBreedViewModel(private val favBreedDao: FavBreedDao) : ViewModel() {
    enum class DogCeoApiStatus { LOADING, ERROR, DONE }
    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<DogCeoApiStatus>()

    val dogListAdapter= BreedListAdapter()
    // The external immutable LiveData for the request status
    val status: LiveData<DogCeoApiStatus> = _status
    val selectedBreedsCount: MutableLiveData<String> = dogListAdapter.selectedBreedsCount
    val selectedBreedsList: MutableLiveData<MutableList<BreedDetailModel>> = dogListAdapter.selectedBreeds
    val totalBreedsCount=MutableLiveData<String>("0")

    // Internally, we use a MutableLiveData, because we will be updating the List of MarsPhoto
    // with new values
    private val _dogBreeds = MutableLiveData<List<BreedDetailModel>>()
    private val _dogBreedsLive = MutableLiveData<List<BreedDetailModel>>()

    // The external LiveData interface to the property is immutable, so only this class can modify
    val dogBreeds: LiveData<List<BreedDetailModel>> = _dogBreeds
    val selectedBreeds =MutableLiveData<String>("0")
    /**
     * Call getMarsPhotos() on init so we can display status immediately.
     */
    init {
        getDogBreedsList()
    }
    class DashboardViewModelFactory(
        private val favBreedDao: FavBreedDao
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DashboardViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return DashboardViewModel(favBreedDao) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }


    /**
     * Gets Mars photos information from the Mars API Retrofit service and updates the
     * [BreedType] [List] [LiveData].
     */
    private fun getDogBreedsList() {
        Log.d("__________________", "__________________ We fetching breeds doing so,___________")
        viewModelScope.launch {
            _status.value = DogCeoApiStatus.LOADING
            try {
                val dogBreedsResponse: DogBreeds = DogCeoApi.retrofitService.getDogBreedList()
                Log.d("__________________", "__________________ SERVER RESPONSE SUCCESS___________")
                val tempList = mutableListOf<BreedDetailModel>()
                for (dogBreed in dogBreedsResponse.breedType::class.memberProperties) {
                    tempList.add(BreedDetailModel(dogBreed.name, "https://images.dog.ceo/breeds/buhund-norwegian/hakon3.jpg"))
                }
                selectedBreeds.value=tempList.size.toString()
                _dogBreeds.value =  tempList
                _dogBreedsLive.value=tempList
                _status.value = DogCeoApiStatus.DONE
                totalBreedsCount.value=tempList.size.toString()
            } catch (e: Exception) {
                Log.d("__________________",e.message.toString())
                _status.value = DogCeoApiStatus.ERROR
            }
        }
    }


     fun searchBreedFromFavList(breed:CharSequence){
         _dogBreeds.value= _dogBreedsLive.value
         if(breed.isNotEmpty()){
             _dogBreeds.value=_dogBreeds.value?.filter { it.breedName.contains(breed,true) }
         }else{
             _dogBreeds.value= _dogBreedsLive.value
         }
    }

    fun addSelectedFavBreeds(){
        val tempUrl="https://images.dog.ceo/breeds/terrier-irish/n02093991_403.jpg"
        GlobalScope.launch(Dispatchers.IO) {
            selectedBreedsList.value?.forEach {
                favBreedDao.insertAll(FavBreedEntity(0,it.breedName,it.breedImageURL))
            }
        }
    }
}