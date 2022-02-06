package co.zw.amosesuwali.dogplayground

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import co.zw.amosesuwali.dogplayground.databinding.DashboardFragmentBinding
import co.zw.amosesuwali.dogplayground.helpers.GridSpacingItemDecorationHelper
import co.zw.amosesuwali.dogplayground.models.DashboardViewModel

class Dashboard : Fragment() {

    companion object {
        fun newInstance() = Dashboard()
    }

    private val dashboardViewModel: DashboardViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DashboardFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this
//        binding.favouriteBreedList.layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL)

        binding.favouriteBreedList.addItemDecoration(
            GridSpacingItemDecorationHelper(
                2,
                20,
                false
            )
        )
        // Giving the binding access to the OverviewViewModel
        binding.viewModel = dashboardViewModel
        binding.favouriteBreedList.adapter=dashboardViewModel.favBreedsListAdapter
        return binding.root
    }


}