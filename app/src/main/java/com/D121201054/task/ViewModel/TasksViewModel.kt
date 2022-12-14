package com.D121201054.task.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.D121201054.task.Data.TasksDatabase
import com.D121201054.task.Repsitory.TasksRepository
import com.D121201054.task.Model.TaskData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TasksViewModel(application: Application):AndroidViewModel(application) {

    val observeTasks: LiveData<List<TaskData>>
    private val repository: TasksRepository

    init {
        val taskDao = TasksDatabase.getDatabase(application).taskDao()
        repository = TasksRepository(taskDao)
        observeTasks = repository.observeTasks
    }

    fun insertTask(task: TaskData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertTask(task)
        }
    }

    fun updateTask(task: TaskData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateTask(task)
        }
    }

    fun deleteTask(task: TaskData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTask(task)
        }
    }

    fun deleteAllTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllTasks()
        }
    }

}