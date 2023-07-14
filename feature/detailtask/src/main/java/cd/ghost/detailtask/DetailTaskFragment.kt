package cd.ghost.detailtask

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.CheckBox
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import cd.ghost.common.base.BaseArgument
import cd.ghost.common.base.args
import cd.ghost.common.helper.setupRefreshLayout
import cd.ghost.common.helper.setupSnackbar
import cd.ghost.common.helper.viewBinding
import cd.ghost.detailtask.databinding.FragmentDetailTaskBinding
import cd.ghost.detailtask.di.DetailTaskSubcompProvider
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class DetailTaskFragment : Fragment(R.layout.fragment_detail_task) {

    class DetailTaskArgument(
        val taskId: String
    ) : BaseArgument

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val viewModel by viewModels<DetailTaskViewModel> { factory }
    private val binding by viewBinding<FragmentDetailTaskBinding>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity() as DetailTaskSubcompProvider)
            .provideDetailTaskSubcomp()
            .create()
            .inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val task = args<DetailTaskArgument>()?.taskId
        viewModel.start(task)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.setupSnackbar(this, viewModel.snackbarText, Snackbar.LENGTH_SHORT)

        this.setupRefreshLayout(binding.refreshLayout)

        binding.apply {

            viewModel.task.observe(viewLifecycleOwner) {
                taskDetailTitleText.text = it?.title
                taskDetailTitleText.text = it?.description
                taskDetailCompleteCheckbox.isChecked = it?.isCompleted ?: false
            }

            refreshLayout.setOnRefreshListener {
                viewModel.refresh()
            }

            viewModel.dataLoading.observe(viewLifecycleOwner) {
                refreshLayout.isRefreshing = it
                noDataText.isVisible = !it
            }

            viewModel.isDataAvailable.observe(viewLifecycleOwner) {
                linearContainer.isVisible = !it
                relativeContent.isVisible = it
            }

            taskDetailCompleteCheckbox.setOnClickListener {
                viewModel.setCompleted((it as CheckBox).isChecked)
            }

            editTaskFab.setOnClickListener {
                viewModel.editTask()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            cd.ghost.common.R.id.menu_delete -> {
                viewModel.deleteTask()
                true
            }

            else -> false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(cd.ghost.common.R.menu.taskdetail_fragment_menu, menu)
    }
}