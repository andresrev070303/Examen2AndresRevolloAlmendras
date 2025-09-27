package com.calyrsoft.ucbp1.features.dollar.data.datasource

import com.calyrsoft.ucbp1.features.dollar.domain.model.DollarModel
import com.calyrsoft.ucbp1.features.dollar.data.database.dao.IDollarDao
import com.calyrsoft.ucbp1.features.dollar.data.mapper.toEntity

class DollarLocalDataSource(
    val dao: IDollarDao
) {


    suspend fun getList(): List<DollarModel> {
        return dao.getList().map {
            it.toModel()
        }


    }
    suspend fun deleteAll() {
        dao.deleteAll()
    }
    suspend fun inserTDollars(list: List<DollarModel>) {
        val dollarEntity = list.map { it.toEntity() }
        dao.insertDollars(dollarEntity)
    }


    suspend fun insert(dollar: DollarModel) {
        dao.insert(dollar.toEntity())
    }


}