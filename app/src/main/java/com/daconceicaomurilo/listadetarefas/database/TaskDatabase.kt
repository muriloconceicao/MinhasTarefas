package com.daconceicaomurilo.listadetarefas.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.daconceicaomurilo.listadetarefas.dao.TaskDao
import com.daconceicaomurilo.listadetarefas.entity.TaskEntity

@Database(entities = [TaskEntity::class], version = 1)
abstract class TaskDatabase : RoomDatabase() {
    abstract val taskDao: TaskDao

    companion object {
        @Volatile
        private var INSTANCE: TaskDatabase? = null

        fun getInstance(context: Context) : TaskDatabase {
            synchronized(this) {
                return INSTANCE ?: Room.databaseBuilder(context.applicationContext, TaskDatabase::class.java, "task_db").build().also { INSTANCE = it }
            }
        }
    }
}