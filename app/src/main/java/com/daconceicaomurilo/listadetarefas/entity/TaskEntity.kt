package com.daconceicaomurilo.listadetarefas.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TaskEntity(
    val title: String,
    val description: String,
){
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}