package com.unl.practica2.base.controller.dataStruct.list;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Contar {

    public static void main(String[] args) {
        String[] datos = cargarDatos("data.txt");

        if (datos.length == 0) {
            System.out.println("No se cargaron datos.");
            return;
        }

        String[] repetidos = obtenerRepetidos(datos);

        System.out.println("Repetidos detectados:");
        for (int i = 0; i < repetidos.length; i++) {
            if (repetidos[i] != null) {
                System.out.print(repetidos[i] + " ");
            }
        }
        System.out.println();

        long startArray = System.nanoTime();
        int cantidadArray = contarConArray(datos, repetidos);
        long endArray = System.nanoTime();

        long startList = System.nanoTime();
        int cantidadLista = contarConLinkedList(datos, repetidos);
        long endList = System.nanoTime();

        System.out.println("\nRepetidos con Arreglo: " + cantidadArray);
        System.out.println("Repetidos con Lista Enlazada: " + cantidadLista);

        System.out.println("\nTiempo con arreglo: " + (endArray - startArray) / 1_000_000.0 + " ms");
        System.out.println("Tiempo con lista enlazada: " + (endList - startList) / 1_000_000.0 + " ms");
    }

    public static String[] cargarDatos(String ruta) {
        String[] temp = new String[100000];
        int count = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (count == temp.length) {
                    String[] nuevo = new String[temp.length * 2];
                    System.arraycopy(temp, 0, nuevo, 0, temp.length);
                    temp = nuevo;
                }
                temp[count++] = linea.trim();
            }
        } catch (IOException e) {
            System.err.println("Error leyendo archivo: " + e.getMessage());
        }

        String[] resultado = new String[count];
        System.arraycopy(temp, 0, resultado, 0, count);
        return resultado;
    }

    public static String[] obtenerRepetidos(String[] data) {
        String[] arreglo = new String[data.length];
        int index = 0;
        String[] repetidos = new String[data.length];
        int repCount = 0;

        for (int i = 0; i < data.length; i++) {
            boolean duplicado = false;
            for (int j = 0; j < index; j++) {
                if (arreglo[j].equals(data[i])) {
                    duplicado = true;
                    boolean yaContado = false;
                    for (int k = 0; k < repCount; k++) {
                        if (repetidos[k].equals(data[i])) {
                            yaContado = true;
                            break;
                        }
                    }
                    if (!yaContado) {
                        repetidos[repCount++] = data[i];
                    }
                    break;
                }
            }
            arreglo[index++] = data[i];
        }

        String[] resultado = new String[repCount];
        System.arraycopy(repetidos, 0, resultado, 0, repCount);
        return resultado;
    }

    public static int contarConArray(String[] data, String[] repetidos) {
        int count = 0;
        for (int i = 0; i < repetidos.length; i++) {
            if (repetidos[i] != null) {
                count++;
            }
        }
        return count;
    }

    public static int contarConLinkedList(String[] data, String[] repetidos) {
        LinkedList<String> lista = new LinkedList<>();
        int count = 0;

        for (int i = 0; i < repetidos.length; i++) {
            lista.add(repetidos[i]);
            count++;
        }

        return count;
    }
}
