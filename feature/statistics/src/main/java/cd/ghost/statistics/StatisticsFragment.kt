package cd.ghost.statistics

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import cd.ghost.common.helper.setupSnackbar
import cd.ghost.common.helper.viewBinding
import cd.ghost.statistics.databinding.FragStatisticsBinding
import cd.ghost.statistics.di.StatisticsSubcompProvider
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class StatisticsFragment : Fragment(R.layout.frag_statistics) {

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private val viewModel by viewModels<StatisticsViewModel> { factory }

    private val binding by viewBinding<FragStatisticsBinding>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity() as StatisticsSubcompProvider)
            .provideStatisticsSubComp()
            .create()
            .inject(this)
    }

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