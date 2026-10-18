package controlador;

import java.awt.event.*;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import modelo.personaDAO;
import vista.ventana;

public class logica_ventana {
    private ventana vista;
    private personaDAO modeloDAO;

    public logica_ventana(ventana vista) {
        this.vista = vista;
        this.modeloDAO = new personaDAO();
        iniciarFunciones();
    }

    private void iniciarFunciones() {
        // funcion para mostrar el menu al hacer clic derecho
        vista.tablaContactos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e) || e.isPopupTrigger()) {
                    vista.menuContextual.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });

        // funcion para exportar usando el teclado
        vista.tablaContactos.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_E) {
                    ejecutarExportacion();
                }
            }
        });

        // funcion para la opcion de exportar del menu
        vista.itemExportar.addActionListener(e -> {
            ejecutarExportacion();
        });

        // funcion para el boton agregar
        vista.btn_add.addActionListener(e -> {
            String nombre = vista.txt_nombres.getText();
            String telefono = vista.txt_telefono.getText();
            String email = vista.txt_email.getText();
            String favorito = vista.chb_favorito.isSelected() ? "Sí" : "No";
            String categoria = vista.cmb_categoria.getSelectedItem().toString();

            if (!nombre.isEmpty() && !telefono.isEmpty()) {
                vista.modeloTabla.addRow(new Object[]{nombre, telefono, email, favorito, categoria});
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(vista, "Por favor, ingrese al menos el nombre y el teléfono.");
            }
        });

        // funcion para el boton eliminar
        vista.btn_eliminar.addActionListener(e -> {
            int filaSeleccionada = vista.tablaContactos.getSelectedRow();
            if (filaSeleccionada != -1) {
                int filaModelo = vista.tablaContactos.convertRowIndexToModel(filaSeleccionada);
                vista.modeloTabla.removeRow(filaModelo);
            } else {
                JOptionPane.showMessageDialog(vista, "Seleccione un contacto de la tabla para eliminar.");
            }
        });
    }

    // funcion para la barra de carga y guardar el archivo
    private void ejecutarExportacion() {
        new Thread(() -> {
            vista.progressBar.setValue(0);
            try {
                for (int i = 0; i <= 100; i += 20) {
                    vista.progressBar.setValue(i);
                    Thread.sleep(150); 
                }
                
                modeloDAO.exportarContactosCSV(vista.modeloTabla, "contactos_exportados.csv");
                
                vista.progressBar.setValue(100);
                JOptionPane.showMessageDialog(vista, "Exportación completada con éxito.");
                vista.progressBar.setValue(0); 
                
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }).start();
    }

    // funcion para vaciar los textos
    private void limpiarCampos() {
        vista.txt_nombres.setText("");
        vista.txt_telefono.setText("");
        vista.txt_email.setText("");
        vista.chb_favorito.setSelected(false);
        vista.cmb_categoria.setSelectedIndex(0);
    }
}