package CRUD_Estudiante_Patrones.logica;

import CRUD_Estudiante_Patrones.datos.RepositorioEstudiante;
import CRUD_Estudiante_Patrones.modelo.Estudiante;

public class ComandoEliminarEstudiante implements ComandoCrudEstudiante {
    private final RepositorioEstudiante repositorio;
    private final int id;
    private Estudiante respaldo;
    private boolean ejecutado = false;

    public ComandoEliminarEstudiante(RepositorioEstudiante repositorio, int id) {
        this.repositorio = repositorio;
        this.id = id;
    }

    @Override
    public String ejecutar() {
        Estudiante actual = repositorio.buscarPorId(id);
        if (actual == null) {
            return "Error: No se encontro estudiante con ID " + id + ".";
        }
        if (actual.getEdad() < 18) {
            return "Error: Regla academica - no se puede eliminar a '" + actual.getNombre() + "' porque es menor de 18 anios (Edad: " + actual.getEdad() + ").";
        }
        this.respaldo = new Estudiante(actual.getId(), actual.getNombre(), actual.getEdad());
        repositorio.eliminar(id);
        ejecutado = true;
        return "Eliminado exitosamente: " + respaldo.getNombre() + " (ID " + id + ")";
    }

    @Override
    public String deshacer() {
        if (!ejecutado || respaldo == null) {
            return "Error: No hay eliminacion para deshacer.";
        }
        repositorio.guardar(respaldo);
        ejecutado = false;
        return "Deshacer eliminacion: restaurado " + respaldo.getNombre() + " (ID " + respaldo.getId() + ")";
    }

    @Override
    public String getDescripcion() {
        return "Eliminar Estudiante ID " + id;
    }
}
