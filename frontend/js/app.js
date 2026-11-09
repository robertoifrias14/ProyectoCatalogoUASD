const selectEscuela = document.getElementById("escuela");
const btnConsultar  = document.getElementById("btnConsultar");
const lista         = document.getElementById("listaCarreras");
const mensaje       = document.getElementById("mensaje");

// Al cargar la página, llenar el select con las escuelas de la BD
async function cargarEscuelas() {
  try {
    const resp = await fetch("/api/escuelas");
    const escuelas = await resp.json();
    escuelas.forEach(e => {
      const op = document.createElement("option");
      op.value = e.id;
      op.textContent = e.nombre;
      selectEscuela.appendChild(op);
    });
  } catch (err) {
    mensaje.textContent = "No se pudo conectar con el servidor.";
  }
}

// Al presionar el botón, pedir las carreras de la escuela elegida
btnConsultar.addEventListener("click", async () => {
  lista.innerHTML = "";
  mensaje.textContent = "";

  const id = selectEscuela.value;
  if (!id) {
    mensaje.textContent = "Por favor selecciona una escuela.";
    return;
  }

  try {
    const resp = await fetch(`/api/carreras?escuela=${id}`);
    const carreras = await resp.json();

    if (carreras.length === 0) {
      mensaje.textContent = "No hay carreras registradas para esta escuela.";
      return;
    }
    carreras.forEach(c => {
      const li = document.createElement("li");
      li.innerHTML = `<span>${c.nombre}</span><span class="grado">${c.grado}</span>`;
      lista.appendChild(li);
    });
  } catch (err) {
    mensaje.textContent = "Error al consultar las carreras.";
  }
});

cargarEscuelas();