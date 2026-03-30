package ui;

import model.HistorialLente;
import repository.HistorialRepository;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.regex.Pattern;

public class HistorialPanel extends JPanel {

    private DefaultTableModel historyTableModel;
    private JTable historyTable;
    private JTextField txtBuscarHistorial;
    private final HistorialRepository historialRepository;

    public HistorialPanel() {
        this.historialRepository = new HistorialRepository();

        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(15, 15, 15, 15));

        Font titleFont = new Font("SansSerif", Font.BOLD, 16);

        JPanel topHistoryPanel = new JPanel(new BorderLayout(8, 8));

        JLabel historyInfo = new JLabel("Historial de lentes calculados");
        historyInfo.setFont(titleFont);

        JPanel searchPanel = new JPanel(new BorderLayout(6, 6));
        JLabel lblBuscarHistorial = new JLabel("Buscar:");
        txtBuscarHistorial = new JTextField();
        txtBuscarHistorial.setToolTipText("Buscar por nombre, material o valores");

        searchPanel.add(lblBuscarHistorial, BorderLayout.WEST);
        searchPanel.add(txtBuscarHistorial, BorderLayout.CENTER);

        topHistoryPanel.add(historyInfo, BorderLayout.NORTH);
        topHistoryPanel.add(searchPanel, BorderLayout.SOUTH);

        String[] columnasHistorial = {
                "Fecha", "Nombre", "CB", "Material", "Índice",
                "Esfera", "Cilindro", "Curva 1", "Curva 2", "Torsión"
        };

        historyTableModel = new DefaultTableModel(columnasHistorial, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        historyTable = new JTable(historyTableModel);
        historyTable.setFont(new Font("SansSerif", Font.PLAIN, 12));
        historyTable.setRowHeight(24);
        historyTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        historyTable.setFillsViewportHeight(true);
        historyTable.setGridColor(new Color(220, 220, 220));
        historyTable.setShowGrid(true);
        historyTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        final TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(historyTableModel);
        historyTable.setRowSorter(sorter);

        txtBuscarHistorial.getDocument().addDocumentListener(new DocumentListener() {
            private void filtrar() {
                String texto = txtBuscarHistorial.getText().trim();
                if (texto.isEmpty()) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + Pattern.quote(texto)));
                }
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                filtrar();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filtrar();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filtrar();
            }
        });

        JScrollPane scrollPane = new JScrollPane(historyTable);

        add(topHistoryPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        recargarHistorial();
    }

    public void recargarHistorial() {
        historyTableModel.setRowCount(0);

        List<HistorialLente> historial = historialRepository.obtenerHistorial();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        for (HistorialLente item : historial) {
            historyTableModel.addRow(new Object[]{
                    sdf.format(item.getFecha()),
                    item.getNombreLente(),
                    String.format("%.2f", item.getCurvaBase()),
                    item.getMaterial(),
                    String.format("%.3f", item.getIndice()),
                    String.format("%.2f", item.getEsferico()),
                    String.format("%.2f", item.getCilindro()),
                    String.format("%.2f", item.getCurva1()),
                    String.format("%.2f", item.getCurva2()),
                    String.format("%.2f", item.getTorsion())
            });
        }
    }
}