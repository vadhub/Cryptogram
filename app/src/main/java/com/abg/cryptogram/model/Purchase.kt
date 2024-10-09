package com.abg.cryptogram.model

import android.content.Context
import android.util.Log
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
                 when (result) {
                    FeatureAvailabilityResult.Available -> {
                        // Process purchases available
                        isAvailable = true
                    }

                        is FeatureAvailabilityResult.Unavailable -> {
                        // Process purchases unavailable
                        Log.d("e", result.cause.toString())
                        isAvailable = false
                    }
                }
            }
            .addOnFailureListener { throwable ->
                // Process unknown error
                Log.d("e",throwable.message.toString())
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
                Log.w("purchase_get",throwable.cause)
            }

        return productList
    }

    fun commitPurchase(billingClient: RuStoreBillingClient, productId: String, purchaseResult: PurchaseResult) {
        val purchasesUseCase: PurchasesUseCase = billingClient.purchases
        purchasesUseCase.purchaseProduct(
            productId = productId,
            orderId = UUID.randomUUID().toString(),
            quantity = 3,
            developerPayload = null,
        ).addOnSuccessListener { paymentResult: PaymentResult ->
            when (paymentResult) {
                is PaymentResult.Cancelled -> purchaseResult.cancel()
                is PaymentResult.Failure -> purchaseResult.fail()
                PaymentResult.InvalidPaymentState -> purchaseResult.fail()
                is PaymentResult.Success -> purchaseResult.success()
            }
        }.addOnFailureListener { throwable: Throwable ->
            purchaseResult.fail()
            Log.w("purchase_commit",throwable.cause)
        }
    }
}