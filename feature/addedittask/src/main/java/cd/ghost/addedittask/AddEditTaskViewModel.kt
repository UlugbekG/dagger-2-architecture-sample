package cd.ghost.addedittask

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cd.ghost.common.helper.MutableLiveEvent
import cd.ghost.common.helper.asLiveData
import cd.ghost.common.helper.publishEvent
import cd.ghost.data.Result.Success
import cd.ghost.data.Task
import cd.ghost.data.source.TasksRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddEditTaskViewModel @Inject constructor(
    private val repository: TasksRepository
) : ViewModel() {

    // Two-way databinding, exposing MutableLiveData
    private val _contentState = MutableLiveData(ContentState())
    val contentState = _contentState.asLiveData()

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading = _dataLoading.asLiveData()

    private val _snackbarText = MutableLiveEvent<Int>()
    val snackbarText = _snackbarText.asLiveData()

    private val _taskUpdatedEvent = MutableLiveEvent<Unit>()
    val taskUpdatedEvent = _taskUpdatedEvent.asLiveData()

    private var isNewTask: Boolean = false

    private var isDataLoaded = false

    private fun getTaskId(): String? = _contentState.value?.taskId

    private fun setTaskId(value: String?) {
        _contentState.value = _contentState.value?.copy(
            taskId = value
        )
    }

    private fun getCompletion(): Boolean = _contentState.value?.isCompleted ?: false

    private fun setCompletion(value: Boolean) {
        _contentState.value = _contentState.value?.copy(
            isCompleted = value
        )
    }

    fun start(taskId: String?) {
        if (_dataLoading.value == true) return

        setTaskId(taskId)

        val task_id = getTaskId()

        if (task_id == null) {
            // No need to populate, it's a new task
            isNewTask = true
            return
        }
        if (isDataLoaded) {
            // No need to populate, already have data.
            return
        }

        isNewTask = false
        _dataLoading.value = true

        viewModelScope.launch {
            repository.getTask(task_id).let { result ->
                if (result is Success) {
                    onTaskLoaded(result.data)
                } else {
                    onDataNotAvailable()
                }
            }
        }
    }

    private fun onTaskLoaded(task: Task) {
        _contentState.value = _contentState.value?.copy(
            title = task.title,
            description = task.description,
            isCompleted = task.isCompleted
        )
        _dataLoading.value = false
        isDataLoaded = true
    }

    private fun onDataNotAvailable() {
        _dataLoading.value = false
    }

    // Called when clicking on fab.
    fun saveTask(title: String?, description: String?) {
        if (title == null || description == null) {
            _snackbarText.publishEvent(cd.ghost.common.R.string.empty_task_message)
            return
        }
        if (Task(title, description).isEmpty) {
            _snackbarText.publishEvent(cd.ghost.common.R.string.empty_task_message)
            return
        }
        val currentTaskId = getTaskId()
        if (isNewTask || currentTaskId == null) {
            createTask(Task(title, description))
        } else {
            val task = Task(title, description, getCompletion(), currentTaskId)
            updateTask(task)
        }

    }

    private fun createTask(newTask: Task) = viewModelScope.launch {
        repository.saveTask(newTask)
        _taskUpdatedEvent.publishEvent(Unit)
    }

    private fun updateTask(task: Task) {
        if (isNewTask) {
            throw RuntimeException("updateTask() was called but task is new.")
        }
        viewModelScope.launch {
            repository.saveTask(task)
            _taskUpdatedEvent.publishEvent(Unit)
        }
    }

    data class ContentState(
        var taskId: String? = null,
        var title: String = "",
        var description: String = "",
        var isCompleted: Boolean = false
    )
}