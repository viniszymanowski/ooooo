package com.manutencao.colheitadeiras.data.repository

import androidx.lifecycle.LiveData
import com.manutencao.colheitadeiras.data.dao.MovimentacaoEstoqueDao
import com.manutencao.colheitadeiras.data.model.MovimentacaoEstoque

class MovimentacaoEstoqueRepository(private val movimentacaoEstoqueDao: MovimentacaoEstoqueDao) {
    val allMovimentacoes: LiveData<List<MovimentacaoEstoque>> = movimentacaoEstoqueDao.getAll()
    
    fun getByItem(itemId: Int): LiveData<List<MovimentacaoEstoque>> {
        return movimentacaoEstoqueDao.getByItem(itemId)
    }
    
    suspend fun insert(movimentacao: MovimentacaoEstoque): Long {
        return movimentacaoEstoqueDao.insert(movimentacao)
    }
    
    suspend fun update(movimentacao: MovimentacaoEstoque) {
        movimentacaoEstoqueDao.update(movimentacao)
    }
    
    suspend fun delete(movimentacao: MovimentacaoEstoque) {
        movimentacaoEstoqueDao.delete(movimentacao)
    }
}
