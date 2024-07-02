package com.abg.cryptogram.model

import android.util.Log
import com.abg.cryptogram.adapter.LetterAdapter
import kotlin.random.Random

class Game(private val gameStatus: (StatusGame) -> Unit) {

    enum class StatusGame {
        WIN, GAME_OVER
    }

    private var allConcreteLetterFindListener: (letter: Char) -> Unit = {}
    private var hilth = 3
    private var letter = ' '
    private var notGuessed = 0
    private var frequency = mutableMapOf<Char, Int>()

    fun setAllConcreteLetterFindListener(allConcreteLetterFind: (letter: Char) -> Unit) {
        this.allConcreteLetterFindListener = allConcreteLetterFind
    }

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
        var letters: MutableList<Symbol> = mutableListOf()
        var counter = 1
        sentence.split("/").forEach { word ->
            word.forEach { char ->
                if (char.isLetter()) {
                    var c = char
                    val number: Int
                    val isFill = Random.nextBoolean()
                    if (char !in alphabet) {
                        c = char
                        number = counter++
                        alphabet[c] = number
                        if (!isFill) {
                            frequency[c] = 1
                        } else {
                            frequency[c] = 0
                        }
                    } else {
                        number = alphabet[char]!!
                        if (!isFill) {
                            val count = frequency[c]
                            if (count != null) {
                                frequency[c] = count + 1
                            }
                        }
                    }

                    if (!isFill) {
                        notGuessed++
                    }
                    letters.add(Symbol(c, number, isFill, viewType = LetterAdapter.VIEW_TYPE_LETTER))
                } else {
                    letters.add(Symbol(char, -1, isFill = true, viewType = LetterAdapter.VIEW_TYPE_SIGN))
                }
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
            val count = frequency[candidate]
            if (count != null) {
                frequency[candidate] = count - 1
                allConcreteLetterFind(frequency[candidate]!!, letter)
            }
            if (notGuessed == 0) {
                gameStatus.invoke(StatusGame.WIN)
            }
            return true
        }
        return false
    }

    fun changeHintAllConcreteLetter(words: MutableList<Word>, letter: Char) {
        words.forEach { word ->
            word.letters.forEach {
                if (it.symbol == letter) {
                    it.hintDestroy = true
                }
            }
        }
    }

    private fun allConcreteLetterFind(count: Int, letter: Char) {
        if (count == 0) {
            allConcreteLetterFindListener.invoke(letter)
        }
    }
}