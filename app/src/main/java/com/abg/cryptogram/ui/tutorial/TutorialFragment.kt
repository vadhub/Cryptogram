package com.abg.cryptogram.ui.tutorial

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
import com.abg.cryptogram.Navigator
import com.abg.cryptogram.R
import com.abg.cryptogram.data.SaveConfig
import com.abg.cryptogram.model.Game
import com.abg.cryptogram.model.Symbol
import com.abg.cryptogram.model.Word
import com.abg.cryptogram.ui.GameFragment
import com.abg.cryptogram.ui.LostFragment
import com.abg.cryptogram.ui.keyboard.KeyBoard
import com.abg.cryptogram.ui.keyboard.KeyBoardClickListener
import java.util.LinkedList


class TutorialFragment : Fragment() {

    private lateinit var thisContext: Context
    private lateinit var currentTextView: TextView
    private val emptyTextViewList: LinkedList<Pair<TextView, Char>> = LinkedList()
    private val codeWithTextViewList: LinkedList<Pair<TextView /* textview code */, Char /* letter */>> = LinkedList()
    private lateinit var navigator: Navigator
    private lateinit var game: Game

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
        return inflater.inflate(R.layout.tutor_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val saveConfig = SaveConfig(thisContext)
        val keyBoardView: View = view.findViewById(R.id.keyboardView)
        val sentenceView = view.findViewById<LinearLayout>(R.id.sentence)
        val keyBoard = KeyBoard()
        val wrongView: View = view.findViewById(R.id.wrong)
        game = Game {
            when(it) {
                Game.StatusGame.GAME_OVER -> {navigator.startFragment(LostFragment())}
                Game.StatusGame.WIN -> {
                    saveConfig.saveIsTutorComplete(true)
                    val gameFragment = GameFragment()
                    sentenceView.animate().alpha(0f).setDuration(200).withEndAction {
                        navigator.startFragment(gameFragment)
                    }.start()
                }
            }
        }
        game.setNotGuessed(6)
        game.setFrequency(mutableMapOf(Pair('П', 1), Pair('М',1), Pair('З',1), Pair('И',1), Pair('Д',1), Pair('Ь',1)))
        keyBoard.inflateKeyBoard(keyBoardView)
        val list = tutorial()
        val listKeyTutorial = keyBoard.tutorialKey()
        var currentKey = listKeyTutorial.peek()
        var objectAnimator = keyBoard.pulseAnimation(currentKey)

        game.setAllConcreteLetterFindListener { letter, textView ->
            keyBoard.killKey(textView)
            clearAllCodeFromFindLetter(letter)
        }

        for (i in 0 until list.size) {
            sentenceView.addView(createRow(list[i].letters))
        }
        val first = emptyTextViewList.peek()
        if (first != null) {
            currentTextView = first.first
            changeBackground(currentTextView, true)
            game.setLetter(emptyTextViewList.element().second)
        }
        val keyBoardListener = KeyBoardClickListener {textview, letter ->
            if (game.compareLetters(textview, letter)) {
                objectAnimator.cancel()
                textview.scaleX = 1f
                textview.scaleY = 1f
                listKeyTutorial.remove(textview)
                currentKey = listKeyTutorial.peek()
                currentKey?.let { objectAnimator = keyBoard.pulseAnimation(it) }

                currentTextView.text = letter.toString()
                currentTextView.setOnClickListener(null /* remove click for forbid selected */)
                changeBackground(currentTextView, false)
                emptyTextViewList.remove(Pair(currentTextView, letter))
                val letterPair = emptyTextViewList.peek()
                if (letterPair != null) {
                    currentTextView = letterPair.first
                    changeBackground(currentTextView, true)
                    game.setLetter(emptyTextViewList.element().second)
                }
            } else {
                wrongView.animate()
                    .alpha(1f)
                    .setDuration(200) // То есть, то нет!
                    .withEndAction{ wrongView.animate().alpha(0f) }.start()
            }
        }

        keyBoard.setCLickListeners(keyBoardListener)
    }

    fun createRow(listSymbols: List<Symbol>): LinearLayout  {
        val row = LinearLayout(thisContext)
        row.orientation = LinearLayout.HORIZONTAL
        row.layoutParams = ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        for (i in listSymbols.indices) {
            if (listSymbols[i].viewType == Game.VIEW_TYPE_LETTER) {
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
                changeBackground(currentTextView, false)
                changeBackground(editLetter, true)
                currentTextView = editLetter
                game.setLetter(symbol.symbol)
            }
            editLetter.text = ""
            emptyTextViewList.add(Pair(editLetter, symbol.symbol))
        }
        codeWithTextViewList.add(Pair(codeTextView, symbol.symbol))
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

    fun clearAllCodeFromFindLetter(letter: Char) {
        val concreteEmptyCodes = codeWithTextViewList.filter { it.second == letter }
        concreteEmptyCodes.forEach {
            it.first.text = ""
            codeWithTextViewList.remove(it)
        }
    }

    fun tutorial(): MutableList<Word> {
        val list: MutableList<Word> = mutableListOf()
        list.add(Word(mutableListOf(Symbol(symbol='У', code=1, isFill=true, isShowCode=true, viewType=1), Symbol(symbol='П', code=2, isFill=true, isShowCode=false, viewType=1), Symbol(symbol='А', code=3, isFill=true, isShowCode=true, viewType=1), Symbol(symbol='Д', code=4, isFill=false, isShowCode=false, viewType=1), Symbol(symbol='И', code=5, isFill=true, isShowCode=false, viewType=1))))
        list.add(Word(mutableListOf(Symbol(symbol='С', code=6, isFill=true, isShowCode=true, viewType=1), Symbol(symbol='Е', code=7, isFill=true, isShowCode=true, viewType=1), Symbol(symbol='М', code=8, isFill=true, isShowCode=false, viewType=1), Symbol(symbol='Ь', code=9, isFill=true, isShowCode=false, viewType=1), Symbol(symbol=' ', code=-1, isFill=true, isShowCode=false, viewType=2), Symbol(symbol='Р', code=10, isFill=true, isShowCode=true, viewType=1), Symbol(symbol='А', code=3, isFill=true, isShowCode=true, viewType=1), Symbol(symbol='З', code=11, isFill=false, isShowCode=false, viewType=1))))
        list.add(Word(mutableListOf(Symbol(symbol='И', code=5, isFill=false, isShowCode=false, viewType=1), Symbol(symbol=' ', code=-1, isFill=true, isShowCode=false, viewType=2), Symbol(symbol='В', code=12, isFill=true, isShowCode=true, viewType=1), Symbol(symbol='О', code=13, isFill=true, isShowCode=true, viewType=1), Symbol(symbol='С', code=6, isFill=true, isShowCode=true, viewType=1), Symbol(symbol='Е', code=7, isFill=true, isShowCode=true, viewType=1), Symbol(symbol='М', code=8, isFill=true, isShowCode=false, viewType=1), Symbol(symbol='Ь', code=9, isFill=true, isShowCode=false, viewType=1))))
        list.add(Word(mutableListOf(Symbol(symbol='Р', code=10, isFill=true, isShowCode=true, viewType=1), Symbol(symbol='А', code=3, isFill=true, isShowCode=true, viewType=1), Symbol(symbol='З', code=11, isFill=true, isShowCode=false, viewType=1))))
        list.add(Word(mutableListOf(Symbol(symbol='П', code=2, isFill=false, isShowCode=false, viewType=1), Symbol(symbol='О', code=13, isFill=true, isShowCode=true, viewType=1), Symbol(symbol='Д', code=4, isFill=true, isShowCode=false, viewType=1), Symbol(symbol='Н', code=14, isFill=true, isShowCode=true, viewType=1), Symbol(symbol='И', code=5, isFill=true, isShowCode=false, viewType=1), Symbol(symbol='М', code=8, isFill=false, isShowCode=false, viewType=1), Symbol(symbol='И', code=5, isFill=true, isShowCode=false, viewType=1), Symbol(symbol='С', code=6, isFill=true, isShowCode=true, viewType=1), Symbol(symbol='Ь', code=9, isFill=false, isShowCode=false, viewType=1))))
        return list
    }

}