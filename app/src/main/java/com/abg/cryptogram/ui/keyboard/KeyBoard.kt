package com.abg.cryptogram.ui.keyboard

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.annotation.SuppressLint
import android.graphics.drawable.TransitionDrawable
import android.view.View
import android.widget.TextView
import com.abg.cryptogram.R
import java.util.LinkedList

class KeyBoard {

    private lateinit var a: TextView
    private lateinit var b: TextView
    private lateinit var v: TextView
    private lateinit var g: TextView
    private lateinit var d: TextView
    private lateinit var e: TextView
    private lateinit var gz: TextView
    private lateinit var z: TextView
    private lateinit var i: TextView
    private lateinit var iS: TextView
    private lateinit var k: TextView
    private lateinit var l: TextView
    private lateinit var m: TextView
    private lateinit var n: TextView
    private lateinit var o: TextView
    private lateinit var p: TextView
    private lateinit var r: TextView
    private lateinit var s: TextView
    private lateinit var t: TextView
    private lateinit var u: TextView
    private lateinit var ph: TextView
    private lateinit var h: TextView
    private lateinit var c: TextView
    private lateinit var ch: TextView
    private lateinit var sh: TextView
    private lateinit var sch: TextView
    private lateinit var gentle: TextView
    private lateinit var strong: TextView
    private lateinit var not: TextView
    private lateinit var ae: TextView
    private lateinit var yu: TextView
    private lateinit var ya: TextView

    fun inflateKeyBoard(keyBoardView: View) {
        a = keyBoardView.findViewById(R.id.letterA)
        b = keyBoardView.findViewById(R.id.letterBlyat)
        v = keyBoardView.findViewById(R.id.letterV)
        g = keyBoardView.findViewById(R.id.letterG)
        d = keyBoardView.findViewById(R.id.letterD)
        e = keyBoardView.findViewById(R.id.letterE)
        gz = keyBoardView.findViewById(R.id.letterGZ)
        z = keyBoardView.findViewById(R.id.letterZ)
        i = keyBoardView.findViewById(R.id.letterI)
        iS = keyBoardView.findViewById(R.id.letterI_short)
        k = keyBoardView.findViewById(R.id.letterK)
        l = keyBoardView.findViewById(R.id.letterL)
        m = keyBoardView.findViewById(R.id.letterM)
        n = keyBoardView.findViewById(R.id.letterN)
        o = keyBoardView.findViewById(R.id.letterO)
        p = keyBoardView.findViewById(R.id.letterP)
        r = keyBoardView.findViewById(R.id.letterR)
        s = keyBoardView.findViewById(R.id.letterS)
        t = keyBoardView.findViewById(R.id.letterT)
        u = keyBoardView.findViewById(R.id.letterU)
        ph = keyBoardView.findViewById(R.id.letterPH)
        h = keyBoardView.findViewById(R.id.letterH)
        c = keyBoardView.findViewById(R.id.letterC)
        ch = keyBoardView.findViewById(R.id.letterCH)
        sh = keyBoardView.findViewById(R.id.letterSH)
        sch = keyBoardView.findViewById(R.id.letterSCH)
        gentle = keyBoardView.findViewById(R.id.letter_gentle_sign)
        strong = keyBoardView.findViewById(R.id.letter_strong)
        not = keyBoardView.findViewById(R.id.letter_not)
        ae = keyBoardView.findViewById(R.id.letterAE)
        yu = keyBoardView.findViewById(R.id.letterYU)
        ya = keyBoardView.findViewById(R.id.letterYA)
    }

    fun setCLickListeners(keyBoard: KeyBoardClickListener) {
        a.setOnClickListener(keyBoard)
        b.setOnClickListener(keyBoard)
        v.setOnClickListener(keyBoard)
        g.setOnClickListener(keyBoard)
        d.setOnClickListener(keyBoard)
        e.setOnClickListener(keyBoard)
        gz.setOnClickListener(keyBoard)
        z.setOnClickListener(keyBoard)
        i.setOnClickListener(keyBoard)
        iS.setOnClickListener(keyBoard)
        k.setOnClickListener(keyBoard)
        l.setOnClickListener(keyBoard)
        m.setOnClickListener(keyBoard)
        n.setOnClickListener(keyBoard)
        o.setOnClickListener(keyBoard)
        p.setOnClickListener(keyBoard)
        r.setOnClickListener(keyBoard)
        s.setOnClickListener(keyBoard)
        t.setOnClickListener(keyBoard)
        u.setOnClickListener(keyBoard)
        ph.setOnClickListener(keyBoard)
        h.setOnClickListener(keyBoard)
        c.setOnClickListener(keyBoard)
        ch.setOnClickListener(keyBoard)
        sh.setOnClickListener(keyBoard)
        sch.setOnClickListener(keyBoard)
        gentle.setOnClickListener(keyBoard)
        strong.setOnClickListener(keyBoard)
        not.setOnClickListener(keyBoard)
        ae.setOnClickListener(keyBoard)
        yu.setOnClickListener(keyBoard)
        ya.setOnClickListener(keyBoard)
    }

    fun tutorialKey(): LinkedList<TextView> {
        val list = LinkedList<TextView>()
        list.add(d)
        list.add(z)
        list.add(i)
        list.add(p)
        return list
    }

    fun killKey(textView: TextView) {
        val transition = TransitionDrawable(arrayOf(textView.context.getDrawable(R.drawable.empty), textView.context.getDrawable(
            R.drawable.border_right
        )) )
        textView.setOnClickListener {  }
        textView.isClickable = false
        textView.animate()
            .scaleY(1.5f)
            .scaleX(1.5f)
            .setDuration(200)
            .withEndAction{
                textView.animate().scaleY(1f).scaleX(1f)
                textView.background = transition
                textView.alpha = 0.3f
            }.start()
    }

    fun pulseAnimation(view: View) {
        val scaleDown: ObjectAnimator = ObjectAnimator.ofPropertyValuesHolder(
            view,
            PropertyValuesHolder.ofFloat("scaleX", 0.8f),
            PropertyValuesHolder.ofFloat("scaleY", 0.8f)
        )
        scaleDown.setDuration(310)

        scaleDown.repeatCount = ObjectAnimator.INFINITE
        scaleDown.repeatMode = ObjectAnimator.REVERSE

        scaleDown.start()
    }
}