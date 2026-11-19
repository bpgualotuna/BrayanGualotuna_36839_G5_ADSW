package CRUD_Estudiante_Patrones.presentacion;

import CRUD_Estudiante_Patrones.logica.BusquedaPorIdStrategy;
import CRUD_Estudiante_Patrones.logica.BusquedaPorNombreStrategy;
import CRUD_Estudiante_Patrones.logica.ControlEstudiante;
import CRUD_Estudiante_Patrones.logica.ObservadorAuditoriaEstudiante;
import CRUD_Estudiante_Patrones.logica.ObservadorNotificacionEstudiante;
import CRUD_Estudiante_Patrones.modelo.Estudiante;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class CRUDEstudiantesGUI extends JFrame {
    private final ControlEstudiante control;
    private final ObservadorAuditoriaEstudiante auditoria;
    private final ObservadorNotificacionEstudiante notificacion;

    private final JTextField txtId = new JTextField(10);
    private final JTextField txtNombre = new JTextField(20);
    private final JTextField txtEdad = new JTextField(10);
    private final JTextField txtBuscar = new JTextField(15);
    private final JComboBox<String> cmbEstrategia = new JComboBox<>(new String[]{"Por ID", "Por Nombre"});
    private final JLabel lblNotificacion = new JLabel("Listo.");

    private final DefaultTableModel modeloTabla = new DefaultTableModel(new String[]{"ID", "Nombre", "Edad"}, 0);
    private final JTable tabla = new JTable(modeloTabla);

    public CRUDEstudiantesGUI() {
        this.control = new ControlEstudiante();
        this.auditoria = new ObservadorAuditoriaEstudiante();
        this.notificacion = new ObservadorNotificacionEstudiante();
        this.control.agregarObservador(auditoria);
        this.control.agregarObservador(notificacion);

        setTitle("CRUD Estudiantes - Observer, Strategy y Command");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(720, 480);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel panelForm = new JPanel(new GridLayout(4, 2, 6, 6));
        panelForm.setBorder(BorderFactory.createTitledBorder("Datos del Estudiante"));
        panelForm.add(new JLabel("ID:"));
        panelForm.add(txtId);
        panelForm.add(new JLabel("Nombre:"));
        panelForm.add(txtNombre);
        panelForm.add(new JLabel("Edad:"));
        panelForm.add(txtEdad);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnAgregar = new JButton("Agregar");
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnDeshacer = new JButton("Deshacer (Command)");
        panelBotones.add(btnAgregar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnDeshacer);

        JPanel panelNorte = new JPanel(new BorderLayout());
        panelNorte.add(panelForm, BorderLayout.CENTER);
        panelNorte.add(panelBotones, BorderLayout.SOUTH);

        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBusqueda.setBorder(BorderFactory.createTitledBorder("Strategy - Busqueda"));
        JButton btnBuscar = new JButton("Buscar");
        JButton btnVerTodos = new JButton("Ver Todos");
        panelBusqueda.add(cmbEstrategia);
        panelBusqueda.add(txtBuscar);
        panelBusqueda.add(btnBuscar);
        panelBusqueda.add(btnVerTodos);

        JPanel panelCentro = new JPanel(new BorderLayout());
        panelCentro.add(panelBusqueda, BorderLayout.NORTH);
        panelCentro.add(new JScrollPane(tabla), BorderLayout.CENTER);

        JPanel panelSur = new JPanel(new BorderLayout());
        panelSur.setBorder(BorderFactory.createEmptyBorder(4, 8, 8, 8));
        panelSur.add(lblNotificacion, BorderLayout.CENTER);

        add(panelNorte, BorderLayout.NORTH);
        add(panelCentro, BorderLayout.CENTER);
        add(panelSur, BorderLayout.SOUTH);

        btnAgregar.addActionListener(e -> ejecutarAccion("ADD"));
        btnActualizar.addActionListener(e -> ejecutarAccion("UPDATE"));
        btnEliminar.addActionListener(e -> ejecutarAccion("DELETE"));
        btnDeshacer.addActionListener(e -> {
            String res = control.deshacerUltimaAccion();
            lblNotificacion.setText(res);
            refrescarTabla(control.listarTodos());
        });
        btnBuscar.addActionListener(e -> {
            if ("Por ID".equals(cmbEstrategia.getSelectedItem())) {
                control.cambiarEstrategiaBusqueda(new BusquedaPorIdStrategy());
            } else {
                control.cambiarEstrategiaBusqueda(new BusquedaPorNombreStrategy());
            }
            refrescarTabla(control.buscarEstudiantes(txtBuscar.getText()));
        });
        btnVerTodos.addActionListener(e -> refrescarTabla(control.listarTodos()));
    }

    private void ejecutarAccion(String tipo) {
        try {
            int id = Integer.parseInt(txtId.getText().trim());
            String res;
            if ("ADD".equals(tipo)) {
                int edad = Integer.parseInt(txtEdad.getText().trim());
                res = control.agregarEstudiante(id, txtNombre.getText().trim(), edad);
            } else if ("UPDATE".equals(tipo)) {
                int edad = Integer.parseInt(txtEdad.getText().trim());
                res = control.actualizarEstudiante(id, txtNombre.getText().trim(), edad);
            } else {
                res = control.eliminarEstudiante(id);
            }
            lblNotificacion.setText(res + " | " + notificacion.getUltimaNotificacion());
            refrescarTabla(control.listarTodos());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID y Edad deben ser numericos.");
        }
    }

    private void refrescarTabla(List<Estudiante> lista) {
        modeloTabla.setRowCount(0);
        for (Estudiante est : lista) {
            modeloTabla.addRow(new Object[]{est.getId(), est.getNombre(), est.getEdad()});
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CRUDEstudiantesGUI().setVisible(true));
    }
}
