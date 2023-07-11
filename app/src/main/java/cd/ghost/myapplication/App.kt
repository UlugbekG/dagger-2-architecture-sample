package cd.ghost.myapplication

import android.app.Application
import cd.ghost.myapplication.di.DaggerAppComponent

class App : Application() {

    val appComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }

    override fun onCreate() {
        super.onCreate()
//        if (BuildConfig.DEBUG) Timber.plant(DebugTree())
    }
}