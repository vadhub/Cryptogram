package com.abg.cryptogram.model

data class Symbol(
    val symbol: Char,
    val code: Int,
    val isFill: Boolean,
    var isShowCode: Boolean = false,
    val viewType: Int,
)

