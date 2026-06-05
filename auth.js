// ============================================================
//  auth.js — Verificação de login
//  Conecta com a API Spring Boot em /api/auth/login
// ============================================================

const API_BASE = 'http://localhost:8080'; // troque quando hospedar

// ── Máscaras e utilitários ───────────────────────────────────

function maskCPF(el) {
  let v = el.value.replace(/\D/g, '').slice(0, 11);
  if (v.length > 9)      v = v.replace(/(\d{3})(\d{3})(\d{3})(\d{0,2})/, '$1.$2.$3-$4');
  else if (v.length > 6) v = v.replace(/(\d{3})(\d{3})(\d{0,3})/, '$1.$2.$3');
  else if (v.length > 3) v = v.replace(/(\d{3})(\d{0,3})/, '$1.$2');
  el.value = v;
}

function togglePass() {
  const inp  = document.getElementById('senha');
  const icon = document.getElementById('eye-icon');
  const show = inp.type === 'password';
  inp.type       = show ? 'text' : 'password';
  icon.className = show ? 'ti ti-eye-off' : 'ti ti-eye';
}

let currentRole = 'adotante';
function setRole(el, role) {
  currentRole = role;
  document.querySelectorAll('.role-tab').forEach(t => {
    t.classList.remove('active');
    t.setAttribute('aria-selected', 'false');
  });
  el.classList.add('active');
  el.setAttribute('aria-selected', 'true');
}

// ── Validação local (antes de chamar a API) ──────────────────

function validarCampos(cpf, senha) {
  let valido = true;

  // limpa erros anteriores
  ['cpf', 'senha'].forEach(id => {
    document.getElementById('err-' + id).classList.remove('show');
    document.getElementById(id).classList.remove('error');
  });

  if (cpf.length !== 11) {
    mostrarErro('cpf', 'Informe um CPF válido (11 dígitos).');
    valido = false;
  }

  if (senha.length < 4) {
    mostrarErro('senha', 'Senha deve ter pelo menos 4 caracteres.');
    valido = false;
  }

  return valido;
}

function mostrarErro(campo, msg) {
  const errEl = document.getElementById('err-' + campo);
  errEl.textContent = msg;
  errEl.classList.add('show');
  document.getElementById(campo).classList.add('error');
}

// ── Estado do botão ──────────────────────────────────────────

function setBtnLoading(loading) {
  const btn     = document.getElementById('btn-login');
  const btnText = document.getElementById('btn-text');
  const spinner = document.getElementById('spinner');
  btn.disabled          = loading;
  btnText.textContent   = loading ? 'Entrando...' : 'Entrar';
  spinner.style.display = loading ? 'block' : 'none';
}

// ── Chamada à API ─────────────────────────────────────────────

async function handleLogin() {
  const cpf   = document.getElementById('cpf').value.replace(/\D/g, '');
  const senha = document.getElementById('senha').value.trim();

  if (!validarCampos(cpf, senha)) return;

  setBtnLoading(true);

  try {
    const response = await fetch(`${API_BASE}/api/auth/login`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ cpf, senha, perfil: currentRole })
    });

    const data = await response.json();

    if (response.ok) {
      // Salva o token e dados do usuário para usar nas outras telas
      localStorage.setItem('token',  data.token);
      localStorage.setItem('nome',   data.nome);
      localStorage.setItem('perfil', data.perfil);

      mostrarSucesso('Login realizado! Redirecionando...');

      // Redireciona conforme o perfil
      setTimeout(() => {
        window.location.href = data.perfil === 'admin'
          ? 'dashboard-admin.html'
          : 'dashboard.html';
      }, 1200);

    } else {
      // Erro vindo do servidor (ex: CPF ou senha incorretos)
      tratarErroServidor(response.status, data.mensagem);
    }

  } catch (erro) {
    // Sem conexão com o servidor
    mostrarErroGlobal('Não foi possível conectar ao servidor. Verifique se o backend está rodando.');
    console.error('Erro de conexão:', erro);
  } finally {
    setBtnLoading(false);
  }
}

// ── Tratamento de erros do servidor ─────────────────────────

function tratarErroServidor(status, mensagemServidor) {
  switch (status) {
    case 401:
      mostrarErroGlobal('CPF ou senha incorretos.');
      break;
    case 403:
      mostrarErroGlobal('Acesso negado para este perfil.');
      break;
    case 404:
      mostrarErroGlobal('Usuário não encontrado.');
      break;
    case 500:
      mostrarErroGlobal('Erro interno no servidor. Tente novamente.');
      break;
    default:
      mostrarErroGlobal(mensagemServidor || 'Erro ao realizar login.');
  }
}

function mostrarErroGlobal(msg) {
  const el = document.getElementById('erro-global');
  if (!el) {
    // cria o elemento se não existir no HTML
    const div = document.createElement('div');
    div.id = 'erro-global';
    div.style.cssText = `
      background: #FCEBEB; border: 1px solid #F09595;
      border-radius: 8px; padding: 10px 14px;
      font-size: 13px; color: #A32D2D;
      display: flex; align-items: center; gap: 8px;
      margin-top: 12px; font-weight: 500;
    `;
    div.innerHTML = `<i class="ti ti-alert-circle" style="font-size:18px"></i><span id="erro-global-text"></span>`;
    document.getElementById('btn-login').insertAdjacentElement('afterend', div);
  }
  document.getElementById('erro-global-text').textContent = msg;
  document.getElementById('erro-global').style.display = 'flex';
}

function mostrarSucesso(msg) {
  // esconde erro global se existir
  const errEl = document.getElementById('erro-global');
  if (errEl) errEl.style.display = 'none';

  const el = document.getElementById('success-msg');
  el.querySelector('span') 
    ? el.querySelector('span').textContent = msg 
    : el.textContent = msg;
  el.classList.add('show');
}

// ── Verificar se já está logado (evita voltar ao login) ──────

function verificarSessao() {
  const token = localStorage.getItem('token');
  if (token) {
    const perfil = localStorage.getItem('perfil');
    window.location.href = perfil === 'admin'
      ? 'dashboard-admin.html'
      : 'dashboard.html';
  }
}

// ── Logout (use nas outras páginas) ─────────────────────────

function logout() {
  localStorage.removeItem('token');
  localStorage.removeItem('nome');
  localStorage.removeItem('perfil');
  window.location.href = 'login.html';
}

// ── Proteger páginas (coloque nas páginas que exigem login) ──
// Chame checkAuth() no topo de dashboard.html, animais.html, etc.

function checkAuth() {
  const token = localStorage.getItem('token');
  if (!token) {
    window.location.href = 'login.html';
  }
}

// ── Enter para submeter ──────────────────────────────────────

document.addEventListener('keydown', e => {
  if (e.key === 'Enter') handleLogin();
});

// Verifica sessão ao carregar a página de login
verificarSessao();
