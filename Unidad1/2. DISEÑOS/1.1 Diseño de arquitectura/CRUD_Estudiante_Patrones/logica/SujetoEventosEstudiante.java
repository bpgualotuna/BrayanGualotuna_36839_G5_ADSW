package CRUD_Estudiante_Patrones.logica;

public interface SujetoEventosEstudiante {
    void agregarObservador(ObservadorEstudiante observador);
    void quitarObservador(ObservadorEstudiante observador);
    void notificarObservadores(EventoEstudiante evento);
}
