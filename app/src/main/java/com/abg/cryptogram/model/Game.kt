package com.abg.cryptogram.model

import android.util.Log
import kotlin.random.Random

class Game(private val gameStatus: (StatusGame) -> Unit) {

    enum class StatusGame {
        WIN, GAME_OVER
    }

    private var hilth = 3
    private var letter = ' '
    private var notGuessed = 0
    private var frequency = mutableMapOf<Char, Int>()

    fun setLetter(letter: Char) {
        this.letter = letter
    }

    fun setHilth(hilth: Int) {
        if (hilth <= 0) {
            gameStatus.invoke(StatusGame.GAME_OVER)
        }
        this.hilth = hilth
    }

    fun sentenceMapToListWords(sentence: String): MutableList<Word> {
        val alphabet: MutableMap<Char, Int> = mutableMapOf()
        val words: MutableList<Word> = mutableListOf()
        var letters: MutableList<Letter> = mutableListOf()
        var counter = 1
        sentence.split(" ").forEach { it ->
            it.filter { it.isLetter() }.forEach { char ->
                var c = char
                val number: Int
                if (char !in alphabet) {
                    c = char
                    number = counter++
                    alphabet[c] = number
                    frequency[c] = 1
                } else {
                    number = alphabet[char]!!
                    val freq = frequency[c]
                    frequency[c] = (freq!! + 1)
                }
                val isFill = Random.nextBoolean()
                if (!isFill) {
                    notGuessed++
                }
                letters.add(Letter(c, number, isFill, frequency[c]!!))
            }
            words.add(Word(letters))
            letters = mutableListOf()
        }
        Log.d("info", frequency.toList().toTypedArray().contentToString())
        return words
    }

    fun compareLetters(candidate: Char): Boolean {
        if (candidate == letter) {
            notGuessed--
            if (notGuessed == 0) {
                gameStatus.invoke(StatusGame.WIN)
            }
            return true
        }
        return false
    }
}