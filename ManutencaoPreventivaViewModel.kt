package com.manutencao.colheitadeiras.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manutencao.colheitadeiras.data.model.ManutencaoPreventiva
import com.manutencao.colheitadeiras.data.repository.ManutencaoPreventivaRepository
import kotlinx.coroutines.launch

class ManutencaoPreventivaViewModel(private val repository: ManutencaoPreventivaRepository) : ViewModel() {
    
    val allManutencoes: LiveData<List<ManutencaoPreventiva>> = repository.allManutencoes
    
    fun getByColheitadeira(colheitadeiraId: Int): LiveData<List<ManutencaoPreventiva>> {
        return repository.getByColheitadeira(colheitadeiraId)
    }
    
    fun getByStatus(status: String): LiveData<List<ManutencaoPreventiva>> {
        return repository.getByStatus(status)
    }
    
    fun insert(manutencao: ManutencaoPreventiva) = viewModelScope.launch {
        repository.insert(manutencao)
    }
    
    fun update(manutencao: ManutencaoPreventiva) = viewModelScope.launch {
        repository.update(manutencao)
    }
    
    fun delete(manutencao: ManutencaoPreventiva) = viewModelScope.launch {
        repository.delete(manutencao)
    }
}
