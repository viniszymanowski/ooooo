package com.manutencao.colheitadeiras.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.manutencao.colheitadeiras.R
import com.manutencao.colheitadeiras.data.database.AppDatabase
import com.manutencao.colheitadeiras.data.repository.ColheitadeiraRepository
import com.manutencao.colheitadeiras.data.repository.ManutencaoPreventivaRepository
import com.manutencao.colheitadeiras.ui.viewmodel.ColheitadeiraViewModel
import com.manutencao.colheitadeiras.ui.viewmodel.ColheitadeiraViewModelFactory
import com.manutencao.colheitadeiras.ui.viewmodel.ManutencaoPreventivaViewModel
import com.manutencao.colheitadeiras.ui.viewmodel.ManutencaoPreventivaViewModelFactory

class DashboardFragment : Fragment() {
    
    private lateinit var colheitadeiraViewModel: ColheitadeiraViewModel
    private lateinit var manutencaoPreventivaViewModel: ManutencaoPreventivaViewModel
    
    private lateinit var colheitadeirasCountTextView: TextView
    private lateinit var manutencoesCountTextView: TextView
    private lateinit var proximasManutencoesRecyclerView: RecyclerView
    private lateinit var alertasRecyclerView: RecyclerView
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)
        
        // Inicializar componentes da UI
        colheitadeirasCountTextView = view.findViewById(R.id.colheitadeirasCountTextView)
        manutencoesCountTextView = view.findViewById(R.id.manutencoesCountTextView)
        proximasManutencoesRecyclerView = view.findViewById(R.id.proximasManutencoesRecyclerView)
        alertasRecyclerView = view.findViewById(R.id.alertasRecyclerView)
        
        // Configurar RecyclerViews
        proximasManutencoesRecyclerView.layoutManager = LinearLayoutManager(context)
        alertasRecyclerView.layoutManager = LinearLayoutManager(context)
        
        return view
    }
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        
        // Inicializar ViewModels
        val database = AppDatabase.getDatabase(requireActivity().application)
        
        val colheitadeiraRepository = ColheitadeiraRepository(database.colheitadeiraDao())
        val colheitadeiraFactory = ColheitadeiraViewModelFactory(colheitadeiraRepository)
        colheitadeiraViewModel = ViewModelProvider(this, colheitadeiraFactory).get(ColheitadeiraViewModel::class.java)
        
        val manutencaoPreventivaRepository = ManutencaoPreventivaRepository(database.manutencaoPreventivaDao())
        val manutencaoPreventivaFactory = ManutencaoPreventivaViewModelFactory(manutencaoPreventivaRepository)
        manutencaoPreventivaViewModel = ViewModelProvider(this, manutencaoPreventivaFactory).get(ManutencaoPreventivaViewModel::class.java)
        
        // Observar dados
        colheitadeiraViewModel.allColheitadeiras.observe(viewLifecycleOwner) { colheitadeiras ->
            colheitadeirasCountTextView.text = colheitadeiras.size.toString()
        }
        
        manutencaoPreventivaViewModel.allManutencoes.observe(viewLifecycleOwner) { manutencoes ->
            manutencoesCountTextView.text = manutencoes.size.toString()
            
            // Atualizar RecyclerViews com adaptadores
            // proximasManutencoesRecyclerView.adapter = ManutencaoPreventivaAdapter(manutencoes.filter { it.status == "Pendente" })
        }
    }
}
