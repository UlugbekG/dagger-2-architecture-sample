package cd.ghost.myapplication

import android.app.Application
import cd.ghost.myapplication.di.DaggerAppComponent
import cd.ghost.tasks.di.TasksComponent
import cd.ghost.tasks.di.TasksComponentProvider

class App : Application(), TasksComponentProvider {

    val appComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }

    override fun onCreate() {
        super.onCreate()
//        if (BuildConfig.DEBUG) Timber.plant(DebugTree())
    }

    override fun provideTaskComponent(): TasksComponent.Factory {
        return appComponent.tasksComponent()
    }
}