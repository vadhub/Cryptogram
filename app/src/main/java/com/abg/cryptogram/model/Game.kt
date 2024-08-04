package com.abg.cryptogram.model

import android.hardware.ConsumerIrManager.CarrierFrequencyRange
import android.util.Log
import android.widget.TextView
import com.abg.cryptogram.data.SaveConfig
import kotlin.random.Random

class Game(private val gameStatus: (StatusGame) -> Unit) {

    enum class StatusGame {
        WIN, GAME_OVER
    }

    private var allConcreteLetterFindListener: (letter: Char, TextView) -> Unit = {_,_ ->}
    private var hilth = 3
    private var letter = ' '
    private var notGuessed = 0
    private var frequency = mutableMapOf<Char, Int>()
    private var hint = 5

    fun setHint(hint: Int) {
        this.hint = hint
    }

    fun minusHint(saveConfig: SaveConfig) {
        if (hint > 0) {
            saveConfig.saveHint(hint--)
        }
    }

    fun setNotGuessed(i: Int) {
        this.notGuessed = i
    }

    fun setFrequency(frequency: MutableMap<Char, Int>) {
        this.frequency = frequency
    }

    fun getHint() = hint

    fun setAllConcreteLetterFindListener(allConcreteLetterFind: (letter: Char, TextView) -> Unit) {
        this.allConcreteLetterFindListener = allConcreteLetterFind
    }

    fun minusHilth() : Int {
        hilth -= 1
        if (hilth <= 0) {
            gameStatus.invoke(StatusGame.GAME_OVER)
        }
        return hilth
    }

    // chat gpt generation
    fun generateRandomBoolean(probability: Int): Boolean {
        require(probability in 0..100) { "Вероятность должна быть в диапазоне от 0 до 100%" }

        val randomValue = Random.nextInt(1, 101) // генерируем случайное число от 1 до 100
        return randomValue <= probability
    }

    /**
     * Algorithm - MONSTER OF STRINGS. for parse string to list of words
     */
    fun sentenceMapToListWords(sentence: String, level: Int): MutableList<Word> {
        val alphabet: MutableMap<Char, Int> = mutableMapOf()
        val words: MutableList<Word> = mutableListOf()
        var letters: MutableList<Symbol> = mutableListOf()
        var counter = 1
        var counterIsSelected = 1
        sentence.split("/").forEach { word ->
            word.forEach { char ->
                if (char.isLetter()) {
                    var c = char
                    val number: Int
                    val isFill = generateRandomBoolean(getProbability(level))
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
                        counterIsSelected++

                        if (counterIsSelected == 2) {
                            letter = c
                        }
                    }

                    letters.add(Symbol(c, number, isFill, viewType = VIEW_TYPE_LETTER))
                } else {
                    letters.add(Symbol(char, -1, isFill = true, viewType = VIEW_TYPE_SIGN))
                }
            }
            words.add(Word(letters))
            letters = mutableListOf()
        }
        clearZeroFrequency(words)
        return words
    }

    private fun getProbability(level: Int): Int {
        var probability = 60

        if (level > 7) {
            probability = 40
        }
        if (level > 15) {
            probability = 35
        }
        if (level > 30) {
            probability = 30
        }
        if (level > 40) {
            probability = 25
        }
        return probability
    }

    fun compareLetters(textView: TextView, candidate: Char): Boolean {
        Log.d("##", candidate.toString())
        if (candidate == letter) {
            notGuessed--
            val count = frequency[candidate]
            if (count != null) {
                frequency[candidate] = count - 1
                allConcreteLetterFind(frequency[candidate]!!, letter, textView)
            }
            if (notGuessed == 0) {
                gameStatus.invoke(StatusGame.WIN)
            }
            return true
        }
        return false
    }

    private fun changeCodeVisibleAllConcreteLetter(words: MutableList<Word>, letter: Char) {
        words.forEach { word ->
            word.letters.forEach {
                if (it.symbol == letter) {
                    it.isShowCode = true
                }
            }
        }
    }

    /**
     * need for clear hint on all guessed letter
     */
    private fun clearZeroFrequency(words: MutableList<Word>) {
        frequency.filter { it.value == 0 }.forEach {
            changeCodeVisibleAllConcreteLetter(words,it.key)
        }
    }

    private fun allConcreteLetterFind(count: Int, letter: Char, textView: TextView) {
        if (count == 0) {
            allConcreteLetterFindListener.invoke(letter, textView)
        }
    }

    fun setLetter(symbol: Char) {
        this.letter = symbol
    }

    companion object {
        const val VIEW_TYPE_LETTER = 1
        const val VIEW_TYPE_SIGN = 2
    }
}