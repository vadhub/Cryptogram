package com.abg.cryptogram.ui.keyboard

import android.view.View
import android.widget.TextView
import com.abg.cryptogram.R
import java.util.LinkedList

class KeyBoardEN : KeyBoard() {

    private lateinit var q: TextView
    private lateinit var w: TextView
    private lateinit var e: TextView
    private lateinit var r: TextView
    private lateinit var t: TextView
    private lateinit var y: TextView
    private lateinit var u: TextView
    private lateinit var i: TextView
    private lateinit var o: TextView
    private lateinit var p: TextView
    private lateinit var a: TextView
    private lateinit var s: TextView
    private lateinit var d: TextView
    private lateinit var f: TextView
    private lateinit var g: TextView
    private lateinit var h: TextView
    private lateinit var j: TextView
    private lateinit var k: TextView
    private lateinit var l: TextView
    private lateinit var z: TextView
    private lateinit var x: TextView
    private lateinit var c: TextView
    private lateinit var v: TextView
    private lateinit var b: TextView
    private lateinit var n: TextView
    private lateinit var m: TextView


    override fun inflateKeyBoard(keyBoardView: View) {
        a = keyBoardView.findViewById(R.id.A)
        b = keyBoardView.findViewById(R.id.B)
        v = keyBoardView.findViewById(R.id.V)
        q = keyBoardView.findViewById(R.id.Q)
        g = keyBoardView.findViewById(R.id.G)
        d = keyBoardView.findViewById(R.id.D)
        e = keyBoardView.findViewById(R.id.E)
        z = keyBoardView.findViewById(R.id.Z)
        i = keyBoardView.findViewById(R.id.I)
        k = keyBoardView.findViewById(R.id.K)
        l = keyBoardView.findViewById(R.id.L)
        m = keyBoardView.findViewById(R.id.M)
        n = keyBoardView.findViewById(R.id.N)
        o = keyBoardView.findViewById(R.id.O)
        p = keyBoardView.findViewById(R.id.P)
        r = keyBoardView.findViewById(R.id.R)
        s = keyBoardView.findViewById(R.id.S)
        t = keyBoardView.findViewById(R.id.T)
        u = keyBoardView.findViewById(R.id.U)
        h = keyBoardView.findViewById(R.id.H)
        c = keyBoardView.findViewById(R.id.C)
        w = keyBoardView.findViewById(R.id.W)
        y = keyBoardView.findViewById(R.id.Y)
        f = keyBoardView.findViewById(R.id.F)
        j = keyBoardView.findViewById(R.id.J)
        x = keyBoardView.findViewById(R.id.X)
    }

    override fun setCLickListeners(keyBoard: KeyBoardClickListener) {
        a.setOnClickListener(keyBoard)
        b.setOnClickListener(keyBoard)
        v.setOnClickListener(keyBoard)
        g.setOnClickListener(keyBoard)
        d.setOnClickListener(keyBoard)
        e.setOnClickListener(keyBoard)
        z.setOnClickListener(keyBoard)
        i.setOnClickListener(keyBoard)
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
        h.setOnClickListener(keyBoard)
        c.setOnClickListener(keyBoard)
        q.setOnClickListener(keyBoard)
        w.setOnClickListener(keyBoard)
        y.setOnClickListener(keyBoard)
        f.setOnClickListener(keyBoard)
        j.setOnClickListener(keyBoard)
        x.setOnClickListener(keyBoard)
    }

    override fun tutorialKey(): LinkedList<TextView> {
        val list = LinkedList<TextView>()
        list.add(l)
        list.add(i)
        list.add(e)
        list.add(h)
        return list
    }
}