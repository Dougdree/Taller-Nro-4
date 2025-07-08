package com.unl.practica2.base.controller.practica3;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class LabyrinthVisualizer extends JPanel {
    private final char[][] maze;
    private final Set<String> path;
    private final int cellSize = 30; // Tamaño de cada celda

    public LabyrinthVisualizer(char[][] maze, Set<String> path) {
        this.maze = maze;
        this.path = path;
        setPreferredSize(new Dimension(maze[0].length * cellSize, maze.length * cellSize));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                String label = i + "," + j;

                if (maze[i][j] == '0') {
                    g.setColor(Color.BLACK);
                } else if (maze[i][j] == 'S') {
                    g.setColor(Color.GREEN);
                } else if (maze[i][j] == 'E') {
                    g.setColor(Color.RED);
                } else if (path.contains(label)) {
                    g.setColor(Color.BLUE);
                } else {
                    g.setColor(Color.WHITE);
                }

                g.fillRect(j * cellSize, i * cellSize, cellSize, cellSize);
                g.setColor(Color.GRAY);
                g.drawRect(j * cellSize, i * cellSize, cellSize, cellSize);
            }
        }
    }

    public static void showLabyrinth(char[][] maze, Set<String> path) {
        JFrame frame = new JFrame("Laberinto Visual");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        LabyrinthVisualizer panel = new LabyrinthVisualizer(maze, path);

        // ✅ Envolver en JScrollPane para permitir scroll si el laberinto es grande
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setPreferredSize(new Dimension(800, 800)); // Tamaño ventana visible

        frame.add(scrollPane);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
