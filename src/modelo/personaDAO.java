package modelo;

import java.io.FileWriter;
import java.io.IOException;
import javax.swing.table.DefaultTableModel;

public class personaDAO {
    
    public void exportarContactosCSV(DefaultTableModel modeloTabla, String rutaDestino) {
        try (FileWriter csvWriter = new FileWriter(rutaDestino)) {
            for (int i = 0; i < modeloTabla.getColumnCount(); i++) {
                csvWriter.append(modeloTabla.getColumnName(i));
                if (i < modeloTabla.getColumnCount() - 1) {
                    csvWriter.append(",");
                }
            }
            csvWriter.append("\n");

            for (int i = 0; i < modeloTabla.getRowCount(); i++) {
                for (int j = 0; j < modeloTabla.getColumnCount(); j++) {
                    Object valor = modeloTabla.getValueAt(i, j);
                    csvWriter.append(valor != null ? valor.toString() : "");
                    if (j < modeloTabla.getColumnCount() - 1) {
                        csvWriter.append(",");
                    }
                }
                csvWriter.append("\n");
            }
            System.out.println("Exportación exitosa.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}