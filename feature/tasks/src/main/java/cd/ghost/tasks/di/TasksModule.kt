package cd.ghost.tasks.di

import androidx.lifecycle.ViewModel
import cd.ghost.common.di.ViewModelKey
import cd.ghost.tasks.TasksViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface TasksModule {

    @Binds
    @IntoMap
    @ViewModelKey(TasksViewModel::class)
    fun bindViewModel(viewModel: TasksViewModel): ViewModel

}