# Huffman Coding Algorithm

## Overview
This Java application implements the Huffman Coding Algorithm for efficient data compression. The program takes a string input and utilizes the Huffman algorithm to compress it by encoding characters based on their frequency in the text. It demonstrates the core concepts of Huffman coding including building a frequency table, constructing a Huffman tree, and generating compressed data.

## Features
- **Frequency Table Construction**: Builds a frequency table of characters in the input text.
- **Huffman Tree Building**: Constructs a Huffman tree based on character frequencies.
- **Data Compression**: Compresses the input text using Huffman coding.
- **Data Decompression**: Decompresses the compressed data back to its original form.
- **Efficiency Calculation**: Calculates and displays the efficiency of the compression process.
- **Priority Queue Implementation**: Utilizes a custom priority queue for managing the tree nodes.

## Running the Program
To run the Huffman Coding Algorithm program, follow these steps:

1. **Compile the Java Program**:
   - Ensure you have Java installed and your `CLASSPATH` is set correctly.
   - Compile the program using `javac Shko_Maghded.java`.

2. **Execute the Program**:
   - Run the compiled program with `java Shko_Maghded`.

3. **View the Output**:
   - Observe the Huffman coding process in the console output, including the character frequencies, Huffman codes, original text, compressed text, and decompressed text.
   - Review the efficiency metrics of the compression.

## Program Structure
- `Shko_Maghded` class contains the `main` method.
- `HuffmanCodingAlgorithm` class handles the Huffman coding logic.
- `Node` class represents a node in the Huffman tree.
- `priorityQueue` class implements a simple priority queue for managing the nodes.

## Note
- The implementation is designed to handle ASCII characters.
- Modifications can be made to adapt the algorithm to other character sets or input types.

## Contribution
Contributions to enhance and improve this Huffman Coding Algorithm implementation are welcome. Whether it's optimizing the algorithm, extending functionality, or fixing bugs, feel free to fork this repository and submit your improvements.

## License
Free To Use.
