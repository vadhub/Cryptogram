package com.abg.cryptogram.model

import android.content.Context
import ru.rustore.sdk.billingclient.RuStoreBillingClient
import ru.rustore.sdk.billingclient.model.product.Product
import ru.rustore.sdk.billingclient.model.purchase.PaymentResult
import ru.rustore.sdk.billingclient.usecase.ProductsUseCase
import ru.rustore.sdk.billingclient.usecase.PurchasesUseCase
import ru.rustore.sdk.billingclient.utils.pub.checkPurchasesAvailability
import ru.rustore.sdk.core.feature.model.FeatureAvailabilityResult
import java.util.UUID

class Purchase(private val context: Context) {

    fun getAvailablePurchase(): Boolean {
        var isAvailable = false
        RuStoreBillingClient.checkPurchasesAvailability(context)
            .addOnSuccessListener { result ->
                isAvailable = when (result) {
                    FeatureAvailabilityResult.Available -> {
                        // Process purchases available
                        true
                    }

                    is FeatureAvailabilityResult.Unavailable -> {
                        // Process purchases unavailable
                        false
                    }
                }
            }
            .addOnFailureListener { throwable ->
                // Process unknown error
                isAvailable = false
            }

        return isAvailable
    }

    fun getPurchases(billingClient: RuStoreBillingClient) : List<Product> {
        var productList: List<Product> = emptyList()
        val productsUseCase: ProductsUseCase = billingClient.products
        productsUseCase.getProducts(productIds = listOf("3_hints"))
            .addOnSuccessListener { products: List<Product> ->
               productList = products
            }
            .addOnFailureListener { throwable: Throwable ->
                // Process error
            }

        return productList
    }

    fun commitPurchase(billingClient: RuStoreBillingClient, productId: String) {
        val purchasesUseCase: PurchasesUseCase = billingClient.purchases
        purchasesUseCase.purchaseProduct(
            productId = productId,
            orderId = UUID.randomUUID().toString(),
            quantity = 1,
            developerPayload = null,
        ).addOnSuccessListener { paymentResult: PaymentResult ->
            when (paymentResult) {
                // Process PaymentResult
                is PaymentResult.Cancelled -> TODO()
                is PaymentResult.Failure -> TODO()
                PaymentResult.InvalidPaymentState -> TODO()
                is PaymentResult.Success -> TODO()
            }
        }.addOnFailureListener { throwable: Throwable ->
            // Process error
        }
    }
}