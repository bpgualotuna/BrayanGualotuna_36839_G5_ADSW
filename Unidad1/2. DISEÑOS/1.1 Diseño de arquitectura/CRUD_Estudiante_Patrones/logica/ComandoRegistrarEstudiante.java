package CRUD_Estudiante_Patrones.logica;

import CRUD_Estudiante_Patrones.datos.RepositorioEstudiante;
import CRUD_Estudiante_Patrones.modelo.Estudiante;

public class ComandoRegistrarEstudiante implements ComandoCrudEstudiante {
    private final RepositorioEstudiante repositorio;
    private final Estudiante estudiante;
    private boolean ejecutado = false;

    public ComandoRegistrarEstudiante(RepositorioEstudiante repositorio, Estudiante estudiante) {
        this.repositorio = repositorio;
        this.estudiante = estudiante;
    }

    @Override
    public String ejecutar() {
        if (estudiante.getNombre() == null || estudiante.getNombre().trim().isEmpty()) {
            return "Error: El nombre del estudiante es obligatorio.";
        }
        if (estudiante.getEdad() <= 0) {
            return "Error: La edad debe ser mayor a 0.";
        }
        if (repositorio.existeId(estudiante.getId())) {
            return "Error: Ya existe un estudiante con ID " + estudiante.getId() + ".";
        }
        repositorio.guardar(estudiante);
        ejecutado = true;
        return "Agregado exitosamente: " + estudiante.getNombre();
    }

    @Override
    public String deshacer() {
        if (!ejecutado) {
            return "Error: No hay registro para deshacer.";
        }
        repositorio.eliminar(estudiante.getId());
        ejecutado = false;
        return "Deshacer registro: eliminado estudiante ID " + estudiante.getId();
    }

    @Override
    public String getDescripcion() {
        return "Registrar Estudiante ID " + estudiante.getId();
    }
}
