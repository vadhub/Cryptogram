package com.abg.cryptogram

import android.content.Context
import com.abg.cryptogram.adapter.LetterAdapter

class LetterHandler(private val selectLetter: (letter: String) -> Unit) {

    private var focusParentPosition = -1
    private var focusChildPosition = -1

    private var currentChildHolder: LetterAdapter.LetterViewHolder? = null

    private fun updateNonFocusChild(focusChildHolder: LetterAdapter.LetterViewHolder?) {
        focusChildHolder?.editLetter?.background = null
    }

    private fun updateFocusView(context: Context) {
        currentChildHolder?.editLetter?.background = context.getDrawable(R.drawable.border)
    }

    fun highlightText(currentPositionParent: Int, currentPositionChild: Int, currentHolder: LetterAdapter.LetterViewHolder, letter: String) {
        if (currentPositionChild == focusChildPosition && currentPositionParent == focusParentPosition) {
            updateFocusView(currentHolder.itemView.context)
        } else {
            updateNonFocusChild(currentChildHolder)
            focusParentPosition = currentPositionParent
            focusChildPosition = currentPositionChild
            currentChildHolder = currentHolder
            updateFocusView(currentHolder.itemView.context)
        }
        selectLetter.invoke(letter)
    }
}