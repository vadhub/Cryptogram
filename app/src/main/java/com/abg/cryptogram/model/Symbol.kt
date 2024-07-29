package com.abg.cryptogram.model

import java.util.UUID

data class Symbol(
    val symbol: Char,
    val code: Int,
    val isFill: Boolean,
    var hintDestroy: Boolean = false,
    val viewType: Int,
    val id: Int = UUID.randomUUID().hashCode()
) {
    companion object {
        fun empty() = Symbol('-', -1, false, false, -1)
    }
}
