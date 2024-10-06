package com.abg.cryptogram.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBar.LayoutParams
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.abg.cryptogram.Navigator
import com.abg.cryptogram.QuoteViewModel
import com.abg.cryptogram.R
import com.abg.cryptogram.data.SaveConfig
import com.abg.cryptogram.model.Game
import com.abg.cryptogram.model.LocaleChange
import com.abg.cryptogram.model.MegaParser
import com.abg.cryptogram.model.Symbol
import com.abg.cryptogram.ui.dialog.HintDialogFragment
import com.abg.cryptogram.ui.dialog.RepeatGameDialogFragment
import com.abg.cryptogram.ui.keyboard.KeyBoard
import com.abg.cryptogram.ui.keyboard.KeyBoardRU
import com.abg.cryptogram.ui.keyboard.KeyBoardClickListener
import com.abg.cryptogram.ui.keyboard.KeyBoardEN
import java.util.LinkedList

class GameFragment : Fragment() {

    private lateinit var thisContext: Context
    private lateinit var currentTextView: TextView
    private val emptyTextViewList: LinkedList<Pair<TextView, Char>> = LinkedList()
    private val codeWithTextViewList: LinkedList<Pair<TextView /* textview code */, Char /* letter */>> = LinkedList()
    private lateinit var navigator: Navigator
    private lateinit var game: Game
    private var isHintEvent = false
    private val quoteViewModel: QuoteViewModel by activityViewModels()
    private lateinit var saveConfig: SaveConfig
    private lateinit var hintCountText: TextView
    private lateinit var keyBoardView: View
    private lateinit var hintTextView: TextView

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

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        navigator.destroyInterstitialAd()
        navigator.loadInterstitialAd()
        saveConfig = SaveConfig(requireContext())
        val level = saveConfig.getLevel()
        val quote = quoteViewModel.getQuote(level)
        val levelTextView: TextView = view.findViewById(R.id.level)
        val keyBoardWrap: FrameLayout = view.findViewById(R.id.keyboardWrap)
        val layoutKeyBoard: Int
        val keyboard: KeyBoard
        if (saveConfig.getLanguage() == "ru") {
            LocaleChange.setLocale(view.context, "ru")
            layoutKeyBoard = R.layout.keyboard_ru
            keyboard = KeyBoardRU()
        } else {
            LocaleChange.setLocale(view.context, "en")
            layoutKeyBoard = R.layout.keyboard_en
            keyboard = KeyBoardEN()
        }
        val settings: ImageButton = view.findViewById(R.id.settings)
        settings.setOnClickListener { showSettingsDialog() }
        keyBoardView = layoutInflater.inflate(layoutKeyBoard, null)
        keyBoardWrap.addView(keyBoardView)
        levelTextView.text = resources.getString(R.string.level) + " ${level + 1}"
        val sentenceView = view.findViewById<LinearLayout>(R.id.sentence)
        val livesView: View = view.findViewById(R.id.lives)
        val lives = Lives()
        lives.setLivesView(livesView)
        game = Game {
            when(it) {
                Game.StatusGame.GAME_OVER -> {
                    showGameRepeatDialog (
                        continueGame = {
                            navigator.showAd()
                            lives.setLives(game.increaseHilth())
                        },
                        repeatGame = {
                            navigator.startFragment(LostFragment())
                        })
                }
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
        game.setHints(saveConfig.getHintsWithPurchase())
        val hint: ImageButton = view.findViewById(R.id.hint)
        hintTextView = view.findViewById(R.id.chooseText)
        hintCountText = view.findViewById(R.id.hintCount)
        val hintCount = game.getHint()
        hintCountText.text = hintCount.toString() +"x"

        hint.setOnClickListener {
            if (hintCount > 0) {
                isHintEvent = true
                keyBoardView.visibility = View.GONE
                hintTextView.visibility = View.VISIBLE
            } else {
                // dialog with suggest to see adv video
            }
        }

        val list = game.sentenceMapToListWords(MegaParser.insertSlashes(quote.quote.uppercase()), level)
        val wrongView: View = view.findViewById(R.id.wrong)

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
                lives.setLives(game.minusHilth())
                wrongView.animate()
                    .alpha(1f)
                    .setDuration(200) // То есть, то нет!
                    .withEndAction{ wrongView.animate().alpha(0f) }.start()
            }
        }

        keyboard.inflateKeyBoard(keyBoardView)
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
                clickOnEmptyField(editLetter, symbol)
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

    fun clickOnEmptyField(editLetter: TextView, symbol: Symbol) {
        changeBackground(currentTextView, false)
        changeBackground(editLetter, true)
        currentTextView = editLetter
        game.setLetter(symbol.symbol)
        if (isHintEvent) {
            hintEvent(symbol.symbol)
        }
    }

    fun hintEvent(symbol: Char) {
        game.minusHint()
        hintCountText.text = game.getHint().toString() + "x"
        showHintDialog(symbol)
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

    fun showHintDialog(symbol: Char) {
        val hintDialogFragment = HintDialogFragment.newInstance(symbol)
        hintDialogFragment.show(childFragmentManager, "Hint dialog")
        keyBoardView.visibility = View.VISIBLE
        hintTextView.visibility = View.GONE
        isHintEvent = false
    }

    fun showSettingsDialog() {
        val settingsDialogFragment = SettingsFragment()
        settingsDialogFragment.setSaveConfig(saveConfig)
        navigator.startFragment(settingsDialogFragment)
    }

    fun showGameRepeatDialog(continueGame: () -> Unit, repeatGame:() -> Unit) {
        val gameRepeatDialog = RepeatGameDialogFragment(continueGame, repeatGame)
        gameRepeatDialog.show(childFragmentManager, "RepeatDialog")
    }
}