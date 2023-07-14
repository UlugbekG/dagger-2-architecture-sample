package cd.ghost.common.base

import android.os.Build
import androidx.fragment.app.Fragment

interface BaseArgument : java.io.Serializable

/**
 * Default arg name for holding screen args in fragments. Use this name
 * if you want to integrate your navigation with the core.
 */
const val ARG_SCREEN = "screen"

/**
 * Get screen args attached to the [Fragment].
 */
inline fun <reified T : BaseArgument> Fragment.args(): T? {
    return when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU ->
            arguments?.getSerializable(ARG_SCREEN, T::class.java)
        else ->
            @Suppress("DEPRECATION") arguments?.getSerializable(ARG_SCREEN) as? T
    }
}
