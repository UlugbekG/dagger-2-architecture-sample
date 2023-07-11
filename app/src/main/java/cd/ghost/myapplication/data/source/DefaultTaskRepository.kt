package cd.ghost.myapplication.data.source

import cd.ghost.myapplication.data.Task
import cd.ghost.myapplication.di.AppModule.TaskLocalDataSource
import cd.ghost.myapplication.di.AppModule.TaskRemoteDataSource
import java.util.concurrent.ConcurrentMap

class DefaultTaskRepository(
    @TaskRemoteDataSource taskRemoteDataSource: TaskDataSource,
    @TaskLocalDataSource taskLocalDataSource: TaskDataSource
) : TaskRepository {

    private var cachedTasks: ConcurrentMap<String, Task>? = null

    override suspend fun getTasks(forceUpdate: Boolean): Result<List<Task>> {
        TODO("Not yet implemented")
    }

    override suspend fun getTask(taskId: String, forceUpdate: Boolean): Result<Task> {
        TODO("Not yet implemented")
    }

    override suspend fun saveTask(task: Task) {
        TODO("Not yet implemented")
    }

    override suspend fun completeTask(task: Task) {
        TODO("Not yet implemented")
    }

    override suspend fun completeTask(taskId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun activateTask(task: Task) {
        TODO("Not yet implemented")
    }

    override suspend fun activateTask(taskId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun clearCompletedTask() {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllTask() {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTask(taskId: String) {
        TODO("Not yet implemented")
    }
}