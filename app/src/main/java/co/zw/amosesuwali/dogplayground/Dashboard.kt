package co.zw.amosesuwali.dogplayground

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import co.zw.amosesuwali.dogplayground.database.app.DogPlayGroundApplication
import co.zw.amosesuwali.dogplayground.databinding.DashboardFragmentBinding
import co.zw.amosesuwali.dogplayground.helpers.GridSpacingItemDecorationHelper
import co.zw.amosesuwali.dogplayground.models.viewmodels.DashboardViewModel
import co.zw.amosesuwali.dogplayground.models.viewmodels.DashboardViewModelFactory
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class Dashboard : Fragment() {

    companion object {
        fun newInstance() = Dashboard()
    }

private val dashboardViewModel: DashboardViewModel by activityViewModels {
    DashboardViewModelFactory(
        (activity?.application as DogPlayGroundApplication).database.favBreedDao()
    )
}
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DashboardFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.favouriteBreedList.addItemDecoration(
            GridSpacingItemDecorationHelper(
                1,
                25,
                true
            )
        )
        // Giving the binding access to the OverviewViewModel
        binding.viewModel = dashboardViewModel
        binding.favouriteBreedList.adapter=dashboardViewModel.favBreedsListAdapter
        binding.playGame.setOnClickListener {
            findNavController().navigate(R.id.action_dashboard_to_gameHome)
        }
        binding.buttonLearn.setOnClickListener {
            findNavController().navigate(R.id.action_dashboard_to_breedDetail)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


}