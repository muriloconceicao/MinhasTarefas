package com.daconceicaomurilo.listadetarefas.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.daconceicaomurilo.listadetarefas.R
import com.daconceicaomurilo.listadetarefas.entity.TaskEntity

class TaskAdapter(private var tasks: List<TaskEntity>) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var title: TextView = itemView.findViewById(R.id.rvTxtTitle)
        internal var description: TextView = itemView.findViewById(R.id.rvTxtDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.title.text = tasks[position].title
        holder.description.text = tasks[position].description
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    fun getTaskAtPosition(position: Int): TaskEntity {
        return tasks[position]
    }

}