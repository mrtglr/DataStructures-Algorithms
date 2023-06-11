/*
 * Title Main.java
 * Author: Halil Mert Güler
 * ID: 
 * Section: 2
 * Assignment: 4, question 1
 * Description: This is a class for representing a Trie data structure
 * with functions to provide search, count prefixes and reverse finding 
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class TrieNode {
    public Map<Character, TrieNode> children; // Represents the child nodes of the current TrieNode
    public boolean isEndOfWord; // Indicates if the TrieNode represents the end of a word
    public int count; // Keeps track of the count of words with the same prefix

    public TrieNode() {
        children = new HashMap<>();
        isEndOfWord = false;
        count = 0;
    }

    public Map<Character, TrieNode> getChildren() {
        return children;
    }

    public boolean isEndOfWord() {
        return isEndOfWord;
    }

    public void setEndOfWord(boolean endOfWord) {
        isEndOfWord = endOfWord;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}

class Trie {
    private TrieNode root; // The root of the Trie data structure

    public ArrayList<String> words = new ArrayList<String>(); // Keeps track of the inserted words in the Trie

    public Trie() {
        root = new TrieNode();
    }

    public void insert(String word) {
        TrieNode current = root;

        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            TrieNode node = current.getChildren().get(ch);

            if (node == null) {
                node = new TrieNode();
                current.getChildren().put(ch, node);
            }

            current = node;
        }

        current.setEndOfWord(true); // Mark the last node as the end of the inserted word
        current.setCount(current.getCount() + 1); // Increment the count of words with the same prefix
    }

    // Search

    public boolean search(String word) {
        TrieNode current = root;

        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            TrieNode node = current.getChildren().get(ch);

            if (node == null) {
                return false; // If any character is not found in the Trie, the word doesn't exist
            }

            current = node;
        }

        return current.isEndOfWord(); // Return true only if the last character's TrieNode represents the end of a
                                      // word
    }

    // Count prefixes

    public void countPrefix(Trie trie) {
        for (String word : words) {
            System.out.print(countPrefixes(word) + " "); // Print the count of words with the same prefix for each word
        }
    }

    public int countPrefixes(String prefix) {
        TrieNode curr = root;

        for (char c : prefix.toCharArray()) {
            if (!curr.children.containsKey(c)) {
                return 0; // If any character is not found in the Trie, there are no words with the given
                          // prefix
            }
            curr = curr.children.get(c);
        }

        return countWords(curr) - 1; // Return the count of words with the same prefix (excluding the word itself)
    }

    private int countWords(TrieNode node) {
        int count = 0;
        if (node.isEndOfWord) {
            count++; // Increment the count if the current TrieNode represents the end of a word
        }

        for (TrieNode child : node.children.values()) {
            count += countWords(child); // Recursively count words in the subtree
        }

        return count;
    }

    // Find reverse

    public void reverseFind(String suffix) {
        boolean[] found = { false }; // Array to keep track of whether any word with the given suffix is found
        reverseFindHelper(root, suffix, "", found);
        if (!found[0]) {
            System.out.println("No result"); // Print if no word with the given suffix is found
        }
    }

    private void reverseFindHelper(TrieNode node, String suffix, String currentWord, boolean[] found) {
        if (node.isEndOfWord && currentWord.endsWith(suffix)) {
            System.out.println(currentWord); // Print the word if it represents the end of a word and ends with the
                                             // suffix
            found[0] = true; // Set found to true since a word with the given suffix is found
        }

        for (char c : node.children.keySet()) {
            TrieNode child = node.children.get(c);
            reverseFindHelper(child, suffix, currentWord + c, found); // Recursively search in the subtree
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Trie trie = new Trie();
        Scanner scanner = new Scanner(System.in);

        int numInputs = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        for (int i = 0; i < numInputs; i++) {
            String word = scanner.nextLine();
            trie.insert(word);
            trie.words.add(word);
        }

        System.out.println("\nChoose the function you want to use:");
        System.out.println("\n1) Search");
        System.out.println("2) Count Prefix");
        System.out.println("3) Find Reverse\n");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        switch (choice) {
            case 1:
                String searchWord = scanner.nextLine();
                System.out.println();
                System.out.println(trie.search(searchWord));
                break;
            case 2:
                System.out.println();
                trie.countPrefix(trie);
                break;
            case 3:
                String suffix = scanner.nextLine();
                trie.reverseFind(suffix);
                break;
            default:
                break;
        }

        scanner.close();
    }
}
