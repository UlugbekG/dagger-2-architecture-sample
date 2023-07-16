package cd.ghost.tasks

interface TasksRouter {

    fun navigateToAddOrNewTask()

    fun navigateToDisplayTask(taskId: String)
}