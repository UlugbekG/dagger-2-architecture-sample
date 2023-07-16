package cd.ghost.myapplication.navigation

import cd.ghost.addedittask.di.AddEditTaskSubcompProvider
import cd.ghost.detailtask.di.DetailTaskSubcompProvider
import cd.ghost.statistics.di.StatisticsSubcompProvider
import cd.ghost.statistics.di.StatisticsSubcomponent
import cd.ghost.tasks.di.TasksSubcompProvider

interface SubcomponentProviders :
    AddEditTaskSubcompProvider,
    TasksSubcompProvider,
    DetailTaskSubcompProvider,
    StatisticsSubcompProvider