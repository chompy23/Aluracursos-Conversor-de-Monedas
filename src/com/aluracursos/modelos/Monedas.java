package com.aluracursos.modelos;

public class Monedas {
    private double valorConversion;
    private String monedaDeConversion;
    private String monedaAConvertir;

    public Monedas() {
    }

    public Monedas(String monedaAConvertir, String monedaDeConversion, double valorConversion) {
        this.monedaAConvertir = monedaAConvertir;
        this.monedaDeConversion = monedaDeConversion;
        this.valorConversion = valorConversion;
    }

    public double getValorConversion() {
        return valorConversion;
    }

    public void setValorConversion(double valorConversion) {
        this.valorConversion = valorConversion;
    }

    public String getMonedaDeConversion() {
        return monedaDeConversion;
    }

    public void setMonedaDeConversion(String monedaDeConversion) {
        this.monedaDeConversion = monedaDeConversion;
    }

    public String getMonedaAConvertir() {
        return monedaAConvertir;
    }

    public void setMonedaAConvertir(String monedaAConvertir) {
        this.monedaAConvertir = monedaAConvertir;
    }

    @Override
    public String toString() {
        return  "###################################################################"+ "\n"+
                "# Moneda de Conversión :"+"(" + monedaDeConversion + ") "+ "\n"+
                "###################################################################"+"\n"+
                "# Moneda a Convertir :"+"(" + monedaAConvertir + ")"+
                "  Cantidad ="+"(" + valorConversion +")"+"\n"+
                "###################################################################";
    }
}
