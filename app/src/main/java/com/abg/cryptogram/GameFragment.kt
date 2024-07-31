package com.abg.cryptogram

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abg.cryptogram.model.Game
import com.abg.cryptogram.adapter.WordAdapter
import com.abg.cryptogram.data.SaveConfig
import com.abg.cryptogram.model.MegaParser

class GameFragment : Fragment() {

    private lateinit var navigator: Navigator
    override fun onAttach(context: Context) {
        super.onAttach(context)
        navigator = context as Navigator
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val keyBoard = KeyBoard()
        val recyclerView: RecyclerView = view.findViewById(R.id.sentence_recycler)
        val wrongView: View = view.findViewById(R.id.wrong)
        val livesView: View = view.findViewById(R.id.lives)
        val lives = Lives()
        lives.setLivesView(livesView)
        val saveConfig = SaveConfig(requireContext())
        val quoteViewModelFactory = QuoteViewModelFactory(context?.assets?.open("test.csv")!!)
        val quoteViewModel: QuoteViewModel = ViewModelProvider(this, quoteViewModelFactory)[QuoteViewModel::class.java]
        val quote = quoteViewModel.getQuote(saveConfig.getLevel())
        val game = Game {
            when(it) {
                Game.StatusGame.GAME_OVER -> {
                    navigator.startFragment(LostFragment())
                }
                Game.StatusGame.WIN -> {
                    saveConfig.saveLevel(saveConfig.getLevel()+1)
                    recyclerView.animate().alpha(0f).setDuration(200).withEndAction {
                        val fragmentWin = FragmentWin()
                        fragmentWin.arguments = Bundle().apply {
                            putString("quote", quote.quote.replace('/', ' ').lowercase())
                            putString("author", quote.author)
                        }
                        navigator.startFragment(fragmentWin)
                    }.start()
                }
            }
        }

        val list = game.sentenceMapToListWords(MegaParser.insertSlashes(quote.quote.toUpperCase()))
        Log.d("##", list.toTypedArray().contentToString())

        val wordAdapter = WordAdapter()
        wordAdapter.setSentences(list)
        wordAdapter.setOnClickItemListener {
            game.setNotSelected(list)
            game.setSelected(list, it)
            wordAdapter.setSentences(list)
        }

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = wordAdapter

        game.setAllConcreteLetterFindListener { letter, textView ->
            game.changeCodeVisibleAllConcreteLetter(list, letter)
            keyBoard.killKey(textView)
            wordAdapter.notifyDataSetChanged()
        }

        val keyBoardView: View = view.findViewById(R.id.keyboardView)
        val keyBoardListener = KeyBoardClickListener { textview, letter ->
            if (game.compareLetters(textview, letter)) {
                val currentPosition = game.getSelectLetter(list)
                game.setFillLetter(list, currentPosition)
                wordAdapter.notifyItemChanged(currentPosition.first)
                game.setNextSelect(list)
                wordAdapter.setSentences(list)
            } else {
                lives.setLives(game.minusHilth())
                wrongView.animate()
                    .setStartDelay(100)
                    .alpha(1f)
                    .setDuration(200) // То есть, то нет!
                    .withEndAction{ wrongView.animate().alpha(0f) }.start()
            }
        }

        keyBoard.inflateKeyBoard(keyBoardView)
        keyBoard.setCLickListeners(keyBoardListener)

    }
}