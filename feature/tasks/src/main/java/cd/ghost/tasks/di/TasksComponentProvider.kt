package cd.ghost.tasks.di

interface TasksComponentProvider {
    fun provideTaskSubcomponent(): TasksSubcomponent.Factory
}