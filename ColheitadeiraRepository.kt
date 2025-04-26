package com.manutencao.colheitadeiras.data.repository

import androidx.lifecycle.LiveData
import com.manutencao.colheitadeiras.data.dao.ColheitadeiraDao
import com.manutencao.colheitadeiras.data.model.Colheitadeira

class ColheitadeiraRepository(private val colheitadeiraDao: ColheitadeiraDao) {
    val allColheitadeiras: LiveData<List<Colheitadeira>> = colheitadeiraDao.getAll()
    
    suspend fun getById(id: Int): Colheitadeira? {
        return colheitadeiraDao.getById(id)
    }
    
    suspend fun insert(colheitadeira: Colheitadeira): Long {
        return colheitadeiraDao.insert(colheitadeira)
    }
    
    suspend fun update(colheitadeira: Colheitadeira) {
        colheitadeiraDao.update(colheitadeira)
    }
    
    suspend fun delete(colheitadeira: Colheitadeira) {
        colheitadeiraDao.delete(colheitadeira)
    }
}
