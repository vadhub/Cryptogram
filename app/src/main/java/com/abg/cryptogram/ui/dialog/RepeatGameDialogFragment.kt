package com.abg.cryptogram.ui.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.abg.cryptogram.R

class RepeatGameDialogFragment(private val continueGame: () -> Unit, private val repeatAgain: () -> Unit) : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return inflater.inflate(R.layout.repeat_game_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.findViewById<Button>(R.id.continue_defeat).setOnClickListener {
            continueGame.invoke()
            dismiss()
        }

        view.findViewById<Button>(R.id.repeat).setOnClickListener {
            repeatAgain.invoke()
            dismiss()
        }
    }
}