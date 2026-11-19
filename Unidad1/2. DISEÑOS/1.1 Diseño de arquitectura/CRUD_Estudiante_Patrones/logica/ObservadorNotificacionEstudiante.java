package CRUD_Estudiante_Patrones.logica;

public class ObservadorNotificacionEstudiante implements ObservadorEstudiante {
    private String ultimaNotificacion = "Sin notificaciones.";

    @Override
    public void actualizar(EventoEstudiante evento) {
        this.ultimaNotificacion = "NOTIFICACION: evento " + evento.getTipo()
                + " para estudiante ID " + evento.getEstudiante().getId()
                + " (" + evento.getEstudiante().getNombre() + ")";
    }

    public String getUltimaNotificacion() {
        return ultimaNotificacion;
    }
}
