import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class HuffmanCompressor {

    private Map<String, Integer> frequencies;
    private Node huffmanTreeRoot;
    private Map<String, String> huffmanCodes;
    private String compressedText;

    public HuffmanCompressor(String textBlock) {
        this.frequencies = calculateFrequencies(textBlock);
        this.huffmanTreeRoot = buildHuffmanTree(this.frequencies);
        
        this.huffmanCodes = new HashMap<>();
        generateCodes(this.huffmanTreeRoot, "", this.huffmanCodes);
        
        this.compressedText = compressText(textBlock, this.huffmanCodes);
    }

    public Map<String, String> getHuffmanCodes() {
        return this.huffmanCodes;
    }

    public String getCompressedText() {
        return this.compressedText;
    }
    
    public Map<String, Integer> getFrequencies() {
        return this.frequencies;
    }

    private Map<String, Integer> calculateFrequencies(String textBlock) {
        Map<String, Integer> wordFrequencies = new HashMap<>();
        String[] words = textBlock.split("\\s+");
        for (String word : words) {
            String cleanWord = word.toLowerCase().replaceAll("[.,!?;]", "");
            if (cleanWord.isEmpty()) continue;
            wordFrequencies.put(cleanWord, wordFrequencies.getOrDefault(cleanWord, 0) + 1);
        }
        
        printSortedFrequencies(wordFrequencies);
        
        return wordFrequencies;
    }
    
    private void printSortedFrequencies(Map<String, Integer> wordFrequencies) {
        List<Map.Entry<String, Integer>> entryList = new ArrayList<>(wordFrequencies.entrySet());
        entryList.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));

        System.out.println("Frequências (em ordem decrescente):");
        for (Map.Entry<String, Integer> entry : entryList) {
            System.out.println(
                "  - Palavra: '" + entry.getKey() + 
                "', Ocorrências: " + entry.getValue()
            );
        }
    }

    private Node buildHuffmanTree(Map<String, Integer> wordFrequencies) {
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>();
        for (Map.Entry<String, Integer> entry : wordFrequencies.entrySet()) {
            priorityQueue.add(new Node(entry.getKey(), entry.getValue()));
        }

        while (priorityQueue.size() > 1) {
            Node left = priorityQueue.poll();
            Node right = priorityQueue.poll();
            int sumFrequency = left.frequency + right.frequency;
            Node parent = new Node(sumFrequency, left, right);
            priorityQueue.add(parent);
        }
        return priorityQueue.poll();
    }

    private void generateCodes(Node node, String currentCode, Map<String, String> huffmanCodes) {
        if (node == null) return;
        if (node.isLeaf()) {
            huffmanCodes.put(node.word, currentCode.isEmpty() ? "0" : currentCode);
            return;
        }
        generateCodes(node.left, currentCode + "0", huffmanCodes);
        generateCodes(node.right, currentCode + "1", huffmanCodes);
    }

    private String compressText(String textBlock, Map<String, String> huffmanCodes) {
        StringBuilder compressedBuilder = new StringBuilder();
        String[] words = textBlock.split("\\s+");
        for (String word : words) {
            String cleanWord = word.toLowerCase().replaceAll("[.,!?;]", "");
            if (cleanWord.isEmpty()) continue;
            String code = huffmanCodes.get(cleanWord);
            if (code != null) {
                compressedBuilder.append(code);
            }
        }
        return compressedBuilder.toString();
    }
}