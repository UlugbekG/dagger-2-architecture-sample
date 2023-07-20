package cd.ghost.myapplication.di

import cd.ghost.data.source.DefaultTasksRepository
import cd.ghost.data.source.TasksRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
interface AppModuleBinds {

    @Binds
    fun bindRepository(repo: DefaultTasksRepository): TasksRepository

}