package cd.ghost.statistics

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

class StatisticsViewModel @Inject constructor(
    private val repository: TasksRepository
) : ViewModel() {

    private val _state = MutableLiveData(State())
    val state = _state.asLiveData()

    private val _error = MutableLiveEvent<Int>()
    val error = _error.asLiveData()

    init {
        start()
    }

    private fun start() {
        if (_state.value?.dataLoading == true) return

        showDataLoading()

        viewModelScope.launch {
            repository.getTasks().let { result ->
                if (result is Success) {
                    computeStats(result.data)
                } else {
                    _error.publishEvent(cd.ghost.common.R.string.statistics_error)
                    computeStats(null)
                }
            }
        }
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


    fun refresh() {
        start()
    }

    /**
     * Called when new data is ready.
     */
    private fun computeStats(tasks: List<Task>?) {
        getActiveAndCompletedStats(tasks).let {
            _state.value = _state.value?.copy(
                activeTasksPercent = it.activeTasksPercent,
                completedTasksPercent = it.completedTasksPercent,
                isEmpty = tasks.isNullOrEmpty(),
                dataLoading = false,
            )
        }
    }

    data class State(
        val activeTasksPercent: Float = 0f,
        val completedTasksPercent: Float = 0f,
        val isEmpty: Boolean = true,
        val dataLoading: Boolean = false
    )
}