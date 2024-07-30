package com.abg.cryptogram.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.abg.cryptogram.R
import com.abg.cryptogram.model.LetterHandler
import com.abg.cryptogram.model.Symbol

class LetterAdapter(
    private val parentPosition: Int,
    private val sentence: MutableList<Symbol>,
    private val letterHandler: LetterHandler
) : Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_LETTER = 1
        const val VIEW_TYPE_SIGN = 2
    }

    inner class LetterViewHolder(view: View) : ViewHolder(view) {

        val editLetter: TextView = view.findViewById(R.id.editLetter)
        val linearLayoutLetter: LinearLayout = view.findViewById(R.id.letter)
        private val hintNumber: TextView = view.findViewById(R.id.hintNumber)

        fun bind(letter: Symbol) {
            val text: String
            if (letter.isFill) {
                editLetter.isClickable = false
                text = letter.symbol.toString()
            } else {
                linearLayoutLetter.setOnClickListener {
                    letterHandler.highlightText(
                        parentPosition,
                        layoutPosition,
                        this,
                        letter.symbol
                    )
                }
                text = ""
            }
            
            editLetter.text = text
            showHint(hintNumber, letter)
            updateFocusView(editLetter.context, letter.isSelected, editLetter)
        }

        private fun updateFocusView(context: Context, isSelect: Boolean, editLetter: TextView) {
            editLetter.background = if (isSelect) context.getDrawable(R.drawable.border) else null
        }

        private fun showHint(hintNumber: TextView, letter: Symbol) {
            hintNumber.text = if (!letter.hintDestroy) letter.code.toString() else ""
        }
    }

    inner class SignViewHolder(view: View) : ViewHolder(view) {

        val signText: TextView = view.findViewById(R.id.sign)

        @SuppressLint("SetTextI18n")
        fun bind(sign: Symbol) {
            signText.text = "${sign.symbol} "
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        when(viewType) {
            VIEW_TYPE_LETTER -> LetterViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_letter, parent, false))
            VIEW_TYPE_SIGN -> SignViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_sign, parent, false))
            else -> throw IllegalArgumentException("Invalid view type")
        }

    override fun getItemCount(): Int = sentence.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when(holder) {
            is LetterViewHolder -> holder.bind(sentence[position])
            is SignViewHolder -> holder.bind(sentence[position])
        }
    }

    override fun getItemViewType(position: Int): Int =
        when (sentence[position].viewType) {
            VIEW_TYPE_LETTER -> VIEW_TYPE_LETTER
            VIEW_TYPE_SIGN -> VIEW_TYPE_SIGN
            else -> -1
        }
}