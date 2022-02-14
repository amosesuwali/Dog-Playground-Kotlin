package co.zw.amosesuwali.dogplayground

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import co.zw.amosesuwali.dogplayground.database.app.DogPlayGroundApplication
import co.zw.amosesuwali.dogplayground.databinding.FragmentSelectFavBreedBinding
import co.zw.amosesuwali.dogplayground.helpers.GridSpacingItemDecorationHelper
import co.zw.amosesuwali.dogplayground.models.viewmodels.SelectFavBreedViewModel
import kotlinx.coroutines.*
import android.widget.ProgressBar
import android.R
import androidx.appcompat.app.AlertDialog


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

            GlobalScope.async(Dispatchers.IO) {
                viewModel.addSelectedFavBreeds()
                withContext (Dispatchers.Main) {
                    findNavController().navigate(R.id.action_selectFavBreed_to_dashboard)
                }
            }.onAwait

        }
        return binding.root
    }

    fun basicAlert(){

        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setCancelable(false) // if you want user to wait for some process to finish,

        builder.setView(R.layout.layout_loading_dialog)
        val dialog: AlertDialog = builder.create()
    }
}


