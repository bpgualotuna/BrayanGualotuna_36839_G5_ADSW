package CRUD_Estudiante_Patrones.logica;

public interface ComandoCrudEstudiante {
    String ejecutar();
    String deshacer();
    String getDescripcion();
}
