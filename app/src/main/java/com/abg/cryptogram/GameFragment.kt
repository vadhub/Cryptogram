package com.abg.cryptogram

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abg.cryptogram.model.Game
import com.abg.cryptogram.adapter.WordAdapter
import com.abg.cryptogram.model.LetterHandler
import com.abg.cryptogram.model.Word

class GameFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView: RecyclerView = view.findViewById(R.id.sentence_recycler)
        val game = Game {
            when(it) {
                Game.StatusGame.GAME_OVER -> { Log.d("info", "lol")}
                Game.StatusGame.WIN -> { Log.d("info", "ok")}
            }
        }
        val sentence = "УПАДИ/СЕМЬ  РАЗ/И  ВОСЕМЬ/РАЗ/ПОДНИМИСЬ"
        val list = game.sentenceMapToListWords(sentence)
        Log.d("##", list.toTypedArray().contentToString())
        val letterHandler = LetterHandler { letter ->
            game.setLetter(letter)
        }

        val wordAdapter = WordAdapter(letterHandler)
        wordAdapter.setSentences(list)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = wordAdapter

        game.setAllConcreteLetterFindListener {
            game.changeHintAllConcreteLetter(list, it)
            wordAdapter.notifyDataSetChanged()
        }

        var oldPosition = game.getNextNotFillLetter(list)
        val keyBoardView: View = view.findViewById(R.id.keyboardView)
        val keyBoard = KeyBoardClickListener {
            if (game.compareLetters(it)) {
                val currentPosition = game.getSelectLetter(list)
                Log.d("##", list.toTypedArray().contentToString())
                Log.d("@gg", currentPosition.toString())
                val oldLetter = list[currentPosition.first].letters[currentPosition.second]
                val letter = oldLetter.copy(isFill = true, isSelected = false)
                list[currentPosition.first].letters[currentPosition.second] = letter
                wordAdapter.notifyItemChanged(currentPosition.first)
                game.setNextSelect(list)
                wordAdapter.setSentences(list)
            }
            letterHandler.setToTextView(it)
        }


        val a: TextView = keyBoardView.findViewById(R.id.letterA)
        val b: TextView = keyBoardView.findViewById(R.id.letterBlyat)
        val v: TextView = keyBoardView.findViewById(R.id.letterV)
        val g: TextView = keyBoardView.findViewById(R.id.letterG)
        val d: TextView = keyBoardView.findViewById(R.id.letterD)
        val e: TextView = keyBoardView.findViewById(R.id.letterE)
        val gz: TextView = keyBoardView.findViewById(R.id.letterGZ)
        val z: TextView = keyBoardView.findViewById(R.id.letterZ)
        val i: TextView = keyBoardView.findViewById(R.id.letterI)
        val iS: TextView = keyBoardView.findViewById(R.id.letterI_short)
        val k: TextView = keyBoardView.findViewById(R.id.letterK)
        val l: TextView = keyBoardView.findViewById(R.id.letterL)
        val m: TextView = keyBoardView.findViewById(R.id.letterM)
        val n: TextView = keyBoardView.findViewById(R.id.letterN)
        val o: TextView = keyBoardView.findViewById(R.id.letterO)
        val p: TextView = keyBoardView.findViewById(R.id.letterP)
        val r: TextView = keyBoardView.findViewById(R.id.letterR)
        val s: TextView = keyBoardView.findViewById(R.id.letterS)
        val t: TextView = keyBoardView.findViewById(R.id.letterT)
        val u: TextView = keyBoardView.findViewById(R.id.letterU)
        val ph: TextView = keyBoardView.findViewById(R.id.letterPH)
        val h: TextView = keyBoardView.findViewById(R.id.letterH)
        val c: TextView = keyBoardView.findViewById(R.id.letterC)
        val ch: TextView = keyBoardView.findViewById(R.id.letterCH)
        val sh: TextView = keyBoardView.findViewById(R.id.letterSH)
        val sch: TextView = keyBoardView.findViewById(R.id.letterSCH)
        val gentle: TextView = keyBoardView.findViewById(R.id.letter_gentle_sign)
        val strong: TextView = keyBoardView.findViewById(R.id.letter_strong)
        val not: TextView = keyBoardView.findViewById(R.id.letter_not)
        val ae: TextView = keyBoardView.findViewById(R.id.letterAE)
        val yu: TextView = keyBoardView.findViewById(R.id.letterYU)
        val ya: TextView = keyBoardView.findViewById(R.id.letterYA)

        a.setOnClickListener(keyBoard)
        b.setOnClickListener(keyBoard)
        v.setOnClickListener(keyBoard)
        g.setOnClickListener(keyBoard)
        d.setOnClickListener(keyBoard)
        e.setOnClickListener(keyBoard)
        gz.setOnClickListener(keyBoard)
        z.setOnClickListener(keyBoard)
        i.setOnClickListener(keyBoard)
        iS.setOnClickListener(keyBoard)
        k.setOnClickListener(keyBoard)
        l.setOnClickListener(keyBoard)
        m.setOnClickListener(keyBoard)
        n.setOnClickListener(keyBoard)
        o.setOnClickListener(keyBoard)
        p.setOnClickListener(keyBoard)
        r.setOnClickListener(keyBoard)
        s.setOnClickListener(keyBoard)
        t.setOnClickListener(keyBoard)
        u.setOnClickListener(keyBoard)
        ph.setOnClickListener(keyBoard)
        h.setOnClickListener(keyBoard)
        c.setOnClickListener(keyBoard)
        ch.setOnClickListener(keyBoard)
        sh.setOnClickListener(keyBoard)
        sch.setOnClickListener(keyBoard)
        gentle.setOnClickListener(keyBoard)
        strong.setOnClickListener(keyBoard)
        not.setOnClickListener(keyBoard)
        ae.setOnClickListener(keyBoard)
        yu.setOnClickListener(keyBoard)
        ya.setOnClickListener(keyBoard)
    }
}