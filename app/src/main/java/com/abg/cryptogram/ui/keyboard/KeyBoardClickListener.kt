package com.abg.cryptogram.ui.keyboard

import android.view.View
import android.view.View.OnClickListener
import android.widget.TextView

class KeyBoardClickListener(private val clickKeyBoard: (TextView,Char) -> Unit) : OnClickListener {
    override fun onClick(v: View?) {
        val textView = v as TextView
        val char = textView.text[0]
        clickKeyBoard.invoke(textView, char)
    }
}