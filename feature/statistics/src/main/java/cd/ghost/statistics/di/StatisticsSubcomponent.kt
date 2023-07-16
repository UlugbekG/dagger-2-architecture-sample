package cd.ghost.statistics.di

import cd.ghost.statistics.StatisticsFragment
import dagger.Subcomponent

@Subcomponent(
    modules = [
        StatisticsBindModule::class
    ]
)
interface StatisticsSubcomponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): StatisticsSubcomponent
    }

    fun inject(fragment: StatisticsFragment)
}