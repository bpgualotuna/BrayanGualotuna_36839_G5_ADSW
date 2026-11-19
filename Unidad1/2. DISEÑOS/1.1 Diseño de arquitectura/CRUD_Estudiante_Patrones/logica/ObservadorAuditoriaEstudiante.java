package CRUD_Estudiante_Patrones.logica;

import java.util.ArrayList;
import java.util.List;

public class ObservadorAuditoriaEstudiante implements ObservadorEstudiante {
    private final List<String> bitacora = new ArrayList<>();

    @Override
    public void actualizar(EventoEstudiante evento) {
        String registro = "AUDITORIA [" + evento.getTipo() + "] "
                + evento.getEstudiante() + " - " + evento.getMensaje();
        bitacora.add(registro);
    }

    public List<String> getBitacora() {
        return new ArrayList<>(bitacora);
    }
}
