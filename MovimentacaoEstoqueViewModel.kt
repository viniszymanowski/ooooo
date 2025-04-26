package com.manutencao.colheitadeiras.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manutencao.colheitadeiras.data.model.MovimentacaoEstoque
import com.manutencao.colheitadeiras.data.repository.MovimentacaoEstoqueRepository
import kotlinx.coroutines.launch

class MovimentacaoEstoqueViewModel(private val repository: MovimentacaoEstoqueRepository) : ViewModel() {
    
    val allMovimentacoes: LiveData<List<MovimentacaoEstoque>> = repository.allMovimentacoes
    
    fun getByItem(itemId: Int): LiveData<List<MovimentacaoEstoque>> {
        return repository.getByItem(itemId)
    }
    
    fun insert(movimentacao: MovimentacaoEstoque) = viewModelScope.launch {
        repository.insert(movimentacao)
    }
    
    fun update(movimentacao: MovimentacaoEstoque) = viewModelScope.launch {
        repository.update(movimentacao)
    }
    
    fun delete(movimentacao: MovimentacaoEstoque) = viewModelScope.launch {
        repository.delete(movimentacao)
    }
}
