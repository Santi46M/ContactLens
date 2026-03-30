package model;

import java.util.Date;

public class HistorialLente {
    private Date fecha;
    private String nombreLente;
    private double curvaBase;
    private String material;
    private double indice;
    private double esferico;
    private double cilindro;
    private double curva1;
    private double curva2;
    private double torsion;

    public HistorialLente(Date fecha, String nombreLente, double curvaBase, String material,
                          double indice, double esferico, double cilindro,
                          double curva1, double curva2, double torsion) {
        this.fecha = fecha;
        this.nombreLente = nombreLente;
        this.curvaBase = curvaBase;
        this.material = material;
        this.indice = indice;
        this.esferico = esferico;
        this.cilindro = cilindro;
        this.curva1 = curva1;
        this.curva2 = curva2;
        this.torsion = torsion;
    }

    public Date getFecha() { return fecha; }
    public String getNombreLente() { return nombreLente; }
    public double getCurvaBase() { return curvaBase; }
    public String getMaterial() { return material; }
    public double getIndice() { return indice; }
    public double getEsferico() { return esferico; }
    public double getCilindro() { return cilindro; }
    public double getCurva1() { return curva1; }
    public double getCurva2() { return curva2; }
    public double getTorsion() { return torsion; }
}