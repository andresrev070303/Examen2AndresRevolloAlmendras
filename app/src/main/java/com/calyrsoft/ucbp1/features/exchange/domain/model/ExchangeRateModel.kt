package com.calyrsoft.ucbp1.features.exchange.domain.model

data class ExchangeRateModel(
    val baseCurrency: String = "BOB",
    // Código de la moneda: "USD", "JPY", "GBP", etc.
    val currency: String,
    // Tasa compra moneda
    val buyRate: Double,
    // Tasa venta
    val sellRate: Double
)