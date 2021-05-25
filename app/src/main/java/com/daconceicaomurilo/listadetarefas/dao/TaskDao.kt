package com.daconceicaomurilo.listadetarefas.dao

import androidx.room.*
import com.daconceicaomurilo.listadetarefas.entity.TaskEntity

@Dao
interface TaskDao {

    @Insert
    suspend fun insertTask(task: TaskEntity)

    @Query("SELECT * FROM TaskEntity")
    suspend fun getTask() : List<TaskEntity>

    @Delete
    suspend fun deleteTask(task: TaskEntity)

}