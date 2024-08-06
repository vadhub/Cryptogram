package com.abg.cryptogram.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.abg.cryptogram.Navigator
import com.abg.cryptogram.R
import kotlin.random.Random

class LostFragment : Fragment() {

    private lateinit var navigator: Navigator

    override fun onAttach(context: Context) {
        super.onAttach(context)
        navigator = context as Navigator
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.lost_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val textLost: TextView = view.findViewById(R.id.lots_text)
        val lost = resources.getStringArray(R.array.lost)[Random.nextInt(2)]
        textLost.text = lost
        view.findViewById<Button>(R.id.restart).setOnClickListener {
            navigator.showAd()
            navigator.startFragment(GameFragment())
        }
    }
}