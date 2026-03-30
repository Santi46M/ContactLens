package service;

import model.ResultadoCalculo;

public class CalculadoraLentesService {

    public double parseNumero(String texto) {
        return Double.parseDouble(texto.trim().replace(",", "."));
    }

    public double obtenerIndice(String material, String indiceManual) {
        return switch (material) {
            case "Boston ES" -> 1.45;
            case "Boston EO" -> 1.429;
            case "Boston XO" -> 1.415;
            case "Ingresar valor" -> {
                if (indiceManual != null && !indiceManual.trim().isEmpty()) {
                    yield parseNumero(indiceManual);
                }
                throw new NumberFormatException("Índice manual vacío");
            }
            default -> 1.45;
        };
    }

    public double calcularCurva(double curvaBase, double indice, double graduacion) {
        if (indice == 1.0) {
            throw new ArithmeticException("El índice no puede ser 1.");
        }

        double denominador = graduacion / (1000.0 * (indice - 1.0)) + 1.0 / curvaBase;

        if (Math.abs(denominador) < 1.0E-7) {
            throw new ArithmeticException("Resultado inestable.");
        }

        return 1.0 / denominador;
    }

    public double calcularTorsionAproximada(double curvaBase, double indice, double esferico, double cilindro) {
        if (indice == 1.0) {
            throw new ArithmeticException("El índice no puede ser 1.");
        }

        double potenciaMedia = esferico + cilindro / 2.0;
        double a = (indice - 1.0) * 1000.0;
        double denominador = a + potenciaMedia * curvaBase;

        if (Math.abs(denominador) < 1.0E-7) {
            throw new ArithmeticException("Resultado inestable.");
        }

        return (indice - 1.0) * curvaBase * curvaBase * 1000.0 * Math.abs(cilindro)
                / (denominador * denominador);
    }

    public ResultadoCalculo calcular(double curvaBase, String material, String indiceManual,
                                     double esferico, double cilindro) {
        double indice = obtenerIndice(material, indiceManual);
        double curva1 = calcularCurva(curvaBase, indice, esferico);
        double curva2 = calcularCurva(curvaBase, indice, esferico + cilindro);
        double torsion = calcularTorsionAproximada(curvaBase, indice, esferico, cilindro);

        return new ResultadoCalculo(curva1, curva2, torsion, indice);
    }
}