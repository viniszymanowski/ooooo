from flask import Flask, render_template, request, redirect, url_for, flash, session
import sqlite3
import os
from datetime import datetime
from functools import wraps

app = Flask(__name__)
app.secret_key = 'sistema_manutencao_secret_key'

# Configuração do banco de dados
DATABASE = 'sistema_manutencao.db'

def get_db_connection():
    conn = sqlite3.connect(DATABASE)
    conn.row_factory = sqlite3.Row
    return conn

def init_db():
    if not os.path.exists(DATABASE):
        conn = get_db_connection()
        cursor = conn.cursor()
        
        # Criar tabelas
        cursor.execute('''
        CREATE TABLE IF NOT EXISTS usuarios (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            username TEXT UNIQUE NOT NULL,
            password TEXT NOT NULL,
            nome TEXT NOT NULL,
            tipo TEXT NOT NULL
        )
        ''')
        
        cursor.execute('''
        CREATE TABLE IF NOT EXISTS colheitadeiras (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            modelo TEXT NOT NULL,
            numero_serie TEXT UNIQUE NOT NULL,
            ano INTEGER NOT NULL,
            ultima_manutencao TEXT,
            horimetro_atual REAL DEFAULT 0,
            status TEXT DEFAULT 'Operacional'
        )
        ''')
        
        cursor.execute('''
        CREATE TABLE IF NOT EXISTS manutencoes_preventivas (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            colheitadeira_id INTEGER NOT NULL,
            descricao TEXT NOT NULL,
            data_agendada TEXT NOT NULL,
            data_realizada TEXT,
            horimetro REAL,
            tecnico TEXT,
            status TEXT DEFAULT 'Pendente',
            observacoes TEXT,
            FOREIGN KEY (colheitadeira_id) REFERENCES colheitadeiras (id)
        )
        ''')
        
        cursor.execute('''
        CREATE TABLE IF NOT EXISTS manutencoes_corretivas (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            colheitadeira_id INTEGER NOT NULL,
            descricao TEXT NOT NULL,
            data_abertura TEXT NOT NULL,
            data_conclusao TEXT,
            horimetro REAL,
            tecnico TEXT,
            status TEXT DEFAULT 'Aberta',
            solucao TEXT,
            FOREIGN KEY (colheitadeira_id) REFERENCES colheitadeiras (id)
        )
        ''')
        
        cursor.execute('''
        CREATE TABLE IF NOT EXISTS trocas_oleo (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            colheitadeira_id INTEGER NOT NULL,
            data TEXT NOT NULL,
            horimetro REAL NOT NULL,
            tipo_oleo TEXT NOT NULL,
            quantidade REAL NOT NULL,
            proxima_troca REAL NOT NULL,
            tecnico TEXT,
            observacoes TEXT,
            FOREIGN KEY (colheitadeira_id) REFERENCES colheitadeiras (id)
        )
        ''')
        
        cursor.execute('''
        CREATE TABLE IF NOT EXISTS registros_horimetro (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            colheitadeira_id INTEGER NOT NULL,
            data TEXT NOT NULL,
            horimetro REAL NOT NULL,
            operador TEXT,
            observacoes TEXT,
            FOREIGN KEY (colheitadeira_id) REFERENCES colheitadeiras (id)
        )
        ''')
        
        cursor.execute('''
        CREATE TABLE IF NOT EXISTS estoque (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            nome TEXT NOT NULL,
            categoria TEXT NOT NULL,
            quantidade INTEGER NOT NULL,
            unidade TEXT NOT NULL,
            preco REAL,
            estoque_minimo INTEGER DEFAULT 5,
            fornecedor TEXT,
            observacoes TEXT
        )
        ''')
        
        # Inserir dados de exemplo
        # Usuários
        cursor.execute("INSERT INTO usuarios (username, password, nome, tipo) VALUES (?, ?, ?, ?)",
                      ('admin', 'admin123', 'Administrador', 'admin'))
        cursor.execute("INSERT INTO usuarios (username, password, nome, tipo) VALUES (?, ?, ?, ?)",
                      ('tecnico', 'tecnico123', 'Técnico de Manutenção', 'tecnico'))
        cursor.execute("INSERT INTO usuarios (username, password, nome, tipo) VALUES (?, ?, ?, ?)",
                      ('operador', 'operador123', 'Operador de Campo', 'operador'))
        
        # Colheitadeiras
        cursor.execute("INSERT INTO colheitadeiras (modelo, numero_serie, ano, ultima_manutencao, horimetro_atual, status) VALUES (?, ?, ?, ?, ?, ?)",
                      ('John Deere S700', 'JD7001234', 2022, '2025-03-15', 350.5, 'Operacional'))
        cursor.execute("INSERT INTO colheitadeiras (modelo, numero_serie, ano, ultima_manutencao, horimetro_atual, status) VALUES (?, ?, ?, ?, ?, ?)",
                      ('John Deere S600', 'JD6005678', 2020, '2025-02-20', 1200.8, 'Operacional'))
        cursor.execute("INSERT INTO colheitadeiras (modelo, numero_serie, ano, ultima_manutencao, horimetro_atual, status) VALUES (?, ?, ?, ?, ?, ?)",
                      ('John Deere T670', 'JDT670123', 2021, '2025-04-01', 800.2, 'Em Manutenção'))
        cursor.execute("INSERT INTO colheitadeiras (modelo, numero_serie, ano, ultima_manutencao, horimetro_atual, status) VALUES (?, ?, ?, ?, ?, ?)",
                      ('John Deere S770', 'JDS770456', 2023, '2025-03-25', 150.0, 'Operacional'))
        
        # Manutenções Preventivas
        cursor.execute("INSERT INTO manutencoes_preventivas (colheitadeira_id, descricao, data_agendada, data_realizada, horimetro, tecnico, status, observacoes) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                      (1, 'Troca de filtros', '2025-05-15', None, 400, 'João Silva', 'Pendente', 'Trocar todos os filtros de ar e combustível'))
        cursor.execute("INSERT INTO manutencoes_preventivas (colheitadeira_id, descricao, data_agendada, data_realizada, horimetro, tecnico, status, observacoes) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                      (2, 'Revisão geral', '2025-04-20', '2025-04-20', 1250, 'Carlos Santos', 'Realizada', 'Revisão completa realizada conforme manual'))
        cursor.execute("INSERT INTO manutencoes_preventivas (colheitadeira_id, descricao, data_agendada, data_realizada, horimetro, tecnico, status, observacoes) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                      (3, 'Calibração de sensores', '2025-04-30', None, 850, 'Pedro Oliveira', 'Pendente', 'Calibrar todos os sensores de colheita'))
        
        # Manutenções Corretivas
        cursor.execute("INSERT INTO manutencoes_corretivas (colheitadeira_id, descricao, data_abertura, data_conclusao, horimetro, tecnico, status, solucao) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                      (3, 'Falha no sistema hidráulico', '2025-04-01', None, 800, 'Pedro Oliveira', 'Em Andamento', 'Substituição de mangueiras e válvulas'))
        cursor.execute("INSERT INTO manutencoes_corretivas (colheitadeira_id, descricao, data_abertura, data_conclusao, horimetro, tecnico, status, solucao) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                      (2, 'Problema no sistema elétrico', '2025-03-10', '2025-03-12', 1150, 'Carlos Santos', 'Concluída', 'Substituição de fusíveis e reparo na fiação'))
        
        # Trocas de Óleo
        cursor.execute("INSERT INTO trocas_oleo (colheitadeira_id, data, horimetro, tipo_oleo, quantidade, proxima_troca, tecnico, observacoes) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                      (1, '2025-03-15', 300, 'John Deere Plus-50 II', 30.5, 800, 'João Silva', 'Troca de óleo e filtro'))
        cursor.execute("INSERT INTO trocas_oleo (colheitadeira_id, data, horimetro, tipo_oleo, quantidade, proxima_troca, tecnico, observacoes) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                      (2, '2025-02-20', 1100, 'John Deere Plus-50 II', 30.5, 1600, 'Carlos Santos', 'Troca de óleo e filtro'))
        
        # Registros de Horímetro
        cursor.execute("INSERT INTO registros_horimetro (colheitadeira_id, data, horimetro, operador, observacoes) VALUES (?, ?, ?, ?, ?)",
                      (1, '2025-04-01', 350.5, 'José Pereira', 'Leitura regular'))
        cursor.execute("INSERT INTO registros_horimetro (colheitadeira_id, data, horimetro, operador, observacoes) VALUES (?, ?, ?, ?, ?)",
                      (2, '2025-04-01', 1200.8, 'Antônio Ferreira', 'Leitura regular'))
        cursor.execute("INSERT INTO registros_horimetro (colheitadeira_id, data, horimetro, operador, observacoes) VALUES (?, ?, ?, ?, ?)",
                      (3, '2025-04-01', 800.2, 'Roberto Almeida', 'Leitura antes da manutenção'))
        cursor.execute("INSERT INTO registros_horimetro (colheitadeira_id, data, horimetro, operador, observacoes) VALUES (?, ?, ?, ?, ?)",
                      (4, '2025-04-01', 150.0, 'Marcos Souza', 'Leitura regular'))
        
        # Estoque
        cursor.execute("INSERT INTO estoque (nome, categoria, quantidade, unidade, preco, estoque_minimo, fornecedor, observacoes) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                      ('Filtro de Ar', 'Filtros', 15, 'unidade', 120.50, 5, 'John Deere Brasil', 'Filtro original'))
        cursor.execute("INSERT INTO estoque (nome, categoria, quantidade, unidade, preco, estoque_minimo, fornecedor, observacoes) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                      ('Filtro de Combustível', 'Filtros', 8, 'unidade', 85.75, 5, 'John Deere Brasil', 'Filtro original'))
        cursor.execute("INSERT INTO estoque (nome, categoria, quantidade, unidade, preco, estoque_minimo, fornecedor, observacoes) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                      ('Óleo John Deere Plus-50 II', 'Lubrificantes', 200, 'litro', 45.90, 50, 'John Deere Brasil', 'Óleo de motor premium'))
        cursor.execute("INSERT INTO estoque (nome, categoria, quantidade, unidade, preco, estoque_minimo, fornecedor, observacoes) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                      ('Correia do Motor', 'Peças', 3, 'unidade', 350.00, 2, 'John Deere Brasil', 'Correia original'))
        cursor.execute("INSERT INTO estoque (nome, categoria, quantidade, unidade, preco, estoque_minimo, fornecedor, observacoes) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                      ('Sensor de Umidade', 'Eletrônicos', 2, 'unidade', 1200.00, 1, 'John Deere Brasil', 'Sensor de precisão'))
        
        conn.commit()
        conn.close()
        print("Banco de dados inicializado com sucesso!")

# Inicializar o banco de dados na primeira execução
init_db()

# Função decoradora para verificar se o usuário está logado
def login_required(f):
    @wraps(f)
    def decorated_function(*args, **kwargs):
        if 'user_id' not in session:
            flash('Por favor, faça login para acessar esta página.', 'danger')
            return redirect(url_for('login'))
        return f(*args, **kwargs)
    return decorated_function

# Rotas
@app.route('/')
def index():
    if 'user_id' in session:
        return redirect(url_for('dashboard'))
    return redirect(url_for('login'))

@app.route('/login', methods=['GET', 'POST'])
def login():
    if request.method == 'POST':
        username = request.form['username']
        password = request.form['password']
        
        conn = get_db_connection()
        user = conn.execute('SELECT * FROM usuarios WHERE username = ? AND password = ?',
                          (username, password)).fetchone()
        conn.close()
        
        if user:
            session['user_id'] = user['id']
            session['username'] = user['username']
            session['nome'] = user['nome']
            session['tipo'] = user['tipo']
            flash(f'Bem-vindo, {user["nome"]}!', 'success')
            return redirect(url_for('dashboard'))
        else:
            flash('Usuário ou senha inválidos.', 'danger')
    
    return render_template('login.html')

@app.route('/logout')
def logout():
    session.clear()
    flash('Você saiu do sistema.', 'info')
    return redirect(url_for('login'))

@app.route('/dashboard')
@login_required
def dashboard():
    conn = get_db_connection()
    
    # Contagem de colheitadeiras
    total_colheitadeiras = conn.execute('SELECT COUNT(*) as count FROM colheitadeiras').fetchone()['count']
    
    # Colheitadeiras operacionais
    colheitadeiras_operacionais = conn.execute('SELECT COUNT(*) as count FROM colheitadeiras WHERE status = "Operacional"').fetchone()['count']
    
    # Manutenções pendentes
    manutencoes_pendentes = conn.execute('SELECT COUNT(*) as count FROM manutencoes_preventivas WHERE status = "Pendente"').fetchone()['count']
    
    # Manutenções corretivas abertas
    manutencoes_corretivas = conn.execute('SELECT COUNT(*) as count FROM manutencoes_corretivas WHERE status != "Concluída"').fetchone()['count']
    
    # Itens com estoque baixo
    itens_estoque_baixo = conn.execute('SELECT COUNT(*) as count FROM estoque WHERE quantidade <= estoque_minimo').fetchone()['count']
    
    # Próximas manutenções
    proximas_manutencoes = conn.execute('''
        SELECT mp.id, mp.descricao, mp.data_agendada, c.modelo, c.numero_serie
        FROM manutencoes_preventivas mp
        JOIN colheitadeiras c ON mp.colheitadeira_id = c.id
        WHERE mp.status = "Pendente"
        ORDER BY mp.data_agendada
        LIMIT 5
    ''').fetchall()
    
    # Últimas trocas de óleo
    ultimas_trocas = conn.execute('''
        SELECT to.id, to.data, to.horimetro, to.proxima_troca, c.modelo, c.numero_serie, c.horimetro_atual
        FROM trocas_oleo to
        JOIN colheitadeiras c ON to.colheitadeira_id = c.id
        ORDER BY to.data DESC
        LIMIT 5
    ''').fetchall()
    
    conn.close()
    
    return render_template('dashboard.html', 
                          total_colheitadeiras=total_colheitadeiras,
                          colheitadeiras_operacionais=colheitadeiras_operacionais,
                          manutencoes_pendentes=manutencoes_pendentes,
                          manutencoes_corretivas=manutencoes_corretivas,
                          itens_estoque_baixo=itens_estoque_baixo,
                          proximas_manutencoes=proximas_manutencoes,
                          ultimas_trocas=ultimas_trocas)

@app.route('/colheitadeiras')
@login_required
def colheitadeiras():
    conn = get_db_connection()
    colheitadeiras = conn.execute('SELECT * FROM colheitadeiras ORDER BY modelo').fetchall()
    conn.close()
    return render_template('colheitadeiras.html', colheitadeiras=colheitadeiras)

@app.route('/colheitadeira/<int:id>')
@login_required
def colheitadeira_detalhes(id):
    conn = get_db_connection()
    colheitadeira = conn.execute('SELECT * FROM colheitadeiras WHERE id = ?', (id,)).fetchone()
    
    # Manutenções preventivas
    manutencoes_preventivas = conn.execute('''
        SELECT * FROM manutencoes_preventivas 
        WHERE colheitadeira_id = ? 
        ORDER BY data_agendada DESC
    ''', (id,)).fetchall()
    
    # Manutenções corretivas
    manutencoes_corretivas = conn.execute('''
        SELECT * FROM manutencoes_corretivas 
        WHERE colheitadeira_id = ? 
        ORDER BY data_abertura DESC
    ''', (id,)).fetchall()
    
    # Trocas de óleo
    trocas_oleo = conn.execute('''
        SELECT * FROM trocas_oleo 
        WHERE colheitadeira_id = ? 
        ORDER BY data DESC
    ''', (id,)).fetchall()
    
    # Registros de horímetro
    registros_horimetro = conn.execute('''
        SELECT * FROM registros_horimetro 
        WHERE colheitadeira_id = ? 
        ORDER BY data DESC
    ''', (id,)).fetchall()
    
    conn.close()
    
    if colheitadeira is None:
        flash('Colheitadeira não encontrada.', 'danger')
        return redirect(url_for('colheitadeiras'))
    
    return render_template('colheitadeira_detalhes.html', 
                          colheitadeira=colheitadeira,
                          manutencoes_preventivas=manutencoes_preventivas,
                          manutencoes_corretivas=manutencoes_corretivas,
                          trocas_oleo=trocas_oleo,
                          registros_horimetro=registros_horimetro)


(Content truncated due to size limit. Use line ranges to read in chunks)