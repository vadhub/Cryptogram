package com.abg.cryptogram.model

import android.animation.AnimatorInflater
import android.annotation.SuppressLint
import android.util.Log
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import com.abg.cryptogram.R
import com.abg.cryptogram.adapter.LetterAdapter

class LetterHandler(private val selectLetter: (letter: Char) -> Unit) {

    private var focusParentPosition = -1
    private var focusChildPosition = -1

    private var currentChildHolder: LetterAdapter.LetterViewHolder? = null

    private fun updateNonFocusChild(focusChildHolder: LetterAdapter.LetterViewHolder?) {
        focusChildHolder?.editLetter?.background = null
    }

    fun updateFocusView(editLetter: TextView) {
        editLetter.background = AppCompatResources.getDrawable(editLetter.context, R.drawable.border)
    }

    fun getCurrentPosition() = Pair(focusParentPosition, focusChildPosition)

    fun setToTextView(char: Char, isTrueLetter: Boolean) {
        val editLetter = currentChildHolder?.editLetter
        if (editLetter != null) {
            editLetter.text = char.toString()

            if (!isTrueLetter) {
                val colorChangeAnimation = AnimatorInflater.loadAnimator(
                    editLetter.context,
                    R.animator.fade_out_with_color
                )
                colorChangeAnimation.setTarget(editLetter)
                colorChangeAnimation.start()
            }
        }
    }

    fun highlightText(
        currentPositionParent: Int,
        currentPositionChild: Int,
        currentHolder: LetterAdapter.LetterViewHolder,
        letter: Char
    ) {
        if (currentPositionChild == focusChildPosition && currentPositionParent == focusParentPosition) {
            updateFocusView(currentHolder.editLetter)
        } else {
            updateNonFocusChild(currentChildHolder)
            focusParentPosition = currentPositionParent
            focusChildPosition = currentPositionChild
            currentChildHolder = currentHolder
            updateFocusView(currentHolder.editLetter)
        }
        selectLetter.invoke(letter)
    }
}