package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CalculadoraPanel extends JPanel {

    private JTextField txtNombreLente;
    private JTextField txtCurvaBase;
    private JComboBox<String> comboMaterial;
    private JLabel lblIndiceManual;
    private JTextField txtIndiceManual;
    private JTextField txtEsferico;
    private JTextField txtCilindro;
    private JTextField txtEje;

    private JLabel resultadoCurva1;
    private JLabel resultadoCurva2;
    private JLabel resultadoTorsion;
    private JLabel resultadoIndice;

    private JButton btnCalcular;
    private JButton btnLimpiar;

    private LensDiagramPanel diagramPanel;

    public CalculadoraPanel() {
        setLayout(new BorderLayout(15, 15));
        setBorder(new EmptyBorder(15, 15, 15, 15));

        Font labelFont = new Font("SansSerif", Font.PLAIN, 14);
        Font valueFont = new Font("SansSerif", Font.BOLD, 14);
        Font resultFont = new Font("SansSerif", Font.BOLD, 16);

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        diagramPanel = new LensDiagramPanel();

        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Datos del lente"));
        inputPanel.setAlignmentX(LEFT_ALIGNMENT);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblNombreLente = new JLabel("Nombre del caso:");
        lblNombreLente.setFont(labelFont);
        txtNombreLente = new JTextField(12);

        JLabel lblCurvaBase = new JLabel("Curva Base (mm):");
        lblCurvaBase.setFont(labelFont);
        txtCurvaBase = new JTextField(12);

        JLabel lblMaterial = new JLabel("Material:");
        lblMaterial.setFont(labelFont);
        comboMaterial = new JComboBox<>(new String[]{"Boston ES", "Boston EO", "Boston XO", "Ingresar valor"});

        lblIndiceManual = new JLabel("Índice manual:");
        lblIndiceManual.setFont(labelFont);
        txtIndiceManual = new JTextField(12);
        lblIndiceManual.setVisible(false);
        txtIndiceManual.setVisible(false);

        JLabel lblEsferico = new JLabel("Esfera:");
        lblEsferico.setFont(labelFont);
        txtEsferico = new JTextField(12);

        JLabel lblCilindro = new JLabel("Cilindro:");
        lblCilindro.setFont(labelFont);
        txtCilindro = new JTextField(12);

        JLabel lblEje = new JLabel("Eje (°):");
        lblEje.setFont(labelFont);
        txtEje = new JTextField(12);

        int y = 0;

        gbc.gridx = 0; gbc.gridy = y;
        inputPanel.add(lblNombreLente, gbc);
        gbc.gridx = 1;
        inputPanel.add(txtNombreLente, gbc);
        y++;

        gbc.gridx = 0; gbc.gridy = y;
        inputPanel.add(lblCurvaBase, gbc);
        gbc.gridx = 1;
        inputPanel.add(txtCurvaBase, gbc);
        y++;

        gbc.gridx = 0; gbc.gridy = y;
        inputPanel.add(lblMaterial, gbc);
        gbc.gridx = 1;
        inputPanel.add(comboMaterial, gbc);
        y++;

        gbc.gridx = 0; gbc.gridy = y;
        inputPanel.add(lblIndiceManual, gbc);
        gbc.gridx = 1;
        inputPanel.add(txtIndiceManual, gbc);
        y++;

        gbc.gridx = 0; gbc.gridy = y;
        inputPanel.add(lblEsferico, gbc);
        gbc.gridx = 1;
        inputPanel.add(txtEsferico, gbc);
        y++;

        gbc.gridx = 0; gbc.gridy = y;
        inputPanel.add(lblCilindro, gbc);
        gbc.gridx = 1;
        inputPanel.add(txtCilindro, gbc);
        y++;

        gbc.gridx = 0; gbc.gridy = y;
        inputPanel.add(lblEje, gbc);
        gbc.gridx = 1;
        inputPanel.add(txtEje, gbc);

        JPanel resultPanel = new JPanel(new GridBagLayout());
        resultPanel.setBorder(BorderFactory.createTitledBorder("Resultados"));
        resultPanel.setAlignmentX(LEFT_ALIGNMENT);

        GridBagConstraints gbcRes = new GridBagConstraints();
        gbcRes.insets = new Insets(8, 8, 8, 8);
        gbcRes.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblCurva1 = new JLabel("Primera curva:");
        lblCurva1.setFont(labelFont);
        resultadoCurva1 = new JLabel("-");
        resultadoCurva1.setFont(resultFont);

        JLabel lblCurva2 = new JLabel("Segunda curva:");
        lblCurva2.setFont(labelFont);
        resultadoCurva2 = new JLabel("-");
        resultadoCurva2.setFont(resultFont);

        JLabel lblTorsion = new JLabel("Torsión:");
        lblTorsion.setFont(labelFont);
        resultadoTorsion = new JLabel("-");
        resultadoTorsion.setFont(resultFont);

        JLabel lblIndiceUsado = new JLabel("Índice usado:");
        lblIndiceUsado.setFont(labelFont);
        resultadoIndice = new JLabel("-");
        resultadoIndice.setFont(valueFont);

        int yr = 0;

        gbcRes.gridx = 0; gbcRes.gridy = yr;
        resultPanel.add(lblCurva1, gbcRes);
        gbcRes.gridx = 1;
        resultPanel.add(resultadoCurva1, gbcRes);
        yr++;

        gbcRes.gridx = 0; gbcRes.gridy = yr;
        resultPanel.add(lblCurva2, gbcRes);
        gbcRes.gridx = 1;
        resultPanel.add(resultadoCurva2, gbcRes);
        yr++;

        gbcRes.gridx = 0; gbcRes.gridy = yr;
        resultPanel.add(lblTorsion, gbcRes);
        gbcRes.gridx = 1;
        resultPanel.add(resultadoTorsion, gbcRes);
        yr++;

        gbcRes.gridx = 0; gbcRes.gridy = yr;
        resultPanel.add(lblIndiceUsado, gbcRes);
        gbcRes.gridx = 1;
        resultPanel.add(resultadoIndice, gbcRes);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        buttonPanel.setAlignmentX(LEFT_ALIGNMENT);

        btnCalcular = new JButton("Calcular");
        btnLimpiar = new JButton("Limpiar");

        btnCalcular.setFont(valueFont);
        btnLimpiar.setFont(valueFont);

        buttonPanel.add(btnCalcular);
        buttonPanel.add(btnLimpiar);

        leftPanel.add(inputPanel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 12)));
        leftPanel.add(resultPanel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 12)));
        leftPanel.add(buttonPanel);

        add(leftPanel, BorderLayout.CENTER);
        add(diagramPanel, BorderLayout.EAST);
    }

    public JTextField getTxtNombreLente() {
        return txtNombreLente;
    }

    public JTextField getTxtCurvaBase() {
        return txtCurvaBase;
    }

    public JComboBox<String> getComboMaterial() {
        return comboMaterial;
    }

    public JTextField getTxtIndiceManual() {
        return txtIndiceManual;
    }

    public JTextField getTxtEsferico() {
        return txtEsferico;
    }

    public JTextField getTxtCilindro() {
        return txtCilindro;
    }

    public JTextField getTxtEje() {
        return txtEje;
    }

    public JButton getBtnCalcular() {
        return btnCalcular;
    }

    public JButton getBtnLimpiar() {
        return btnLimpiar;
    }

    public LensDiagramPanel getDiagramPanel() {
        return diagramPanel;
    }

    public void setResultadoCurva1(String text) {
        resultadoCurva1.setText(text);
    }

    public void setResultadoCurva2(String text) {
        resultadoCurva2.setText(text);
    }

    public void setResultadoTorsion(String text) {
        resultadoTorsion.setText(text);
    }

    public void setResultadoIndice(String text) {
        resultadoIndice.setText(text);
    }

    public void setIndiceManualVisible(boolean visible) {
        lblIndiceManual.setVisible(visible);
        txtIndiceManual.setVisible(visible);
        revalidate();
        repaint();
    }

    public void limpiarFormulario() {
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
    }
}