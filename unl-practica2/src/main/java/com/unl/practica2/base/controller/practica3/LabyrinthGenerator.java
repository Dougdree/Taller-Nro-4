package com.unl.practica2.base.controller.practica3;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.unl.practica2.base.controller.dataStruct.graphs.UndirectedLabelGraph;
import com.unl.practica2.base.controller.dataStruct.list.LinkedList;

public class LabyrinthGenerator {
    private char[][] matrizGlobal;
    public String generar(int r, int c) {
        // Inicializar matriz de muros
        StringBuilder s = new StringBuilder(c);
        for (int x = 0; x < c; x++) {
            s.append('0');
        }
        char[][] maz = new char[r][c];
        for (int x = 0; x < r; x++) {
            maz[x] = s.toString().toCharArray();
        }

        // Punto inicial
        Point st = new Point((int) (Math.random() * r), (int) (Math.random() * c), null);
        maz[st.r][st.c] = 'S';

        // Lista frontera
        LinkedList<Point> frontier = new LinkedList<>();
        agregarVecinos(maz, st, frontier);

        Point last = null;

        while (!frontier.isEmpty()) {
            // Convertir a array y escoger aleatorio
            Point[] puntos = frontier.toArray();
            int index = (int) (Math.random() * puntos.length);
            Point cu = puntos[index];

            // Eliminar cu de frontier
            int pos = frontier.indexOf(cu);
            if (pos != -1) {
                try {
                    frontier.delete(pos);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            Point op = cu.opposite();
            try {
                if (maz[cu.r][cu.c] == '0' && maz[op.r][op.c] == '0') {
                    maz[cu.r][cu.c] = '1';
                    maz[op.r][op.c] = '1';
                    last = op;
                    agregarVecinos(maz, op, frontier);
                }
            } catch (Exception e) {
                // Ignorar errores de índice
            }

            if (frontier.isEmpty() && last != null) {
                maz[last.r][last.c] = 'E';
            }
        }

        // Construir string final
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < r; i++) {
            String aux = "";
            for (int j = 0; j < c; j++) {
                aux += maz[i][j] + ",";
            }
            aux = aux.substring(0, aux.length() - 1);
            result.append(aux).append("\n");
        }

        return result.toString();
    }

    private void agregarVecinos(char[][] maz, Point p, LinkedList<Point> frontier) {
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                if (x == 0 && y == 0 || x != 0 && y != 0) {
                    continue;
                }
                try {
                    if (maz[p.r + x][p.c + y] == '1') {
                        continue;
                    }
                } catch (Exception e) {
                    continue;
                }
                frontier.add(new Point(p.r + x, p.c + y, p));
            }
        }
    }

    public void crearGrafoDesdeMatriz(char[][] laberinto) {
        this.matrizGlobal = laberinto;
        int filas = laberinto.length;
        int columnas = laberinto[0].length;

        // Contar celdas libres
        int totalVertices = 0;
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (laberinto[i][j] == '1' || laberinto[i][j] == 'S' || laberinto[i][j] == 'E') {
                    totalVertices++;
                }
            }
        }

        UndirectedLabelGraph<String> graph = new UndirectedLabelGraph<>(totalVertices, String.class);

        int contador = 1;
        String start = "";
        String end = "";

        // Etiquetar vértices
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (laberinto[i][j] == '1' || laberinto[i][j] == 'S' || laberinto[i][j] == 'E') {
                    String etiqueta = i + "," + j;
                    graph.label_vertex(contador, etiqueta);

                    if (laberinto[i][j] == 'S') {
                        start = etiqueta;
                    }
                    if (laberinto[i][j] == 'E') {
                        end = etiqueta;
                    }

                    contador++;
                }
            }
        }

        // Conectar vecinos
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (laberinto[i][j] == '1' || laberinto[i][j] == 'S' || laberinto[i][j] == 'E') {
                    String actual = i + "," + j;

                    // Derecha
                    if (j + 1 < columnas && (laberinto[i][j + 1] == '1' || laberinto[i][j + 1] == 'S' || laberinto[i][j + 1] == 'E')) {
                        String derecha = i + "," + (j + 1);
                        graph.insert_label(actual, derecha, 1.0f);
                    }

                    // Abajo
                    if (i + 1 < filas && (laberinto[i + 1][j] == '1' || laberinto[i + 1][j] == 'S' || laberinto[i + 1][j] == 'E')) {
                        String abajo = (i + 1) + "," + j;
                        graph.insert_label(actual, abajo, 1.0f);
                    }
                }
            }
        }

        // Imprimir para verificar
        System.out.println(graph.toString());
        System.out.println("Start: " + start);
        System.out.println("End: " + end);
        dijkstra(graph, start, end);
    }


    public void dijkstra(UndirectedLabelGraph<String> graph, String start, String end) {
        Map<String, Integer> dist = new HashMap<>();
        Map<String, String> prev = new HashMap<>();
        LinkedList<String> queue = new LinkedList<>();

        for (int i = 1; i <= graph.nro_vertex(); i++) {
            String v = graph.getLabel(i);
            dist.put(v, Integer.MAX_VALUE);
            prev.put(v, null);
            queue.add(v);
        }

        dist.put(start, 0);

        while (!queue.isEmpty()) {
            String u = null;
            int minDist = Integer.MAX_VALUE;
            for (String v : queue.toArray()) {
                if (dist.get(v) < minDist) {
                    minDist = dist.get(v);
                    u = v;
                }
            }

            if (u == null) break;

            int pos = queue.indexOf(u);
            try {
                queue.delete(pos);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (u.equals(end)) {
                break;
            }

            for (var adj : graph.adjacencies_label(u).toArray()) {
                String v = graph.getLabel(adj.getDestiny());
                if (!queueContains(queue, v)) continue;

                int alt = dist.get(u) + 1;
                if (alt < dist.get(v)) {
                    dist.put(v, alt);
                    prev.put(v, u);
                }
            }
        }

        LinkedList<String> path = new LinkedList<>();
        String current = end;
        while (current != null) {
            path.add(current);
            current = prev.get(current);
        }

        System.out.println("Camino encontrado:");
        for (int i = path.getLength() - 1; i >= 0; i--) {
            System.out.print(path.get(i));
            if (i != 0) System.out.print(" -> ");
        }
        System.out.println();

        Set<String> pathSet = new HashSet<>();
        for (String v : path.toArray()) {
            pathSet.add(v);
        }

        // Mostrar ventana visual
        LabyrinthVisualizer.showLabyrinth(this.matrizGlobal, pathSet);



    }

    private boolean queueContains(LinkedList<String> list, String value) {
        for (String v : list.toArray()) {
            if (v.equals(value)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        LabyrinthGenerator gen = new LabyrinthGenerator();
        String lab = gen.generar(30, 30);
        System.out.println(lab);

        String[] filas = lab.split("\n");
        char[][] matriz = new char[filas.length][];
        for (int i = 0; i < filas.length; i++) {
            String[] columnas = filas[i].split(",");
            matriz[i] = new char[columnas.length];
            for (int j = 0; j < columnas.length; j++) {
                matriz[i][j] = columnas[j].charAt(0);
            }
        }

        gen.crearGrafoDesdeMatriz(matriz);
    }



}
