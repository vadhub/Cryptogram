package com.abg.cryptogram

import androidx.lifecycle.ViewModel
import com.abg.cryptogram.model.ParseCSV
import com.abg.cryptogram.model.Quote
import java.io.InputStream

class QuoteViewModel : ViewModel() {
    private var quotes: List<Quote> = mutableListOf()

    fun createListQuotes(inputStream: InputStream) {
        quotes = ParseCSV().readCsv(inputStream)
    }

    fun getQuote(level: Int): Quote = quotes[level]
}