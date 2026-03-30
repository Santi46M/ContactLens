import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final String DB_URL = "jdbc:hsqldb:file:data/lentesdb;shutdown=true";
    private static final String DB_USER = "SA";
    private static final String DB_PASSWORD = "";

    public static void main(String[] args) {
        initDatabase();

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Calculadora de Lentes");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1080, 660);
            frame.setLocationRelativeTo(null);

            Font labelFont = new Font("SansSerif", Font.PLAIN, 14);
            Font valueFont = new Font("SansSerif", Font.BOLD, 14);
            Font titleFont = new Font("SansSerif", Font.BOLD, 16);
            Font resultFont = new Font("SansSerif", Font.BOLD, 16);

            DefaultListModel<String> historyModel = new DefaultListModel<>();
            List<String> historialCompleto = new ArrayList<>();

            JPanel calculadoraPanel = new JPanel(new BorderLayout(15, 15));
            calculadoraPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

            JPanel leftPanel = new JPanel();
            leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

            LensDiagramPanel diagramPanel = new LensDiagramPanel();

            JPanel inputPanel = new JPanel(new GridBagLayout());
            inputPanel.setBorder(BorderFactory.createTitledBorder("Datos del lente"));
            inputPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(8, 8, 8, 8);
            gbc.fill = GridBagConstraints.HORIZONTAL;

            JLabel lblNombreLente = new JLabel("Nombre del caso:");
            lblNombreLente.setFont(labelFont);
            JTextField txtNombreLente = new JTextField(12);

            JLabel lblCurvaBase = new JLabel("Curva Base (mm):");
            lblCurvaBase.setFont(labelFont);
            JTextField txtCurvaBase = new JTextField(12);

            JLabel lblMaterial = new JLabel("Material:");
            lblMaterial.setFont(labelFont);
            String[] materiales = {
                    "Boston ES",
                    "Boston EO",
                    "Boston XO",
                    "Ingresar valor"
            };
            JComboBox<String> comboMaterial = new JComboBox<>(materiales);

            JLabel lblIndiceManual = new JLabel("Índice manual:");
            lblIndiceManual.setFont(labelFont);
            JTextField txtIndiceManual = new JTextField(12);
            lblIndiceManual.setVisible(false);
            txtIndiceManual.setVisible(false);

            JLabel lblEsferico = new JLabel("Esfera:");
            lblEsferico.setFont(labelFont);
            JTextField txtEsferico = new JTextField(12);

            JLabel lblCilindro = new JLabel("Cilindro:");
            lblCilindro.setFont(labelFont);
            JTextField txtCilindro = new JTextField(12);

            JLabel lblEje = new JLabel("Eje (°):");
            lblEje.setFont(labelFont);
            JTextField txtEje = new JTextField(12);

            int y = 0;

            gbc.gridx = 0;
            gbc.gridy = y;
            inputPanel.add(lblNombreLente, gbc);
            gbc.gridx = 1;
            inputPanel.add(txtNombreLente, gbc);
            y++;

            gbc.gridx = 0;
            gbc.gridy = y;
            inputPanel.add(lblCurvaBase, gbc);
            gbc.gridx = 1;
            inputPanel.add(txtCurvaBase, gbc);
            y++;

            gbc.gridx = 0;
            gbc.gridy = y;
            inputPanel.add(lblMaterial, gbc);
            gbc.gridx = 1;
            inputPanel.add(comboMaterial, gbc);
            y++;

            gbc.gridx = 0;
            gbc.gridy = y;
            inputPanel.add(lblIndiceManual, gbc);
            gbc.gridx = 1;
            inputPanel.add(txtIndiceManual, gbc);
            y++;

            gbc.gridx = 0;
            gbc.gridy = y;
            inputPanel.add(lblEsferico, gbc);
            gbc.gridx = 1;
            inputPanel.add(txtEsferico, gbc);
            y++;

            gbc.gridx = 0;
            gbc.gridy = y;
            inputPanel.add(lblCilindro, gbc);
            gbc.gridx = 1;
            inputPanel.add(txtCilindro, gbc);
            y++;

            gbc.gridx = 0;
            gbc.gridy = y;
            inputPanel.add(lblEje, gbc);
            gbc.gridx = 1;
            inputPanel.add(txtEje, gbc);
            y++;

            JPanel resultPanel = new JPanel(new GridBagLayout());
            resultPanel.setBorder(BorderFactory.createTitledBorder("Resultados"));
            resultPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

            JLabel lblCurva1 = new JLabel("Primera curva:");
            lblCurva1.setFont(labelFont);
            JLabel resultadoCurva1 = new JLabel("-");
            resultadoCurva1.setFont(resultFont);

            JLabel lblCurva2 = new JLabel("Segunda curva:");
            lblCurva2.setFont(labelFont);
            JLabel resultadoCurva2 = new JLabel("-");
            resultadoCurva2.setFont(resultFont);

            JLabel lblTorsion = new JLabel("Torsión:");
            lblTorsion.setFont(labelFont);
            JLabel resultadoTorsion = new JLabel("-");
            resultadoTorsion.setFont(resultFont);

            JLabel lblIndiceUsado = new JLabel("Índice usado:");
            lblIndiceUsado.setFont(labelFont);
            JLabel resultadoIndice = new JLabel("-");
            resultadoIndice.setFont(valueFont);

            GridBagConstraints gbcRes = new GridBagConstraints();
            gbcRes.insets = new Insets(8, 8, 8, 8);
            gbcRes.fill = GridBagConstraints.HORIZONTAL;

            int yr = 0;

            gbcRes.gridx = 0;
            gbcRes.gridy = yr;
            resultPanel.add(lblCurva1, gbcRes);
            gbcRes.gridx = 1;
            resultPanel.add(resultadoCurva1, gbcRes);
            yr++;

            gbcRes.gridx = 0;
            gbcRes.gridy = yr;
            resultPanel.add(lblCurva2, gbcRes);
            gbcRes.gridx = 1;
            resultPanel.add(resultadoCurva2, gbcRes);
            yr++;

            gbcRes.gridx = 0;
            gbcRes.gridy = yr;
            resultPanel.add(lblTorsion, gbcRes);
            gbcRes.gridx = 1;
            resultPanel.add(resultadoTorsion, gbcRes);
            yr++;

            gbcRes.gridx = 0;
            gbcRes.gridy = yr;
            resultPanel.add(lblIndiceUsado, gbcRes);
            gbcRes.gridx = 1;
            resultPanel.add(resultadoIndice, gbcRes);

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
            buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

            JButton btnCalcular = new JButton("Calcular");
            JButton btnLimpiar = new JButton("Limpiar");

            btnCalcular.setFont(valueFont);
            btnLimpiar.setFont(valueFont);

            buttonPanel.add(btnCalcular);
            buttonPanel.add(btnLimpiar);

            leftPanel.add(inputPanel);
            leftPanel.add(Box.createRigidArea(new Dimension(0, 12)));
            leftPanel.add(resultPanel);
            leftPanel.add(Box.createRigidArea(new Dimension(0, 12)));
            leftPanel.add(buttonPanel);

            calculadoraPanel.add(leftPanel, BorderLayout.CENTER);
            calculadoraPanel.add(diagramPanel, BorderLayout.EAST);

            JPanel historialPanel = new JPanel(new BorderLayout(10, 10));
            historialPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

            JPanel topHistoryPanel = new JPanel(new BorderLayout(8, 8));

            JLabel historyInfo = new JLabel("Historial de lentes calculados");
            historyInfo.setFont(titleFont);

            JPanel searchPanel = new JPanel(new BorderLayout(6, 6));
            JLabel lblBuscarHistorial = new JLabel("Buscar:");
            JTextField txtBuscarHistorial = new JTextField();
            txtBuscarHistorial.setToolTipText("Buscar por nombre, material o valores");

            searchPanel.add(lblBuscarHistorial, BorderLayout.WEST);
            searchPanel.add(txtBuscarHistorial, BorderLayout.CENTER);

            topHistoryPanel.add(historyInfo, BorderLayout.NORTH);
            topHistoryPanel.add(searchPanel, BorderLayout.SOUTH);

            String[] columnasHistorial = {
                    "Fecha",
                    "Nombre",
                    "CB",
                    "Material",
                    "Índice",
                    "Esfera",
                    "Cilindro",
                    "Curva 1",
                    "Curva 2",
                    "Torsión"
            };

            DefaultTableModel historyTableModel = new DefaultTableModel(columnasHistorial, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            JTable historyTable = new JTable(historyTableModel);
            historyTable.setFont(new Font("SansSerif", Font.PLAIN, 12));
            historyTable.setRowHeight(24);
            historyTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
            historyTable.setFillsViewportHeight(true);
            historyTable.setGridColor(new Color(220, 220, 220));
            historyTable.setShowGrid(true);
            historyTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

            historyTable.getColumnModel().getColumn(0).setPreferredWidth(140); // Fecha
            historyTable.getColumnModel().getColumn(1).setPreferredWidth(140); // Nombre
            historyTable.getColumnModel().getColumn(2).setPreferredWidth(60);  // CB
            historyTable.getColumnModel().getColumn(3).setPreferredWidth(110); // Material
            historyTable.getColumnModel().getColumn(4).setPreferredWidth(60);  // Índice
            historyTable.getColumnModel().getColumn(5).setPreferredWidth(70);  // Esfera
            historyTable.getColumnModel().getColumn(6).setPreferredWidth(70);  // Cilindro
            historyTable.getColumnModel().getColumn(7).setPreferredWidth(70);  // Curva 1
            historyTable.getColumnModel().getColumn(8).setPreferredWidth(70);  // Curva 2
            historyTable.getColumnModel().getColumn(9).setPreferredWidth(70);  // Torsión

            TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(historyTableModel);
            historyTable.setRowSorter(sorter);

            txtBuscarHistorial.getDocument().addDocumentListener(new DocumentListener() {
                private void filtrar() {
                    String texto = txtBuscarHistorial.getText().trim();
                    if (texto.isEmpty()) {
                        sorter.setRowFilter(null);
                    } else {
                        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + java.util.regex.Pattern.quote(texto)));
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

            historialPanel.add(topHistoryPanel, BorderLayout.NORTH);
            historialPanel.add(scrollPane, BorderLayout.CENTER);
            JTabbedPane tabs = new JTabbedPane();
            tabs.addTab("Calculadora", calculadoraPanel);
            tabs.addTab("Historial", historialPanel);

            frame.setContentPane(tabs);

            cargarHistorial(historyTableModel);

            comboMaterial.addActionListener(e -> {
                boolean manual = "Ingresar valor".equals(comboMaterial.getSelectedItem());
                lblIndiceManual.setVisible(manual);
                txtIndiceManual.setVisible(manual);
                frame.revalidate();
                frame.repaint();
            });

            Action calcularAction = new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        String nombreLente = txtNombreLente.getText().trim();
                        if (nombreLente.isEmpty()) {
                            throw new NumberFormatException("Nombre del lente vacío");
                        }

                        double curvaBase = parseNumero(txtCurvaBase.getText());
                        double esferico = parseNumero(txtEsferico.getText());
                        double cilindro = parseNumero(txtCilindro.getText());
                        double eje = parseNumero(txtEje.getText());

                        if (eje < 0 || eje > 180) {
                            throw new NumberFormatException("El eje debe estar entre 0 y 180.");
                        }

                        String materialSeleccionado = comboMaterial.getSelectedItem().toString();
                        double indice = obtenerIndice(materialSeleccionado, txtIndiceManual.getText());

                        double curva1 = calcularCurva(curvaBase, indice, esferico);
                        double curva2 = calcularCurva(curvaBase, indice, esferico + cilindro);
                        double torsion = calcularTorsionAproximada(curvaBase, indice, esferico, cilindro);

                        resultadoCurva1.setText(String.format("%.2f mm", curva1));
                        resultadoCurva2.setText(String.format("%.2f mm", curva2));
                        resultadoTorsion.setText(String.format("%.2f mm", torsion));
                        resultadoIndice.setText(String.format("%.3f", indice));

                        diagramPanel.updateData(curva1, curva2, eje);

                        guardarCalculo(
                                nombreLente,
                                curvaBase,
                                materialSeleccionado,
                                indice,
                                esferico,
                                cilindro,
                                curva1,
                                curva2,
                                torsion
                        );

                        cargarHistorial(historyTableModel);

                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(
                                frame,
                                "Ingresá valores válidos, completá el nombre del lente y un eje entre 0 y 180.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE
                        );
                    } catch (ArithmeticException ex) {
                        JOptionPane.showMessageDialog(
                                frame,
                                ex.getMessage(),
                                "Error de cálculo",
                                JOptionPane.ERROR_MESSAGE
                        );
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(
                                frame,
                                "Ocurrió un error al calcular.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }
                }
            };

            btnCalcular.addActionListener(calcularAction);

            btnLimpiar.addActionListener(e -> {
                txtNombreLente.setText("");
                txtCurvaBase.setText("");
                txtEsferico.setText("");
                txtCilindro.setText("");
                txtEje.setText("");
                txtIndiceManual.setText("");
                comboMaterial.setSelectedIndex(0);

                resultadoCurva1.setText("-");
                resultadoCurva2.setText("-");
                resultadoTorsion.setText("-");
                resultadoIndice.setText("-");

                diagramPanel.clearData();

                txtNombreLente.requestFocus();
            });

            frame.getRootPane().setDefaultButton(btnCalcular);

            try {
                Image icon = new ImageIcon(Main.class.getResource("/img.png")).getImage();
                frame.setIconImage(icon);
            } catch (Exception e) {
                System.out.println("No se pudo cargar el icono");
            }

            frame.setVisible(true);
        });
    }

    public static double parseNumero(String texto) {
        return Double.parseDouble(texto.trim().replace(",", "."));
    }

    public static double obtenerIndice(String material, String indiceManual) {
        switch (material) {
            case "Boston ES":
                return 1.45;
            case "Boston EO":
                return 1.429;
            case "Boston XO":
                return 1.415;
            case "Ingresar valor":
                if (indiceManual == null || indiceManual.trim().isEmpty()) {
                    throw new NumberFormatException("Índice manual vacío");
                }
                return parseNumero(indiceManual);
            default:
                return 1.45;
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    public static double calcularCurva(double curvaBase, double indice, double graduacion) {
        // double A = (indice - 1) * 1000.0;
        double denominador =   (graduacion / (1000 * (indice - 1)) + 1/curvaBase ) ;

        if (indice == 1) {
            throw new ArithmeticException("El índice no puede ser 1.");
        }

        if (Math.abs(denominador) < 0.0000001) {
            throw new ArithmeticException("Resultado inestable.");
        }

        return  (1/ denominador);
    }

    public static double calcularTorsionAproximada(double curvaBase, double indice, double esferico, double cilindro) {
        double potenciaMedia = esferico + (cilindro / 2.0);

        double A = (indice - 1) * 1000.0;
        double denominador = A + potenciaMedia * curvaBase;

        if (indice == 1) {
            throw new ArithmeticException("El índice no puede ser 1.");
        }

        if (Math.abs(denominador) < 0.0000001) {
            throw new ArithmeticException("Resultado inestable.");
        }

        return ((indice - 1) * curvaBase * curvaBase * 1000.0 * Math.abs(cilindro))
                / (denominador * denominador);
    }

    public static void initDatabase() {
        String createTableSql = """
                CREATE TABLE IF NOT EXISTS historial_lentes (
                    id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                    nombre_lente VARCHAR(100),
                    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    curva_base DOUBLE,
                    material VARCHAR(100),
                    indice DOUBLE,
                    esferico DOUBLE,
                    cilindro DOUBLE,
                    curva1 DOUBLE,
                    curva2 DOUBLE,
                    torsion DOUBLE
                )
                """;

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute(createTableSql);

            try {
                stmt.execute("ALTER TABLE historial_lentes ADD COLUMN nombre_lente VARCHAR(100)");
            } catch (SQLException ignored) {
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "No se pudo inicializar la base de datos.");
        }
    }

    public static void filtrarHistorial(DefaultListModel<String> historyModel, List<String> historialCompleto, String texto) {
        historyModel.clear();

        String filtro = texto.trim().toLowerCase();

        for (String item : historialCompleto) {
            if (item.toLowerCase().contains(filtro)) {
                historyModel.addElement(item);
            }
        }
    }

    public static void guardarCalculo(
            String nombreLente,
            double curvaBase,
            String material,
            double indice,
            double esferico,
            double cilindro,
            double curva1,
            double curva2,
            double torsion
    ) {
        String sql = """
                INSERT INTO historial_lentes
                (nombre_lente, curva_base, material, indice, esferico, cilindro, curva1, curva2, torsion)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nombreLente);
            ps.setDouble(2, curvaBase);
            ps.setString(3, material);
            ps.setDouble(4, indice);
            ps.setDouble(5, esferico);
            ps.setDouble(6, cilindro);
            ps.setDouble(7, curva1);
            ps.setDouble(8, curva2);
            ps.setDouble(9, torsion);

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "No se pudo guardar el cálculo.");
        }
    }

    public static void cargarHistorial(DefaultTableModel historyTableModel) {
        historyTableModel.setRowCount(0);

        String sql = """
            SELECT nombre_lente, fecha, curva_base, material, indice, esferico, cilindro, curva1, curva2, torsion
            FROM historial_lentes
            ORDER BY id DESC
            """;

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Object[] fila = {
                        new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                                .format(rs.getTimestamp("fecha")),
                        rs.getString("nombre_lente"),
                        String.format("%.2f", rs.getDouble("curva_base")),
                        rs.getString("material"),
                        String.format("%.3f", rs.getDouble("indice")),
                        String.format("%.2f", rs.getDouble("esferico")),
                        String.format("%.2f", rs.getDouble("cilindro")),
                        String.format("%.2f", rs.getDouble("curva1")),
                        String.format("%.2f", rs.getDouble("curva2")),
                        String.format("%.2f", rs.getDouble("torsion"))
                };
                historyTableModel.addRow(fila);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "No se pudo cargar el historial.");
        }
    }

    static class LensDiagramPanel extends JPanel {
        private double curvaEsferica = 0;
        private double curvaCilindrica = 0;
        private double eje = 0;
        private boolean hasData = false;

        private final Color verdePrincipal = new Color(0, 140, 0);
        private final Color verdeSecundario = new Color(0, 180, 90);

        public LensDiagramPanel() {
            setPreferredSize(new Dimension(400, 320));
            setBackground(new Color(255, 252, 235));
            setBorder(BorderFactory.createTitledBorder("Diagrama de curvas"));
        }

        public void updateData(double curvaEsferica, double curvaCilindrica, double eje) {
            this.curvaEsferica = curvaEsferica;
            this.curvaCilindrica = curvaCilindrica;
            this.eje = eje;
            this.hasData = true;
            repaint();
        }

        public void clearData() {
            this.curvaEsferica = 0;
            this.curvaCilindrica = 0;
            this.eje = 0;
            this.hasData = false;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int w = getWidth();
            int h = getHeight();
            Insets insets = getInsets();

            int availableWidth = w - insets.left - insets.right;
            int availableHeight = h - insets.top - insets.bottom;

            int titleOffset = 10;

            int cx = insets.left + availableWidth / 2;
            int cy = insets.top + availableHeight / 2 + titleOffset;

            int radius = Math.min(availableWidth, availableHeight) / 2 - 20;

            g2.setColor(verdeSecundario);
            g2.setStroke(new BasicStroke(2f));
            g2.drawOval(cx - radius, cy - radius, radius * 2, radius * 2);

            if (!hasData) {
                g2.setColor(Color.GRAY);
                g2.drawString("Sin datos", cx - 25, cy);
                g2.dispose();
                return;
            }

            drawAxisLine(
                    g2,
                    cx,
                    cy,
                    radius,
                    eje,
                    curvaEsferica,
                    verdePrincipal,
                    1.00
            );

            double ejeTranspuesto = (eje + 90) % 180;
            drawAxisLine(
                    g2,
                    cx,
                    cy,
                    radius,
                    ejeTranspuesto,
                    curvaCilindrica,
                    verdePrincipal,
                    0.82
            );

            g2.setColor(verdePrincipal);
            g2.fillOval(cx - 4, cy - 4, 8, 8);

            g2.dispose();
        }

        private void drawAxisLine(
                Graphics2D g2,
                int cx,
                int cy,
                int radius,
                double axisDeg,
                double curva,
                Color lineColor,
                double lineFactor
        ) {
            double angleRad = Math.toRadians(-axisDeg);

            int effectiveRadius = (int) (radius * lineFactor);

            int x1 = (int) (cx - effectiveRadius * Math.cos(angleRad));
            int y1 = (int) (cy - effectiveRadius * Math.sin(angleRad));
            int x2 = (int) (cx + effectiveRadius * Math.cos(angleRad));
            int y2 = (int) (cy + effectiveRadius * Math.sin(angleRad));

            g2.setColor(lineColor);

            Stroke oldStroke = g2.getStroke();
            g2.setStroke(new BasicStroke(1.5f));

            double perpX = -Math.sin(angleRad);
            double perpY = Math.cos(angleRad);

            int offset = 4; // separación entre líneas

            g2.drawLine(x1, y1, x2, y2);

            g2.drawLine(
                    (int)(x1 + perpX * offset),
                    (int)(y1 + perpY * offset),
                    (int)(x2 + perpX * offset),
                    (int)(y2 + perpY * offset)
            );

            g2.drawLine(
                    (int)(x1 - perpX * offset),
                    (int)(y1 - perpY * offset),
                    (int)(x2 - perpX * offset),
                    (int)(y2 - perpY * offset)
            );

            g2.setStroke(oldStroke);

            String text = String.format("%.2f mm %.0f°", curva, axisDeg);
            g2.setColor(Color.BLACK);
            g2.setFont(new Font("SansSerif", Font.BOLD, 12));

            drawTextOnBorder(g2, text, cx, cy, radius, angleRad);
        }

        private void drawTextOnBorder(Graphics2D g2, String text, int cx, int cy, int radius, double angleRad) {
            FontMetrics fm = g2.getFontMetrics();

            int tx = (int) (cx + (radius + 2) * Math.cos(angleRad));
            int ty = (int) (cy + (radius + 2) * Math.sin(angleRad));

            int textWidth = fm.stringWidth(text);
            int textHeight = fm.getAscent();

            if (Math.cos(angleRad) < 0) {
                tx -= textWidth;
            }

            if (Math.sin(angleRad) >= 0) {
                ty += textHeight;
            }

            g2.setColor(Color.WHITE);
            g2.fillRoundRect(tx - 2, ty - textHeight, textWidth + 4, textHeight + 4, 6, 6);

            g2.setColor(Color.BLACK);
            g2.drawString(text, tx, ty);
        }
    }
}