package com.manutencao.colheitadeiras.data.repository

import androidx.lifecycle.LiveData
import com.manutencao.colheitadeiras.data.dao.ItemEstoqueDao
import com.manutencao.colheitadeiras.data.model.ItemEstoque

class ItemEstoqueRepository(private val itemEstoqueDao: ItemEstoqueDao) {
    val allItens: LiveData<List<ItemEstoque>> = itemEstoqueDao.getAll()
    
    fun getByCategoria(categoria: String): LiveData<List<ItemEstoque>> {
        return itemEstoqueDao.getByCategoria(categoria)
    }
    
    fun getItemsBaixoEstoque(): LiveData<List<ItemEstoque>> {
        return itemEstoqueDao.getItemsBaixoEstoque()
    }
    
    suspend fun insert(item: ItemEstoque): Long {
        return itemEstoqueDao.insert(item)
    }
    
    suspend fun update(item: ItemEstoque) {
        itemEstoqueDao.update(item)
    }
    
    suspend fun delete(item: ItemEstoque) {
        itemEstoqueDao.delete(item)
    }
}
