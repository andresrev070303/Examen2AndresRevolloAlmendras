package com.calyrsoft.ucbp1.features.dollar.data.datasource

import com.calyrsoft.ucbp1.features.dollar.domain.model.DollarModel
import com.google.firebase.database.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class RealTimeRemoteDataSource {
    fun getDollarUpdates(): Flow<DollarModel> = callbackFlow {
        val db = FirebaseDatabase.getInstance()
        val ref = db.getReference("dollar")

        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val v = snapshot.getValue(DollarModel::class.java)
                val model = (v ?: DollarModel()).copy(updatedAt = System.currentTimeMillis())
                trySend(model)
            }
            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }

        ref.addValueEventListener(listener)
        awaitClose { ref.removeEventListener(listener) }
    }
}
