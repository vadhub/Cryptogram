package com.abg.cryptogram

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.abg.cryptogram.data.ParseCSV
import com.abg.cryptogram.model.Quote
import java.io.InputStream

class QuoteViewModel(inputStream: InputStream) : ViewModel() {
    private var quotes: List<Quote> = mutableListOf()
    init {
        quotes = ParseCSV().readCsv(inputStream)
    }

    fun getQuote(level: Int): Quote = quotes[level]
}

@Suppress("UNCHECKED_CAST")
class QuoteViewModelFactory(private val inputStream: InputStream) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return QuoteViewModel(inputStream) as T
    }
}