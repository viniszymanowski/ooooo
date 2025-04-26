package com.manutencao.colheitadeiras.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manutencao.colheitadeiras.data.model.RegistroHorimetro
import com.manutencao.colheitadeiras.data.repository.RegistroHorimetroRepository
import kotlinx.coroutines.launch

class RegistroHorimetroViewModel(private val repository: RegistroHorimetroRepository) : ViewModel() {
    
    val allRegistros: LiveData<List<RegistroHorimetro>> = repository.allRegistros
    
    fun getByColheitadeira(colheitadeiraId: Int): LiveData<List<RegistroHorimetro>> {
        return repository.getByColheitadeira(colheitadeiraId)
    }
    
    suspend fun getLatestByColheitadeira(colheitadeiraId: Int): RegistroHorimetro? {
        return repository.getLatestByColheitadeira(colheitadeiraId)
    }
    
    fun insert(registro: RegistroHorimetro) = viewModelScope.launch {
        repository.insert(registro)
    }
    
    fun update(registro: RegistroHorimetro) = viewModelScope.launch {
        repository.update(registro)
    }
    
    fun delete(registro: RegistroHorimetro) = viewModelScope.launch {
        repository.delete(registro)
    }
}
