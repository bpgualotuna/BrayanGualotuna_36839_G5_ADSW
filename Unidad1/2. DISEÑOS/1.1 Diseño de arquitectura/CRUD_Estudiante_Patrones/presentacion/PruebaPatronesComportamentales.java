package CRUD_Estudiante_Patrones.presentacion;

import CRUD_Estudiante_Patrones.logica.BusquedaPorIdStrategy;
import CRUD_Estudiante_Patrones.logica.BusquedaPorNombreStrategy;
import CRUD_Estudiante_Patrones.logica.ControlEstudiante;
import CRUD_Estudiante_Patrones.logica.ObservadorAuditoriaEstudiante;
import CRUD_Estudiante_Patrones.logica.ObservadorNotificacionEstudiante;

public class PruebaPatronesComportamentales {
    public static void main(String[] args) {
        ControlEstudiante control = new ControlEstudiante();
        ObservadorAuditoriaEstudiante auditoria = new ObservadorAuditoriaEstudiante();
        ObservadorNotificacionEstudiante notificacion = new ObservadorNotificacionEstudiante();

        control.agregarObservador(auditoria);
        control.agregarObservador(notificacion);

        System.out.println("=== 1. OBSERVER y COMMAND: Operaciones CRUD ===");
        System.out.println(control.agregarEstudiante(1, "Brayan Gualotuna", 22));
        System.out.println(notificacion.getUltimaNotificacion());

        System.out.println(control.agregarEstudiante(2, "David Rodriguez", 21));
        System.out.println(notificacion.getUltimaNotificacion());

        System.out.println(control.agregarEstudiante(3, "Steven Allauca", 17));
        System.out.println(notificacion.getUltimaNotificacion());

        System.out.println(control.actualizarEstudiante(1, "Brayan Gualotuna (Lider)", 23));
        System.out.println(notificacion.getUltimaNotificacion());

        System.out.println(control.eliminarEstudiante(3));
        System.out.println(control.eliminarEstudiante(2));
        System.out.println(notificacion.getUltimaNotificacion());

        System.out.println("\n=== 2. COMMAND: Deshacer (Undo) ultima operacion ===");
        System.out.println(control.deshacerUltimaAccion());
        System.out.println("Estudiantes actuales tras Undo:");
        control.listarTodos().forEach(System.out::println);

        System.out.println("\n=== 3. OBSERVER: Bitacora de Auditoria ===");
        auditoria.getBitacora().forEach(System.out::println);

        System.out.println("\n=== 4. COMMAND: Historial de Comandos ===");
        control.getHistorialComandos().forEach(System.out::println);

        System.out.println("\n=== 5. STRATEGY: Busqueda Dinamica ===");
        control.cambiarEstrategiaBusqueda(new BusquedaPorIdStrategy());
        System.out.println("Estrategia: " + control.getNombreEstrategiaBusqueda() + " | Criterio=1");
        control.buscarEstudiantes("1").forEach(System.out::println);

        control.cambiarEstrategiaBusqueda(new BusquedaPorNombreStrategy());
        System.out.println("Estrategia: " + control.getNombreEstrategiaBusqueda() + " | Criterio=David");
        control.buscarEstudiantes("David").forEach(System.out::println);
    }
}
