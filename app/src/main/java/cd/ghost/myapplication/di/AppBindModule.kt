package cd.ghost.myapplication.di

import cd.ghost.myapplication.data.source.DefaultTasksRepository
import cd.ghost.myapplication.data.source.TasksRepository
import dagger.Binds
import dagger.Module
import dagger.Provides


@Module
interface AppBindModule {

    @Binds
    fun bindRepository(repo: DefaultTasksRepository): TasksRepository

}