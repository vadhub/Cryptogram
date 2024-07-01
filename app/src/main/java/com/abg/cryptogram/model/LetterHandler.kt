package com.abg.cryptogram.model

import android.content.Context
import android.widget.TextView
import com.abg.cryptogram.R
import com.abg.cryptogram.adapter.LetterAdapter

class LetterHandler(private val selectLetter: (letter: Char) -> Unit) {

    private var focusParentPosition = -1
    private var focusChildPosition = -1

    private var currentChildHolder: LetterAdapter.LetterViewHolder? = null
    private var editLetter: TextView? = null

    private fun updateNonFocusChild(focusChildHolder: LetterAdapter.LetterViewHolder?) {
        focusChildHolder?.editLetter?.background = null
    }

    private fun updateFocusView(context: Context) {
        editLetter = currentChildHolder?.editLetter
        editLetter?.background = context.getDrawable(R.drawable.border)
    }

    fun getCurrentPosition() = Pair(focusParentPosition, focusChildPosition)

    fun setToTextView(char: Char) {
        editLetter?.text = char.toString()
    }

    fun highlightText(currentPositionParent: Int, currentPositionChild: Int, currentHolder: LetterAdapter.LetterViewHolder, letter: Char) {
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