package cd.ghost.addedittask

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import cd.ghost.addedittask.databinding.FragmentAddEditTaskBinding
import cd.ghost.common.base.BaseArgument
import cd.ghost.common.base.args
import cd.ghost.common.helper.setupRefreshLayout
import cd.ghost.common.helper.setupSnackbar
import cd.ghost.common.helper.viewBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddEditTaskFragment : Fragment(R.layout.fragment_add_edit_task) {

    class AddEditTaskArgument(
        val taskId: String?
    ) : BaseArgument

    private val viewModel by viewModels<AddEditTaskViewModel>()

    private val binding by viewBinding<FragmentAddEditTaskBinding>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val taskId = args<AddEditTaskArgument>()?.taskId
        viewModel.start(taskId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.setupSnackbar(this, viewModel.snackbarText, Snackbar.LENGTH_SHORT)

//        requireContext().showToast(viewLifecycleOwner, viewModel.snackbarText)

        this.setupRefreshLayout(binding.refreshLayout)

        binding.apply {

            viewModel.dataLoading.observe(viewLifecycleOwner) {
                refreshLayout.isRefreshing = it
                refreshLayout.isEnabled = it
//                linearContainer.isVisible = !it
            }

            addTaskTitleEditText.addTextChangedListener {
                viewModel.title = it?.toString() ?: ""
            }
            addTaskDescriptionEditText.addTextChangedListener {
                viewModel.description = it?.toString() ?: ""
            }

            addTaskTitleEditText.setText(viewModel.title)

            addTaskDescriptionEditText.setText(viewModel.description)

            saveTaskFab.setOnClickListener {
                viewModel.saveTask()
            }
        }
    }
}