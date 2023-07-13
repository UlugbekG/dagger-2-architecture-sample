package cd.ghost.myapplication

import android.app.Application
import cd.ghost.myapplication.di.AppComponent
import cd.ghost.myapplication.di.DaggerAppComponent
import cd.ghost.tasks.di.TasksComponent
import cd.ghost.tasks.di.TasksComponentProvider

class MyApplication : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(this)

//        if (BuildConfig.DEBUG) Timber.plant(DebugTree())
    }


}