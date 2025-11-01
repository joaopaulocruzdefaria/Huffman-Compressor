# Código de Huffman para Compressão de Texto

Implementação do Código de Huffman em Java, focado na compressão de texto baseada na frequência de palavras . Este projeto foi desenvolvido como um trabalho individual para a consolidação de conhecimentos sobre estruturas em árvore .

O programa segue uma estrutura Orientada a Objetos, separando a lógica de compressão (`HuffmanCompressor.java`) da estrutura de dados (`Node.java`) e da orquestração principal (`Main.java`).

## 🚀 Como Executar

### Pré-requisitos

  * Java (JDK) 11 ou superior instalado e configurado no PATH.

### Passos

1.  **Clone o repositório:**

    ```bash
    git clone <https://github.com/joaopaulocruzdefaria/Huffman-Compressor>
    cd <https://github.com/joaopaulocruzdefaria/Huffman-Compressor>
    ```

2.  **Prepare o arquivo de entrada:**

      * Altere o arquivo `data/input.dat`, adicionando os textos que deseja comprimir.
      * **Importante:** Cada bloco de texto deve ser separado por uma linha em branco .

3.  **Compile os arquivos Java:**

    ```bash
    javac *.java
    ```

4.  **Execute o programa:**

    ```bash
    java Main
    ```

5.  **Verifique a saída:**

      * O console exibirá o progresso, incluindo as frequências de palavras e os códigos gerados.
      * O arquivo `data/output.dat` será criado, contendo o mapa de códigos e o texto comprimido para cada bloco processado.

## ⚙️ Funcionamento do Algoritmo

O algoritmo de Huffman é um método de compressão sem perdas (*lossless*) que se baseia na frequência de ocorrência dos símbolos em uma mensagem . Nesta implementação específica, os "símbolos" não são caracteres, mas sim **palavras** .

O processo executado pelo programa é o seguinte:

1.  **Leitura e Contagem:** O programa lê os textos do `data/input.dat` . Para cada bloco de texto, ele calcula a frequência de cada palavra (ignorando pontuação e convertendo para minúsculas).

2.  **Construção da Árvore:** Utilizando uma Fila de Prioridade (`PriorityQueue`), o algoritmo constrói a árvore de Huffman "de baixo para cima":

      * Cada palavra se torna um nó "folha" com um peso igual à sua frequência.
      * A fila de prioridade organiza os nós, mantendo os de menor frequência primeiro.
      * O algoritmo remove os dois nós de menor frequência da fila, cria um nó "pai" (interno) com a soma das frequências, e insere esse novo nó de volta na fila.
      * Isso se repete até que reste apenas um nó na fila: a raiz da árvore.
      * O resultado é uma árvore binária onde as palavras mais frequentes ficam perto da raiz (caminho curto) e as menos frequentes ficam nas folhas mais profundas (caminho longo) .

3.  **Geração de Códigos:** A árvore é percorrida recursivamente. O caminho para um ramo à **esquerda** é codificado como "0" e um ramo à **direita** como "1" . Ao atingir uma folha (palavra), o caminho binário acumulado (ex: "010") torna-se o código de Huffman para aquela palavra.

4.  **Compressão e Saída:** O texto original é lido novamente, e cada palavra é substituída pelo seu respectivo código binário. O resultado final (o mapa de códigos e o texto comprimido ) é salvo em `data/output.dat` .

---

### Exemplo Visual da Árvore

Para ilustrar o processo, considere o texto: `"O rato roeu a roupa. O rato é o rei."`

Isso geraria o seguinte mapa de frequências (simplificado):

  * `o`: 3
  * `rato`: 2
  * `roeu`: 1
  * `a`: 1
  * `roupa`: 1
  * `é`: 1
  * `rei`: 1

A árvore de Huffman resultante (onde `(0)` é o ramo esquerdo e `(1)` é o ramo direito ) seria semelhante a esta:

```
                [RAIZ: Freq 10]
               /               \
         (0) /                 \ (1)
           /                     \
       [Freq 4]                  [Freq 6]
       /      \                  /      \
 (0) /        \ (1)          (0) /        \ (1)
   /            \              /            \
[Freq 2]      [Freq 2]       ('o', 3)       [Freq 3]
 /    \        /    \                     /      \
(0)  (1)    (0)  (1)                 (0)  (1)
/      \    /      \                 /      \
('roeu', 1) ('a', 1) ('roupa', 1) ('é', 1)   ('rei', 1) ('rato', 2)
```

Note que palavras mais frequentes, como 'o' (freq 3), ficam mais perto da raiz, recebendo um código menor (neste exemplo, `10`). Palavras raras, como 'roeu' (freq 1), ficam mais fundas, recebendo um código maior (neste exemplo, `000`). Isso demonstra o princípio de associar códigos menores aos símbolos mais frequentes .
