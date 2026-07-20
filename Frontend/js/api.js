
const API_BASE = "http://localhost:8080/api";

async function apiRequest(path, options = {}) {
  const res = await fetch(`${API_BASE}${path}`, {
    headers: { "Content-Type": "application/json" },
    ...options,
  });

  if (!res.ok) {
    const texto = await res.text().catch(() => "");
    throw new Error(texto || `Erro ${res.status} ao chamar ${path}`);
  }

  const tipo = res.headers.get("content-type") || "";
  if (tipo.includes("application/json")) {
    return res.json();
  }
  return res.text();
}

const api = {
  get: (path) => apiRequest(path),
  post: (path, body) => apiRequest(path, { method: "POST", body: JSON.stringify(body) }),
  put: (path, body) => apiRequest(path, { method: "PUT", body: JSON.stringify(body) }),
  del: (path) => apiRequest(path, { method: "DELETE" }),
};
