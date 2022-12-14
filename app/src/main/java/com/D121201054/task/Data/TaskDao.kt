package com.D121201054.task.Data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.D121201054.task.Model.TaskData

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTask(task: TaskData)

    @Query("SELECT * FROM tasks ORDER BY entryid ASC")
    fun observeTasks(): LiveData<List<TaskData>>

    @Update
    suspend fun updateTask(task: TaskData)

    @Delete
    suspend fun deleteTask(task: TaskData)

    @Query("DELETE FROM tasks")
    suspend fun deleteAllTasks()


}