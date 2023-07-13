package cd.ghost.addedittask

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import cd.ghost.addedittask.databinding.FragmentAddEditTaskBinding
import cd.ghost.common.helper.viewBinding

class AddEditTaskFragment : Fragment(R.layout.fragment_add_edit_task) {

    private val binding by viewBinding<FragmentAddEditTaskBinding>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}