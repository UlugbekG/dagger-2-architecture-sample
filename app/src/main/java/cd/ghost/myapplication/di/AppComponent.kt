package cd.ghost.myapplication.di;

import android.content.Context
import cd.ghost.myapplication.data.source.TasksRepository
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        AppBindModule::class,
        SubcomponentsModule::class
    ]
)
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): AppComponent
    }


    val tasksRepository: TasksRepository
}

@Module(
    subcomponents = []
)
object SubcomponentsModule