package com.calyrsoft.ucbp1.features.dollar.domain.repository

import com.calyrsoft.ucbp1.features.dollar.domain.model.DollarModel
import kotlinx.coroutines.flow.Flow

interface IDollarRepository {
    fun observeDollar(): Flow<DollarModel>
    suspend fun getHistory(): List<DollarModel>  // opcional (útil para depurar/mostrar)
}
