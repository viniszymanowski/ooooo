package com.manutencao.colheitadeiras.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.manutencao.colheitadeiras.data.repository.ColheitadeiraRepository

class ColheitadeiraViewModelFactory(private val repository: ColheitadeiraRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ColheitadeiraViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ColheitadeiraViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
