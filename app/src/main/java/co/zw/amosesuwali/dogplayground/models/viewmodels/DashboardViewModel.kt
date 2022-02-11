package co.zw.amosesuwali.dogplayground.models.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import co.zw.amosesuwali.dogplayground.data.FavBreedIconListAdapter
import co.zw.amosesuwali.dogplayground.database.favBreed.FavBreedDao
import co.zw.amosesuwali.dogplayground.models.BreedDetailModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DashboardViewModel(private val favBreedDao: FavBreedDao) : ViewModel() {

    private var _favouriteDogBreeds = MutableLiveData<List<BreedDetailModel>>()
    val favouriteDogBreeds = _favouriteDogBreeds
    val favBreedsListAdapter= FavBreedIconListAdapter()

    init {
        getSavedFavouriteBreeds()
    }

     private fun getSavedFavouriteBreeds(){
         GlobalScope.launch(Dispatchers.IO) {
             _favouriteDogBreeds.postValue(favBreedDao.getSavedFavBreeds())
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
