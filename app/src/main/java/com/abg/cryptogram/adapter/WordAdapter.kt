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
    fun setSentences(newWords: MutableList<Word>) {
        val diffResult = DiffUtil.calculateDiff(WordDiffCallback(this.sentences, newWords))
        this.sentences.clear()
        this.sentences.addAll(newWords)
        diffResult.dispatchUpdatesTo(this)
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

    class WordDiffCallback(private val oldList: List<Word>, private val newList: List<Word>) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
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