package cd.ghost.statistics.di

interface StatisticsSubcompProvider {

    fun provideStatisticsSubComp(): StatisticsSubcomponent.Factory
}