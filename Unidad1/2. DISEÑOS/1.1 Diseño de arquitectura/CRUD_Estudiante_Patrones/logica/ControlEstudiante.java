package CRUD_Estudiante_Patrones.logica;

import CRUD_Estudiante_Patrones.datos.RepositorioEstudiante;
import CRUD_Estudiante_Patrones.modelo.Estudiante;
import java.util.ArrayList;
import java.util.List;

public class ControlEstudiante implements SujetoEventosEstudiante {
    private final RepositorioEstudiante repositorio;
    private final InvocadorComandosEstudiante invocador;
    private final List<ObservadorEstudiante> observadores = new ArrayList<>();
    private EstrategiaBusquedaEstudiante estrategiaBusqueda;

    public ControlEstudiante() {
        this.repositorio = new RepositorioEstudiante();
        this.invocador = new InvocadorComandosEstudiante();
        this.estrategiaBusqueda = new BusquedaPorIdStrategy();
    }

    public String agregarEstudiante(int id, String nombre, int edad) {
        Estudiante estudiante = new Estudiante(id, nombre, edad);
        ComandoCrudEstudiante comando = new ComandoRegistrarEstudiante(repositorio, estudiante);
        String resultado = invocador.ejecutarComando(comando);
        if (esOperacionExitosa(resultado)) {
            notificarObservadores(new EventoEstudiante(TipoEventoEstudiante.REGISTRADO, estudiante, resultado));
        }
        return resultado;
    }

    public String actualizarEstudiante(int id, String nuevoNombre, int nuevaEdad) {
        Estudiante estudiante = new Estudiante(id, nuevoNombre, nuevaEdad);
        ComandoCrudEstudiante comando = new ComandoActualizarEstudiante(repositorio, estudiante);
        String resultado = invocador.ejecutarComando(comando);
        if (esOperacionExitosa(resultado)) {
            notificarObservadores(new EventoEstudiante(TipoEventoEstudiante.ACTUALIZADO, estudiante, resultado));
        }
        return resultado;
    }

    public String eliminarEstudiante(int id) {
        Estudiante objetivo = repositorio.buscarPorId(id);
        ComandoCrudEstudiante comando = new ComandoEliminarEstudiante(repositorio, id);
        String resultado = invocador.ejecutarComando(comando);
        if (esOperacionExitosa(resultado) && objetivo != null) {
            notificarObservadores(new EventoEstudiante(TipoEventoEstudiante.ELIMINADO, objetivo, resultado));
        }
        return resultado;
    }

    public String deshacerUltimaAccion() {
        return invocador.deshacerUltimo();
    }

    public List<String> getHistorialComandos() {
        return invocador.getRegistroComandos();
    }

    public List<Estudiante> listarTodos() {
        return repositorio.listarTodos();
    }

    public void cambiarEstrategiaBusqueda(EstrategiaBusquedaEstudiante estrategiaBusqueda) {
        if (estrategiaBusqueda == null) {
            throw new IllegalArgumentException("La estrategia de busqueda no puede ser nula.");
        }
        this.estrategiaBusqueda = estrategiaBusqueda;
    }

    public String getNombreEstrategiaBusqueda() {
        return estrategiaBusqueda.getNombre();
    }

    public List<Estudiante> buscarEstudiantes(String criterio) {
        return estrategiaBusqueda.buscar(listarTodos(), criterio);
    }

    @Override
    public void agregarObservador(ObservadorEstudiante observador) {
        if (observador != null && !observadores.contains(observador)) {
            observadores.add(observador);
        }
    }

    @Override
    public void quitarObservador(ObservadorEstudiante observador) {
        observadores.remove(observador);
    }

    @Override
    public void notificarObservadores(EventoEstudiante evento) {
        for (ObservadorEstudiante observador : observadores) {
            observador.actualizar(evento);
        }
    }

    private boolean esOperacionExitosa(String resultado) {
        return resultado != null && !resultado.startsWith("Error:");
    }
}
