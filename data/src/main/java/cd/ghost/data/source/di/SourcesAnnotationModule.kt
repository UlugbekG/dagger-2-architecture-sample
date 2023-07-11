package cd.ghost.data.source.di

import dagger.Module
import javax.inject.Qualifier

@Module
interface SourcesAnnotationModule {

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class TaskRemoteDataSource

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class TaskLocalDataSource
}