package cd.ghost.myapplication.navigation.di

import cd.ghost.myapplication.navigation.tasks.TasksDestinationProvider
import cd.ghost.tasks.TasksAction
import dagger.Binds
import dagger.Module

@Module
interface ActionsBindModule {

    @Binds
    fun actionTasks(tasksDestinationProvider: TasksDestinationProvider): TasksAction

}