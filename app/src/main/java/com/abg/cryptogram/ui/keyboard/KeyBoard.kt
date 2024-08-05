package com.abg.cryptogram.ui.keyboard

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.graphics.drawable.TransitionDrawable
import android.view.View
import android.widget.TextView
import com.abg.cryptogram.R
import java.util.LinkedList

abstract class KeyBoard {
    abstract fun inflateKeyBoard(keyBoardView: View)
    abstract fun setCLickListeners(keyBoard: KeyBoardClickListener)
    abstract fun tutorialKey() : LinkedList<TextView>

    fun killKey(textView: TextView) {
        val transition = TransitionDrawable(arrayOf(textView.context.getDrawable(R.drawable.empty), textView.context.getDrawable(
            R.drawable.border_right
        )) )
        textView.setOnClickListener {  }
        textView.isClickable = false
        textView.animate()
            .scaleY(1.5f)
            .scaleX(1.5f)
            .setDuration(200)
            .withEndAction{
                textView.animate().scaleY(1f).scaleX(1f)
                textView.background = transition
                textView.alpha = 0.3f
            }.start()
    }

    fun pulseAnimation(view: View) : ObjectAnimator {
        val scaleDown: ObjectAnimator = ObjectAnimator.ofPropertyValuesHolder(
            view,
            PropertyValuesHolder.ofFloat("scaleX", 0.8f),
            PropertyValuesHolder.ofFloat("scaleY", 0.8f)
        )
        scaleDown.setDuration(310)

        scaleDown.repeatCount = ObjectAnimator.INFINITE
        scaleDown.repeatMode = ObjectAnimator.REVERSE

        scaleDown.start()

        return scaleDown
    }
}