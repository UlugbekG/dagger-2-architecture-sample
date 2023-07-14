package cd.ghost.addedittask

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cd.ghost.common.base.Constants
import cd.ghost.common.helper.MutableLiveEvent
import cd.ghost.common.helper.asLiveData
import cd.ghost.common.helper.publishEvent
import cd.ghost.data.Result.Success
import cd.ghost.data.Task
import cd.ghost.data.source.TasksRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddEditTaskViewModel @Inject constructor(
    private val repository: TasksRepository,
    private val router: AddEditTaskRouter
) : ViewModel() {
    // Two-way databinding, exposing MutableLiveData
    var title = ""

    // Two-way databinding, exposing MutableLiveData
    var description = ""

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading = _dataLoading.asLiveData()

    private val _snackbarText = MutableLiveEvent<Int>()
    val snackbarText = _snackbarText.asLiveData()

    private var taskId: String? = null

    private var isNewTask: Boolean = false

    private var isDataLoaded = false

    private var taskCompleted = false

    fun start(taskId: String?) {
        if (_dataLoading.value == true) {
            return
        }

        this.taskId = taskId
        if (taskId == null) {
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
            repository.getTask(taskId).let { result ->
                if (result is Success) {
                    onTaskLoaded(result.data)
                } else {
                    onDataNotAvailable()
                }
            }
        }
    }

    private fun onTaskLoaded(task: Task) {
        title = task.title
        description = task.description
        taskCompleted = task.isCompleted
        _dataLoading.value = false
        isDataLoaded = true
    }

    private fun onDataNotAvailable() {
        _dataLoading.value = false
    }

    // Called when clicking on fab.
    fun saveTask() {
        val currentTitle = title
        val currentDescription = description

        if (currentTitle == null || currentDescription == null) {
            _snackbarText.publishEvent(cd.ghost.common.R.string.empty_task_message)
            return
        }
        if (Task(currentTitle, currentDescription).isEmpty) {
            _snackbarText.publishEvent(cd.ghost.common.R.string.empty_task_message)
            return
        }

        val currentTaskId = taskId
        if (isNewTask || currentTaskId == null) {
            createTask(Task(currentTitle, currentDescription))
        } else {
            val task = Task(currentTitle, currentDescription, taskCompleted, currentTaskId)
            updateTask(task)
        }
    }

    private fun createTask(newTask: Task) = viewModelScope.launch {
        repository.saveTask(newTask)
        router.popWithMessage(Constants.ADD_EDIT_RESULT_OK)
    }

    private fun updateTask(task: Task) {
        if (isNewTask) {
            throw RuntimeException("updateTask() was called but task is new.")
        }
        viewModelScope.launch {
            repository.saveTask(task)
            router.popWithMessage(Constants.ADD_EDIT_RESULT_OK)
        }
    }
}