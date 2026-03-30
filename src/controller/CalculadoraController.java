package controller;

import model.ResultadoCalculo;
import repository.HistorialRepository;
import service.CalculadoraLentesService;
import ui.CalculadoraPanel;
import ui.HistorialPanel;

import javax.swing.*;

public class CalculadoraController {

    private final CalculadoraPanel calculadoraPanel;
    private final HistorialPanel historialPanel;
    private final CalculadoraLentesService calculadoraService;
    private final HistorialRepository historialRepository;

    public CalculadoraController(CalculadoraPanel calculadoraPanel,
                                 HistorialPanel historialPanel,
                                 CalculadoraLentesService calculadoraService,
                                 HistorialRepository historialRepository) {
        this.calculadoraPanel = calculadoraPanel;
        this.historialPanel = historialPanel;
        this.calculadoraService = calculadoraService;
        this.historialRepository = historialRepository;

        initController();
    }

    private void initController() {
        calculadoraPanel.getBtnCalcular().addActionListener(e -> calcular());
        calculadoraPanel.getBtnLimpiar().addActionListener(e -> limpiar());
        calculadoraPanel.getComboMaterial().addActionListener(e -> actualizarIndiceManual());
    }

    private void actualizarIndiceManual() {
        boolean manual = "Ingresar valor".equals(calculadoraPanel.getComboMaterial().getSelectedItem());
        calculadoraPanel.setIndiceManualVisible(manual);
    }

    private void calcular() {
        try {
            String nombreLente = calculadoraPanel.getTxtNombreLente().getText().trim();
            if (nombreLente.isEmpty()) {
                throw new NumberFormatException("Nombre vacío");
            }

            double curvaBase = calculadoraService.parseNumero(calculadoraPanel.getTxtCurvaBase().getText());
            double esferico = calculadoraService.parseNumero(calculadoraPanel.getTxtEsferico().getText());
            double cilindro = calculadoraService.parseNumero(calculadoraPanel.getTxtCilindro().getText());
            double eje = calculadoraService.parseNumero(calculadoraPanel.getTxtEje().getText());

            if (eje < 0 || eje > 180) {
                throw new NumberFormatException("El eje debe estar entre 0 y 180.");
            }

            String materialSeleccionado = calculadoraPanel.getComboMaterial().getSelectedItem().toString();
            String indiceManual = calculadoraPanel.getTxtIndiceManual().getText();

            ResultadoCalculo resultado = calculadoraService.calcular(
                    curvaBase,
                    materialSeleccionado,
                    indiceManual,
                    esferico,
                    cilindro
            );

            calculadoraPanel.setResultadoCurva1(String.format("%.2f mm", resultado.getCurva1()));
            calculadoraPanel.setResultadoCurva2(String.format("%.2f mm", resultado.getCurva2()));
            calculadoraPanel.setResultadoTorsion(String.format("%.2f mm", resultado.getTorsion()));
            calculadoraPanel.setResultadoIndice(String.format("%.3f", resultado.getIndiceUsado()));

            calculadoraPanel.getDiagramPanel().updateData(
                    resultado.getCurva1(),
                    resultado.getCurva2(),
                    eje
            );

            historialRepository.guardarCalculo(
                    nombreLente,
                    curvaBase,
                    materialSeleccionado,
                    resultado.getIndiceUsado(),
                    esferico,
                    cilindro,
                    resultado.getCurva1(),
                    resultado.getCurva2(),
                    resultado.getTorsion()
            );

            historialPanel.recargarHistorial();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                    calculadoraPanel,
                    "Ingresá valores válidos, completá el nombre del lente y un eje entre 0 y 180.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        } catch (ArithmeticException ex) {
            JOptionPane.showMessageDialog(
                    calculadoraPanel,
                    ex.getMessage(),
                    "Error de cálculo",
                    JOptionPane.ERROR_MESSAGE
            );
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(
                    calculadoraPanel,
                    "Ocurrió un error al calcular.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void limpiar() {
        calculadoraPanel.limpiarFormulario();
    }
}