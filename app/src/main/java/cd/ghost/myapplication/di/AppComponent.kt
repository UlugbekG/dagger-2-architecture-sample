package cd.ghost.myapplication.di;

import android.content.Context
import cd.ghost.data.source.TasksRepository
import cd.ghost.myapplication.navigation.actions.DestinationLauncher
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        AppModuleBinds::class,
    ]
)
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): AppComponent
    }

    val tasksRepository: TasksRepository

    val destinationLauncher: DestinationLauncher

}

