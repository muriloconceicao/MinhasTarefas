package com.daconceicaomurilo.listadetarefas.modules.taskdetails

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.daconceicaomurilo.listadetarefas.R
import com.daconceicaomurilo.listadetarefas.database.TaskDatabase
import com.daconceicaomurilo.listadetarefas.databinding.FragmentTaskDetailBinding
import com.daconceicaomurilo.listadetarefas.entity.TaskEntity
import com.daconceicaomurilo.listadetarefas.modules.main.MainFragment
import com.daconceicaomurilo.listadetarefas.utils.Utils
import com.daconceicaomurilo.listadetarefas.utils.fadeIn
import com.daconceicaomurilo.listadetarefas.utils.fadeOut
import com.daconceicaomurilo.listadetarefas.utils.slideFadeIn
import kotlinx.coroutines.launch

class TaskDetailFragment : Fragment(R.layout.fragment_task_detail) {
    private var _binding: FragmentTaskDetailBinding? = null
    private val binding: FragmentTaskDetailBinding get() = _binding!!
    private lateinit var contextFragment: Context

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentTaskDetailBinding.bind(view)
        prepareFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.contextFragment = context
    }

    private fun prepareFragment() {
        setupInAnimations()
        prepareClickListeners()
    }

    private fun prepareClickListeners() {
        binding.imgBackArrow.setOnClickListener { goBackToMain() }
        binding.btnReset.setOnClickListener { resetTextFields() }
        binding.btnSave.setOnClickListener { saveTask() }
    }

    private fun saveTask() {
        val title = binding.edtDetailsTitle.text.toString()
        val description = binding.edtDetailsDescription.text.toString()

        if(title.isNotEmpty() && description.isNotEmpty()) {
            val task = TaskEntity(title, description)
            val dao = TaskDatabase.getInstance(contextFragment).taskDao
            lifecycleScope.launch {
                dao.insertTask(task)
                Utils.showMessage(view, "Tarefa Salva")
                resetTextFields()
            }
        } else {
            Utils.showMessage(view, "Preencha os campos")
        }
    }

    private fun resetTextFields() {
        binding.edtDetailsTitle.setText("")
        binding.edtDetailsDescription.setText("")
    }

    private fun goBackToMain() {
        setupOutAnimations()
    }

    private fun setupInAnimations() {
        binding.imgBackArrow.fadeIn(1000, 0)
        binding.txtNewNote.fadeIn(1000, 0)
        binding.txtDetailsTitle.fadeIn(1000, 0)
        binding.txtDetailsDescription.fadeIn(1000, 0)
        binding.edtDetailsTitle.fadeIn(1000, 0)
        binding.edtDetailsDescription.fadeIn(1000, 0)
        binding.btnReset.slideFadeIn(1000, 300)
        binding.btnSave.slideFadeIn(1000, 300)
    }

    private fun setupOutAnimations() {
        binding.imgBackArrow.fadeOut(500, 0)
        binding.txtNewNote.fadeOut(500, 0)
        binding.txtDetailsTitle.fadeOut(500, 0)
        binding.txtDetailsDescription.fadeOut(500, 0)
        binding.edtDetailsTitle.fadeOut(500, 0)
        binding.edtDetailsDescription.fadeOut(500, 0)
        binding.btnReset.fadeOut(500, 0)
        binding.btnSave.fadeOut(500, 0)
        binding.btnSave.animation.setAnimationListener(object: Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                changeFragment()
            }

            override fun onAnimationRepeat(animation: Animation?) {
            }

        })
    }

    private fun changeFragment() {
        val mainFragment = MainFragment()
        fragmentManager?.beginTransaction()?.replace(R.id.flFragment, mainFragment)?.addToBackStack(null)?.commit()
    }
}