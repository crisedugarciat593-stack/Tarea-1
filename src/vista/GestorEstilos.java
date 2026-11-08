package vista;

import java.awt.Color;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class GestorEstilos {

    public static void aplicarTema(ventana v) {
        Color colorPrincipal = new Color(227, 100, 20);
        Color colorBlanco = Color.WHITE;
        Color colorTexto = new Color(51, 51, 51);

        Color colorAgregar = new Color(40, 167, 69);
        Color colorModificar = new Color(255, 193, 7);
        Color colorEliminar = new Color(220, 53, 69);
        Color textoModificar = new Color(33, 37, 41);

        Font fuenteBase = new Font("San Francisco", Font.PLAIN, 14);
        Font fuenteNegrita = new Font("San Francisco", Font.BOLD, 14);

        // Fondos de contenedores principales
        v.contentPane.setBackground(colorBlanco);
        v.getComponents(); // Asegurar que los paneles expuestos cambian

        // Estilos para etiquetas
        JLabel[] etiquetas = { v.lbl_nombres, v.lbl_telefono, v.lbl_email, v.lbl_buscar, v.lbl_idioma };
        for (JLabel lbl : etiquetas) {
            lbl.setFont(fuenteNegrita);
            lbl.setForeground(colorTexto);
        }
        v.chb_favorito.setFont(fuenteNegrita);
        v.chb_favorito.setForeground(colorTexto);
        v.chb_favorito.setBackground(colorBlanco);

        v.lblEstadisticas.setFont(new Font("San Francisco", Font.BOLD, 18));
        v.lblEstadisticas.setForeground(colorPrincipal);

        // Estilos para campos de texto y combos
        v.txt_nombres.setFont(fuenteBase);
        v.txt_telefono.setFont(fuenteBase);
        v.txt_email.setFont(fuenteBase);
        v.txt_buscar.setFont(fuenteBase);
        v.cmb_categoria.setFont(fuenteBase);
        v.cmb_idioma.setFont(fuenteBase);
        v.tablaContactos.setFont(fuenteBase);
        v.tablaContactos.getTableHeader().setFont(fuenteNegrita);

        // Estilos para botones
        v.btn_add.setBackground(colorAgregar);
        v.btn_add.setForeground(colorBlanco);
        v.btn_add.setFont(fuenteNegrita);
        v.btn_add.setFocusPainted(false);

        v.btn_modificar.setBackground(colorModificar);
        v.btn_modificar.setForeground(textoModificar);
        v.btn_modificar.setFont(fuenteNegrita);
        v.btn_modificar.setFocusPainted(false);

        v.btn_eliminar.setBackground(colorEliminar);
        v.btn_eliminar.setForeground(colorBlanco);
        v.btn_eliminar.setFont(fuenteNegrita);
        v.btn_eliminar.setFocusPainted(false);

        // Carga de iconos para los botones
        try {
            v.btn_add.setIcon(new ImageIcon(GestorEstilos.class.getResource("/recursos/add.png")));
            v.btn_modificar.setIcon(new ImageIcon(GestorEstilos.class.getResource("/recursos/edit.png")));
            v.btn_eliminar.setIcon(new ImageIcon(GestorEstilos.class.getResource("/recursos/delete.png")));
        } catch (Exception e) {
            System.out.println("Iconos no encontrados en la ruta /recursos/");
        }
    }
}