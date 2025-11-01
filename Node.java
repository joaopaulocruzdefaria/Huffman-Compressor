
 // Representa um nó na Árvore de Huffman.
 // Esta classe implementa a interface `Comparable` 
 // para permitir que seja usada
 // em uma fila de prioridade, ordenando os nós
 // pela sua frequência (peso).


public class Node implements Comparable<Node> {
    
    String word; 
    int frequency; 
    
    Node left; 
    Node right; 
    // Construtor para um nó folha (nó que contém a palavra)
    public Node(String word, int frequency) {
        this.word = word;
        this.frequency = frequency;
        this.left = null;
        this.right = null;
    }
    
    // Construtor para um nó intermédiario que serve pra codificar a 
    // Árvore de derivação

    public Node(int frequency, Node left, Node right) {
        this.word = null; 
        this.frequency = frequency;
        this.left = left;
        this.right = right;
    }
    
    // Métodos Auxiliares

    public boolean isLeaf() {
        return this.left == null && this.right == null;
    }

    @Override
    public int compareTo(Node other) {
        return this.frequency - other.frequency;
    }

    @Override
    public String toString() {
        if (isLeaf()) {
            return "['" + word + "':" + frequency + "]";
        }
        return "[Freq:" + frequency + "]";
    }
}