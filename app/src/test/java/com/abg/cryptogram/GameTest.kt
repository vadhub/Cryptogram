package com.abg.cryptogram

import com.abg.cryptogram.adapter.LetterAdapter
import com.abg.cryptogram.model.Game
import com.abg.cryptogram.model.Symbol
import com.abg.cryptogram.model.Word
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GameTest {

    private var sentence: MutableList<Word> = mutableListOf()
    private var sentence2: MutableList<Word> = mutableListOf()
    private var sentence3: MutableList<Word> = mutableListOf()

    @Before
    fun setSentence() {
        sentence = mutableListOf(
        Word(mutableListOf(Symbol('A', 1, true, false, LetterAdapter.VIEW_TYPE_LETTER, true), Symbol('B', 1, true, false, LetterAdapter.VIEW_TYPE_LETTER, false))),
        Word(mutableListOf(Symbol('C', 1, true, false, LetterAdapter.VIEW_TYPE_LETTER, false))),
        Word(mutableListOf(Symbol('D', 1, true, false, LetterAdapter.VIEW_TYPE_LETTER, false))),
        Word(mutableListOf(Symbol('Z', 1, true, false, LetterAdapter.VIEW_TYPE_LETTER, false), Symbol('V', 1, true, false, LetterAdapter.VIEW_TYPE_LETTER, false)))
        )

        sentence2 = mutableListOf(
            Word(mutableListOf(Symbol('A', 1, true, false, LetterAdapter.VIEW_TYPE_LETTER, false), Symbol('B', 1, true, false, LetterAdapter.VIEW_TYPE_LETTER, false))),
            Word(mutableListOf(Symbol('C', 1, true, false, LetterAdapter.VIEW_TYPE_LETTER, false))),
            Word(mutableListOf(Symbol('D', 1, true, false, LetterAdapter.VIEW_TYPE_LETTER, false))),
            Word(mutableListOf(Symbol('Z', 1, true, false, LetterAdapter.VIEW_TYPE_LETTER, false), Symbol('V', 1, true, false, LetterAdapter.VIEW_TYPE_LETTER, true)))
        )

        sentence3 = mutableListOf(
            Word(mutableListOf(Symbol('A', 1, true, false, LetterAdapter.VIEW_TYPE_LETTER, false), Symbol('B', 1, true, false, LetterAdapter.VIEW_TYPE_LETTER, false))),
            Word(mutableListOf(Symbol('C', 1, true, false, LetterAdapter.VIEW_TYPE_LETTER, true))),
            Word(mutableListOf(Symbol('D', 1, true, false, LetterAdapter.VIEW_TYPE_LETTER, false))),
            Word(mutableListOf(Symbol('Z', 1, true, false, LetterAdapter.VIEW_TYPE_LETTER, false), Symbol('V', 1, true, false, LetterAdapter.VIEW_TYPE_LETTER, false)))
        )
    }

    @Test
    fun test_get_selected_first() {
        val game = Game {}
        val right = Pair(0,0)
        val test = game.getSelectLetter(sentence)
        Assert.assertEquals(right.first, test.first)
        Assert.assertEquals(right.second, test.second)
    }

    @Test
    fun test_get_selected_last() {
        val game = Game {}
        val right = Pair(3,1)
        val test = game.getSelectLetter(sentence2)
        Assert.assertEquals(right.first, test.first)
        Assert.assertEquals(right.second, test.second)
    }

    @Test
    fun test_get_selected_middle() {
        val game = Game {}
        val right = Pair(1,0)
        val test = game.getSelectLetter(sentence3)
        Assert.assertEquals(right.first, test.first)
        Assert.assertEquals(right.second, test.second)
    }
}