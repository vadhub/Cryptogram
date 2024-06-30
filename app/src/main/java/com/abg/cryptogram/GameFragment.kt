package com.abg.cryptogram

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abg.cryptogram.adapter.WordAdapter
import com.abg.cryptogram.model.Letter
import com.abg.cryptogram.model.Word

class GameFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView: RecyclerView = view.findViewById(R.id.sentence_recycler)
        val wordAdapter = WordAdapter()
        val sentence = "Упади семь раз и восемь раз поднимись"
        wordAdapter.setSentences(sentenceMapToListWords(sentence))
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = wordAdapter
    }

    private fun sentenceMapToListWords(sentence: String): MutableList<Word> =
        sentence.split(" ")
            .map { Word(it.map { word -> Letter(word, Math.random().toInt(), true) }.toMutableList()) }
            .toMutableList()
}