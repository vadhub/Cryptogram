package com.abg.cryptogram.ui

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.abg.cryptogram.R

class HintDialogFragment : DialogFragment() {

    companion object {
        fun newInstance(title: Char): HintDialogFragment {
            val frag = HintDialogFragment()
            val args = Bundle()
            args.putChar("hint", title)
            frag.setArguments(args)
            return frag
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return inflater.inflate(R.layout.hint_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val hint = arguments?.getChar("hint")
        view.findViewById<TextView>(R.id.hint_letter).text = hint.toString()
        view.findViewById<TextView>(R.id.ok_text).setOnClickListener { dismiss() }
    }
}