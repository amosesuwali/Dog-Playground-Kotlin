package co.zw.amosesuwali.dogplayground

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import co.zw.amosesuwali.dogplayground.database.app.DogPlayGroundApplication
import co.zw.amosesuwali.dogplayground.databinding.FragmentSelectFavBreedBinding
import co.zw.amosesuwali.dogplayground.helpers.GridSpacingItemDecorationHelper
import co.zw.amosesuwali.dogplayground.models.viewmodels.SelectFavBreedViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext


class SelectFavBreed : Fragment() {

//    private val viewModel: SelectFavBreedViewModel by viewModels()
    private val viewModel: SelectFavBreedViewModel by activityViewModels {
    SelectFavBreedViewModel.SelectFavBreedViewModelFactory(
        (activity?.application as DogPlayGroundApplication).database.favBreedDao()
    )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSelectFavBreedBinding.inflate(inflater)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        // Sets the adapter of the photosGrid RecyclerView
        binding.breedGrid.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.breedGrid.adapter = viewModel.dogListAdapter
        binding.breedGrid.addItemDecoration(
            GridSpacingItemDecorationHelper(
                2,
                50,
                false
            )
        )
        // Giving the binding access to the OverviewViewModel
        binding.viewModel = viewModel

        binding.buttonFirst.setOnClickListener {

            viewModel.viewModelScope.async {
                viewModel.addSelectedFavBreeds()
                withContext (Dispatchers.Main) {
//                    findNavController().navigate(R.id.action_selectFavBreed_to_dashboard)
                }
            }.onAwait

        }
        return binding.root
    }

    fun basicAlert(){
//        var title = "KotlinApp"
//        val progressDialog = ProgressDialog(this@MainActivity)
//        progressDialog.setTitle("Kotlin Progress Bar")
//        progressDialog.setMessage("Application is loading, please wait")
//        progressDialog.show()
    }
}


