package com.abg.cryptogram.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.abg.cryptogram.R
import com.abg.cryptogram.model.Letter

class LetterAdapter(private val sentence: MutableList<Letter>) : Adapter<LetterAdapter.LetterViewHolder>() {

    class LetterViewHolder(view: View): ViewHolder(view)  {

        private val editLetter: TextView = view.findViewById(R.id.editLetter)
        private val hintNumber: TextView = view.findViewById(R.id.hintNumber)

        fun bind(letter: Letter) {
            editLetter.isClickable = !letter.isFill
            editLetter.text = letter.letter.toString()
            hintNumber.text = letter.code.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LetterViewHolder =
        LetterViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_letter, parent,false))

    override fun getItemCount(): Int = sentence.size

    override fun onBindViewHolder(holder: LetterViewHolder, position: Int) {
        holder.bind(sentence[position])
    }
}