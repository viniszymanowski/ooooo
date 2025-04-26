package com.manutencao.colheitadeiras.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.manutencao.colheitadeiras.data.repository.ManutencaoPreventivaRepository

class ManutencaoPreventivaViewModelFactory(private val repository: ManutencaoPreventivaRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ManutencaoPreventivaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ManutencaoPreventivaViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
