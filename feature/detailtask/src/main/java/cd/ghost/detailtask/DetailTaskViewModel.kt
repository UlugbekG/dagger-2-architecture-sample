package cd.ghost.detailtask

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import cd.ghost.common.helper.MutableLiveEvent
import cd.ghost.common.helper.asLiveData
import cd.ghost.common.helper.publishEvent
import cd.ghost.data.Result
import cd.ghost.data.Result.Success
import cd.ghost.data.Task
import cd.ghost.data.source.TasksRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailTaskViewModel @Inject constructor(
    private val repository: TasksRepository,
    private val router: TaskDetailRouter
) : ViewModel() {

    private val _task = MutableLiveData<Task?>()
    val task = _task.asLiveData()

    private val _isDataAvailable = MutableLiveData<Boolean>()
    val isDataAvailable: LiveData<Boolean> = _isDataAvailable

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _editTaskEvent = MutableLiveEvent<Unit>()
    val editTaskEvent = _editTaskEvent.asLiveData()

    private val _snackbarText = MutableLiveEvent<Int>()
    val snackbarText = _snackbarText.asLiveData()

    private val taskId: String? get() = _task.value?.id

    // This LiveData depends on another so we can use a transformation.
    val completed: LiveData<Boolean> = _task.map { input: Task? ->
        input?.isCompleted ?: false
    }

    fun deleteTask() = viewModelScope.launch {
        taskId?.let {
            repository.deleteTask(it)
            router.navigateUp()
        }
    }

    fun editTask() {
        taskId?.let {
            router.actionToAddEditTask(it)
        }
    }

    fun setCompleted(completed: Boolean) = viewModelScope.launch {
        val task = _task.value ?: return@launch
        if (completed) {
            repository.completeTask(task)
            showSnackbarMessage(cd.ghost.common.R.string.task_marked_complete)
        } else {
            repository.activateTask(task)
            showSnackbarMessage(cd.ghost.common.R.string.task_marked_active)
        }
    }

    fun start(taskId: String?, forceRefresh: Boolean = false) {
        if (_isDataAvailable.value == true && !forceRefresh || _dataLoading.value == true) {
            return
        }
        // Show loading indicator
        _dataLoading.value = true

        viewModelScope.launch {
            if (taskId != null) {
                repository.getTask(taskId, false).let { result ->
                    if (result is Success) {
                        onTaskLoaded(result.data)
                    } else {
                        onDataNotAvailable(result)
                    }
                }
            }
            _dataLoading.value = false
        }
    }

    private fun setTask(task: Task?) {
        this._task.value = task
        _isDataAvailable.value = task != null
    }

    private fun onTaskLoaded(task: Task) {
        setTask(task)
    }

    private fun onDataNotAvailable(result: Result<Task>) {
        _task.value = null
        _isDataAvailable.value = false
    }

    fun refresh() {
        taskId?.let { start(it, true) }
    }

    private fun showSnackbarMessage(@StringRes message: Int) {
        _snackbarText.publishEvent(message)
    }
}