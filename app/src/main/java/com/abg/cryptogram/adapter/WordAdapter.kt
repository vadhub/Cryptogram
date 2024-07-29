package com.abg.cryptogram.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.abg.cryptogram.R
import com.abg.cryptogram.model.LetterHandler
import com.abg.cryptogram.model.Symbol
import com.abg.cryptogram.model.Word

class WordAdapter(private val sentenceHandler: LetterHandler) :
    Adapter<WordAdapter.SentenceViewHolder>() {

    private var sentences: MutableList<Word> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setSentences(sentences: MutableList<Word>) {
        this.sentences = sentences
        notifyDataSetChanged()
    }

    inner class SentenceViewHolder(private val view: View) : ViewHolder(view) {

        val recyclerLetter: RecyclerView = view.findViewById(R.id.recycler_letter)

        fun bind(sentence: Word) {
            val adapter = LetterAdapter(layoutPosition, sentence.letters, sentenceHandler)
            recyclerLetter.setHasFixedSize(true)
            recyclerLetter.layoutManager = LinearLayoutManager(view.context, HORIZONTAL, false)
            recyclerLetter.adapter = adapter
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SentenceViewHolder = SentenceViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_sentence, parent, false)
    )

    override fun onBindViewHolder(holder: SentenceViewHolder, position: Int) {
        holder.bind(sentences[position])
    }

    override fun getItemCount(): Int = sentences.size
}