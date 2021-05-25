package com.daconceicaomurilo.listadetarefas.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar

class Utils {
    companion object {
        fun showMessage(view: View?, msg: String) {
            Snackbar.make(view!!, msg, Snackbar.LENGTH_SHORT).show()
        }
    }
}