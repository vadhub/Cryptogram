package com.abg.cryptogram.ui.tutorial

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBar.LayoutParams
import androidx.fragment.app.DialogFragment
import com.abg.cryptogram.R

class TutorialIntroFragment : DialogFragment() {

    data class SymbolShort(val char: Char, val code: Int, val selected: Boolean)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val view = layoutInflater.inflate(R.layout.tutor_intro_dialog, null)

        val word: LinearLayout = view.findViewById(R.id.word)
        val letter = resources.getStringArray(R.array.letter)
        val locale = resources.configuration.locale
        val listSymbol: List<SymbolShort>
        if (locale.language == "ru") {
            listSymbol = mutableListOf(
                SymbolShort(letter[0][0], 1, false), // с
                SymbolShort(letter[1][0], 2, false), // л
                SymbolShort(letter[2][0], 3, true),  // о
                SymbolShort(letter[3][0], 4, false), // в
                SymbolShort(letter[2][0], 3, true)   // о
            )
        } else {
            listSymbol = mutableListOf(
                SymbolShort(letter[0][0], 1, false), // l
                SymbolShort(letter[1][0], 2, true),  // e
                SymbolShort(letter[2][0], 3, false), // t
                SymbolShort(letter[2][0], 3, false), // t
                SymbolShort(letter[1][0], 2, true),  // e
                SymbolShort(letter[3][0], 4, false), // r
            )
        }

        word.addView(createRow(listSymbol, view.context))

        val builder = AlertDialog.Builder(view.context)
        builder.setView(view).setPositiveButton("Ok") {
            dialog,_ -> dialog.dismiss()
        }
        return builder.show()
    }

    fun createRow(listSymbols: List<SymbolShort>, thisContext: Context): LinearLayout {
        val row = LinearLayout(thisContext)
        row.orientation = LinearLayout.HORIZONTAL
        row.layoutParams = ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        for (i in listSymbols.indices) {
            row.addView(createLetter(listSymbols[i]))
        }
        return row
    }

    fun createLetter(symbol: SymbolShort): View {
        val view: View = layoutInflater.inflate(R.layout.item_letter, null)
        val editLetter = view.findViewById<TextView>(R.id.editLetter)
        val codeTextView = view.findViewById<TextView>(R.id.codeTextView)
        if (symbol.selected) select(codeTextView)
        editLetter.text = symbol.char.toString()
        codeTextView.text = symbol.code.toString()

        return view
    }

    fun select(editLetter: TextView) {
        editLetter.setTextColor(resources.getColor(R.color.beige))
    }
}