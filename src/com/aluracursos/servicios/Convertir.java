package com.aluracursos.servicios;

import com.aluracursos.modelos.Menu;
import com.aluracursos.modelos.Monedas;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;



import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.Scanner;

public class Convertir {
    Scanner elegir = new Scanner(System.in);
    Monedas dinero;

    Gson gson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
            .setPrettyPrinting()
            .create();
    double cantidadDolares;
    double cantidadMOnedaAConvertir;

    public void conversor() {
        double pesoArgentino = 0;
        double pesoColombiano = 0;
        double realBrasilero = 0;



        while (true) {

            dinero = new Monedas();

            // Menu con las opciones para ser elegidas por el Usuario.

            int a;
            a = 1;
            System.out.println("************************************");
            System.out.println("...Seleccione una Opción...");
            for (Menu opcion : Menu.values()) {
                System.out.println(a + ") " + opcion.toString().replace("_", " "));
                ++a;
            }
            System.out.println("************************************");

            //Salida del Loop Y/o verifico que elija la opción numérica adecuada.

            String codigo = elegir.next();
            if (codigo.equalsIgnoreCase("7")) {
                break;
            } else if (!codigo.matches("[1-7]*")) {
                System.out.println("Debe introducir un numero del 1 al 7");

            }

            //Llamada y lectura de la Api.

            try {
                HttpClient cliente = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create("https://v6.exchangerate-api.com/v6/c288cc3cc0db9fa78e3a7c39/latest/USD"))
                        .build();
                HttpResponse<String> response = cliente
                        .send(request, HttpResponse.BodyHandlers.ofString());
                String json = response.body();

                //Busqueda de los valores de conversion de Monedas.

                Map<?, ?> valorConv = gson.fromJson(json, Map.class);
                String var = valorConv.get("conversion_rates").toString();
                String[] array = var.split(",");
                for (String prueba : array) {
                    if (prueba.contains("ARS")) {
                        pesoArgentino = currencyCode(prueba);
                    }
                    if (prueba.contains("COP")) {
                        pesoColombiano = currencyCode(prueba);
                    }
                    if (prueba.contains("BRL")) {
                        realBrasilero =  currencyCode(prueba);
                    }

                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            //Cálculos de los Ratios de conversión.

            if (codigo.equalsIgnoreCase("1")) {
                System.out.println(" Cuantos Dolares quiere cambiar a Pesos Argentinos: ");
                cantidadDolares = cifraAConvertir();
                if (cantidadDolares == 0){
                    continue;
                }
                dinero.setValorConversion(convertirDolares(cantidadDolares, pesoArgentino));
                dinero.setMonedaDeConversion("Dolares");
                dinero.setMonedaAConvertir("Pesos Argentinos");
                System.out.println(dinero.toString());
            }
            if (codigo.equalsIgnoreCase("3")) {
                System.out.println(" Cuantos Dolares quiere cambiar a Reales Brasileros: ");
                cantidadDolares = cifraAConvertir();
                if (cantidadDolares == 0){
                    continue;
                }
                dinero.setValorConversion(convertirDolares(cantidadDolares, realBrasilero));
                dinero.setMonedaDeConversion("Dolares");
                dinero.setMonedaAConvertir("Reales Brasileros");
                System.out.println(dinero.toString());
            }

            if (codigo.equalsIgnoreCase("5")) {
                System.out.println(" Cuantos Dolares quiere cambiar a Pesos Colombianos: ");
                cantidadDolares = cifraAConvertir();
                if (cantidadDolares == 0){
                    continue;
                }
                dinero.setValorConversion(convertirDolares(cantidadDolares, pesoColombiano));
                dinero.setMonedaDeConversion("Dolares");
                dinero.setMonedaAConvertir("Pesos Colombianos");
                System.out.println(dinero.toString());
            }

            if (codigo.equalsIgnoreCase("2")) {
                System.out.println(" Cuantos Pesos Argentinos quiere cambiar a Dolares: ");
                cantidadMOnedaAConvertir = cifraAConvertir();
                if (cantidadMOnedaAConvertir == 0){
                    continue;
                }
                dinero.setValorConversion(otraMonedaADolar(cantidadMOnedaAConvertir, pesoArgentino));
                dinero.setMonedaDeConversion("Pesos Argentinos");
                dinero.setMonedaAConvertir("Dolares");
                System.out.println(dinero.toString());
            }

            if (codigo.equalsIgnoreCase("4")) {
                System.out.println(" Cuantos Reales Brasileros quiere cambiar a Dolares: ");
                cantidadMOnedaAConvertir = cifraAConvertir();
                if (cantidadMOnedaAConvertir == 0){
                    continue;
                }
                dinero.setValorConversion(otraMonedaADolar(cantidadMOnedaAConvertir, realBrasilero));
                dinero.setMonedaDeConversion("Reales Brasileros");
                dinero.setMonedaAConvertir("Dolares");
                System.out.println(dinero.toString());
            }

            if (codigo.equalsIgnoreCase("6")) {
                System.out.println(" Cuantos Pesos Colombianos quiere cambiar a Dolares: ");
                cantidadMOnedaAConvertir = cifraAConvertir();
                if (cantidadMOnedaAConvertir == 0){
                    continue;
                }
                dinero.setValorConversion(otraMonedaADolar(cantidadMOnedaAConvertir, pesoColombiano));
                dinero.setMonedaDeConversion("Pesos Colombianos");
                dinero.setMonedaAConvertir("Dolares");
                System.out.println(dinero.toString());
            }


        }

    }

    public double convertirDolares(double cantidadDolares, double valorMonedaAConvertir) {
        return cantidadDolares * valorMonedaAConvertir;
    }

    public double otraMonedaADolar(double cantidadMOnedaAConvertir, double valorMonedaAConvertir) {
        return cantidadMOnedaAConvertir / valorMonedaAConvertir;
    }

    public Double currencyCode(String prueba) {
        return Double.valueOf(prueba.substring(5, prueba.length() - 1));
    }

    public Double cifraAConvertir(){
        String cifra = elegir.next();
        if (!cifra.matches("[a-zA-Z]*")) {
            if (cifra.matches("[0-9,\\.]*")) {
                return Double.valueOf(cifra);
            } else {
                System.out.println("Debe introducir un valor Numérico sin Letras o símbolos salvo el (.) punto decimal");
            }
        }else {
            System.out.println("Debe introducir un valor Numérico sin Letras o símbolos salvo el (.) punto decimal");
        }
        return 0.0;
    }
}

