package cd.ghost.tasks.di

interface TasksSubcompProvider {
    fun provideTaskSubcomp(): TasksSubcomponent.Factory
}