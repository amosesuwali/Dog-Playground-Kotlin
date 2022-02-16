package co.zw.amosesuwali.dogplayground.models.viewmodels

import android.util.Log
import androidx.lifecycle.*
import co.zw.amosesuwali.dogplayground.helpers.uibinders.BreedListAdapter
import co.zw.amosesuwali.dogplayground.database.favBreed.FavBreedDao
import co.zw.amosesuwali.dogplayground.database.favBreed.FavBreedEntity
import co.zw.amosesuwali.dogplayground.models.BreedDetailModel
import co.zw.amosesuwali.dogplayground.models.BreedRandomResponse
import co.zw.amosesuwali.dogplayground.models.ServerResponse
import co.zw.amosesuwali.dogplayground.api.DogCeoApi
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlin.reflect.full.memberProperties

class SelectFavBreedViewModel(private val favBreedDao: FavBreedDao) : ViewModel() {
    enum class DogCeoApiStatus { LOADING, ERROR, DONE }
    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<DogCeoApiStatus>()

    val dogListAdapter= BreedListAdapter()
    // The external immutable LiveData for the request status
    val status: LiveData<DogCeoApiStatus> = _status
    val isSelectedListEmpty: MutableLiveData<Boolean> = dogListAdapter.isSelectedListNotEmpty
    val selectedBreedsCount: MutableLiveData<String> = dogListAdapter.selectedBreedsCount
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
    class SelectFavBreedViewModelFactory(
        private val favBreedDao: FavBreedDao
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SelectFavBreedViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return SelectFavBreedViewModel(favBreedDao) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }


    /**
     * Gets Mars photos information from the Mars API Retrofit service and updates the
     * [BreedRandomResponse] [List] [LiveData].
     */
    private fun getDogBreedsList() {

        viewModelScope.launch {
            _status.value = DogCeoApiStatus.LOADING
            try {
                val serverResponseResponse: ServerResponse = DogCeoApi.retrofitService.getDogBreedList()
                val tempList = mutableListOf<BreedDetailModel>()
                for (dogBreed in serverResponseResponse.message::class.memberProperties) {
                    tempList.add(BreedDetailModel(dogBreed.name,  ""))
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

    private suspend fun getBreedImage(breedName:String) :String{
        var breedUrlImage =breedName
        viewModelScope.async {
            try {
                val serverResponseResponse: BreedRandomResponse = DogCeoApi.retrofitService.getDogBreedRandomImage(breedName)
               breedUrlImage = serverResponseResponse.message
                Log.d("__Get__breedUrlImage__", breedUrlImage)
            } catch (e: Exception) {
                Log.d("__________________",e.message.toString())
            }
        }.await()

        return breedUrlImage
    }

    fun searchBreedFromFavList(breed:CharSequence){
         _dogBreeds.value= _dogBreedsLive.value
         if(breed.isNotEmpty()){
             _dogBreeds.value=_dogBreeds.value?.filter { it.breedName.contains(breed,true) }
         }else{
             _dogBreeds.value= _dogBreedsLive.value
         }
    }

  suspend fun addSelectedFavBreeds(){
        viewModelScope.async{
//            favBreedDao.deleteAll()
            dogListAdapter.selectedBreeds.value?.forEach {
                favBreedDao.insertAll(FavBreedEntity(0,it.breedName,getBreedImage(it.breedName)))
            }
        }.await()

    }
}