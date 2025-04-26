package com.manutencao.colheitadeiras.data.repository

import androidx.lifecycle.LiveData
import com.manutencao.colheitadeiras.data.dao.UsuarioDao
import com.manutencao.colheitadeiras.data.model.Usuario

class UsuarioRepository(private val usuarioDao: UsuarioDao) {
    val allUsuarios: LiveData<List<Usuario>> = usuarioDao.getAll()
    
    suspend fun getByUsername(username: String): Usuario? {
        return usuarioDao.getByUsername(username)
    }
    
    suspend fun insert(usuario: Usuario): Long {
        return usuarioDao.insert(usuario)
    }
    
    suspend fun update(usuario: Usuario) {
        usuarioDao.update(usuario)
    }
    
    suspend fun delete(usuario: Usuario) {
        usuarioDao.delete(usuario)
    }
    
    suspend fun authenticate(username: String, password: String): Usuario? {
        val usuario = usuarioDao.getByUsername(username)
        return if (usuario != null && usuario.password == password) {
            usuario
        } else {
            null
        }
    }
}
