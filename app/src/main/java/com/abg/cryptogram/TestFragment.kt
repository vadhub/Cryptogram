package com.abg.cryptogram

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBar.LayoutParams
import androidx.fragment.app.Fragment
import com.abg.cryptogram.adapter.LetterAdapter
import com.abg.cryptogram.model.Game
import com.abg.cryptogram.model.MegaParser
import com.abg.cryptogram.model.Symbol
import java.util.LinkedList

class TestFragment : Fragment() {

    private lateinit var thisContext: Context
    private var currentTextView: TextView? = null
    private val emptyTextViewList: LinkedList<TextView> = LinkedList()
    private lateinit var navigator: Navigator

    override fun onAttach(context: Context) {
        super.onAttach(context)
        thisContext = context
        navigator = context as Navigator
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.test_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val sentence = "УПАДИ СЕМЬ РАЗ И ВОСЕМ РАЗ ПОДНИМИСЬ"
        val keyBoard = KeyBoard()
        val sentenceView = view.findViewById<LinearLayout>(R.id.sentence)
        val game = Game {
            when(it) {
                Game.StatusGame.GAME_OVER -> {navigator.startFragment(LostFragment())}
                Game.StatusGame.WIN -> {val fragmentWin = FragmentWin()
                    fragmentWin.arguments = Bundle().apply {
                        putString("quote", sentence.lowercase())
                        putString("author", "hz")
                    }
                    navigator.startFragment(fragmentWin)}
            }
        }


        val str = MegaParser.insertSlashes(sentence)
        Log.d("123", str)
        val list = game.sentenceMapToListWords(MegaParser.insertSlashes(sentence))
        Log.d("##", list.toTypedArray().contentToString())
        for (i in 0 until list.size) {
            sentenceView.addView(createRow(list[i].letters))
        }

        val keyBoardView: View = view.findViewById(R.id.keyboardView)
        val keyBoardListener = KeyBoardClickListener { textview, letter ->
            if (game.compareLetters(textview, letter)) {
                currentTextView?.text = letter.toString()
                changeBackground(currentTextView!!, false)
                emptyTextViewList.remove(currentTextView)
                currentTextView = emptyTextViewList.element()
                changeBackground(currentTextView!!, true)
            } else {
                Log.d("@@", "(:LOL:) $letter")
            }
        }

        keyBoard.inflateKeyBoard(keyBoardView)
        keyBoard.setCLickListeners(keyBoardListener)
    }

    fun createRow(listSymbols: List<Symbol>): LinearLayout  {
        val row = LinearLayout(thisContext)
        row.orientation = LinearLayout.HORIZONTAL
        row.layoutParams = ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        for (i in listSymbols.indices) {
            if (listSymbols[i].viewType == LetterAdapter.VIEW_TYPE_LETTER) {
                row.addView(createLetter(listSymbols[i]))
            } else {
                row.addView(createSigh(listSymbols[i]))
            }
        }
        return row
    }

    fun createLetter(symbol: Symbol): View {
        val view: View = layoutInflater.inflate(R.layout.item_letter, null)
        val editLetter = view.findViewById<TextView>(R.id.editLetter)
        val codeTextView = view.findViewById<TextView>(R.id.codeTextView)

        if (symbol.isFill) {
            editLetter.text = symbol.symbol.toString()
        } else {
            editLetter.setOnClickListener {
                if (currentTextView!=null) changeBackground(currentTextView!!, false)
                changeBackground(editLetter, true)
                currentTextView = editLetter
            }
            editLetter.text = ""
            emptyTextViewList.add(editLetter)
        }

        codeTextView.text = symbol.code.toString()
        codeTextView.text = if (!symbol.isShowCode) symbol.code.toString() else ""

        return view
    }

    fun changeBackground(editLetter: TextView, isSelected: Boolean) {
        editLetter.background = if (isSelected) thisContext.getDrawable(R.drawable.border) else null
    }

    fun createSigh(symbol: Symbol): View {
        val view: View = layoutInflater.inflate(R.layout.item_sign, null)
        val editLetter = view.findViewById<TextView>(R.id.sign)
        editLetter.text = symbol.symbol.toString()
        return view
    }
}