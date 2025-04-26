package com.manutencao.colheitadeiras.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.manutencao.colheitadeiras.data.dao.*
import com.manutencao.colheitadeiras.data.model.*

@Database(
    entities = [
        Usuario::class,
        Colheitadeira::class,
        ManutencaoPreventiva::class,
        ManutencaoCorretiva::class,
        RegistroHorimetro::class,
        TrocaOleo::class,
        ItemEstoque::class,
        MovimentacaoEstoque::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun usuarioDao(): UsuarioDao
    abstract fun colheitadeiraDao(): ColheitadeiraDao
    abstract fun manutencaoPreventivaDao(): ManutencaoPreventivaDao
    abstract fun manutencaoCorretivaDao(): ManutencaoCorretivaDao
    abstract fun registroHorimetroDao(): RegistroHorimetroDao
    abstract fun trocaOleoDao(): TrocaOleoDao
    abstract fun itemEstoqueDao(): ItemEstoqueDao
    abstract fun movimentacaoEstoqueDao(): MovimentacaoEstoqueDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "manutencao_colheitadeiras_database"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
