package com.manutencao.colheitadeiras.data.repository

import androidx.lifecycle.LiveData
import com.manutencao.colheitadeiras.data.dao.ManutencaoCorretivaDao
import com.manutencao.colheitadeiras.data.model.ManutencaoCorretiva

class ManutencaoCorretivaRepository(private val manutencaoCorretivaDao: ManutencaoCorretivaDao) {
    val allManutencoes: LiveData<List<ManutencaoCorretiva>> = manutencaoCorretivaDao.getAll()
    
    fun getByColheitadeira(colheitadeiraId: Int): LiveData<List<ManutencaoCorretiva>> {
        return manutencaoCorretivaDao.getByColheitadeira(colheitadeiraId)
    }
    
    fun getByStatus(status: String): LiveData<List<ManutencaoCorretiva>> {
        return manutencaoCorretivaDao.getByStatus(status)
    }
    
    suspend fun insert(manutencao: ManutencaoCorretiva): Long {
        return manutencaoCorretivaDao.insert(manutencao)
    }
    
    suspend fun update(manutencao: ManutencaoCorretiva) {
        manutencaoCorretivaDao.update(manutencao)
    }
    
    suspend fun delete(manutencao: ManutencaoCorretiva) {
        manutencaoCorretivaDao.delete(manutencao)
    }
}
