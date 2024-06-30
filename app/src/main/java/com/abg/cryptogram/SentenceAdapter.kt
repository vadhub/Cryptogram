package com.abg.cryptogram

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class SentenceAdapter : Adapter<SentenceAdapter.SentenceViewHolder>() {

    private var sentences: MutableList<Sentence> = mutableListOf()

    class SentenceViewHolder(private val view: View): ViewHolder(view) {

        private val recyclerLetter: RecyclerView = view.findViewById(R.id.recycler_letter)

        fun bind(sentence: Sentence) {
            recyclerLetter.layoutManager = LinearLayoutManager(view.context, HORIZONTAL, false)

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SentenceViewHolder = SentenceViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_sentence, parent,false))

    override fun onBindViewHolder(holder: SentenceViewHolder, position: Int) {

    }

    override fun getItemCount(): Int = sentences.size
}