package com.abg.cryptogram

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.abg.cryptogram.data.ParseCSV
import com.abg.cryptogram.model.MegaParser
import com.abg.cryptogram.model.Quote

class MainActivity : AppCompatActivity(), Navigator {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, TestFragment()).commit()
    }

    override fun startFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.fade_in_fragment, R.anim.fade_out_fragment)
            .replace(R.id.fragmentContainer, fragment).commit()

    }


}