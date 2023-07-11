package cd.ghost.myapplication.di;

import android.content.Context
import cd.ghost.data.source.TasksRepository
import cd.ghost.data.source.di.SourcesAnnotationModule
import cd.ghost.tasks.di.TasksComponent
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        AppBindModule::class,
        SourcesAnnotationModule::class,
        SubcomponentsModule::class
    ]
)
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): AppComponent
    }

    fun tasksComponent(): TasksComponent.Factory

    val tasksRepository: TasksRepository
}

