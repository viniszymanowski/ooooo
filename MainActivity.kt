package com.manutencao.colheitadeiras.ui.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.manutencao.colheitadeiras.R
import com.manutencao.colheitadeiras.data.database.AppDatabase
import com.manutencao.colheitadeiras.data.repository.UsuarioRepository
import com.manutencao.colheitadeiras.ui.viewmodel.LoginViewModel
import com.manutencao.colheitadeiras.ui.viewmodel.LoginViewModelFactory

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var fab: FloatingActionButton
    private lateinit var viewModel: LoginViewModel
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        // Configurar a toolbar
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        
        // Inicializar componentes da UI
        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.nav_view)
        fab = findViewById(R.id.fab)
        
        // Configurar o drawer toggle
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.app_name, R.string.app_name
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        
        // Configurar o listener do navigation view
        navigationView.setNavigationItemSelectedListener(this)
        
        // Inicializar ViewModel
        val database = AppDatabase.getDatabase(application)
        val repository = UsuarioRepository(database.usuarioDao())
        val factory = LoginViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(LoginViewModel::class.java)
        
        // Configurar o FAB
        fab.setOnClickListener {
            // Ação do FAB depende do fragmento atual
            // Por exemplo, adicionar nova colheitadeira, nova manutenção, etc.
        }
        
        // Carregar o fragmento inicial (Dashboard)
        if (savedInstanceState == null) {
            // loadDashboardFragment()
            navigationView.setCheckedItem(R.id.nav_dashboard)
        }
    }
    
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_dashboard -> {
                // loadDashboardFragment()
            }
            R.id.nav_colheitadeiras -> {
                // loadColheitadeirasFragment()
            }
            R.id.nav_manutencoes_preventivas -> {
                // loadManutencoesPreventivas()
            }
            R.id.nav_manutencoes_corretivas -> {
                // loadManutencoesCorretivas()
            }
            R.id.nav_trocas_oleo -> {
                // loadTrocasOleo()
            }
            R.id.nav_horimetro -> {
                // loadHorimetro()
            }
            R.id.nav_estoque -> {
                // loadEstoque()
            }
            R.id.nav_relatorios -> {
                // loadRelatorios()
            }
            R.id.nav_logout -> {
                viewModel.logout()
                finish()
                // startActivity(Intent(this, LoginActivity::class.java))
            }
        }
        
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
    
    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
