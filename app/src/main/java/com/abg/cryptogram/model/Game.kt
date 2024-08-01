package com.abg.cryptogram.model

import android.util.Log
import android.widget.TextView
import com.abg.cryptogram.adapter.LetterAdapter
import java.util.LinkedList
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
    private var selectedLetterPosition: Pair<Int, Int> = Pair(-1, -1)
    private var queueNotFillPosition: LinkedList<Pair<Int, Int>> = LinkedList()

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

    /**
     * Algorithm - MONSTER OF STRINGS. for parse string to list of words
     */
    fun sentenceMapToListWords(sentence: String): MutableList<Word> {
        val alphabet: MutableMap<Char, Int> = mutableMapOf()
        val words: MutableList<Word> = mutableListOf()
        var letters: MutableList<Symbol> = mutableListOf()
        var outPos = -1
        var inPos = -1
        var counter = 1
        var counterIsSelected = 1
        sentence.split("/").forEach { word ->
            outPos++
            word.forEach { char ->
                inPos++
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
                        counterIsSelected++
                        queueNotFillPosition.add(Pair(outPos, inPos))

                        if (counterIsSelected == 2) {
                            letter = c
                            selectedLetterPosition = Pair(outPos, inPos)
                        }
                    }

                    letters.add(Symbol(c, number, isFill, viewType = LetterAdapter.VIEW_TYPE_LETTER, isSelected = !isFill && counterIsSelected == 2 /* first empty letter is select */))
                } else {
                    letters.add(Symbol(char, -1, isFill = true, viewType = LetterAdapter.VIEW_TYPE_SIGN))
                }
            }
            inPos = -1
            words.add(Word(letters))
            letters = mutableListOf()
        }
        clearZeroFrequency(words)
        return words
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

    fun changeCodeVisibleAllConcreteLetter(words: MutableList<Word>, letter: Char) {
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

    fun getSelectLetter(): Pair<Int, Int> {
        return selectedLetterPosition
    }

    fun removeNotFill(notFillPosition: Pair<Int, Int>) {
        queueNotFillPosition.remove(notFillPosition)
    }

    private fun getNextNotFillLetter() : Pair<Int, Int> {
        return queueNotFillPosition.peek() ?: Pair(-1, -1)
    }

    fun setNextSelect(list: MutableList<Word>) {
        val nextPosition = getNextNotFillLetter()
        if (nextPosition.first!=-1&&nextPosition.second!=-1) {
            setSelected(list, nextPosition)
        }
    }

    fun setSelected(list: MutableList<Word>, position: Pair<Int, Int>) {
        val oldLetter = list[position.first].letters[position.second]
        val newLetter = oldLetter.copy(isSelected = true)
        list[position.first].letters[position.second] = newLetter
        letter = newLetter.symbol
        selectedLetterPosition = Pair(position.first, position.second)
    }

    fun setFillLetter(list: MutableList<Word>, currentPosition: Pair<Int, Int>) {
        val oldLetter = list[currentPosition.first].letters[currentPosition.second]
        val letter = oldLetter.copy(isFill = true, isSelected = false)
        list[currentPosition.first].letters[currentPosition.second] = letter
    }

    fun setNotSelected(list: MutableList<Word>) {
        val currentPosition = getSelectLetter()
        val oldLetter = list[currentPosition.first].letters[currentPosition.second]
        val letter = oldLetter.copy(isSelected = false)
        list[currentPosition.first].letters[currentPosition.second] = letter
    }

    fun setLetter(symbol: Char) {
        this.letter = symbol
    }
}