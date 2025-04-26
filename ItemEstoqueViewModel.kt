package com.manutencao.colheitadeiras.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manutencao.colheitadeiras.data.model.ItemEstoque
import com.manutencao.colheitadeiras.data.repository.ItemEstoqueRepository
import kotlinx.coroutines.launch

class ItemEstoqueViewModel(private val repository: ItemEstoqueRepository) : ViewModel() {
    
    val allItens: LiveData<List<ItemEstoque>> = repository.allItens
    
    fun getByCategoria(categoria: String): LiveData<List<ItemEstoque>> {
        return repository.getByCategoria(categoria)
    }
    
    fun getItemsBaixoEstoque(): LiveData<List<ItemEstoque>> {
        return repository.getItemsBaixoEstoque()
    }
    
    fun insert(item: ItemEstoque) = viewModelScope.launch {
        repository.insert(item)
    }
    
    fun update(item: ItemEstoque) = viewModelScope.launch {
        repository.update(item)
    }
    
    fun delete(item: ItemEstoque) = viewModelScope.launch {
        repository.delete(item)
    }
}
