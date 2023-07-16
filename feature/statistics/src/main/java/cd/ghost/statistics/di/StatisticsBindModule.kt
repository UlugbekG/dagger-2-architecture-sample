package cd.ghost.statistics.di

import androidx.lifecycle.ViewModel
import cd.ghost.common.di.ViewModelKey
import cd.ghost.statistics.StatisticsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class StatisticsBindModule {

    @Binds
    @IntoMap
    @ViewModelKey(StatisticsViewModel::class)
    abstract fun bindViewModel(viewModel: StatisticsViewModel): ViewModel

}