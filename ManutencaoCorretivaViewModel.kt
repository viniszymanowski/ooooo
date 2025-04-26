package com.manutencao.colheitadeiras.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manutencao.colheitadeiras.data.model.ManutencaoCorretiva
import com.manutencao.colheitadeiras.data.repository.ManutencaoCorretivaRepository
import kotlinx.coroutines.launch

class ManutencaoCorretivaViewModel(private val repository: ManutencaoCorretivaRepository) : ViewModel() {
    
    val allManutencoes: LiveData<List<ManutencaoCorretiva>> = repository.allManutencoes
    
    fun getByColheitadeira(colheitadeiraId: Int): LiveData<List<ManutencaoCorretiva>> {
        return repository.getByColheitadeira(colheitadeiraId)
    }
    
    fun getByStatus(status: String): LiveData<List<ManutencaoCorretiva>> {
        return repository.getByStatus(status)
    }
    
    fun insert(manutencao: ManutencaoCorretiva) = viewModelScope.launch {
        repository.insert(manutencao)
    }
    
    fun update(manutencao: ManutencaoCorretiva) = viewModelScope.launch {
        repository.update(manutencao)
    }
    
    fun delete(manutencao: ManutencaoCorretiva) = viewModelScope.launch {
        repository.delete(manutencao)
    }
}
