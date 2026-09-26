package com.lihan.studioghibli.core.domain.datastore

import kotlinx.coroutines.flow.Flow

interface AppDataStore {
    val dataExpired: Flow<Long>
    suspend fun getDataExpired(): Long
    suspend fun setDataExpired(timestamp: Long)
}
