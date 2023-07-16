package cd.ghost.tasks

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cd.ghost.common.R
import cd.ghost.common.base.Constants.ADD_EDIT_RESULT_OK
import cd.ghost.common.base.Constants.DELETE_RESULT_OK
import cd.ghost.common.base.Constants.EDIT_RESULT_OK
import cd.ghost.common.helper.MutableLiveEvent
import cd.ghost.common.helper.asLiveData
import cd.ghost.common.helper.publishEvent
import cd.ghost.data.Result.Success
import cd.ghost.data.Task
import cd.ghost.data.source.TasksRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class TasksViewModel @Inject constructor(
    private val repository: TasksRepository,
    private val action: TasksRouter
) : ViewModel() {

    private val _state = MutableLiveData(State())
    val state = _state.asLiveData()

    private val _error = MutableLiveEvent<Int>()
    val error = _error.asLiveData()

    private val _userMessage = MutableLiveEvent<Int>()
    val userMessage = _userMessage.asLiveData()

    init {
        // Set initial state
        setFiltering(TasksFilterType.ALL_TASKS)
        loadTasks(true)
    }

    fun clearCompletedTasks() {
        viewModelScope.launch {
            repository.clearCompletedTasks()
            _userMessage.publishEvent(R.string.completed_tasks_cleared)
            // Refresh list to show the new state
            loadTasks(false)
        }
    }

    fun completeTask(task: Task, completed: Boolean) = viewModelScope.launch {
        if (completed) {
            repository.completeTask(task)
            _userMessage.publishEvent(R.string.task_marked_complete)
        } else {
            repository.activateTask(task)
            _userMessage.publishEvent(R.string.task_marked_active)
        }
        // Refresh list to show the new state
        loadTasks(false)
    }

    fun showEditResultMessage(result: Int?) {
        when (result) {
            EDIT_RESULT_OK -> _userMessage.publishEvent(R.string.successfully_saved_task_message)
            ADD_EDIT_RESULT_OK -> _userMessage.publishEvent(R.string.successfully_added_task_message)
            DELETE_RESULT_OK -> _userMessage.publishEvent(R.string.successfully_deleted_task_message)
        }
    }

    /**
     * @param forceUpdate   Pass in true to refresh the data in the [TasksDataSource]
     */
    fun loadTasks(forceUpdate: Boolean) {
        if (_state.value?.dataLoading == true) return

        showDataLoading()

        viewModelScope.launch {
            val tasksResult = repository.getTasks(forceUpdate)

            if (tasksResult is Success) {
                val tasks = tasksResult.data

                val tasksToShow = ArrayList<Task>()

                for (task in tasks) {
                    when (_state.value?.filterUiC?.filterType) {
                        TasksFilterType.ACTIVE_TASKS -> if (task.isActive) {
                            tasksToShow.add(task)
                        }
                        TasksFilterType.COMPLETED_TASKS -> if (task.isCompleted) {
                            tasksToShow.add(task)
                        }
                        else -> tasksToShow.add(task)
                    }
                }

                _state.value = _state.value?.copy(
                    tasks = tasksToShow,
                    dataLoading = false
                )

            } else {
                _state.value = _state.value?.copy(
                    tasks = emptyList(),
                    dataLoading = false,
                )
                _error.publishEvent(R.string.loading_tasks_error)
            }

        }
    }

    fun refresh() {
        loadTasks(true)
    }

    private fun showDataLoading() {
        _state.value = _state.value?.copy(
            dataLoading = true
        )
    }

    private fun hideDataLoading() {
        _state.value = _state.value?.copy(
            dataLoading = false
        )
    }

    /**
     * Sets the current task filtering type.
     *
     * @param requestType Can be [TasksFilterType.ALL_TASKS],
     * [TasksFilterType.COMPLETED_TASKS], or
     * [TasksFilterType.ACTIVE_TASKS]
     */
    fun setFiltering(requestType: TasksFilterType) = when (requestType) {
        TasksFilterType.ALL_TASKS -> {
            setFilter(
                requestType,
                R.string.label_all, R.string.no_tasks_all,
                R.drawable.logo_no_fill, true
            )
        }

        TasksFilterType.ACTIVE_TASKS -> {
            setFilter(
                requestType, R.string.label_active,
                R.string.no_tasks_active, R.drawable.ic_check_circle_96dp, false
            )
        }

        TasksFilterType.COMPLETED_TASKS -> {
            setFilter(
                requestType, R.string.label_completed,
                R.string.no_tasks_completed, R.drawable.ic_verified_user_96dp, false
            )
        }
    }

    private fun setFilter(
        requestType: TasksFilterType,
        @StringRes filteringLabelString: Int,
        @StringRes noTasksLabelString: Int,
        @DrawableRes noTaskIconDrawable: Int,
        tasksAddVisible: Boolean
    ) {
        _state.value = _state.value?.copy(
            filterUiC = FilterUiC(
                filterType = requestType,
                filterLabel = filteringLabelString,
                noTasksLabel = noTasksLabelString,
                noTaskIconRes = noTaskIconDrawable,
                tasksAddVisible = tasksAddVisible
            )
        )
    }

    fun addNewTask() {
        action.navigateToAddOrNewTask()
    }

    fun openTask(taskId: String) {
        action.navigateToDisplayTask(taskId)
    }

    data class State(
        val tasks: List<Task> = emptyList(),
        val dataLoading: Boolean = false,
        val filterUiC: FilterUiC = FilterUiC(),
    ) {
        val empty: Boolean = tasks.isEmpty()
    }

    data class FilterUiC(
        val filterType: TasksFilterType = TasksFilterType.ALL_TASKS,
        val filterLabel: Int = R.string.label_all,
        val noTaskIconRes: Int = R.drawable.logo_no_fill,
        val noTasksLabel: Int = R.string.no_tasks_all,
        val tasksAddVisible: Boolean = true
    )
}