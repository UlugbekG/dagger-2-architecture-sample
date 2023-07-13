package cd.ghost.myapplication.navigation.di

import cd.ghost.tasks.di.TasksComponent
import dagger.Module

@Module(
    subcomponents = [
        TasksComponent::class,
    ]
)
class SubcomponentsModule