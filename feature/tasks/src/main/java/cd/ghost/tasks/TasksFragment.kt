package cd.ghost.tasks

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import cd.ghost.tasks.di.TasksComponentProvider

class TasksFragment : Fragment(R.layout.fragment_tasks) {

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as TasksComponentProvider)
            .provideTaskComponent()
            .create()
            .inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}