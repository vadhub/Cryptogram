package com.abg.cryptogram.model

data class Letter(
    val letter: Char,
    val code: Int,
    val isFill: Boolean,
    var hintDestroy: Boolean = false
)
