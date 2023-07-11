package cd.ghost.tasks.di

interface TasksComponentProvider {
    fun provideTaskComponent(): TasksComponent.Factory
}