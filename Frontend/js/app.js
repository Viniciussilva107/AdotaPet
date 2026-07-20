const state = {
  animais: [],
  adotantes: [],
  vacinas: [],
};


function toast(mensagem, isError = false) {
  const el = document.getElementById("toast");
  el.textContent = mensagem;
  el.classList.toggle("error", isError);
  el.classList.add("show");
  clearTimeout(toast._t);
  toast._t = setTimeout(() => el.classList.remove("show"), 3200);
}

function formToObject(form) {
  const data = new FormData(form);
  const obj = {};
  for (const [key, value] of data.entries()) {
    obj[key] = value;
  }

  form.querySelectorAll('input[type="checkbox"]').forEach((cb) => {
    obj[cb.name] = cb.checked;
  });
  return obj;
}

function showForm(id) {
  document.getElementById(id).hidden = false;
}
function hideForm(id) {
  const form = document.getElementById(id);
  form.hidden = true;
  form.reset();
  const hiddenId = form.querySelector('input[type="hidden"]');
  if (hiddenId) hiddenId.value = "";
}

document.querySelectorAll("[data-open-form]").forEach((btn) => {
  btn.addEventListener("click", () => showForm(btn.dataset.openForm));
});
document.querySelectorAll("[data-close-form]").forEach((btn) => {
  btn.addEventListener("click", () => hideForm(btn.dataset.closeForm));
});


const loaders = {
  animais: carregarAnimais,
  adotantes: carregarAdotantes,
  adocoes: carregarAdocoes,
  vacinas: carregarVacinas,
};

document.getElementById("tabs").addEventListener("click", (e) => {
  const btn = e.target.closest(".tab");
  if (!btn) return;

  document.querySelectorAll(".tab").forEach((t) => t.classList.remove("is-active"));
  btn.classList.add("is-active");

  const section = btn.dataset.section;
  document.querySelectorAll(".view").forEach((v) => v.classList.remove("is-active"));
  document.getElementById(`view-${section}`).classList.add("is-active");

  loaders[section]();
});


async function verificarApi() {
  const badge = document.getElementById("apiStatus");
  const texto = document.getElementById("apiStatusText");
  try {
    await api.get("/animais");
    badge.className = "api-status ok";
    texto.textContent = "API ligada";
  } catch (e) {
    badge.className = "api-status fail";
    texto.textContent = "API offline — inicie o backend em localhost:8080";
  }
}


async function carregarAnimais(termo) {
  const lista = document.getElementById("listaAnimais");
  try {
    const query = termo ? `?nome=${encodeURIComponent(termo)}` : "";
    state.animais = await api.get(`/animais${query}`);
  } catch (e) {
    lista.innerHTML = `<p class="hint">Não foi possível carregar os animais. Verifique se o backend está a correr.</p>`;
    return;
  }

  if (state.animais.length === 0) {
    lista.innerHTML = termo
        ? `<p class="hint">Nenhum animal encontrado para "${escapeHtml(termo)}".</p>`
        : `<p class="hint">Ainda não há animais cadastrados.</p>`;
    return;
  }

  lista.innerHTML = "";
  state.animais.forEach((animal) => lista.appendChild(criarCartaoAnimal(animal)));
}

function criarCartaoAnimal(animal) {
  const div = document.createElement("div");
  div.className = "animal-card";
  const disponivel = animal.status_animal;

  div.innerHTML = `
    <div class="animal-card-head">
      <div>
        <h3>${escapeHtml(animal.nome)}</h3>
        <p class="animal-meta">#${animal.id_animal} · ${escapeHtml(animal.raca || "raça não informada")} · porte ${escapeHtml(animal.porte || "—")}</p>
      </div>
      <span class="stamp ${disponivel ? "available" : "adopted"}">${disponivel ? "Disponível" : "Adotado"}</span>
    </div>
    <div class="animal-card-actions">
      <button class="btn btn-ghost" data-action="vacinas">Carteira de vacinas</button>
      <button class="btn btn-ghost" data-action="editar">Editar</button>
      <button class="btn btn-danger" data-action="excluir">Excluir</button>
    </div>
  `;

  div.querySelector('[data-action="editar"]').addEventListener("click", () => editarAnimal(animal));
  div.querySelector('[data-action="excluir"]').addEventListener("click", () => excluirAnimal(animal));
  div.querySelector('[data-action="vacinas"]').addEventListener("click", () => abrirModalVacinacao(animal));

  return div;
}

function editarAnimal(animal) {
  const form = document.getElementById("form-animal");
  form.id_animal.value = animal.id_animal;
  form.nome.value = animal.nome;
  form.raca.value = animal.raca || "";
  form.porte.value = animal.porte || "Médio";
  form.status_animal.checked = animal.status_animal;
  showForm("form-animal");
}

async function excluirAnimal(animal) {
  if (!confirm(`Remover ${animal.nome} do sistema?`)) return;
  try {
    await api.del(`/animais/${animal.id_animal}`);
    toast("Animal removido.");
    carregarAnimais();
  } catch (e) {
    toast(e.message || "Não foi possível remover o animal.", true);
  }
}

document.getElementById("form-animal").addEventListener("submit", async (e) => {
  e.preventDefault();
  const form = e.target;
  const dados = formToObject(form);
  const id = dados.id_animal;
  delete dados.id_animal;

  try {
    if (id) {
      await api.put(`/animais/${id}`, dados);
      toast("Animal atualizado.");
    } else {
      await api.post("/animais", dados);
      toast("Animal cadastrado.");
    }
    hideForm("form-animal");
    carregarAnimais();
  } catch (e) {
    toast("Não foi possível guardar o animal.", true);
  }
});


async function carregarAdotantes(termo) {
  const tbody = document.querySelector("#tabelaAdotantes tbody");
  try {
    const query = termo ? `?nome=${encodeURIComponent(termo)}` : "";
    state.adotantes = await api.get(`/adotantes${query}`);
  } catch (e) {
    tbody.innerHTML = `<tr class="empty-row"><td colspan="4">Não foi possível carregar os adotantes.</td></tr>`;
    return;
  }

  if (state.adotantes.length === 0) {
    tbody.innerHTML = termo
        ? `<tr class="empty-row"><td colspan="4">Nenhum adotante encontrado para "${escapeHtml(termo)}".</td></tr>`
        : `<tr class="empty-row"><td colspan="4">Ainda não há adotantes cadastrados.</td></tr>`;
    return;
  }

  tbody.innerHTML = "";
  state.adotantes.forEach((adotante) => {
    const tr = document.createElement("tr");
    tr.innerHTML = `
      <td>${escapeHtml(adotante.cpf)}</td>
      <td>${escapeHtml(adotante.nome)}</td>
      <td>${escapeHtml(adotante.celular || "—")}</td>
      <td class="actions">
        <button class="btn btn-ghost" data-action="editar">Editar</button>
        <button class="btn btn-danger" data-action="excluir">Excluir</button>
      </td>
    `;
    tr.querySelector('[data-action="editar"]').addEventListener("click", () => editarAdotante(adotante));
    tr.querySelector('[data-action="excluir"]').addEventListener("click", () => excluirAdotante(adotante));
    tbody.appendChild(tr);
  });
}

function editarAdotante(adotante) {
  const form = document.getElementById("form-adotante");
  form.cpf.value = adotante.cpf;
  form.cpf.readOnly = true;
  form.nome.value = adotante.nome;
  form.celular.value = adotante.celular || "";
  showForm("form-adotante");
}

async function excluirAdotante(adotante) {
  if (!confirm(`Remover o adotante ${adotante.nome}?`)) return;
  try {
    await api.del(`/adotantes/${adotante.cpf}`);
    toast("Adotante removido.");
    carregarAdotantes();
  } catch (e) {
    toast("Não foi possível remover o adotante.", true);
  }
}

document.getElementById("form-adotante").addEventListener("submit", async (e) => {
  e.preventDefault();
  const form = e.target;
  const dados = formToObject(form);

  try {
    if (form.cpf.readOnly) {
      await api.put(`/adotantes/${dados.cpf}`, dados);
      toast("Adotante atualizado.");
    } else {
      await api.post("/adotantes", dados);
      toast("Adotante cadastrado.");
    }
    form.cpf.readOnly = false;
    hideForm("form-adotante");
    carregarAdotantes();
  } catch (e) {
    toast("Não foi possível guardar o adotante.", true);
  }
});


async function carregarAdocoes(termo) {
  const tbody = document.querySelector("#tabelaAdocoes tbody");

  if (state.animais.length === 0) state.animais = await api.get("/animais").catch(() => []);
  if (state.adotantes.length === 0) state.adotantes = await api.get("/adotantes").catch(() => []);
  preencherSelectsAdocao();

  let adocoes;
  try {
    const query = termo ? `?termo=${encodeURIComponent(termo)}` : "";
    adocoes = await api.get(`/adocoes${query}`);
  } catch (e) {
    tbody.innerHTML = `<tr class="empty-row"><td colspan="4">Não foi possível carregar as adoções.</td></tr>`;
    return;
  }

  if (adocoes.length === 0) {
    tbody.innerHTML = termo
        ? `<tr class="empty-row"><td colspan="4">Nenhuma adoção encontrada para "${escapeHtml(termo)}".</td></tr>`
        : `<tr class="empty-row"><td colspan="4">Ainda não há adoções registadas.</td></tr>`;
    return;
  }

  tbody.innerHTML = "";
  adocoes.forEach((adocao) => {
    const animal = state.animais.find((a) => a.id_animal === adocao.id_animal);
    const adotante = state.adotantes.find((a) => a.cpf === adocao.cpf_adotante);
    const tr = document.createElement("tr");
    tr.innerHTML = `
      <td>${adocao.id_adocao ?? "—"}</td>
      <td>${escapeHtml(animal ? animal.nome : `#${adocao.id_animal}`)}</td>
      <td>${escapeHtml(adotante ? adotante.nome : adocao.cpf_adotante)}</td>
      <td>${escapeHtml(adocao.data_adocao || "—")}</td>
    `;
    tbody.appendChild(tr);
  });
}

function preencherSelectsAdocao() {
  const selectAnimal = document.getElementById("selectAnimalAdocao");
  const disponiveis = state.animais.filter((a) => a.status_animal);
  selectAnimal.innerHTML = disponiveis.length
      ? disponiveis.map((a) => `<option value="${a.id_animal}">${escapeHtml(a.nome)} (#${a.id_animal})</option>`).join("")
      : `<option value="">Nenhum animal disponível</option>`;

  const selectAdotante = document.getElementById("selectAdotanteAdocao");
  selectAdotante.innerHTML = state.adotantes.length
      ? state.adotantes.map((a) => `<option value="${a.cpf}">${escapeHtml(a.nome)} (${a.cpf})</option>`).join("")
      : `<option value="">Nenhum adotante cadastrado</option>`;
}

document.getElementById("form-adocao").addEventListener("submit", async (e) => {
  e.preventDefault();
  const dados = formToObject(e.target);

  if (!dados.id_animal || !dados.cpf_adotante) {
    toast("Cadastre um animal e um adotante antes de registar a adoção.", true);
    return;
  }

  try {
    await api.post("/adocoes", dados);
    toast("Adoção registada!");
    hideForm("form-adocao");
    state.animais = await api.get("/animais");
    carregarAdocoes();
  } catch (e) {
    toast("Não foi possível registar a adoção.", true);
  }
});


async function carregarVacinas(termo) {
  const tbody = document.querySelector("#tabelaVacinas tbody");
  try {
    const query = termo ? `?nome=${encodeURIComponent(termo)}` : "";
    state.vacinas = await api.get(`/vacinas${query}`);
  } catch (e) {
    tbody.innerHTML = `<tr class="empty-row"><td colspan="4">Não foi possível carregar as vacinas.</td></tr>`;
    return;
  }

  if (state.vacinas.length === 0) {
    tbody.innerHTML = termo
        ? `<tr class="empty-row"><td colspan="4">Nenhuma vacina encontrada para "${escapeHtml(termo)}".</td></tr>`
        : `<tr class="empty-row"><td colspan="4">Ainda não há vacinas cadastradas.</td></tr>`;
    return;
  }

  tbody.innerHTML = "";
  state.vacinas.forEach((vacina) => {
    const tr = document.createElement("tr");
    tr.innerHTML = `
      <td>${vacina.id_vacina}</td>
      <td>${escapeHtml(vacina.nome)}</td>
      <td>${escapeHtml(vacina.fabricante || "—")}</td>
      <td class="actions"><button class="btn btn-danger" data-action="excluir">Excluir</button></td>
    `;
    tr.querySelector('[data-action="excluir"]').addEventListener("click", () => excluirVacina(vacina));
    tbody.appendChild(tr);
  });
}

async function excluirVacina(vacina) {
  if (!confirm(`Remover a vacina ${vacina.nome} do catálogo?`)) return;
  try {
    await api.del(`/vacinas/${vacina.id_vacina}`);
    toast("Vacina removida.");
    carregarVacinas();
  } catch (e) {
    toast("Não foi possível remover a vacina.", true);
  }
}

document.getElementById("form-vacina").addEventListener("submit", async (e) => {
  e.preventDefault();
  const dados = formToObject(e.target);
  try {
    await api.post("/vacinas", dados);
    toast("Vacina cadastrada.");
    hideForm("form-vacina");
    carregarVacinas();
  } catch (e) {
    toast("Não foi possível guardar a vacina.", true);
  }
});


async function abrirModalVacinacao(animal) {
  document.getElementById("modalVacinacaoTitulo").textContent = `Carteira de vacinação — ${animal.nome}`;
  document.querySelector('#form-vacinacao input[name="id_animal"]').value = animal.id_animal;

  if (state.vacinas.length === 0) {
    state.vacinas = await api.get("/vacinas").catch(() => []);
  }
  const select = document.getElementById("selectVacinaAplicar");
  select.innerHTML = state.vacinas.length
      ? state.vacinas.map((v) => `<option value="${v.id_vacina}">${escapeHtml(v.nome)}</option>`).join("")
      : `<option value="">Cadastre uma vacina primeiro</option>`;

  await carregarHistoricoVacinacao(animal.id_animal);
  document.getElementById("modalVacinacao").hidden = false;
}

async function carregarHistoricoVacinacao(idAnimal) {
  const lista = document.getElementById("listaVacinacoes");
  let historico;
  try {
    historico = await api.get(`/vacinacoes/animal/${idAnimal}`);
  } catch (e) {
    lista.innerHTML = `<li class="vaccine-history-empty">Não foi possível carregar o histórico.</li>`;
    return;
  }

  lista.innerHTML = historico.length
      ? historico.map((v) => `<li><span>${escapeHtml(v.nome_vacina)}</span><span class="v-date">${escapeHtml(v.data_aplicacao)}</span></li>`).join("")
      : `<li class="vaccine-history-empty">Nenhuma vacina aplicada ainda.</li>`;
}

document.getElementById("fecharModalVacinacao").addEventListener("click", () => {
  document.getElementById("modalVacinacao").hidden = true;
});

document.getElementById("form-vacinacao").addEventListener("submit", async (e) => {
  e.preventDefault();
  const dados = formToObject(e.target);
  if (!dados.id_vacina) {
    toast("Cadastre uma vacina antes de registar a aplicação.", true);
    return;
  }
  try {
    await api.post("/vacinacoes", dados);
    toast("Vacinação registada!");
    e.target.reset();
    document.querySelector('#form-vacinacao input[name="id_animal"]').value = dados.id_animal;
    carregarHistoricoVacinacao(dados.id_animal);
  } catch (e) {
    toast("Não foi possível registar a vacinação.", true);
  }
});


function escapeHtml(str) {
  const div = document.createElement("div");
  div.textContent = str ?? "";
  return div.innerHTML;
}


function ligarBusca(inputId, callback) {
  const input = document.getElementById(inputId);
  let timer;
  input.addEventListener("input", () => {
    clearTimeout(timer);
    timer = setTimeout(() => callback(input.value.trim()), 300);
  });
}

ligarBusca("buscaAnimais", carregarAnimais);
ligarBusca("buscaAdotantes", carregarAdotantes);
ligarBusca("buscaAdocoes", carregarAdocoes);
ligarBusca("buscaVacinas", carregarVacinas);


verificarApi();
carregarAnimais();