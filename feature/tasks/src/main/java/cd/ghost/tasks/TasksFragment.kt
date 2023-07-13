package cd.ghost.tasks

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import cd.ghost.common.helper.viewBinding
import cd.ghost.data.Task
import cd.ghost.tasks.databinding.FragmentTasksBinding
import cd.ghost.tasks.di.TasksComponentProvider
import javax.inject.Inject

class TasksFragment : Fragment(R.layout.fragment_tasks), TaskItemClickListener {

    private val binding by viewBinding<FragmentTasksBinding>()

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private val viewModel by viewModels<TasksViewModel> { factory }

    private lateinit var adapter: TasksAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity() as TasksComponentProvider)
            .provideTaskSubcomponent()
            .create()
            .inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = TasksAdapter(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            tasksList.adapter = adapter

            addTaskFab.setOnClickListener {
                viewModel.addNewTask()
            }

            refreshLayout.setOnRefreshListener {
                viewModel.refresh()
            }

            viewModel.dataLoading.observe(viewLifecycleOwner) {
                refreshLayout.isRefreshing = it
            }

            viewModel.empty.observe(viewLifecycleOwner) {
                tasksLinearLayout.isVisible = it
                noTasksLayout.isVisible = !it
            }

            viewModel.currentFilteringLabel.observe(viewLifecycleOwner) {
                filteringText.text = getText(it)
            }

            viewModel.items.observe(viewLifecycleOwner) {

            }

            viewModel.noTaskIconRes.observe(viewLifecycleOwner) {
                noTasksIcon.setImageDrawable(ContextCompat.getDrawable(requireContext(), it))
            }

            viewModel.noTasksLabel.observe(viewLifecycleOwner) {
                noTasksText.text = getText(it)
            }
        }
    }

    override fun itemClick(task: Task) {
        viewModel.openTask(taskId = task.id)
    }

    override fun itemCheckBoxClick(task: Task, isCompleted: Boolean) {
        viewModel.completeTask(task, isCompleted)
    }

}

// Keys for navigation
const val ADD_EDIT_RESULT_OK = Activity.RESULT_FIRST_USER + 1
const val DELETE_RESULT_OK = Activity.RESULT_FIRST_USER + 2
const val EDIT_RESULT_OK = Activity.RESULT_FIRST_USER + 3