package model;

public class Lente {
    private String nombre;
    private double curvaBase;
    private String material;
    private double indice;
    private double esferico;
    private double cilindro;
    private double eje;

    public Lente() {
    }

    public Lente(String nombre, double curvaBase, String material, double indice,
                 double esferico, double cilindro, double eje) {
        this.nombre = nombre;
        this.curvaBase = curvaBase;
        this.material = material;
        this.indice = indice;
        this.esferico = esferico;
        this.cilindro = cilindro;
        this.eje = eje;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public double getCurvaBase() { return curvaBase; }
    public void setCurvaBase(double curvaBase) { this.curvaBase = curvaBase; }

    public String getMaterial() { return material; }
    public void setMaterial(String material) { this.material = material; }

    public double getIndice() { return indice; }
    public void setIndice(double indice) { this.indice = indice; }

    public double getEsferico() { return esferico; }
    public void setEsferico(double esferico) { this.esferico = esferico; }

    public double getCilindro() { return cilindro; }
    public void setCilindro(double cilindro) { this.cilindro = cilindro; }

    public double getEje() { return eje; }
    public void setEje(double eje) { this.eje = eje; }
}