package com.calyrsoft.ucbp1.features.dollar.domain.model

data class DollarModel(
    var officialBuy: String? = null,
    var officialSell: String? = null,
    var parallelBuy: String? = null,
    var parallelSell: String? = null,
    var updatedAt: Long? = null
)

