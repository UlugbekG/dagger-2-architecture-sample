package cd.ghost.addedittask

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import cd.ghost.addedittask.databinding.FragmentAddEditTaskBinding
import cd.ghost.addedittask.di.AddEditTaskSubcompProvider
import cd.ghost.common.base.BaseArgument
import cd.ghost.common.base.args
import cd.ghost.common.helper.viewBinding
import javax.inject.Inject

class AddEditTaskFragment : Fragment(R.layout.fragment_add_edit_task) {
    class AddEditTaskArgument(
        val taskId: String?
    ) : BaseArgument

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private val viewModel by viewModels<AddEditTaskViewModel> { factory }

    private val binding by viewBinding<FragmentAddEditTaskBinding>()

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (requireActivity() as AddEditTaskSubcompProvider)
            .provideAddEditTaskSubcomp()
            .create()
            .inject(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val taskId = args<AddEditTaskArgument>()?.taskId
        viewModel.start(taskId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            viewModel.dataLoading.observe(viewLifecycleOwner) {
                refreshLayout.refreshingProcess = it
                refreshLayout.isEnabled = it
                linearContainer.isVisible = !it
            }

            viewModel.contentState.observe(viewLifecycleOwner) { task ->
                addTaskTitleEditText.setText(task.title)
                addTaskDescriptionEditText.setText(task.description)
            }

            saveTaskFab.setOnClickListener {
                viewModel.saveTask(
                    addTaskTitleEditText.text?.toString(),
                    addTaskDescriptionEditText.text?.toString()
                )
            }
        }
    }
}