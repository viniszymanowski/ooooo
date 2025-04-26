package com.manutencao.colheitadeiras.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.manutencao.colheitadeiras.R
import com.manutencao.colheitadeiras.data.model.ManutencaoPreventiva
import java.text.SimpleDateFormat
import java.util.Locale

class ManutencaoPreventivaAdapter(
    private val manutencoes: List<ManutencaoPreventiva>,
    private val listener: OnItemClickListener? = null
) : RecyclerView.Adapter<ManutencaoPreventivaAdapter.ManutencaoViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(manutencao: ManutencaoPreventiva)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ManutencaoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_manutencao_preventiva, parent, false)
        return ManutencaoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ManutencaoViewHolder, position: Int) {
        val manutencao = manutencoes[position]
        holder.bind(manutencao)
    }

    override fun getItemCount(): Int = manutencoes.size

    inner class ManutencaoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val descricaoTextView: TextView = itemView.findViewById(R.id.descricaoTextView)
        private val dataTextView: TextView = itemView.findViewById(R.id.dataTextView)
        private val statusTextView: TextView = itemView.findViewById(R.id.statusTextView)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener?.onItemClick(manutencoes[position])
                }
            }
        }

        fun bind(manutencao: ManutencaoPreventiva) {
            descricaoTextView.text = manutencao.descricao
            
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR"))
            val dataFormatada = try {
                dateFormat.format(dateFormat.parse(manutencao.data_agendada)!!)
            } catch (e: Exception) {
                manutencao.data_agendada
            }
            
            dataTextView.text = dataFormatada
            statusTextView.text = manutencao.status
            
            // Definir cor do status
            when (manutencao.status) {
                "Pendente" -> statusTextView.setTextColor(itemView.context.getColor(R.color.warning))
                "Realizada" -> statusTextView.setTextColor(itemView.context.getColor(R.color.success))
                else -> statusTextView.setTextColor(itemView.context.getColor(R.color.text_secondary))
            }
        }
    }
}
