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
import androidx.lifecycle.ViewModelProvider
import com.abg.cryptogram.data.SaveConfig
import com.abg.cryptogram.model.Game
import com.abg.cryptogram.model.MegaParser
import com.abg.cryptogram.model.Symbol
import java.util.LinkedList

class GameFragment : Fragment() {

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
        return inflater.inflate(R.layout.game_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val quoteViewModelFactory = QuoteViewModelFactory(context?.assets?.open("test.csv")!!)
        val quoteViewModel: QuoteViewModel = ViewModelProvider(this, quoteViewModelFactory)[QuoteViewModel::class.java]
        val saveConfig = SaveConfig(requireContext())
        val quote = quoteViewModel.getQuote(29)
        val sentenceView = view.findViewById<LinearLayout>(R.id.sentence)
        game = Game {
            when(it) {
                Game.StatusGame.GAME_OVER -> {navigator.startFragment(LostFragment())}
                Game.StatusGame.WIN -> {
                    saveConfig.saveLevel(saveConfig.getLevel()+1)
                    val fragmentWin = FragmentWin()
                    fragmentWin.arguments = Bundle().apply {
                        putString("quote", quote.quote)
                        putString("author", quote.author)
                    }
                    sentenceView.animate().alpha(0f).setDuration(200).withEndAction {
                        navigator.startFragment(fragmentWin)
                    }.start()
                }
            }
        }
        val list = game.sentenceMapToListWords(MegaParser.insertSlashes(quote.quote.uppercase()))
        val keyBoard = KeyBoard()
        val wrongView: View = view.findViewById(R.id.wrong)
        val livesView: View = view.findViewById(R.id.lives)
        val lives = Lives()
        lives.setLivesView(livesView)

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

        val keyBoardView: View = view.findViewById(R.id.keyboardView)
        val keyBoardListener = KeyBoardClickListener {textview, letter ->
            if (game.compareLetters(textview, letter)) {
                currentTextView.text = letter.toString()
                changeBackground(currentTextView, false)
                emptyTextViewList.remove(Pair(currentTextView, letter))
                val letterPair = emptyTextViewList.peek()
                if (letterPair != null) {
                    currentTextView = letterPair.first
                    changeBackground(currentTextView, true)
                    game.setLetter(emptyTextViewList.element().second)
                }
            } else {
                lives.setLives(game.minusHilth())
                wrongView.animate()
                    .alpha(1f)
                    .setDuration(200) // То есть, то нет!
                    .withEndAction{ wrongView.animate().alpha(0f) }.start()
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
}