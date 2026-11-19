package CRUD_Estudiante_Patrones.logica;

import CRUD_Estudiante_Patrones.modelo.Estudiante;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class BusquedaPorIdStrategy implements EstrategiaBusquedaEstudiante {
    @Override
    public List<Estudiante> buscar(List<Estudiante> estudiantes, String criterio) {
        if (criterio == null || criterio.trim().isEmpty()) {
            return estudiantes;
        }
        try {
            int idBuscado = Integer.parseInt(criterio.trim());
            return estudiantes.stream()
                    .filter(e -> e.getId() == idBuscado)
                    .collect(Collectors.toList());
        } catch (NumberFormatException ex) {
            return Collections.emptyList();
        }
    }

    @Override
    public String getNombre() {
        return "Busqueda por ID";
    }
}
