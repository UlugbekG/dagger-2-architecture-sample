package cd.ghost.myapplication.navigation.di

import cd.ghost.detailtask.di.DetailTaskSubcomponent
import cd.ghost.tasks.di.TasksSubcomponent
import dagger.Module

@Module(
    subcomponents = [
        TasksSubcomponent::class,
        DetailTaskSubcomponent::class
    ]
)
class SubcomponentsModule