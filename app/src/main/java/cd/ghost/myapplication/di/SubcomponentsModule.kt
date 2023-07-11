package cd.ghost.myapplication.di

import cd.ghost.tasks.di.TasksComponent
import dagger.Module

@Module(
    subcomponents = [
        TasksComponent::class
    ]
)
object SubcomponentsModule