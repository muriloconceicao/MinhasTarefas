package com.daconceicaomurilo.listadetarefas.utils

import android.view.View
import android.view.animation.AnimationUtils
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.daconceicaomurilo.listadetarefas.R

fun View.slideFadeIn(animTime: Long, startOffset: Long) {
    val slideFadeIn = AnimationUtils.loadAnimation(context, R.anim.fadein_move_anim).apply {
        duration = animTime
        interpolator = FastOutSlowInInterpolator()
        this.startOffset = startOffset
    }
    startAnimation(slideFadeIn)
}

fun View.fadeIn(animTime: Long, startOffset: Long) {
    val slideFadeOut = AnimationUtils.loadAnimation(context, R.anim.fadein_anim).apply {
        duration = animTime
        interpolator = FastOutSlowInInterpolator()
        this.startOffset = startOffset
    }
    startAnimation(slideFadeOut)
}

fun View.fadeOut(animTime: Long, startOffset: Long) {
    val slideFadeOut = AnimationUtils.loadAnimation(context, R.anim.fadeout_anim).apply {
        duration = animTime
        interpolator = FastOutSlowInInterpolator()
        this.startOffset = startOffset
    }
    startAnimation(slideFadeOut)
}