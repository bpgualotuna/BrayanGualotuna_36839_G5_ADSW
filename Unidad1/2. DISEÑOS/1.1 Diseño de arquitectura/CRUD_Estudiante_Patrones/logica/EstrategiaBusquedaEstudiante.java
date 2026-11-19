package CRUD_Estudiante_Patrones.logica;

import CRUD_Estudiante_Patrones.modelo.Estudiante;
import java.util.List;

public interface EstrategiaBusquedaEstudiante {
    List<Estudiante> buscar(List<Estudiante> estudiantes, String criterio);
    String getNombre();
}
