package cd.ghost.statistics

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import cd.ghost.common.helper.setupSnackbar
import cd.ghost.common.helper.viewBinding
import cd.ghost.statistics.databinding.FragStatisticsBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StatisticsFragment : Fragment(R.layout.frag_statistics) {

    private val viewModel by viewModels<StatisticsViewModel>()

    private val binding by viewBinding<FragStatisticsBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            viewModel.state.observe(viewLifecycleOwner) { state ->
                refreshLayout.isRefreshing = state.dataLoading
                statsActiveText.text = state.activeTasksPercent.toString()
                statsCompletedText.text = state.completedTasksPercent.toString()
            }

            view.setupSnackbar(viewLifecycleOwner, viewModel.error, Snackbar.LENGTH_SHORT)

            refreshLayout.setOnRefreshListener {
                viewModel.refresh()
            }
        }
    }
}