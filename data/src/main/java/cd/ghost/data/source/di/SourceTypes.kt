package cd.ghost.data.source.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class TaskRemoteDataSource

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class TaskLocalDataSource