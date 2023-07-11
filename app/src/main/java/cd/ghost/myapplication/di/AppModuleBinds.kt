package cd.ghost.myapplication.di

import cd.ghost.data.source.DefaultTasksRepository
import cd.ghost.data.source.TasksRepository
import dagger.Binds
import dagger.Module


@Module
interface AppModuleBinds {

    @Binds
    fun bindRepository(repo: DefaultTasksRepository): TasksRepository

}