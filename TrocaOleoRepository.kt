package com.manutencao.colheitadeiras.data.repository

import androidx.lifecycle.LiveData
import com.manutencao.colheitadeiras.data.dao.TrocaOleoDao
import com.manutencao.colheitadeiras.data.model.TrocaOleo

class TrocaOleoRepository(private val trocaOleoDao: TrocaOleoDao) {
    val allTrocas: LiveData<List<TrocaOleo>> = trocaOleoDao.getAll()
    
    fun getByColheitadeira(colheitadeiraId: Int): LiveData<List<TrocaOleo>> {
        return trocaOleoDao.getByColheitadeira(colheitadeiraId)
    }
    
    suspend fun insert(troca: TrocaOleo): Long {
        return trocaOleoDao.insert(troca)
    }
    
    suspend fun update(troca: TrocaOleo) {
        trocaOleoDao.update(troca)
    }
    
    suspend fun delete(troca: TrocaOleo) {
        trocaOleoDao.delete(troca)
    }
}
