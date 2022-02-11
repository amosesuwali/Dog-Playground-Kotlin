package co.zw.amosesuwali.dogplayground

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import co.zw.amosesuwali.dogplayground.databinding.FragmentSelectFavBreedBinding
import co.zw.amosesuwali.dogplayground.helpers.GridSpacingItemDecorationHelper
import co.zw.amosesuwali.dogplayground.models.SelectFavBreedViewModel


class SelectFavBreed : Fragment() {

    private val viewModel: SelectFavBreedViewModel by viewModels()

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
            viewModel.addSelectedFavBreeds()
            findNavController().navigate(R.id.action_selectFavBreed_to_dashboard)
        }
        return binding.root
    }
}


