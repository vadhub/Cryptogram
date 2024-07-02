package com.abg.cryptogram

import android.view.View
import android.view.View.OnClickListener

class KeyBoardClickListener(private val clickKeyBoard: (Char) -> Unit) : OnClickListener {
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.letterA -> {clickKeyBoard.invoke('А')}
            R.id.letterBlyat -> {clickKeyBoard.invoke('Б')}
            R.id.letterV -> {clickKeyBoard.invoke('В')}
            R.id.letterG -> {clickKeyBoard.invoke('Г')}
            R.id.letterD -> {clickKeyBoard.invoke('Д')}
            R.id.letterE -> {clickKeyBoard.invoke('Е')}
            R.id.letterGZ -> {clickKeyBoard.invoke('Ж')}
            R.id.letterZ -> {clickKeyBoard.invoke('З')}
            R.id.letterI -> {clickKeyBoard.invoke('И')}
            R.id.letterI_short -> {clickKeyBoard.invoke('Й')}
            R.id.letterK -> {clickKeyBoard.invoke('К')}
            R.id.letterL -> {clickKeyBoard.invoke('Л')}
            R.id.letterM -> {clickKeyBoard.invoke('М')}
            R.id.letterN -> {clickKeyBoard.invoke('Н')}
            R.id.letterO -> {clickKeyBoard.invoke('О')}
            R.id.letterP -> {clickKeyBoard.invoke('П')}
            R.id.letterR -> {clickKeyBoard.invoke('Р')}
            R.id.letterS -> {clickKeyBoard.invoke('С')}
            R.id.letterT -> {clickKeyBoard.invoke('Т')}
            R.id.letterU -> {clickKeyBoard.invoke('У')}
            R.id.letterPH -> {clickKeyBoard.invoke('Ф')}
            R.id.letterH -> {clickKeyBoard.invoke('Х')}
            R.id.letterCH -> {clickKeyBoard.invoke('Ч')}
            R.id.letterC -> {clickKeyBoard.invoke('Ц')}
            R.id.letterSH -> {clickKeyBoard.invoke('Ш')}
            R.id.letterSCH -> {clickKeyBoard.invoke('Щ')}
            R.id.letter_not -> {clickKeyBoard.invoke('Ы')}
            R.id.letter_strong -> {clickKeyBoard.invoke('Ъ')}
            R.id.letter_gentle_sign -> {clickKeyBoard.invoke('Ь')}
            R.id.letterAE -> {clickKeyBoard.invoke('Э')}
            R.id.letterYU -> {clickKeyBoard.invoke('Ю')}
            R.id.letterYA -> {clickKeyBoard.invoke('Я')}
        }
    }
}