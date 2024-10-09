package com.abg.cryptogram.model

interface PurchaseResult {
    fun success()
    fun cancel()
    fun fail()
}