package com.abg.cryptogram.model

import ru.rustore.sdk.billingclient.model.purchase.PaymentResult

interface PurchaseResult {
    fun success(paymentResult: PaymentResult.Success)
    fun cancel()
    fun fail(paymentResult: PaymentResult.Failure)
    fun fail()
}