import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        Path inputPath = Paths.get("data/input.dat");
        Path outputPath = Paths.get("data/output.dat");

        try {
            File dataDir = new File("data");
            if (!dataDir.exists()) {
                dataDir.mkdirs();
            }
            Files.deleteIfExists(outputPath);

            String fullContent = Files.readString(inputPath);
            String[] textBlocks = fullContent.split("\\n\\n");

            for (String block : textBlocks) {
                if (block.isEmpty()) continue;

                System.out.println("--- Processando Bloco ---");
                
                HuffmanCompressor compressor = new HuffmanCompressor(block);

                Map<String, String> huffmanCodes = compressor.getHuffmanCodes();
                String compressedText = compressor.getCompressedText();

                System.out.println("Códigos Gerados: " + huffmanCodes);
                System.out.println(
                    "Texto Comprimido: (primeiros 100 chars) " +
                    (compressedText.length() > 100
                        ? compressedText.substring(0, 100) + "..."
                        : compressedText)
                );
                
                writeOutput(outputPath, huffmanCodes, compressedText);
                
                System.out.println("--------------------------\n");
            }
            
            System.out.println(">>> Processamento concluído! Verifique o arquivo 'data/output.dat'.");

        } catch (IOException e) {
            System.err.println("Ocorreu um erro: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void writeOutput(Path outputPath, Map<String, String> huffmanCodes, String compressedText) 
            throws IOException {
        
        StringBuilder outputBuilder = new StringBuilder();
        outputBuilder.append("--- NOVO BLOCO DE TEXTO ---\n");
        
        outputBuilder.append("[MAPA DE CÓDIGOS]\n");
        for (Map.Entry<String, String> entry : huffmanCodes.entrySet()) {
            outputBuilder.append(entry.getKey())
                         .append(":")
                         .append(entry.getValue())
                         .append("\n");
        }
        
        outputBuilder.append("[TEXTO COMPRIMIDO]\n");
        outputBuilder.append(compressedText);
        outputBuilder.append("\n\n");

        Files.writeString(
            outputPath,
            outputBuilder.toString(),
            StandardOpenOption.CREATE,
            StandardOpenOption.APPEND
        );
    }
}