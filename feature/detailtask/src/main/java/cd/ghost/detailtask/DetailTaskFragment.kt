package cd.ghost.detailtask

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import cd.ghost.common.base.BaseArgument
import cd.ghost.common.base.args
import cd.ghost.common.helper.viewBinding
import cd.ghost.detailtask.databinding.FragmentDetailTaskBinding
import cd.ghost.detailtask.di.DetailTaskSubcompProvider
import javax.inject.Inject

class DetailTaskFragment : Fragment(R.layout.fragment_detail_task) {

    class DetailTaskArgument(
        val taskId: String
    ) : BaseArgument

    private val binding by viewBinding<FragmentDetailTaskBinding>()

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private val viewModel by viewModels<DetailTaskViewModel> { factory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity() as DetailTaskSubcompProvider)
            .provideDetailTaskSubcomp()
            .create()
            .inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val task = args<DetailTaskArgument>()?.taskId
        viewModel.initTask(task)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}