package model;

public enum MaterialLente {
    BOSTON_ES("Boston ES", 1.45),
    BOSTON_EO("Boston EO", 1.429),
    BOSTON_XO("Boston XO", 1.415),
    INGRESAR_VALOR("Ingresar valor", -1);

    private final String nombre;
    private final double indice;

    MaterialLente(String nombre, double indice) {
        this.nombre = nombre;
        this.indice = indice;
    }

    public String getNombre() {
        return nombre;
    }

    public double getIndice() {
        return indice;
    }

    @Override
    public String toString() {
        return nombre;
    }
}