package cd.ghost.myapplication.navigation.actions

import cd.ghost.addedittask.AddEditTaskRouter
import javax.inject.Inject

class AddEditTaskDestinations @Inject constructor(
    private val destinationLauncher: DestinationLauncher
) : AddEditTaskRouter {

    override fun popWithMessage(message: Int) {
        destinationLauncher.pop()
    }
}