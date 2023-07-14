package cd.ghost.tasks

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import cd.ghost.common.R
import cd.ghost.common.base.Constants.ADD_EDIT_RESULT_OK
import cd.ghost.common.base.Constants.DELETE_RESULT_OK
import cd.ghost.common.base.Constants.EDIT_RESULT_OK
import cd.ghost.common.helper.MutableLiveEvent
import cd.ghost.common.helper.asLiveData
import cd.ghost.common.helper.publishEvent
import cd.ghost.data.Result
import cd.ghost.data.Task
import cd.ghost.data.source.TasksRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class TasksViewModel @Inject constructor(
    private val repository: TasksRepository,
    private val action: TasksAction
) : ViewModel() {

    private val _items = MutableLiveData<List<Task>>(emptyList())
    val items = _items.asLiveData()

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading = _dataLoading.asLiveData()

    private val _currentFilteringLabel = MutableLiveData<Int>()
    val currentFilteringLabel = _currentFilteringLabel.asLiveData()

    private val _noTasksLabel = MutableLiveData<Int>()
    val noTasksLabel = _noTasksLabel.asLiveData()

    private val _noTaskIconRes = MutableLiveData<Int>()
    val noTaskIconRes = _noTaskIconRes.asLiveData()

    private val _tasksAddViewVisible = MutableLiveData<Boolean>()
    val tasksAddViewVisible = _tasksAddViewVisible.asLiveData()

    private val _snackbarText = MutableLiveEvent<Int>()
    val snackbarText = _snackbarText.asLiveData()

    private var _currentFiltering = TasksFilterType.ALL_TASKS

    // Not used at the moment
    private val isDataLoadingError = MutableLiveData<Boolean>()

    //     This LiveData depends on another so we can use a transformation.
    val empty: LiveData<Boolean> = _items.map {
        it.isEmpty()
    }

    init {
        // Set initial state
        setFiltering(TasksFilterType.ALL_TASKS)
        loadTasks(true)
    }

    fun clearCompletedTasks() {
        viewModelScope.launch {
            repository.clearCompletedTasks()
            showSnackbarMessage(R.string.completed_tasks_cleared)
            // Refresh list to show the new state
            loadTasks(false)
        }
    }

    fun completeTask(task: Task, completed: Boolean) = viewModelScope.launch {
        if (completed) {
            repository.completeTask(task)
            showSnackbarMessage(R.string.task_marked_complete)
        } else {
            repository.activateTask(task)
            showSnackbarMessage(R.string.task_marked_active)
        }
        // Refresh list to show the new state
        loadTasks(false)
    }

    fun addNewTask() {
        action.navigateToAddOrNewTask()
    }

    fun openTask(taskId: String) {
        action.navigateToDisplayTask(taskId)
    }

    fun showEditResultMessage(result: Int?) {
        when (result) {
            EDIT_RESULT_OK -> showSnackbarMessage(R.string.successfully_saved_task_message)
            ADD_EDIT_RESULT_OK -> showSnackbarMessage(R.string.successfully_added_task_message)
            DELETE_RESULT_OK -> showSnackbarMessage(R.string.successfully_deleted_task_message)
        }
    }

    /**
     * Sets the current task filtering type.
     *
     * @param requestType Can be [TasksFilterType.ALL_TASKS],
     * [TasksFilterType.COMPLETED_TASKS], or
     * [TasksFilterType.ACTIVE_TASKS]
     */
    fun setFiltering(requestType: TasksFilterType) {
        _currentFiltering = requestType

        // Depending on the filter type, set the filtering label, icon drawables, etc.
        when (requestType) {
            TasksFilterType.ALL_TASKS -> {
                setFilter(
                    R.string.label_all, R.string.no_tasks_all,
                    R.drawable.logo_no_fill, true
                )
            }

            TasksFilterType.ACTIVE_TASKS -> {
                setFilter(
                    R.string.label_active, R.string.no_tasks_active,
                    R.drawable.ic_check_circle_96dp, false
                )
            }

            TasksFilterType.COMPLETED_TASKS -> {
                setFilter(
                    R.string.label_completed, R.string.no_tasks_completed,
                    R.drawable.ic_verified_user_96dp, false
                )
            }
        }
    }

    private fun setFilter(
        @StringRes filteringLabelString: Int, @StringRes noTasksLabelString: Int,
        @DrawableRes noTaskIconDrawable: Int, tasksAddVisible: Boolean
    ) {
        _currentFilteringLabel.value = filteringLabelString
        _noTasksLabel.value = noTasksLabelString
        _noTaskIconRes.value = noTaskIconDrawable
        _tasksAddViewVisible.value = tasksAddVisible
    }

    private fun showSnackbarMessage(message: Int) {
        _snackbarText.publishEvent(message)
    }

    /**
     * @param forceUpdate   Pass in true to refresh the data in the [TasksDataSource]
     */
    fun loadTasks(forceUpdate: Boolean) {
        _dataLoading.value = true

        viewModelScope.launch {
            val tasksResult = repository.getTasks(forceUpdate)

            if (tasksResult is Result.Success) {
                val tasks = tasksResult.data

                val tasksToShow = ArrayList<Task>()
                // We filter the tasks based on the requestType
                for (task in tasks) {
                    when (_currentFiltering) {
                        TasksFilterType.ALL_TASKS -> tasksToShow.add(task)
                        TasksFilterType.ACTIVE_TASKS -> if (task.isActive) {
                            tasksToShow.add(task)
                        }

                        TasksFilterType.COMPLETED_TASKS -> if (task.isCompleted) {
                            tasksToShow.add(task)
                        }
                    }
                }
                isDataLoadingError.value = false
                _items.value = ArrayList(tasksToShow)

            } else {
                isDataLoadingError.value = false
                _items.value = emptyList()
                showSnackbarMessage(R.string.loading_tasks_error)
            }

            _dataLoading.value = false
        }
    }

    fun refresh() {
        loadTasks(true)
    }
}