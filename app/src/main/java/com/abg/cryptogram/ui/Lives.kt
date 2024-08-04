package com.abg.cryptogram.ui

import android.view.View
import android.widget.ImageView
import com.abg.cryptogram.R

class Lives {

    private lateinit var live1: ImageView
    private lateinit var live2: ImageView
    private lateinit var live3: ImageView

    fun setLivesView(lives: View) {
        live1 = lives.findViewById(R.id.lives_1)
        live2 = lives.findViewById(R.id.lives_2)
        live3 = lives.findViewById(R.id.lives_3)
    }

    fun setLives(countLive: Int) {
        when (countLive) {
            3 -> {
                live1.setImageDrawable(live1.context.getDrawable(R.drawable.favorite_24dp_cccccc_fill1_wght200_grad0_opsz24))
                live2.setImageDrawable(live2.context.getDrawable(R.drawable.favorite_24dp_cccccc_fill1_wght200_grad0_opsz24))
                live3.setImageDrawable(live3.context.getDrawable(R.drawable.favorite_24dp_cccccc_fill1_wght200_grad0_opsz24))
            }
            2 -> {
                live1.setImageDrawable(live1.context.getDrawable(R.drawable.favorite_24dp_cccccc_fill1_wght200_grad0_opsz24))
                live2.setImageDrawable(live2.context.getDrawable(R.drawable.favorite_24dp_cccccc_fill1_wght200_grad0_opsz24))
                live3.setImageDrawable(live3.context.getDrawable(R.drawable.favorite_24dp_cccccc_fill0_wght200_grad0_opsz24))
            }
            1 -> {
                live1.setImageDrawable(live1.context.getDrawable(R.drawable.favorite_24dp_cccccc_fill1_wght200_grad0_opsz24))
                live2.setImageDrawable(live2.context.getDrawable(R.drawable.favorite_24dp_cccccc_fill0_wght200_grad0_opsz24))
                live3.setImageDrawable(live3.context.getDrawable(R.drawable.favorite_24dp_cccccc_fill0_wght200_grad0_opsz24))
            }
            0 -> {
                live1.setImageDrawable(live1.context.getDrawable(R.drawable.favorite_24dp_cccccc_fill0_wght200_grad0_opsz24))
                live2.setImageDrawable(live2.context.getDrawable(R.drawable.favorite_24dp_cccccc_fill0_wght200_grad0_opsz24))
                live3.setImageDrawable(live3.context.getDrawable(R.drawable.favorite_24dp_cccccc_fill0_wght200_grad0_opsz24))
            }
        }
    }
}