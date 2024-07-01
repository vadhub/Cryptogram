package com.abg.cryptogram.model

import kotlin.random.Random

class Game(private val gameStatus: (StatusGame) -> Unit) {

    enum class StatusGame {
        WIN, GAME_OVER
    }

    private var hilth = 3
    private var letter = ' '
    private var notGuessed = 0

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
                } else {
                    number = alphabet[char]!!
                }
                val isFill = Random.nextBoolean()
                if (!isFill) {
                    notGuessed++
                }
                letters.add(Letter(c, number, isFill))
            }
            words.add(Word(letters))
            letters = mutableListOf()
        }
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