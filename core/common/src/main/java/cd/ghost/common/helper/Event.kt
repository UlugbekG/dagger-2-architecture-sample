package cd.ghost.common.helper

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

/**
 * Used as a wrapper for data that is exposed via a LiveData that represents an event.
 */
open class Event<out T>(private val content: T) {

    @Suppress("MemberVisibilityCanBePrivate")
    var hasBeenHandled = false
        private set // Allow external read but not write

    /**
     * Returns the content and prevents its use again.
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekContent(): T = content
}

typealias MutableLiveEvent<T> = MutableLiveData<Event<T>>
typealias LiveEvent<T> = LiveData<Event<T>>
typealias EventListener<T> = (T) -> Unit
fun <T> MutableLiveData<T>.asLiveData(): LiveData<T> = this
fun <T> MutableLiveEvent<T>.publishEvent(value: T) {
    this.value = Event(value)
}
fun <T> LiveEvent<T>.observeEvent(lifecycleOwner: LifecycleOwner, listener: EventListener<T>) {
    this.observe(lifecycleOwner) {
        it?.getContentIfNotHandled()?.let { value ->
            listener(value)
        }
    }
}




/**
 * An [Observer] for [Event]s, simplifying the pattern of checking if the [Event]'s content has
 * already been handled.
 *
 * [onEventUnhandledContent] is *only* called if the [Event]'s contents has not been handled.
 */
class EventObserver<T>(private val onEventUnhandledContent: (T) -> Unit) : Observer<Event<T>> {
    override fun onChanged(event: Event<T>?) {
        event?.getContentIfNotHandled()?.let {
            onEventUnhandledContent(it)
        }
    }
}
