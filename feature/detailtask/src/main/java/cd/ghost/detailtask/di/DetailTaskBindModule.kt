package cd.ghost.detailtask.di

import androidx.lifecycle.ViewModel
import cd.ghost.common.di.ViewModelKey
import cd.ghost.detailtask.DetailTaskViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface DetailTaskBindModule {

    @Binds
    @IntoMap
    @ViewModelKey(DetailTaskViewModel::class)
    fun provideViewModel(viewModel: DetailTaskViewModel): ViewModel

}