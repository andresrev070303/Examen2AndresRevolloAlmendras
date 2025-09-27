package com.calyrsoft.ucbp1.features.exchange.domain.repository

import com.calyrsoft.ucbp1.features.exchange.domain.model.ExchangeRateModel

interface IExchangeRateRepository {
    fun getExchangeRate(currency: String): Result<ExchangeRateModel>
}