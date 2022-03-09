package co.zw.amosesuwali.dogplayground

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import co.zw.amosesuwali.dogplayground.database.app.DogPlayGroundApplication
import co.zw.amosesuwali.dogplayground.databinding.FragmentSelectFavBreedBinding
import co.zw.amosesuwali.dogplayground.helpers.GridSpacingItemDecorationHelper
import co.zw.amosesuwali.dogplayground.models.viewmodels.SelectFavBreedViewModel
import kotlinx.coroutines.*


class SelectFavBreed : Fragment() {

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
        val dialogBuilder: AlertDialog.Builder? = activity?.let {
            AlertDialog.Builder(it)
        }
        dialogBuilder?.setView(R.layout.progress_dialog);
        binding.buttonFirst.setOnClickListener {
            // This should be called once in your Fragment's onViewCreated() or in Activity onCreate() method to avoid dialog duplicates.
            dialog = dialogBuilder?.create();
            dialog?.show()

            runBlocking {
                viewModel.addSelectedFavBreeds()
                delay(233)
                openNextPage()
            }


        }
        return binding.root
    }
//
    fun openNextPage(){
    Log.d("Adding Fav to DB","We have finished thank you")
    dialog?.dismiss()
    findNavController().navigate(R.id.action_selectFavBreed_to_dashboard)
    }

    var dialog : Dialog? = context?.let { Dialog(it) }

}


