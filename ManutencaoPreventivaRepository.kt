package com.manutencao.colheitadeiras.data.repository

import androidx.lifecycle.LiveData
import com.manutencao.colheitadeiras.data.dao.ManutencaoPreventivaDao
import com.manutencao.colheitadeiras.data.model.ManutencaoPreventiva

class ManutencaoPreventivaRepository(private val manutencaoPreventivaDao: ManutencaoPreventivaDao) {
    val allManutencoes: LiveData<List<ManutencaoPreventiva>> = manutencaoPreventivaDao.getAll()
    
    fun getByColheitadeira(colheitadeiraId: Int): LiveData<List<ManutencaoPreventiva>> {
        return manutencaoPreventivaDao.getByColheitadeira(colheitadeiraId)
    }
    
    fun getByStatus(status: String): LiveData<List<ManutencaoPreventiva>> {
        return manutencaoPreventivaDao.getByStatus(status)
    }
    
    suspend fun insert(manutencao: ManutencaoPreventiva): Long {
        return manutencaoPreventivaDao.insert(manutencao)
    }
    
    suspend fun update(manutencao: ManutencaoPreventiva) {
        manutencaoPreventivaDao.update(manutencao)
    }
    
    suspend fun delete(manutencao: ManutencaoPreventiva) {
        manutencaoPreventivaDao.delete(manutencao)
    }
}
