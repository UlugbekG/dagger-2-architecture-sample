package cd.ghost.myapplication.navigation.actions

import androidx.annotation.IdRes
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentActivity
import cd.ghost.common.base.ARG_SCREEN
import java.io.Serializable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DestinationLauncher @Inject constructor() {

    private var currentActivity: FragmentActivity? = null
    private var started = false

    fun onCreate(activity: FragmentActivity) {
        this.currentActivity = activity
    }

    fun onStart() {
        started = true
    }

    fun onStopped() {
        started = false
    }

    fun onDestroy() {
        currentActivity = null
    }

    fun launch(@IdRes destinationId: Int, args: Serializable? = null) {
        if (started) {
            if (args == null) {
                getNavHolder().navController().navigate(resId = destinationId)
            } else {
                getNavHolder().navController().navigate(
                    resId = destinationId,
                    args = bundleOf(ARG_SCREEN to args)
                )
            }
        }
    }

    fun pop() {
        if (started) {
            getNavHolder().navController().popBackStack()
        }
    }

    private fun getNavHolder() = (currentActivity as NavControllerHolder)
}