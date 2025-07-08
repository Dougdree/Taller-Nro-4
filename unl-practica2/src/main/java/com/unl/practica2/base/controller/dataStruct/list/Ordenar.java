package com.unl.practica2.base.controller.dataStruct.list;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class Ordenar {
    public static void main(String[] args) {
        String[] datos = cargarDatos("data.txt");

        if (datos.length == 0) {
            System.out.println("No se cargaron datos.");
            return;
        }

        int[] numerosOriginales = new int[datos.length];
        for (int i = 0; i < datos.length; i++) {
            numerosOriginales[i] = Integer.parseInt(datos[i]);
        }

        // Copias para comparar ambos algoritmos
        int[] numerosQuick = numerosOriginales.clone();
        int[] numerosShell = numerosOriginales.clone();

        // Quicksort
        long startQuick = System.nanoTime();
        quickSort(numerosQuick, 0, numerosQuick.length - 1);
        long endQuick = System.nanoTime();

        // Shell Sort
        long startShell = System.nanoTime();
        shellSort(numerosShell);
        long endShell = System.nanoTime();

        System.out.println("Tiempo con Quicksort: " + (endQuick - startQuick) / 1_000_000.0 + "ns");
        System.out.println("Tiempo con Shell Sort: " + (endShell - startShell) / 1_000_000.0 + "ns");

       
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

    public static void quickSort(int[] arr, int begin, int end) {
        if (begin < end) {
            int partitionIndex = partition(arr, begin, end);
            quickSort(arr, begin, partitionIndex - 1);
            quickSort(arr, partitionIndex + 1, end);
        }
    }

    private static int partition(int arr[], int begin, int end) {
        int pivot = arr[end];
        int i = (begin - 1);

        for (int j = begin; j < end; j++) {
            if (arr[j] <= pivot) {
                i++;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        int temp = arr[i + 1];
        arr[i + 1] = arr[end];
        arr[end] = temp;

        return i + 1;
    }

    public static void shellSort(int[] arr) {
        int n = arr.length;

        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i++) {
                int temp = arr[i];
                int j = i;
                while (j >= gap && arr[j - gap] > temp) {
                    arr[j] = arr[j - gap];
                    j -= gap;
                }
                arr[j] = temp;
            }
        }
    }

    public static void imprimir(int[] arr) {
        for (int num : arr) {
            System.out.print(num + " ");
        }
        System.out.println();
    }

}
