package com.unl.practica2.base.controller.dataStruct.graphs;

public class UndirectedLabelGraph<E> extends DirectLabelGraph<E> {

	public UndirectedLabelGraph(Integer nro_vertex, Class clazz) {
        super(nro_vertex, clazz);
        //TODO Auto-generated constructor stub
    }

    @Override
    public void insert_label(E o, E d, Float peso) {
        if (isLabelsGraph()) {
            insert(getVertex(o), getVertex(d), peso);
            insert(getVertex(d), getVertex(o), peso);
        }
    }
    public static void main(String[] args) {
		// Crear un grafo no dirigido con etiquetas, 4 vértices
		UndirectedLabelGraph<String> graph = new UndirectedLabelGraph<>(4, String.class);
	
		// Etiquetar los vértices
		graph.label_vertex(1, "A");
		graph.label_vertex(2, "B");
		graph.label_vertex(3, "C");
		graph.label_vertex(4, "D");
	
		// Insertar aristas bidireccionales
		graph.insert_label("A", "B", 1.0f);
		graph.insert_label("A", "C", 2.5f);
		graph.insert_label("B", "D", 3.0f);
		graph.insert_label("C", "D", 4.5f);
	
		// Imprimir el grafo
		System.out.println(graph.toString());
	
		// Probar adjacencies_label
		System.out.println("Adyacencias de A:");
		for (Adjacency adj : graph.adjacencies_label("A").toArray()) {
			System.out.println(" -> " + graph.getLabel(adj.getDestiny()) + " (peso: " + adj.getWeigth() + ")");
		}
	}

}
