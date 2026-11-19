package CRUD_Estudiante_Patrones.logica;

import CRUD_Estudiante_Patrones.datos.RepositorioEstudiante;
import CRUD_Estudiante_Patrones.modelo.Estudiante;

public class ComandoActualizarEstudiante implements ComandoCrudEstudiante {
    private final RepositorioEstudiante repositorio;
    private final Estudiante nuevoEstado;
    private Estudiante estadoAnterior;
    private boolean ejecutado = false;

    public ComandoActualizarEstudiante(RepositorioEstudiante repositorio, Estudiante nuevoEstado) {
        this.repositorio = repositorio;
        this.nuevoEstado = nuevoEstado;
    }

    @Override
    public String ejecutar() {
        Estudiante actual = repositorio.buscarPorId(nuevoEstado.getId());
        if (actual == null) {
            return "Error: No se encontro estudiante con ID " + nuevoEstado.getId() + ".";
        }
        if (nuevoEstado.getNombre() == null || nuevoEstado.getNombre().trim().isEmpty()) {
            return "Error: El nombre del estudiante es obligatorio.";
        }
        if (nuevoEstado.getEdad() <= 0) {
            return "Error: La edad debe ser mayor a 0.";
        }
        this.estadoAnterior = new Estudiante(actual.getId(), actual.getNombre(), actual.getEdad());
        repositorio.actualizar(nuevoEstado);
        ejecutado = true;
        return "Actualizado exitosamente: ID " + nuevoEstado.getId();
    }

    @Override
    public String deshacer() {
        if (!ejecutado || estadoAnterior == null) {
            return "Error: No hay actualizacion para deshacer.";
        }
        repositorio.actualizar(estadoAnterior);
        ejecutado = false;
        return "Deshacer actualizacion: restaurado ID " + estadoAnterior.getId() + " (" + estadoAnterior.getNombre() + ")";
    }

    @Override
    public String getDescripcion() {
        return "Actualizar Estudiante ID " + nuevoEstado.getId();
    }
}
