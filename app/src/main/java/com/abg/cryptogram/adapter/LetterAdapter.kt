package com.abg.cryptogram.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.abg.cryptogram.R
import com.abg.cryptogram.model.Letter
import com.abg.cryptogram.model.LetterHandler

class LetterAdapter(
    private val parentPosition: Int,
    private val sentence: MutableList<Letter>,
    private val letterHandler: LetterHandler
) : Adapter<LetterAdapter.LetterViewHolder>() {

    inner class LetterViewHolder(view: View) : ViewHolder(view) {

        val editLetter: TextView = view.findViewById(R.id.editLetter)
        private val hintNumber: TextView = view.findViewById(R.id.hintNumber)

        fun bind(letter: Letter) {

            val text: String

            if (letter.isFill) {
                editLetter.isClickable = false
                text = letter.letter.toString()
            } else {
                editLetter.setOnClickListener {
                    letterHandler.highlightText(
                        parentPosition,
                        layoutPosition,
                        this,
                        letter.letter
                    )
                }
                text = ""
            }
            editLetter.text = text
            hintNumber.text = if (!letter.hintDestroy) letter.code.toString() else ""
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LetterViewHolder =
        LetterViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_letter, parent, false)
        )

    override fun getItemCount(): Int = sentence.size

    override fun onBindViewHolder(holder: LetterViewHolder, position: Int) {
        holder.bind(sentence[position])
    }
}