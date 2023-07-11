package cd.ghost.myapplication.di

import android.content.Context
import androidx.room.Room
import cd.ghost.myapplication.data.source.TaskDataSource
import cd.ghost.myapplication.data.source.local.TaskLocalDataSource
import cd.ghost.myapplication.data.source.local.ToDoDatabase
import cd.ghost.myapplication.data.source.remote.TaskRemoteDataSource
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
object AppModule {

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class TaskRemoteDataSource

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class TaskLocalDataSource

    @JvmStatic
    @Singleton
    @TaskRemoteDataSource
    @Provides
    fun provideTaskRemoteDataSource(): TaskDataSource {
        return TaskRemoteDataSource
    }

    @JvmStatic
    @Singleton
    @TaskLocalDataSource
    @Provides
    fun provideTaskLocalDataSource(
        database: ToDoDatabase,
        ioDispatcher: CoroutineDispatcher
    ): TaskDataSource {
        return TaskLocalDataSource(
            tasksDao = database.taskDao(),
            ioDispatcher = ioDispatcher
        )
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @JvmStatic
    @Singleton
    @Provides
    fun provideAppDatabase(
        context: Context
    ): ToDoDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            ToDoDatabase::class.java,
            "cd-ghost-toDo-db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

}