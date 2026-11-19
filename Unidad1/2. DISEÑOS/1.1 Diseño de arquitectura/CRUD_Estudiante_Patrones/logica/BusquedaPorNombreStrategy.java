package CRUD_Estudiante_Patrones.logica;

import CRUD_Estudiante_Patrones.modelo.Estudiante;
import java.util.List;
import java.util.stream.Collectors;

public class BusquedaPorNombreStrategy implements EstrategiaBusquedaEstudiante {
    @Override
    public List<Estudiante> buscar(List<Estudiante> estudiantes, String criterio) {
        if (criterio == null || criterio.trim().isEmpty()) {
            return estudiantes;
        }
        String texto = criterio.trim().toLowerCase();
        return estudiantes.stream()
                .filter(e -> e.getNombre().toLowerCase().contains(texto))
                .collect(Collectors.toList());
    }

    @Override
    public String getNombre() {
        return "Busqueda por Nombre";
    }
}
