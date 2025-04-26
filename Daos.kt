package com.manutencao.colheitadeiras.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.manutencao.colheitadeiras.data.model.*

@Dao
interface UsuarioDao {
    @Query("SELECT * FROM usuarios")
    fun getAll(): LiveData<List<Usuario>>
    
    @Query("SELECT * FROM usuarios WHERE username = :username LIMIT 1")
    suspend fun getByUsername(username: String): Usuario?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(usuario: Usuario): Long
    
    @Update
    suspend fun update(usuario: Usuario)
    
    @Delete
    suspend fun delete(usuario: Usuario)
}

@Dao
interface ColheitadeiraDao {
    @Query("SELECT * FROM colheitadeiras")
    fun getAll(): LiveData<List<Colheitadeira>>
    
    @Query("SELECT * FROM colheitadeiras WHERE id = :id")
    suspend fun getById(id: Int): Colheitadeira?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(colheitadeira: Colheitadeira): Long
    
    @Update
    suspend fun update(colheitadeira: Colheitadeira)
    
    @Delete
    suspend fun delete(colheitadeira: Colheitadeira)
}

@Dao
interface ManutencaoPreventivaDao {
    @Query("SELECT * FROM manutencoes_preventivas")
    fun getAll(): LiveData<List<ManutencaoPreventiva>>
    
    @Query("SELECT * FROM manutencoes_preventivas WHERE colheitadeira_id = :colheitadeiraId")
    fun getByColheitadeira(colheitadeiraId: Int): LiveData<List<ManutencaoPreventiva>>
    
    @Query("SELECT * FROM manutencoes_preventivas WHERE status = :status")
    fun getByStatus(status: String): LiveData<List<ManutencaoPreventiva>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(manutencao: ManutencaoPreventiva): Long
    
    @Update
    suspend fun update(manutencao: ManutencaoPreventiva)
    
    @Delete
    suspend fun delete(manutencao: ManutencaoPreventiva)
}

@Dao
interface ManutencaoCorretivaDao {
    @Query("SELECT * FROM manutencoes_corretivas")
    fun getAll(): LiveData<List<ManutencaoCorretiva>>
    
    @Query("SELECT * FROM manutencoes_corretivas WHERE colheitadeira_id = :colheitadeiraId")
    fun getByColheitadeira(colheitadeiraId: Int): LiveData<List<ManutencaoCorretiva>>
    
    @Query("SELECT * FROM manutencoes_corretivas WHERE status = :status")
    fun getByStatus(status: String): LiveData<List<ManutencaoCorretiva>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(manutencao: ManutencaoCorretiva): Long
    
    @Update
    suspend fun update(manutencao: ManutencaoCorretiva)
    
    @Delete
    suspend fun delete(manutencao: ManutencaoCorretiva)
}

@Dao
interface RegistroHorimetroDao {
    @Query("SELECT * FROM registros_horimetro ORDER BY data DESC")
    fun getAll(): LiveData<List<RegistroHorimetro>>
    
    @Query("SELECT * FROM registros_horimetro WHERE colheitadeira_id = :colheitadeiraId ORDER BY data DESC")
    fun getByColheitadeira(colheitadeiraId: Int): LiveData<List<RegistroHorimetro>>
    
    @Query("SELECT * FROM registros_horimetro WHERE colheitadeira_id = :colheitadeiraId ORDER BY data DESC LIMIT 1")
    suspend fun getLatestByColheitadeira(colheitadeiraId: Int): RegistroHorimetro?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(registro: RegistroHorimetro): Long
    
    @Update
    suspend fun update(registro: RegistroHorimetro)
    
    @Delete
    suspend fun delete(registro: RegistroHorimetro)
}

@Dao
interface TrocaOleoDao {
    @Query("SELECT * FROM trocas_oleo ORDER BY data DESC")
    fun getAll(): LiveData<List<TrocaOleo>>
    
    @Query("SELECT * FROM trocas_oleo WHERE colheitadeira_id = :colheitadeiraId ORDER BY data DESC")
    fun getByColheitadeira(colheitadeiraId: Int): LiveData<List<TrocaOleo>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(troca: TrocaOleo): Long
    
    @Update
    suspend fun update(troca: TrocaOleo)
    
    @Delete
    suspend fun delete(troca: TrocaOleo)
}

@Dao
interface ItemEstoqueDao {
    @Query("SELECT * FROM itens_estoque")
    fun getAll(): LiveData<List<ItemEstoque>>
    
    @Query("SELECT * FROM itens_estoque WHERE categoria = :categoria")
    fun getByCategoria(categoria: String): LiveData<List<ItemEstoque>>
    
    @Query("SELECT * FROM itens_estoque WHERE quantidade <= quantidade_minima")
    fun getItemsBaixoEstoque(): LiveData<List<ItemEstoque>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: ItemEstoque): Long
    
    @Update
    suspend fun update(item: ItemEstoque)
    
    @Delete
    suspend fun delete(item: ItemEstoque)
}

@Dao
interface MovimentacaoEstoqueDao {
    @Query("SELECT * FROM movimentacoes_estoque ORDER BY data DESC")
    fun getAll(): LiveData<List<MovimentacaoEstoque>>
    
    @Query("SELECT * FROM movimentacoes_estoque WHERE item_id = :itemId ORDER BY data DESC")
    fun getByItem(itemId: Int): LiveData<List<MovimentacaoEstoque>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movimentacao: MovimentacaoEstoque): Long
    
    @Update
    suspend fun update(movimentacao: MovimentacaoEstoque)
    
    @Delete
    suspend fun delete(movimentacao: MovimentacaoEstoque)
}
