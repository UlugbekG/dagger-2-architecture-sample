package cd.ghost.detailtask

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import cd.ghost.common.helper.viewBinding
import cd.ghost.detailtask.databinding.FragmentDetailTaskBinding

class DetailTaskFragment : Fragment(R.layout.fragment_detail_task) {

    private val binding by viewBinding<FragmentDetailTaskBinding>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}