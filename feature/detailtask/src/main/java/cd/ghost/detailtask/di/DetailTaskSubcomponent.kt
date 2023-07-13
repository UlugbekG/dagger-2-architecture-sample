package cd.ghost.detailtask.di

import cd.ghost.detailtask.DetailTaskFragment
import dagger.Subcomponent

@Subcomponent(
    modules = [
        DetailTaskBindModule::class
    ]
)
interface DetailTaskSubcomponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): DetailTaskSubcomponent
    }

    fun inject(fragment: DetailTaskFragment)
}