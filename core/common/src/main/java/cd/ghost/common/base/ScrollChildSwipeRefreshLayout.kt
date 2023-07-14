package cd.ghost.common.base

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class ScrollChildSwipeRefreshLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : SwipeRefreshLayout(context, attrs) {

    var scrollUpChild: View? = null

    var refreshingProcess: Boolean?
        get() = isRefreshing
        set(value) {
            isRefreshing = value ?: false
        }
    fun setRefreshListener(listen: () -> Unit) = setOnRefreshListener { listen() }

    override fun canChildScrollUp() =
        scrollUpChild?.canScrollVertically(-1) ?: super.canChildScrollUp()
}
