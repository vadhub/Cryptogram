package com.abg.cryptogram

import android.view.View
import android.view.View.OnClickListener
import android.widget.TextView

class KeyBoardClickListener(private val clickKeyBoard: (TextView,Char) -> Unit) : OnClickListener {
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.letterA -> {clickKeyBoard.invoke(v as TextView, 'А')}
            R.id.letterBlyat -> {clickKeyBoard.invoke(v as TextView, 'Б')}
            R.id.letterV -> {clickKeyBoard.invoke(v as TextView, 'В')}
            R.id.letterG -> {clickKeyBoard.invoke(v as TextView, 'Г')}
            R.id.letterD -> {clickKeyBoard.invoke(v as TextView, 'Д')}
            R.id.letterE -> {clickKeyBoard.invoke(v as TextView,'Е')}
            R.id.letterGZ -> {clickKeyBoard.invoke(v as TextView,'Ж')}
            R.id.letterZ -> {clickKeyBoard.invoke(v as TextView,'З')}
            R.id.letterI -> {clickKeyBoard.invoke(v as TextView,'И')}
            R.id.letterI_short -> {clickKeyBoard.invoke(v as TextView,'Й')}
            R.id.letterK -> {clickKeyBoard.invoke(v as TextView, 'К')}
            R.id.letterL -> {clickKeyBoard.invoke(v as TextView, 'Л')}
            R.id.letterM -> {clickKeyBoard.invoke(v as TextView, 'М')}
            R.id.letterN -> {clickKeyBoard.invoke(v as TextView, 'Н')}
            R.id.letterO -> {clickKeyBoard.invoke(v as TextView, 'О')}
            R.id.letterP -> {clickKeyBoard.invoke(v as TextView, 'П')}
            R.id.letterR -> {clickKeyBoard.invoke(v as TextView, 'Р')}
            R.id.letterS -> {clickKeyBoard.invoke(v as TextView, 'С')}
            R.id.letterT -> {clickKeyBoard.invoke(v as TextView, 'Т')}
            R.id.letterU -> {clickKeyBoard.invoke(v as TextView, 'У')}
            R.id.letterPH -> {clickKeyBoard.invoke(v as TextView,'Ф')}
            R.id.letterH -> {clickKeyBoard.invoke(v as TextView,'Х')}
            R.id.letterCH -> {clickKeyBoard.invoke(v as TextView,'Ч')}
            R.id.letterC -> {clickKeyBoard.invoke(v as TextView,'Ц')}
            R.id.letterSH -> {clickKeyBoard.invoke(v as TextView,'Ш')}
            R.id.letterSCH -> {clickKeyBoard.invoke(v as TextView,'Щ')}
            R.id.letter_not -> {clickKeyBoard.invoke(v as TextView,'Ы')}
            R.id.letter_strong -> {clickKeyBoard.invoke(v as TextView,'Ъ')}
            R.id.letter_gentle_sign -> {clickKeyBoard.invoke(v as TextView,'Ь')}
            R.id.letterAE -> {clickKeyBoard.invoke(v as TextView,'Э')}
            R.id.letterYU -> {clickKeyBoard.invoke(v as TextView,'Ю')}
            R.id.letterYA -> {clickKeyBoard.invoke(v as TextView,'Я')}
        }
    }
}