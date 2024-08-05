package com.abg.cryptogram.model

import java.io.InputStream

class ParseCSV {
    fun readCsv(inputStream: InputStream): List<Quote> {
        val reader = inputStream.bufferedReader()
        return reader.lineSequence()
            .filter { it.isNotBlank() }
            .map {
                val (id, author, quote) = it.split(',', ignoreCase = false, limit = 3)
                Quote(id.trim().toInt(), author.trim(), quote.trim().removeSurrounding("\""))
            }.toList()
    }
}