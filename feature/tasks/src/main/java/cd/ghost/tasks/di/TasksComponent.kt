package cd.ghost.tasks.di

import cd.ghost.tasks.TasksFragment
import dagger.Subcomponent

@Subcomponent(modules = [TasksModule::class])
interface TasksComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): TasksComponent
    }

    fun inject(fragment: TasksFragment)
}