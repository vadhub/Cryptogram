package com.abg.cryptogram.ui.keyboard

import android.view.View
import android.widget.TextView
import com.abg.cryptogram.R
import java.util.LinkedList

class KeyBoardRU : KeyBoard() {

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


    override fun inflateKeyBoard(keyBoardView: View) {
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

    override fun setCLickListeners(keyBoard: KeyBoardClickListener) {
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

    override fun tutorialKey(): LinkedList<TextView> {
        val list = LinkedList<TextView>()
        list.add(d)
        list.add(z)
        list.add(i)
        list.add(p)
        return list
    }
}