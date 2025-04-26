package com.manutencao.colheitadeiras.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios")
data class Usuario(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val username: String,
    val nome: String,
    val email: String,
    val password: String,
    val cargo: String = "Operador"
)

@Entity(tableName = "colheitadeiras")
data class Colheitadeira(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val modelo: String,
    val ano: Int,
    val numero_frota: String,
    val status: String
)

@Entity(tableName = "manutencoes_preventivas")
data class ManutencaoPreventiva(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val colheitadeira_id: Int,
    val descricao: String,
    val data_agendada: String,
    val horimetro_agendado: Int,
    val status: String,
    val data_realizacao: String? = null,
    val observacoes: String? = null
)

@Entity(tableName = "manutencoes_corretivas")
data class ManutencaoCorretiva(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val colheitadeira_id: Int,
    val descricao_falha: String,
    val data_inicio: String,
    val status: String,
    val data_conclusao: String? = null,
    val solucao: String? = null,
    val pecas_substituidas: String? = null
)

@Entity(tableName = "registros_horimetro")
data class RegistroHorimetro(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val colheitadeira_id: Int,
    val valor: Int,
    val data: String
)

@Entity(tableName = "trocas_oleo")
data class TrocaOleo(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val colheitadeira_id: Int,
    val data: String,
    val horimetro: Int,
    val tipo_oleo: String,
    val quantidade: Float,
    val proxima_troca: String
)

@Entity(tableName = "itens_estoque")
data class ItemEstoque(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nome: String,
    val codigo: String,
    val categoria: String,
    val quantidade: Float,
    val quantidade_minima: Float,
    val unidade: String,
    val localizacao: String
)

@Entity(tableName = "movimentacoes_estoque")
data class MovimentacaoEstoque(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val item_id: Int,
    val tipo: String,
    val quantidade: Float,
    val data: String,
    val observacao: String
)
