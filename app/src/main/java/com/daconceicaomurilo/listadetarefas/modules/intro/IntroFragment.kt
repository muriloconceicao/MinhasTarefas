package com.daconceicaomurilo.listadetarefas.modules.intro

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.createDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.daconceicaomurilo.listadetarefas.R
import com.daconceicaomurilo.listadetarefas.databinding.FragmentIntroBinding
import com.daconceicaomurilo.listadetarefas.modules.main.MainFragment
import com.daconceicaomurilo.listadetarefas.utils.fadeOut
import com.daconceicaomurilo.listadetarefas.utils.slideFadeIn
import kotlinx.coroutines.launch

class IntroFragment : Fragment(R.layout.fragment_intro) {
    private var _binding: FragmentIntroBinding? = null
    private val binding: FragmentIntroBinding get() = _binding!!

    private lateinit var contextFragment: Context
    private lateinit var datastore: DataStore<Preferences>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentIntroBinding.bind(view)
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
        prepareClickListeners()
    }

    private fun prepareClickListeners() {
        binding.btnContinuar.setOnClickListener { progress() }
    }

    private fun progress() {
        if(binding.editTextNome.text.isEmpty()) {
            showMessage()
        } else {
            saveName()
        }
    }

    private fun saveName() {
        datastore = contextFragment.createDataStore(name = "name")
        lifecycleScope.launch {
            save(
                "name",
                binding.editTextNome.text.toString()
            )
        }
        setupOutAnimation()
    }

    private suspend fun save(key: String, value: String) {
        val dataStoreKey = stringPreferencesKey(key)
        datastore.edit { name ->
            name[dataStoreKey] = value
        }
    }

    private fun setupInAnimations() {
        binding.txtOla.slideFadeIn(700, 0)
        binding.txtDigiteSeuNome.slideFadeIn(700, 500)
        binding.editTextNome.slideFadeIn(700, 500)
        binding.btnContinuar.slideFadeIn(700, 500)
    }

    private fun setupOutAnimation() {
        binding.txtOla.fadeOut(500, 0)
        binding.txtDigiteSeuNome.fadeOut(500, 0)
        binding.editTextNome.fadeOut(500, 0)
        binding.btnContinuar.fadeOut(500, 0)

        binding.btnContinuar.animation.setAnimationListener(object: Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                changeFragment()
            }

            override fun onAnimationStart(animation: Animation?) {
            }
        })
    }

    private fun changeFragment() {
        val mainFragment = MainFragment()
        fragmentManager?.beginTransaction()?.replace(R.id.flFragment, mainFragment)?.addToBackStack(null)?.commit()
    }

    private fun showMessage() {
        Toast.makeText(context, "Digite um nome válido.", Toast.LENGTH_SHORT).show()
    }

}