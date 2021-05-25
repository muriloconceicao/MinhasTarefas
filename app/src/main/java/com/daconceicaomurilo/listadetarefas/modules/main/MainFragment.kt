package com.daconceicaomurilo.listadetarefas.modules.main

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.createDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.daconceicaomurilo.listadetarefas.R
import com.daconceicaomurilo.listadetarefas.adapters.SwipeToDelete
import com.daconceicaomurilo.listadetarefas.adapters.TaskAdapter
import com.daconceicaomurilo.listadetarefas.database.TaskDatabase
import com.daconceicaomurilo.listadetarefas.databinding.FragmentMainBinding
import com.daconceicaomurilo.listadetarefas.entity.TaskEntity
import com.daconceicaomurilo.listadetarefas.modules.taskdetails.TaskDetailFragment
import com.daconceicaomurilo.listadetarefas.utils.Utils
import com.daconceicaomurilo.listadetarefas.utils.fadeIn
import com.daconceicaomurilo.listadetarefas.utils.fadeOut
import com.daconceicaomurilo.listadetarefas.utils.slideFadeIn
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainFragment : Fragment(R.layout.fragment_main) {
    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding get() = _binding!!
    private lateinit var contextFragment: Context
    private lateinit var datastore: DataStore<Preferences>
    private lateinit var _adapter: TaskAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMainBinding.bind(view)
        prepareFragment()
    }

    override fun onAttach(context: Context) {
        this.contextFragment = context
        super.onAttach(context)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun prepareFragment() {
        setupInAnimations()
        readName()
        getTask()
        prepareClickListeners()

    }

    private fun prepareClickListeners() {
        binding.btnAddTask.setOnClickListener { goToTaskDetails() }
    }

    private fun goToTaskDetails() {
        setupOutAnimations()
    }

    private fun getTask() {
        val dao = TaskDatabase.getInstance(contextFragment).taskDao
        var taskList: List<TaskEntity>

        lifecycleScope.launch {
            taskList = dao.getTask()
            checkIfTaskIsEmpty(taskList)
        }
    }

    private fun checkIfTaskIsEmpty(tasksList: List<TaskEntity>) {
        if(tasksList.isEmpty()) {
            changeListVisibility()
        } else {
            prepareRecycleView(tasksList)
        }
    }

    private fun changeListVisibility() {
        binding.rvTasks.visibility = View.GONE
        binding.txtNoTasks.visibility = View.VISIBLE
    }

    private fun prepareRecycleView(taskList: List<TaskEntity>) {
        val adapter = TaskAdapter(taskList)
        _adapter = adapter
        binding.rvTasks.adapter = adapter
        binding.rvTasks.layoutManager = LinearLayoutManager(contextFragment)
        setupSwipeToDelete(binding.rvTasks)
    }

    private fun readName() {
        datastore = contextFragment.createDataStore("name")
        lifecycleScope.launch {
            val value = read("name")
            binding.txtName.text = value
        }
    }

    private suspend fun read(key: String): String? {
        val dataStoreKey = stringPreferencesKey(key)
        val preferences = datastore.data.first()
        return preferences[dataStoreKey]
    }

    private fun setupInAnimations() {
        binding.clTopLayout.fadeIn(1000, 0)
        binding.txtName.slideFadeIn(1000, 600)
        binding.txtNoTasks.slideFadeIn(1000, 600)
        binding.rvTasks.slideFadeIn(1000, 600)
        binding.btnAddTask.slideFadeIn(1000, 600)
    }

    private fun setupOutAnimations() {
        binding.clTopLayout.fadeOut(500, 0)
        binding.txtName.fadeOut(500, 0)
        binding.txtNoTasks.fadeOut(500, 0)
        binding.rvTasks.fadeOut(500, 0)
        binding.btnAddTask.fadeOut(500, 0)
        binding.btnAddTask.animation.setAnimationListener(object: Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                changeFragment()
            }

            override fun onAnimationRepeat(animation: Animation?) {
            }

        })
    }

    private fun setupSwipeToDelete(recyclerView: RecyclerView) {
        val swipeToDeleteCallback = object : SwipeToDelete() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val taskToDelete = _adapter.getTaskAtPosition(viewHolder.adapterPosition)
                val dao = TaskDatabase.getInstance(contextFragment).taskDao
                lifecycleScope.launch {
                    dao.deleteTask(taskToDelete)
                    Utils.showMessage(view, "Tarefa Deletada")
                }
                getTask()
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun changeFragment() {
        val taskDetailFragment = TaskDetailFragment()
        fragmentManager?.beginTransaction()?.replace(R.id.flFragment, taskDetailFragment)?.addToBackStack(null)?.commit()
    }
}