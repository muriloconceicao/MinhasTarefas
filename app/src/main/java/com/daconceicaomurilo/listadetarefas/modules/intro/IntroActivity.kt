package com.daconceicaomurilo.listadetarefas.modules.intro

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.createDataStore
import androidx.lifecycle.lifecycleScope
import com.daconceicaomurilo.listadetarefas.R
import com.daconceicaomurilo.listadetarefas.databinding.ActivityIntroBinding
import com.daconceicaomurilo.listadetarefas.modules.main.MainFragment
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class IntroActivity : AppCompatActivity() {

    private var _binding: ActivityIntroBinding? = null
    private val binding: ActivityIntroBinding get() = _binding!!
    private lateinit var datastore: DataStore<Preferences>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        prepareActivity()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun prepareActivity() {
        checkName()
    }

    private fun checkName() {
        datastore = createDataStore("name")
        lifecycleScope.launch {
            val value = readName("name")
            if(value != null) {
                changeToMainFragment()
            } else {
                changeToIntroFragment()
            }
        }
    }

    private suspend fun readName(key: String): String? {
        val dataStoreKey = stringPreferencesKey(key)
        val preferences = datastore.data.first()
        return preferences[dataStoreKey]
    }

    private fun changeToIntroFragment() {
        val introFragment = IntroFragment()
        supportFragmentManager.beginTransaction().apply { replace(R.id.flFragment, introFragment).addToBackStack(null).commit() }
    }

    private fun changeToMainFragment() {
        val mainFragment = MainFragment()
        supportFragmentManager.beginTransaction().apply { replace(R.id.flFragment, mainFragment).addToBackStack(null).commit() }
    }
}