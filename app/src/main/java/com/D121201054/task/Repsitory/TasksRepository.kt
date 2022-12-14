package com.D121201054.task.Repsitory

import androidx.lifecycle.LiveData
import com.D121201054.task.Data.TaskDao
import com.D121201054.task.Model.TaskData


class TasksRepository (private val taskDao: TaskDao) {

    val observeTasks: LiveData<List<TaskData>> = taskDao.observeTasks()

    suspend fun insertTask(task: TaskData) {
        taskDao.insertTask(task)
    }

    suspend fun updateTask(task: TaskData) {
        taskDao.updateTask(task)
    }

    suspend fun deleteTask(task: TaskData) {
        taskDao.deleteTask(task)
    }

    suspend fun deleteAllTasks() {
        taskDao.deleteAllTasks()
    }
}