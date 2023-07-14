package cd.ghost.myapplication.navigation.tasks

import cd.ghost.detailtask.DetailTaskFragment.DetailTaskArgument
import cd.ghost.myapplication.R
import cd.ghost.myapplication.navigation.DestinationLauncher
import cd.ghost.tasks.TasksAction
import javax.inject.Inject


class TasksDestinationProvider @Inject constructor(
    private val destinationLauncher: DestinationLauncher
) : TasksAction {

    override fun navigateToAddOrNewTask() {
        destinationLauncher.launch(R.id.action_tasksFragment_to_addEditTaskFragment)
    }

    override fun navigateToDisplayTask(taskId: String) {
        destinationLauncher.launch(
            destinationId = R.id.action_tasksFragment_to_detailTaskFragment,
            args = DetailTaskArgument(taskId)
        )
    }

}