package CRUD_Estudiante_Patrones.logica;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class InvocadorComandosEstudiante {
    private final Deque<ComandoCrudEstudiante> historial = new ArrayDeque<>();
    private final List<String> registroComandos = new ArrayList<>();

    public String ejecutarComando(ComandoCrudEstudiante comando) {
        String resultado = comando.ejecutar();
        if (resultado != null && !resultado.startsWith("Error:")) {
            historial.push(comando);
            registroComandos.add("EJECUTADO: " + comando.getDescripcion());
        } else {
            registroComandos.add("FALLIDO: " + comando.getDescripcion() + " -> " + resultado);
        }
        return resultado;
    }

    public String deshacerUltimo() {
        if (historial.isEmpty()) {
            return "Error: No existen comandos previos para deshacer.";
        }
        ComandoCrudEstudiante ultimo = historial.pop();
        String resultado = ultimo.deshacer();
        registroComandos.add("DESHECHO: " + ultimo.getDescripcion());
        return resultado;
    }

    public List<String> getRegistroComandos() {
        return new ArrayList<>(registroComandos);
    }
}
