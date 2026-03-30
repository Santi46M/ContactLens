package model;

public class ResultadoCalculo {
    private final double curva1;
    private final double curva2;
    private final double torsion;
    private final double indiceUsado;

    public ResultadoCalculo(double curva1, double curva2, double torsion, double indiceUsado) {
        this.curva1 = curva1;
        this.curva2 = curva2;
        this.torsion = torsion;
        this.indiceUsado = indiceUsado;
    }

    public double getCurva1() {
        return curva1;
    }

    public double getCurva2() {
        return curva2;
    }

    public double getTorsion() {
        return torsion;
    }

    public double getIndiceUsado() {
        return indiceUsado;
    }
}