package com.abg.cryptogram.model

import ru.rustore.sdk.billingclient.RuStoreBillingClient

interface BillingClientProvider {
    fun billingClient() : RuStoreBillingClient
}