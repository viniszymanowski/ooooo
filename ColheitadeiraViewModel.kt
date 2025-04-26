package com.manutencao.colheitadeiras.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manutencao.colheitadeiras.data.model.Colheitadeira
import com.manutencao.colheitadeiras.data.repository.ColheitadeiraRepository
import kotlinx.coroutines.launch

class ColheitadeiraViewModel(private val repository: ColheitadeiraRepository) : ViewModel() {
    
    val allColheitadeiras: LiveData<List<Colheitadeira>> = repository.allColheitadeiras
    
    fun insert(colheitadeira: Colheitadeira) = viewModelScope.launch {
        repository.insert(colheitadeira)
    }
    
    fun update(colheitadeira: Colheitadeira) = viewModelScope.launch {
        repository.update(colheitadeira)
    }
    
    fun delete(colheitadeira: Colheitadeira) = viewModelScope.launch {
        repository.delete(colheitadeira)
    }
}
