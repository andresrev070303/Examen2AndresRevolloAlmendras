package com.calyrsoft.ucbp1.features.exchange.domain.usecase

import com.calyrsoft.ucbp1.features.exchange.domain.model.ExchangeRateModel
import com.calyrsoft.ucbp1.features.exchange.domain.repository.IExchangeRateRepository

class GetExchangeRateUseCase(private val repository: IExchangeRateRepository) {
    fun invoke(currency: String): Result<ExchangeRateModel> {
        return repository.getExchangeRate(currency)
    }
}