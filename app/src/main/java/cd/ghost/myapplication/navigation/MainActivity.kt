package cd.ghost.myapplication.navigation

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import cd.ghost.addedittask.di.AddEditTaskSubcomponent
import cd.ghost.detailtask.di.DetailTaskSubcomponent
import cd.ghost.myapplication.MyApplication
import cd.ghost.myapplication.R
import cd.ghost.myapplication.navigation.actions.DestinationLauncher
import cd.ghost.myapplication.navigation.actions.NavControllerHolder
import cd.ghost.myapplication.navigation.di.DaggerActivityComponent
import cd.ghost.statistics.di.StatisticsSubcomponent
import cd.ghost.tasks.di.TasksSubcomponent
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavControllerHolder {

    @Inject
    lateinit var destinationLauncher: DestinationLauncher

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.setStatusBarBackground(cd.ghost.common.R.color.colorPrimaryDark)

        setSupportActionBar(findViewById(R.id.toolbar))

        val navHost =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        navController = navHost.navController

        appBarConfiguration =
            AppBarConfiguration.Builder(
                R.id.tasksFragment,
                R.id.statisticsFragment
            )
                .setDrawerLayout(drawerLayout).build()

        setupActionBarWithNavController(navController, appBarConfiguration)

        findViewById<NavigationView>(R.id.nav_view)
            .setupWithNavController(navController)

        destinationLauncher.onCreate(this)

        if (savedInstanceState == null) prepareRootNavGraph(navController)
        else prepareRestorationRootNavGraph(savedInstanceState, navController)

    }

    private fun prepareRootNavGraph(navController: NavController) {
        val graph = navController.navInflater.inflate(R.navigation.nav_graph)
        navController.graph = graph
    }

    private fun prepareRestorationRootNavGraph(
        savedInstState: Bundle,
        navController: NavController
    ) {
        val stateRestoration = savedInstState.getBundle(KEY_START_DESTINATION)
        navController.restoreState(stateRestoration)
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_fragment).navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBundle(KEY_START_DESTINATION, navController.saveState())
    }

    override fun onRestoreInstanceState(
        savedInstanceState: Bundle?, persistentState: PersistableBundle?
    ) {
        super.onRestoreInstanceState(savedInstanceState, persistentState)
        navController.restoreState(savedInstanceState?.getBundle(KEY_START_DESTINATION))
    }

    private companion object {
        const val KEY_START_DESTINATION = "startDestination"
    }

    override fun onStart() {
        super.onStart()
        destinationLauncher.onStart()
    }

    override fun onStop() {
        super.onStop()
        destinationLauncher.onStopped()
    }

    override fun onDestroy() {
        super.onDestroy()
        destinationLauncher.onDestroy()
    }

    override fun navController(): NavController {
        return navController
    }

}