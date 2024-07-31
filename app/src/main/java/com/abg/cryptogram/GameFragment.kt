package com.abg.cryptogram

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.core.view.ancestors
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abg.cryptogram.model.Game
import com.abg.cryptogram.adapter.WordAdapter

class GameFragment : Fragment() {

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
        val animationFadeOut = AnimationUtils.loadAnimation(context, R.anim.fade_out)
        val game = Game {
            when(it) {
                Game.StatusGame.GAME_OVER -> { Log.d("info", "lol")}
                Game.StatusGame.WIN -> {
                    recyclerView.startAnimation(animationFadeOut)
                    Log.d("info", "ok")
                }
            }
        }

        val sentence = "УПАДИ/СЕМЬ  РАЗ/И  ВОСЕМЬ/РАЗ/ПОДНИМИСЬ"
        val list = game.sentenceMapToListWords(sentence)
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