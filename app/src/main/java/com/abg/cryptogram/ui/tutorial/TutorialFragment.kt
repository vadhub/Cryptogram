package com.abg.cryptogram.ui.tutorial

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBar.LayoutParams
import androidx.fragment.app.Fragment
import com.abg.cryptogram.Navigator
import com.abg.cryptogram.R
import com.abg.cryptogram.data.SaveConfig
import com.abg.cryptogram.model.Game
import com.abg.cryptogram.model.LocaleChange
import com.abg.cryptogram.model.MegaParser
import com.abg.cryptogram.model.Symbol
import com.abg.cryptogram.model.Word
import com.abg.cryptogram.ui.FragmentWin
import com.abg.cryptogram.ui.GameFragment
import com.abg.cryptogram.ui.LostFragment
import com.abg.cryptogram.ui.keyboard.KeyBoard
import com.abg.cryptogram.ui.keyboard.KeyBoardRU
import com.abg.cryptogram.ui.keyboard.KeyBoardClickListener
import com.abg.cryptogram.ui.keyboard.KeyBoardEN
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
        val sentenceView = view.findViewById<LinearLayout>(R.id.sentence_tutor)
        val keyBoardWrap: FrameLayout = view.findViewById(R.id.keyboardWrap_tutor)
        val layoutKeyBoard: Int
        val keyboard: KeyBoard
        val list: MutableList<Word>

        val wrongView: View = view.findViewById(R.id.wrong_tutor)
        createTutorDialog()
        game = Game {
            when(it) {
                Game.StatusGame.GAME_OVER -> {navigator.startFragment(LostFragment())}
                Game.StatusGame.WIN -> {
                    saveConfig.saveIsTutorComplete(true)
                    val fragmentWin = FragmentWin()
                    fragmentWin.arguments = Bundle().apply {
                        putString("quote", resources.getString(R.string.quote_tutor))
                        putString("author", resources.getString(R.string.author_tutor))
                    }
                    sentenceView.animate().alpha(0f).setDuration(200).withEndAction {
                        navigator.startFragment(fragmentWin)
                    }.start()
                }
            }
        }
        if (LocaleChange.getLocale(view.context) == "ru") {
            layoutKeyBoard = R.layout.keyboard_ru
            keyboard = KeyBoardRU()
            list = tutorialRU()
            game.setFrequency(mutableMapOf(Pair('П', 1), Pair('М',1), Pair('З',1), Pair('И',1), Pair('Д',1), Pair('Ь',1)))
        } else {
            layoutKeyBoard = R.layout.keyboard_en
            keyboard = KeyBoardEN()
            list = tutorialEN()
            game.setFrequency(mutableMapOf(Pair('L', 1), Pair('I',2), Pair('E',2), Pair('H',1)))
        }
        val keyBoardView: View = layoutInflater.inflate(layoutKeyBoard, null)
        keyBoardWrap.addView(keyBoardView)
        game.setNotGuessed(6)
        keyboard.inflateKeyBoard(keyBoardView)
        val listKeyTutorial = keyboard.tutorialKey()
        var currentKey = listKeyTutorial.peek()
        var objectAnimator = keyboard.pulseAnimation(currentKey)

        game.setAllConcreteLetterFindListener { letter, textView ->
            keyboard.killKey(textView)
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
                currentKey?.let { objectAnimator = keyboard.pulseAnimation(it) }

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

        keyboard.setCLickListeners(keyBoardListener)
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

    fun tutorialRU(): MutableList<Word> {
        val list: MutableList<Word> = mutableListOf()
        list.add(Word(mutableListOf(Symbol(symbol='У', code=1, isFill=true, isShowCode=true, viewType=1), Symbol(symbol='П', code=2, isFill=true, isShowCode=false, viewType=1), Symbol(symbol='А', code=3, isFill=true, isShowCode=true, viewType=1), Symbol(symbol='Д', code=4, isFill=false, isShowCode=false, viewType=1), Symbol(symbol='И', code=5, isFill=true, isShowCode=false, viewType=1))))
        list.add(Word(mutableListOf(Symbol(symbol='С', code=6, isFill=true, isShowCode=true, viewType=1), Symbol(symbol='Е', code=7, isFill=true, isShowCode=true, viewType=1), Symbol(symbol='М', code=8, isFill=true, isShowCode=false, viewType=1), Symbol(symbol='Ь', code=9, isFill=true, isShowCode=false, viewType=1), Symbol(symbol=' ', code=-1, isFill=true, isShowCode=false, viewType=2), Symbol(symbol='Р', code=10, isFill=true, isShowCode=true, viewType=1), Symbol(symbol='А', code=3, isFill=true, isShowCode=true, viewType=1), Symbol(symbol='З', code=11, isFill=false, isShowCode=false, viewType=1))))
        list.add(Word(mutableListOf(Symbol(symbol='И', code=5, isFill=false, isShowCode=false, viewType=1), Symbol(symbol=' ', code=-1, isFill=true, isShowCode=false, viewType=2), Symbol(symbol='В', code=12, isFill=true, isShowCode=true, viewType=1), Symbol(symbol='О', code=13, isFill=true, isShowCode=true, viewType=1), Symbol(symbol='С', code=6, isFill=true, isShowCode=true, viewType=1), Symbol(symbol='Е', code=7, isFill=true, isShowCode=true, viewType=1), Symbol(symbol='М', code=8, isFill=true, isShowCode=false, viewType=1), Symbol(symbol='Ь', code=9, isFill=true, isShowCode=false, viewType=1))))
        list.add(Word(mutableListOf(Symbol(symbol='Р', code=10, isFill=true, isShowCode=true, viewType=1), Symbol(symbol='А', code=3, isFill=true, isShowCode=true, viewType=1), Symbol(symbol='З', code=11, isFill=true, isShowCode=false, viewType=1))))
        list.add(Word(mutableListOf(Symbol(symbol='П', code=2, isFill=false, isShowCode=false, viewType=1), Symbol(symbol='О', code=13, isFill=true, isShowCode=true, viewType=1), Symbol(symbol='Д', code=4, isFill=true, isShowCode=false, viewType=1), Symbol(symbol='Н', code=14, isFill=true, isShowCode=true, viewType=1), Symbol(symbol='И', code=5, isFill=true, isShowCode=false, viewType=1), Symbol(symbol='М', code=8, isFill=false, isShowCode=false, viewType=1), Symbol(symbol='И', code=5, isFill=true, isShowCode=false, viewType=1), Symbol(symbol='С', code=6, isFill=true, isShowCode=true, viewType=1), Symbol(symbol='Ь', code=9, isFill=false, isShowCode=false, viewType=1))))
        return list
    }

    fun tutorialEN(): MutableList<Word> {
        val list: MutableList<Word> = mutableListOf()
        list.add(Word(mutableListOf(Symbol(symbol='F', code=1, isFill=true, isShowCode=true, viewType=1), Symbol(symbol='A', code=2, isFill=true, isShowCode=true, viewType=1), Symbol(symbol='L', code=3, isFill=true, isShowCode=true, viewType=1), Symbol(symbol='L', code=3, isFill=false, isShowCode=true, viewType=1))))
        list.add(Word(mutableListOf(Symbol(symbol='S', code=4, isFill=true, isShowCode=true, viewType=1), Symbol(symbol='E', code=5, isFill=true, isShowCode=false, viewType=1), Symbol(symbol='V', code=6, isFill=true, isShowCode=true, viewType=1), Symbol(symbol='E', code=5, isFill=true, isShowCode=false, viewType=1), Symbol(symbol='N', code=7, isFill=true, isShowCode=true, viewType=1))))
        list.add(Word(mutableListOf(Symbol(symbol='T', code=8, isFill=true, isShowCode=false, viewType=1), Symbol(symbol='I', code=9, isFill=false, isShowCode=false, viewType=1), Symbol(symbol='M', code=10, isFill=true, isShowCode=true, viewType=1), Symbol(symbol='E', code=5, isFill=false, isShowCode=false, viewType=1), Symbol(symbol='S', code=4, isFill=true, isShowCode=true, viewType=1), Symbol(symbol=' ', code=-1, isFill=true, isShowCode=false, viewType=2), Symbol(symbol='A', code=2, isFill=true, isShowCode=true, viewType=1), Symbol(symbol='N', code=7, isFill=true, isShowCode=true, viewType=1), Symbol(symbol='D', code=11, isFill=true, isShowCode=true, viewType=1))))
        list.add(Word(mutableListOf(Symbol(symbol='G', code=12, isFill=true, isShowCode=true, viewType=1), Symbol(symbol='E', code=5, isFill=true, isShowCode=false, viewType=1), Symbol(symbol='T', code=8, isFill=true, isShowCode=false, viewType=1), Symbol(symbol=' ', code=-1, isFill=true, isShowCode=false, viewType=2), Symbol(symbol='U', code=13, isFill=true, isShowCode=true, viewType=1), Symbol(symbol='P', code=14, isFill=true, isShowCode=true, viewType=1))))
        list.add(Word(mutableListOf(Symbol(symbol='E', code=5, isFill=true, isShowCode=false, viewType=1), Symbol(symbol='I', code=9, isFill=true, isShowCode=false, viewType=1), Symbol(symbol='G', code=12, isFill=true, isShowCode=true, viewType=1), Symbol(symbol='H', code=15, isFill=false, isShowCode=false, viewType=1), Symbol(symbol='T', code=8, isFill=true, isShowCode=false, viewType=1))))
        list.add(Word(mutableListOf(Symbol(symbol='T', code=8, isFill=true, isShowCode=false, viewType=1), Symbol(symbol='I', code=9, isFill=false, isShowCode=false, viewType=1), Symbol(symbol='M', code=10, isFill=true, isShowCode=true, viewType=1), Symbol(symbol='E', code=5, isFill=false, isShowCode=false, viewType=1), Symbol(symbol='S', code=4, isFill=true, isShowCode=true, viewType=1), Symbol(symbol=' ', code=-1, isFill=true, isShowCode=false, viewType=2), Symbol(symbol='A', code=2, isFill=true, isShowCode=true, viewType=1), Symbol(symbol='N', code=7, isFill=true, isShowCode=true, viewType=1), Symbol(symbol='D', code=11, isFill=true, isShowCode=true, viewType=1))))
        return list
    }

    fun createTutorDialog() {
        val tutorialIntro = TutorialIntroFragment()
        tutorialIntro.show(childFragmentManager, "IntroFragment")
    }

}