public class Node implements Comparable<Node> {
    
    String word; 
    int frequency; 
    
    Node left; 
    Node right; 

    public Node(String word, int frequency) {
        this.word = word;
        this.frequency = frequency;
        this.left = null;
        this.right = null;
    }

    public Node(int frequency, Node left, Node right) {
        this.word = null; 
        this.frequency = frequency;
        this.left = left;
        this.right = right;
    }

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