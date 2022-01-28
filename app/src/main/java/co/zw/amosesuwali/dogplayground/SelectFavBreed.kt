package co.zw.amosesuwali.dogplayground

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import co.zw.amosesuwali.dogplayground.data.BreedListAdapter
import co.zw.amosesuwali.dogplayground.data.PhotoGridAdapter
import co.zw.amosesuwali.dogplayground.databinding.FragmentFirstBinding
import co.zw.amosesuwali.dogplayground.databinding.FragmentSelectFavBreedBinding
import co.zw.amosesuwali.dogplayground.models.FirstScreenViewModel
import co.zw.amosesuwali.dogplayground.models.SelectFavBreedViewModel


class SelectFavBreed : Fragment() {

    private val viewModel: SelectFavBreedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSelectFavBreedBinding.inflate(inflater)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this


        // Giving the binding access to the OverviewViewModel
        binding.viewModel = viewModel

        // Sets the adapter of the photosGrid RecyclerView
        binding.photosGrid.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.photosGrid.adapter = BreedListAdapter()
//        viewModel.selectedBreeds=
        binding.selectedBreeds = viewModel.selectedBreeds.value?.length.toString()
        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
        return binding.root
    }
}