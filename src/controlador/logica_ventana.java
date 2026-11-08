package controlador;

import java.awt.event.*;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
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
        // Función para cambiar idioma
        vista.cmb_idioma.addActionListener(e -> {
            int sel = vista.cmb_idioma.getSelectedIndex();
            if (sel == 0) {
                vista.cargarIdioma("es", "ES");
            } else if (sel == 1) {
                vista.cargarIdioma("en", "US");
            } else if (sel == 2) {
                vista.cargarIdioma("fr", "FR");
            }
            vista.actualizarTextosInterfaz();
        });

        vista.tablaContactos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e) || e.isPopupTrigger()) {
                    vista.menuContextual.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });

        vista.tablaContactos.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_E) {
                    ejecutarExportacion();
                }
            }
        });

        vista.itemExportar.addActionListener(e -> ejecutarExportacion());

        vista.btn_add.addActionListener(e -> {
            String nombre = vista.txt_nombres.getText();
            String telefono = vista.txt_telefono.getText();
            String email = vista.txt_email.getText();
            String favorito = vista.chb_favorito.isSelected() ? vista.bundle.getString("val.si")
                    : vista.bundle.getString("val.no");
            String categoria = vista.cmb_categoria.getSelectedItem().toString();

            if (!nombre.isEmpty() && !telefono.isEmpty()) {
                vista.modeloTabla.addRow(new Object[] { nombre, telefono, email, favorito, categoria });
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(vista, vista.bundle.getString("msg.error.campos"));
            }
        });

        vista.btn_eliminar.addActionListener(e -> {
            int filaSeleccionada = vista.tablaContactos.getSelectedRow();
            if (filaSeleccionada != -1) {
                int filaModelo = vista.tablaContactos.convertRowIndexToModel(filaSeleccionada);
                vista.modeloTabla.removeRow(filaModelo);
            } else {
                JOptionPane.showMessageDialog(vista, vista.bundle.getString("msg.error.eliminar"));
            }
        });

        vista.txt_buscar.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String textoBusqueda = vista.txt_buscar.getText();
                TableRowSorter<DefaultTableModel> sorter = (TableRowSorter<DefaultTableModel>) vista.tablaContactos
                        .getRowSorter();
                if (textoBusqueda.trim().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + textoBusqueda));
                }
            }
        });
    }

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
                JOptionPane.showMessageDialog(vista, vista.bundle.getString("msg.exito.exportar"));
                vista.progressBar.setValue(0);

            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }).start();
    }

    private void limpiarCampos() {
        vista.txt_nombres.setText("");
        vista.txt_telefono.setText("");
        vista.txt_email.setText("");
        vista.chb_favorito.setSelected(false);
        vista.cmb_categoria.setSelectedIndex(0);
    }
}