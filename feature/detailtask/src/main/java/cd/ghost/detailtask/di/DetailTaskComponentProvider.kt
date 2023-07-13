package cd.ghost.detailtask.di

interface DetailTaskComponentProvider {

    fun provideDetailTaskSubcomponent(): DetailTaskSubcomponent.Factory
}