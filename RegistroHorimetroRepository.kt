package com.manutencao.colheitadeiras.data.repository

import androidx.lifecycle.LiveData
import com.manutencao.colheitadeiras.data.dao.RegistroHorimetroDao
import com.manutencao.colheitadeiras.data.model.RegistroHorimetro

class RegistroHorimetroRepository(private val registroHorimetroDao: RegistroHorimetroDao) {
    val allRegistros: LiveData<List<RegistroHorimetro>> = registroHorimetroDao.getAll()
    
    fun getByColheitadeira(colheitadeiraId: Int): LiveData<List<RegistroHorimetro>> {
        return registroHorimetroDao.getByColheitadeira(colheitadeiraId)
    }
    
    suspend fun getLatestByColheitadeira(colheitadeiraId: Int): RegistroHorimetro? {
        return registroHorimetroDao.getLatestByColheitadeira(colheitadeiraId)
    }
    
    suspend fun insert(registro: RegistroHorimetro): Long {
        return registroHorimetroDao.insert(registro)
    }
    
    suspend fun update(registro: RegistroHorimetro) {
        registroHorimetroDao.update(registro)
    }
    
    suspend fun delete(registro: RegistroHorimetro) {
        registroHorimetroDao.delete(registro)
    }
}
