package com.abg.cryptogram

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.abg.cryptogram.model.MegaParser

class MainActivity : AppCompatActivity(), Navigator {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, GameFragment()).commit()

        val sentence = "УПАДИ СЕМЬ РАЗ И ВОСЕМЬ РАЗ ПОДНИМИСЬ"
        val sentence1 = "Своим успехом я обязана тому, что никогда не оправдывалась и не принимала оправданий от других"
        val sentence2 = "За свою карьеру я пропустил более 9000 бросков, проиграл почти 300 игр. 26 раз мне доверяли сделать финальный победный бросок, и я промахивался. Я терпел поражения снова, и снова, и снова. И именно поэтому я добился успеха"
        Log.d("##", MegaParser.insertSlashes(sentence2).uppercase())
    }

    override fun startFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.fade_in_fragment, R.anim.fade_out_fragment)
            .replace(R.id.fragmentContainer, fragment).commit()

    }


}