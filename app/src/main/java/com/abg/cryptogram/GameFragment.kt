package com.abg.cryptogram

import android.os.Bundle
import android.util.Log
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

    private fun sentenceMapToListWords(sentence: String): MutableList<Word> {
        val alphabet: MutableMap<Char, Int> = mutableMapOf()
        val words: MutableList<Word> = mutableListOf()
        var letters: MutableList<Letter> = mutableListOf()
        var counter = 1
        sentence.split(" ").forEach { it ->
            it.filter { it.isLetter() }.forEach { char ->
                var c = char
                val number: Int
                if (char !in alphabet) {
                    c = char
                    number = counter++
                    alphabet[c] = number
                } else {
                    number = alphabet[char]!!
                }
                letters.add(Letter(c, number, true))
            }
            words.add(Word(letters))
            letters = mutableListOf()
        }

        words.forEach {
            Log.d("info", it.letters.toTypedArray().contentToString())
        }

        return words
    }
}