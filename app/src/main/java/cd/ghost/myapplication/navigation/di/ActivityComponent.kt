package cd.ghost.myapplication.navigation.di

import cd.ghost.common.di.ActivityScope
import cd.ghost.common.di.ViewModelBuilderModule
import cd.ghost.myapplication.di.AppComponent
import cd.ghost.myapplication.navigation.MainActivity
import cd.ghost.tasks.di.TasksComponent
import dagger.Component

@ActivityScope
@Component(
    dependencies = [
        AppComponent::class
    ],
    modules = [
        ActivityModule::class,
        ActionsBindModule::class,
        SubcomponentsModule::class,
        ViewModelBuilderModule::class,
    ]
)
interface ActivityComponent {

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): ActivityComponent
    }

    fun inject(activity: MainActivity)

    fun taskSubcomponent(): TasksComponent.Factory

}