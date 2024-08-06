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


class FragmentWin : Fragment() {

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
        return inflater.inflate(R.layout.fragment_win, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val buttonNext: Button = view.findViewById(R.id.next)
        val quote: TextView = view.findViewById(R.id.quot)
        val author: TextView = view.findViewById(R.id.author)

        quote.text = arguments?.getString("quote")
        author.text = arguments?.getString("author")

        buttonNext.setOnClickListener {
            navigator.showAd()
            navigator.startFragment(GameFragment())
        }
    }

}