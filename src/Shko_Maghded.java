public class Shko_Maghded {
    public static void main(String[] args) {
        String text = "Erie eyes seen near lake.";
        
        HuffmanCodingAlgorithm huffmanCodingAlgorithm = new HuffmanCodingAlgorithm(text);
        //build the Huffman tree
        Node root = huffmanCodingAlgorithm.buildHuffmanTree();
        //putting it inside a Node isn't necessary, the method can be void, but it was done, so you can use it later if you want
        
    }
}

class HuffmanCodingAlgorithm {
    //The number of characters in the ASCII table is 256.
    public final int ASCII_CHARS_NUM = 256;
    private final String text;
    
    HuffmanCodingAlgorithm(String text) {
        this.text = text;
    }
    
    public Node buildHuffmanTree() {
        int[] freq = buildFrequencyTable();
        
        priorityQueue queue = new priorityQueue(freq.length);
        //The frequency table is used to build a priority queue containing the leaf nodes.
        for (char i = 0; i < ASCII_CHARS_NUM; i++) {
            //if the character is not present in the given text, it is not included in the frequency table
            if (freq[i] > 0) {
                queue.insert(new Node(freq[i], i, null, null));
            }
        }
        
        /* Because each symbol is represented by a leaf node
        the tree must have at least as many leaf nodes as there are symbols in the input. */
        if (queue.size() == 1) {
            queue.insert(new Node(0, '\0', null, null));
        }
        
        while (queue.size() > 1) {
            //remove the two nodes of the highest priority (the lowest frequency) from the queue
            Node left = queue.remove();
            Node right = queue.remove();
            /* create a new internal node with these two nodes as children and with
            frequency equal to the sum of the two nodes' frequencies */
            Node parent = new Node(left.frequency + right.frequency, '\0', left, right);
            queue.insert(parent);
        }
        Node root = queue.remove();
        buildCode(root, "");
        
        System.out.println("Character" + "\t" + "Frequency" + "\t" + "Huffman Code");
        for (int i = 0; i < ASCII_CHARS_NUM; i++) {
            if (freq[i] > 0) {
                System.out.println("\t" + (char) i + "\t\t\t" + freq[i] + "\t\t\t" + findNode((char) i, root).code);
            }
        }
        
        System.out.println("------------------Compressing and Decompressing------------------");
        System.out.println("Original Text: " + text);
        System.out.println("Compressed Text: " + compress(root));
        System.out.println("Decompressed Text: " + decompress(root, compress(root)));
        
        calculateEfficiency(freq, root);
        
        return root;
    }
    
    //build the frequency table for the characters in the text
    public int[] buildFrequencyTable() {
        /* The frequency table is an array of integers, where the index is the character code and the element is the
        number of occurrences of that character in the text. */
        int[] frequencies = new int[ASCII_CHARS_NUM];
        for (char c : text.toCharArray()) {
            frequencies[c]++;
        }
        return frequencies;
    }
    
    //build the code for each character using the Huffman tree
    public void buildCode(Node node, String code) {
        //if the node is not a leaf node, recursively traverse the tree until a leaf node is found
        if (!node.isLeaf()) {
            //append 0 to the code if we move to left
            buildCode(node.left, code + '0');
            //append 1 to the code if we move to right
            buildCode(node.right, code + '1');
        }
        else {
            //if the node is a leaf node, store the code inside the node
            node.code = code;
        }
    }
    
    //compress the text using the Huffman tree
    public String compress(Node node) {
        StringBuilder compressedText = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            //append the code of the current character to the compressed text
            compressedText.append(findNode(text.charAt(i), node).code);
        }
        return compressedText.toString();
    }
    
    //decompress the compressed text using the Huffman tree
    public String decompress(Node node, String compressedText) {
        StringBuilder decompressedText = new StringBuilder();
        Node currentNode = node;
        for (int i = 0; i < compressedText.length(); i++) {
            //if a leaf node is reached, append the character to the decompressed text
            if (currentNode.isLeaf()) {
                decompressedText.append(currentNode.character);
                currentNode = node;
            }
            //if the current bit is 0, move to the left child node
            if (compressedText.charAt(i) == '0') {
                currentNode = currentNode.left;
            }
            //if the current bit is 1, move to the right child node
            else if (compressedText.charAt(i) == '1') {
                currentNode = currentNode.right;
            }
            else {
                System.out.println("Invalid bit in message!");
            }
        }
        //append the last character
        decompressedText.append(currentNode.character);
        return decompressedText.toString();
    }
    
    //find node from the lectures with adjustments for character search instead of integer
    public Node findNode(char character, Node node) {
        //if the node is null, return null
        if (node == null) {
            return null;
        }
        //if the node is a leaf node, return the node
        else if (node.character == character) {
            return node;
        }
        //if the node is not a leaf node, recursively traverse the tree until a leaf node is found
        else {
            Node left = findNode(character, node.left);
            Node right = findNode(character, node.right);
            //return either the left or right node to the previous call in case the node is found
            if (left != null) {
                return left;
            }
            else {
                return right;
            }
        }
    }
    
    //calculate the compression ratio and efficiency of the compression
    public void calculateEfficiency(int[] freq, Node node) {
        int originalBits = text.length() * 8;
        int totalBits = 0;
        int totalChars = text.length();
        int uniqueChars = 0;
        for (int i = 0; i < ASCII_CHARS_NUM; i++) {
            if (freq[i] > 0) {
                totalBits += freq[i] * findNode((char) i, node).code.length();
                uniqueChars++;
            }
        }
        System.out.println("---------------Compression---------------");
        System.out.println("Original bits: " + originalBits);
        System.out.println("Total bits after compression: " + totalBits);
        System.out.println("Compression ratio: " + (double) originalBits / totalBits);
        System.out.println("Efficiency: " + (double) totalBits / originalBits * 100 + "%");
        System.out.println("Total characters: " + totalChars);
        System.out.println("Total unique characters: " + uniqueChars);
        
        System.out.println("---------------Bits per Character---------------");
        System.out.println("Average bits per character before compression: " + (double) originalBits / totalChars);
        System.out.println("Average bits per unique character before compression: " + (double) originalBits / uniqueChars);
        System.out.println("Average bits per character after compression: " + (double) totalBits / totalChars);
        System.out.println("Average bits per unique character after compression: " + (double) totalBits / uniqueChars);
        
        System.out.println("---------------Saving Bits---------------");
        System.out.println("Total bits saved: " + (originalBits - totalBits));
        System.out.println("Total bits saved per character: " + (double) (originalBits - totalBits) / totalChars);
        System.out.println("Total bits saved per unique character: " + (double) (originalBits - totalBits) / uniqueChars);
    }
}

class Node {
    public final int frequency;
    public final char character;
    public final Node left, right;
    public String code;
    
    public Node(int frequency, char character, Node left, Node right) {
        this.frequency = frequency;
        this.character = character;
        this.left = left;
        this.right = right;
    }
    
    public boolean isLeaf() {
        return left == null && right == null;
    }
}

class priorityQueue {
    private final Node[] queueArray;
    private int nItems;
    
    public priorityQueue(int s) {
        queueArray = new Node[s];
        nItems = 0;
    }
    
    public void insert(Node item) {
        int j;
        if (nItems == 0) {
            queueArray[nItems++] = item;
        }
        else {
            for (j = nItems - 1; j >= 0; j--) {
                if (item.frequency >= queueArray[j].frequency) {
                    queueArray[j + 1] = queueArray[j];
                }
                else {
                    break;
                }
            }
            queueArray[j + 1] = item;
            nItems++;
        }
    }
    
    public Node remove() {
        return queueArray[--nItems];
    }
    
    public int size() {
        return nItems;
    }
}