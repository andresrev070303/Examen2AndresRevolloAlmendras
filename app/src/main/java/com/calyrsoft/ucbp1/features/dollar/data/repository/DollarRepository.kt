package com.calyrsoft.ucbp1.features.dollar.data.repository

import com.calyrsoft.ucbp1.features.dollar.data.datasource.DollarLocalDataSource
import com.calyrsoft.ucbp1.features.dollar.data.datasource.RealTimeRemoteDataSource
import com.calyrsoft.ucbp1.features.dollar.domain.model.DollarModel
import com.calyrsoft.ucbp1.features.dollar.domain.repository.IDollarRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

class DollarRepository(
    private val remote: RealTimeRemoteDataSource,
    private val local: DollarLocalDataSource
) : IDollarRepository {

    override fun observeDollar(): Flow<DollarModel> {
        return remote.getDollarUpdates()
            .onEach { model ->
                local.insert(model) // guarda cada snapshot (histórico)
            }
    }

    override suspend fun getHistory(): List<DollarModel> {
        return local.getList()
    }
}
