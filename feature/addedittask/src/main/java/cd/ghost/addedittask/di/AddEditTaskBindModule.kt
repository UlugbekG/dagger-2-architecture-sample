package cd.ghost.addedittask.di

import androidx.lifecycle.ViewModel
import cd.ghost.addedittask.AddEditTaskViewModel
import cd.ghost.common.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface AddEditTaskBindModule {

    @Binds
    @IntoMap
    @ViewModelKey(AddEditTaskViewModel::class)
    fun bindViewModel(viewModel: AddEditTaskViewModel): ViewModel

}