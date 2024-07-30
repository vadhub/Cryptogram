package com.abg.cryptogram.model

import java.util.UUID

data class Word(
    val letters: MutableList<Symbol>,
    val id: Int = UUID.randomUUID().hashCode()
)
