package ui;

import controller.CalculadoraController;
import repository.HistorialRepository;
import service.CalculadoraLentesService;

import javax.swing.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        setTitle("Calculadora de Lentes");
        setSize(1080, 660);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane tabs = new JTabbedPane();

        CalculadoraPanel calculadoraPanel = new CalculadoraPanel();
        HistorialPanel historialPanel = new HistorialPanel();

        new CalculadoraController(
                calculadoraPanel,
                historialPanel,
                new CalculadoraLentesService(),
                new HistorialRepository()
        );

        tabs.addTab("Calculadora", calculadoraPanel);
        tabs.addTab("Historial", historialPanel);

        setContentPane(tabs);
    }
}