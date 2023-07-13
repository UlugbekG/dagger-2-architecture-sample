package cd.ghost.tasks.di

import cd.ghost.tasks.TasksFragment
import dagger.Subcomponent

@Subcomponent(modules = [TasksModule::class])
interface TasksSubcomponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): TasksSubcomponent
    }

    fun inject(fragment: TasksFragment)
}