package vista;

import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import controlador.logica_ventana;

public class ventana extends JFrame {

    public JPanel contentPane;
    public JTextField txt_nombres, txt_telefono, txt_email, txt_buscar;
    public JCheckBox chb_favorito;
    public JComboBox<String> cmb_categoria, cmb_idioma;
    public JButton btn_add, btn_modificar, btn_eliminar;

    public JTabbedPane tabbedPane;
    public JTable tablaContactos;
    public DefaultTableModel modeloTabla;
    public JProgressBar progressBar;
    public JPopupMenu menuContextual;
    public JMenuItem itemExportar;

    public JLabel lbl_nombres, lbl_telefono, lbl_email, lbl_buscar, lbl_idioma, lblEstadisticas;
    public ResourceBundle bundle;

    // Paneles de la interfaz
    public JPanel pnlNorte, pnlFormulario, pnlBotones, pnlIdioma, pnlSur, pnlBusqueda;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                ventana frame = new ventana();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public ventana() {
        cargarIdioma("es", "ES");

        setTitle("GESTIÓN DE CONTACTOS");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1050, 760);

        contentPane = new JPanel(new BorderLayout(10, 10));
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);

        pnlNorte = new JPanel(new BorderLayout(10, 10));
        pnlFormulario = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        lbl_nombres = new JLabel();
        gbc.gridx = 0;
        gbc.gridy = 0;
        pnlFormulario.add(lbl_nombres, gbc);

        txt_nombres = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        pnlFormulario.add(txt_nombres, gbc);

        lbl_telefono = new JLabel();
        gbc.gridx = 0;
        gbc.gridy = 1;
        pnlFormulario.add(lbl_telefono, gbc);

        txt_telefono = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        pnlFormulario.add(txt_telefono, gbc);

        lbl_email = new JLabel();
        gbc.gridx = 0;
        gbc.gridy = 2;
        pnlFormulario.add(lbl_email, gbc);

        txt_email = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 2;
        pnlFormulario.add(txt_email, gbc);

        chb_favorito = new JCheckBox();
        gbc.gridx = 0;
        gbc.gridy = 3;
        pnlFormulario.add(chb_favorito, gbc);

        cmb_categoria = new JComboBox<>();
        gbc.gridx = 1;
        gbc.gridy = 3;
        pnlFormulario.add(cmb_categoria, gbc);

        pnlBotones = new JPanel(new GridLayout(3, 1, 5, 10));
        btn_add = new JButton();
        btn_modificar = new JButton();
        btn_eliminar = new JButton();
        pnlBotones.add(btn_add);
        pnlBotones.add(btn_modificar);
        pnlBotones.add(btn_eliminar);

        pnlIdioma = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        lbl_idioma = new JLabel();
        cmb_idioma = new JComboBox<>(new String[] { "Español", "English", "Français" });
        pnlIdioma.add(lbl_idioma);
        pnlIdioma.add(cmb_idioma);

        pnlNorte.add(pnlIdioma, BorderLayout.NORTH);
        pnlNorte.add(pnlFormulario, BorderLayout.CENTER);
        pnlNorte.add(pnlBotones, BorderLayout.EAST);
        contentPane.add(pnlNorte, BorderLayout.NORTH);

        tabbedPane = new JTabbedPane();
        JPanel panelContactos = new JPanel(new BorderLayout());
        modeloTabla = new DefaultTableModel(new Object[] { "", "", "", "", "" }, 0);
        tablaContactos = new JTable(modeloTabla);
        tablaContactos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaContactos.setRowHeight(25);
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modeloTabla);
        tablaContactos.setRowSorter(sorter);
        panelContactos.add(new JScrollPane(tablaContactos), BorderLayout.CENTER);

        JPanel panelEstadisticas = new JPanel(new BorderLayout());
        lblEstadisticas = new JLabel("", SwingConstants.CENTER);
        panelEstadisticas.add(lblEstadisticas, BorderLayout.CENTER);

        tabbedPane.addTab("", panelContactos);
        tabbedPane.addTab("", panelEstadisticas);
        contentPane.add(tabbedPane, BorderLayout.CENTER);

        pnlSur = new JPanel(new BorderLayout(5, 5));
        pnlBusqueda = new JPanel(new BorderLayout(5, 5));
        lbl_buscar = new JLabel();
        txt_buscar = new JTextField();
        pnlBusqueda.add(lbl_buscar, BorderLayout.WEST);
        pnlBusqueda.add(txt_buscar, BorderLayout.CENTER);

        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);

        pnlSur.add(pnlBusqueda, BorderLayout.NORTH);
        pnlSur.add(progressBar, BorderLayout.SOUTH);
        contentPane.add(pnlSur, BorderLayout.SOUTH);

        menuContextual = new JPopupMenu();
        itemExportar = new JMenuItem();
        menuContextual.add(itemExportar);

        // Llamada modular a la inyección de estilos visuales
        GestorEstilos.aplicarTema(this);
        actualizarTextosInterfaz();

        new logica_ventana(this);
    }

    public void cargarIdioma(String idioma, String pais) {
        Locale locale = new Locale(idioma, pais);
        bundle = ResourceBundle.getBundle("idiomas.mensajes", locale);
    }

    public void actualizarTextosInterfaz() {
        lbl_nombres.setText(bundle.getString("lbl.nombres"));
        lbl_telefono.setText(bundle.getString("lbl.telefono"));
        lbl_email.setText(bundle.getString("lbl.email"));
        chb_favorito.setText(bundle.getString("chb.favorito"));
        btn_add.setText(bundle.getString("btn.agregar"));
        btn_modificar.setText(bundle.getString("btn.modificar"));
        btn_eliminar.setText(bundle.getString("btn.eliminar"));

        lbl_idioma.setText(bundle.getString("lbl.idioma"));
        lbl_buscar.setText(bundle.getString("lbl.buscar"));
        itemExportar.setText(bundle.getString("menu.exportar"));
        lblEstadisticas.setText(bundle.getString("lbl.estadisticas"));

        tabbedPane.setTitleAt(0, bundle.getString("tab.contactos"));
        tabbedPane.setTitleAt(1, bundle.getString("tab.estadisticas"));

        String[] columnas = {
                bundle.getString("tabla.nombres"),
                bundle.getString("tabla.telefono"),
                bundle.getString("tabla.email"),
                bundle.getString("tabla.favorito"),
                bundle.getString("tabla.categoria")
        };
        modeloTabla.setColumnIdentifiers(columnas);

        int idx = cmb_categoria.getSelectedIndex();
        cmb_categoria.removeAllItems();
        cmb_categoria.addItem(bundle.getString("cat.elija"));
        cmb_categoria.addItem(bundle.getString("cat.familia"));
        cmb_categoria.addItem(bundle.getString("cat.amigos"));
        cmb_categoria.addItem(bundle.getString("cat.trabajo"));
        if (idx >= 0)
            cmb_categoria.setSelectedIndex(idx);
    }
}