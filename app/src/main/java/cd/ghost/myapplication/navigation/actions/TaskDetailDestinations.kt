package cd.ghost.myapplication.navigation.actions

import cd.ghost.addedittask.AddEditTaskFragment.AddEditTaskArgument
import cd.ghost.detailtask.TaskDetailRouter
import cd.ghost.myapplication.R
import javax.inject.Inject

class TaskDetailDestinations @Inject constructor(
    private val destinationLauncher: DestinationLauncher
) : TaskDetailRouter {
    override fun navigateUp() = destinationLauncher.pop()

    override fun actionToAddEditTask(taskId: String) {
        destinationLauncher.launch(
            destinationId = R.id.action_detailTaskFragment_to_addEditTaskFragment,
            args = AddEditTaskArgument(taskId)
        )
    }
}