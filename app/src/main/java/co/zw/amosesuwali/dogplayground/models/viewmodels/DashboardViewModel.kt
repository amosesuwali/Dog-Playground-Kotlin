package co.zw.amosesuwali.dogplayground.models.viewmodels

import android.util.Log
import androidx.lifecycle.*
import androidx.navigation.findNavController
import co.zw.amosesuwali.dogplayground.Dashboard
import co.zw.amosesuwali.dogplayground.helpers.uibinders.FavBreedIconListAdapter
import co.zw.amosesuwali.dogplayground.database.favBreed.FavBreedDao
import co.zw.amosesuwali.dogplayground.database.favBreed.FavBreedRepository
import co.zw.amosesuwali.dogplayground.models.BreedDetailModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class DashboardViewModel(private val favBreedDao: FavBreedDao) : ViewModel() {

    private var _favouriteDogBreeds = MutableLiveData<List<BreedDetailModel>>()
    val favouriteDogBreeds = _favouriteDogBreeds
    val favBreedsListAdapter= FavBreedIconListAdapter({})
    init {
//        getSavedFavouriteBreeds()
    }

//     fun getSavedFavouriteBreeds(): MutableLiveData<List<BreedDetailModel>> = favBreedDao.getSavedFavBreeds().collect()
    suspend fun getSavedFavouriteBreeds(){
    favBreedDao.getSavedFavBreeds().collect{
        _favouriteDogBreeds.value=it
    }
    }


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
