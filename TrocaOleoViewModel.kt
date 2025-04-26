package com.manutencao.colheitadeiras.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manutencao.colheitadeiras.data.model.TrocaOleo
import com.manutencao.colheitadeiras.data.repository.TrocaOleoRepository
import kotlinx.coroutines.launch

class TrocaOleoViewModel(private val repository: TrocaOleoRepository) : ViewModel() {
    
    val allTrocas: LiveData<List<TrocaOleo>> = repository.allTrocas
    
    fun getByColheitadeira(colheitadeiraId: Int): LiveData<List<TrocaOleo>> {
        return repository.getByColheitadeira(colheitadeiraId)
    }
    
    fun insert(troca: TrocaOleo) = viewModelScope.launch {
        repository.insert(troca)
    }
    
    fun update(troca: TrocaOleo) = viewModelScope.launch {
        repository.update(troca)
    }
    
    fun delete(troca: TrocaOleo) = viewModelScope.launch {
        repository.delete(troca)
    }
}
