package cd.ghost.tasks

interface TasksAction {

    fun navigateToAddOrNewTask()

    fun navigateToDisplayTask(taskId: String)
}