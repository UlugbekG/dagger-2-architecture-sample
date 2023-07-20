package cd.ghost.myapplication.navigation.di

import cd.ghost.addedittask.AddEditTaskRouter
import cd.ghost.detailtask.TaskDetailRouter
import cd.ghost.myapplication.navigation.actions.AddEditTaskDestinations
import cd.ghost.myapplication.navigation.actions.TaskDetailDestinations
import cd.ghost.myapplication.navigation.actions.TasksDestinations
import cd.ghost.tasks.TasksRouter
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent


@Module
@InstallIn(ActivityComponent::class)
interface ActionsBindModule {

    @Binds
    fun bindTasks(tasks: TasksDestinations): TasksRouter

    @Binds
    fun bindTaskDetail(taskDetail: TaskDetailDestinations): TaskDetailRouter

    @Binds
    fun bindAddEditTask(addEditTask: AddEditTaskDestinations): AddEditTaskRouter


}