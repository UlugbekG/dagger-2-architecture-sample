package cd.ghost.tasks.view

import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import cd.ghost.tasks.R

fun Fragment.setupRefreshLayout(
    refreshLayout: ScrollChildSwipeRefreshLayout,
    scrollUpChild: View? = null
) {
//    refreshLayout.setColorSchemeColors(
//        ContextCompat.getColor(requireActivity(), R.color.colorPrimary),
//        ContextCompat.getColor(requireActivity(), R.color.colorAccent),
//        ContextCompat.getColor(requireActivity(), R.color.colorPrimaryDark)
//    )
    // Set the scrolling view in the custom SwipeRefreshLayout.
    scrollUpChild?.let {
        refreshLayout.scrollUpChild = it
    }
}
