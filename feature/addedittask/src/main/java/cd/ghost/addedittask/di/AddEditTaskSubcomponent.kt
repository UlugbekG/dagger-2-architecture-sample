package cd.ghost.addedittask.di

import cd.ghost.addedittask.AddEditTaskFragment
import dagger.Subcomponent


@Subcomponent(
    modules = [AddEditTaskBindModule::class]
)
interface AddEditTaskSubcomponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): AddEditTaskSubcomponent
    }

    fun inject(fragment: AddEditTaskFragment)
}