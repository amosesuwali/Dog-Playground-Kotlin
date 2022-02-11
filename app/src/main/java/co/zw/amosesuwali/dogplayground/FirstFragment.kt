package co.zw.amosesuwali.dogplayground

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import co.zw.amosesuwali.dogplayground.data.PhotoGridAdapter
import co.zw.amosesuwali.dogplayground.databinding.FragmentFirstBinding
import co.zw.amosesuwali.dogplayground.helpers.GridSpacingItemDecorationHelper
import co.zw.amosesuwali.dogplayground.models.viewmodels.FirstScreenViewModel


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private val viewModel: FirstScreenViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentFirstBinding.inflate(inflater)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        // Giving the binding access to the OverviewViewModel
        binding.viewModel = viewModel

        // Sets the adapter of the photosGrid RecyclerView
        binding.photosGrid.layoutManager = StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.HORIZONTAL)
        binding.photosGrid.layoutParams.width = Resources.getSystem().displayMetrics.widthPixels + (Resources.getSystem().displayMetrics.widthPixels*(0.3)).toInt()
        binding.photosGrid.adapter = PhotoGridAdapter()
        binding.photosGrid.addItemDecoration(
            GridSpacingItemDecorationHelper(
                5,
                5,
                false
            )
        )
        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_selectFavBreed)
        }
        return binding.root

    }

}