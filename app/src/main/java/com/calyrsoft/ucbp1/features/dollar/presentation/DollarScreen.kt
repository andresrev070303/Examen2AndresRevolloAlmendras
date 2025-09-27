package com.calyrsoft.ucbp1.features.dollar.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel

@Composable
fun DollarScreen(viewModelDollar: DollarViewModel = koinViewModel()) {
    val state = viewModelDollar.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        when (val stateValue = state.value) {
            is DollarViewModel.DollarUIState.Error -> {
                Text(stateValue.message)
            }
            DollarViewModel.DollarUIState.Loading -> {
                Spacer(Modifier.height(24.dp))
                CircularProgressIndicator()
            }
            is DollarViewModel.DollarUIState.Success -> {
                val d = stateValue.data

                Text(
                    text = "Tipos de cambio",
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(Modifier.height(12.dp))

                // Tarjeta para el tipo OFICIAL
                RateCard(
                    title = "Oficial",
                    buy = d.officialBuy,
                    sell = d.officialSell
                )
                Spacer(Modifier.height(12.dp))

                // Tarjeta para el tipo PARALELO
                RateCard(
                    title = "Paralelo",
                    buy = d.parallelBuy,
                    sell = d.parallelSell
                )

                Spacer(Modifier.height(16.dp))

                // Fecha de actualización
                d.updatedAt?.let { ts ->
                    val formatted = java.text.SimpleDateFormat("dd/MM/yyyy HH:mm")
                        .format(java.util.Date(ts))
                    Text(
                        text = "Última actualización: $formatted",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}

@Composable
private fun RateCard(
    title: String,
    buy: String?,
    sell: String?
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(title, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("Compra", style = MaterialTheme.typography.labelSmall)
                    Text(buy ?: "-", style = MaterialTheme.typography.bodyLarge)
                }
                Column {
                    Text("Venta", style = MaterialTheme.typography.labelSmall)
                    Text(sell ?: "-", style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }
}
