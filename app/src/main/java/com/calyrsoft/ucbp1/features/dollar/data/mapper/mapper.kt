package com.calyrsoft.ucbp1.features.dollar.data.mapper

import com.calyrsoft.ucbp1.features.dollar.data.database.entity.DollarEntity
import com.calyrsoft.ucbp1.features.dollar.domain.model.DollarModel

fun DollarEntity.toDomain(): DollarModel = DollarModel(
    officialBuy  = officialBuy,
    officialSell = officialSell,
    parallelBuy  = parallelBuy,
    parallelSell = parallelSell,
    updatedAt    = timestamp
)

fun DollarModel.toEntity(): DollarEntity = DollarEntity(
    officialBuy  = officialBuy,
    officialSell = officialSell,
    parallelBuy  = parallelBuy,
    parallelSell = parallelSell,
    timestamp    = updatedAt ?: System.currentTimeMillis()
)
