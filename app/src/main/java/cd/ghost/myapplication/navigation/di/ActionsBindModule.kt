package cd.ghost.myapplication.navigation.di

import cd.ghost.addedittask.AddEditTaskRouter
import cd.ghost.common.di.ActivityScope
import cd.ghost.detailtask.TaskDetailRouter
import cd.ghost.myapplication.navigation.actions.AddEditTaskDestinations
import cd.ghost.myapplication.navigation.actions.TaskDetailDestinations
import cd.ghost.myapplication.navigation.actions.TasksDestinations
import cd.ghost.tasks.TasksRouter
import dagger.Binds
import dagger.Module

@Module
interface ActionsBindModule {

    @ActivityScope
    @Binds
    fun bindTasks(tasks: TasksDestinations): TasksRouter

    @ActivityScope
    @Binds
    fun bindTaskDetail(taskDetail: TaskDetailDestinations): TaskDetailRouter

    @ActivityScope
    @Binds
    fun bindAddEditTask(addEditTask: AddEditTaskDestinations): AddEditTaskRouter


}