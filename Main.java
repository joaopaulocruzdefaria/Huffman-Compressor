import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Map;

/**
 * Classe principal (driver) da aplicação.
 * É responsável pela orquestração do processo:
 * 1. Lida com a leitura e escrita de arquivos (I/O).
 * 2. Lê o `input.dat` e o divide em blocos de texto.
 * 3. Para cada bloco, instancia `HuffmanCompressor` para fazer o trabalho.
 * 4. Escreve os resultados no `output.dat`.
 */
public class Main {

    /**
     * Ponto de entrada do programa. Lê o arquivo 'input.dat',
     * processa cada bloco de texto individualmente e salva os 
     * resultados no 'output.dat'.
     * @param args Argumentos da linha de comando (não utilizados).
     */
    public static void main(String[] args) {
        // Define os caminhos dos arquivos de entrada e saída
        Path inputPath = Paths.get("data/input.dat");
        Path outputPath = Paths.get("data/output.dat");

        try {
            // Garante que o diretório "data" exista
            File dataDir = new File("data");
            if (!dataDir.exists()) {
                dataDir.mkdirs();
            }
            // Limpa o arquivo de saída anterior para uma nova execução
            Files.deleteIfExists(outputPath);

            // Lê todo o conteúdo do arquivo de entrada
            String fullContent = Files.readString(inputPath);
            
            // Divide o conteúdo em blocos, usando uma linha em branco como separador
            // (Expressão regular: "uma ou mais quebras de linha")
            String[] textBlocks = fullContent.split("(\\r?\\n){2,}");

            // Processa cada bloco de texto independentemente
            for (String block : textBlocks) {
                if (block.isEmpty()) continue; // Pula blocos vazios

                System.out.println("--- Processando Bloco ---");
                
                // 1. Cria um novo compressor para este bloco.
                // O construtor faz todo o trabalho pesado.
                HuffmanCompressor compressor = new HuffmanCompressor(block);

                // 2. Pega os resultados do compressor
                Map<String, String> huffmanCodes = compressor.getHuffmanCodes();
                String compressedText = compressor.getCompressedText();

                // 3. Imprime logs no console
                System.out.println("Códigos Gerados: " + huffmanCodes);
                System.out.println(
                    "Texto Comprimido: (primeiros 100 chars) " +
                    (compressedText.length() > 100
                        ? compressedText.substring(0, 100) + "..."
                        : compressedText)
                );
                
                // 4. Escreve (anexa) os resultados deste bloco no arquivo de saída
                writeOutput(outputPath, huffmanCodes, compressedText);
                
                System.out.println("--------------------------\n");
            }
            
            System.out.println(">>> Processamento concluído! Verifique o arquivo 'data/output.dat'.");

        } catch (IOException e) {
            System.err.println("Ocorreu um erro: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Escreve (anexa) o resultado da compressão de um bloco de texto 
     * no arquivo de saída.
     * @param outputPath O caminho para o arquivo 'output.dat'.
     * @param huffmanCodes O mapa de códigos gerado para este bloco.
     * @param compressedText O texto comprimido para este bloco.
     * @throws IOException Se ocorrer um erro durante a escrita no arquivo.
     */
    private static void writeOutput(Path outputPath, Map<String, String> huffmanCodes, String compressedText) 
            throws IOException {
        
        StringBuilder outputBuilder = new StringBuilder();
        outputBuilder.append("--- NOVO BLOCO DE TEXTO ---\n");
        
        // Escreve o mapa de códigos (essencial para decodificação)
        outputBuilder.append("[MAPA DE CÓDIGOS]\n");
        for (Map.Entry<String, String> entry : huffmanCodes.entrySet()) {
            outputBuilder.append(entry.getKey())
                         .append(":")
                         .append(entry.getValue())
                         .append("\n");
        }
        
        // Escreve o texto comprimido
        outputBuilder.append("[TEXTO COMPRIMIDO]\n");
        outputBuilder.append(compressedText);
        outputBuilder.append("\n\n");

        // Anexa o conteúdo deste bloco ao arquivo de saída
        Files.writeString(
            outputPath,
            outputBuilder.toString(),
            StandardOpenOption.CREATE, 
            StandardOpenOption.APPEND // Anexa ao invés de sobrescrever
        );
    }
}