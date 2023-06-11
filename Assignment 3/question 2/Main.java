/*
 * Title Main.java
 * Author: Halil Mert Güler
 * ID: 
 * Section: 2
 * Assignment: 4, question 2
 * Description: This is a class for representing a string sorting
 * algorithms that calculates distances between 2 words and do 
 * operations according to the result is even or not
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Main {

    // calculate distance
    public static long calculateStringDistance(String str1, String str2) {
        long value1 = getAlphabeticalValue(str1); // Calculate the alphabetical value of str1
        long value2 = getAlphabeticalValue(str2); // Calculate the alphabetical value of str2

        return Math.abs(value1 - value2); // Return the absolute difference between the values
    }

    private static long getAlphabeticalValue(String str) {
        StringBuilder valueBuilder = new StringBuilder();

        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);

            if (Character.isLetter(c)) { // Check if the character is a letter
                int charValue = Character.toLowerCase(c) - 'a' + 1; // Calculate the alphabetical value of the letter
                valueBuilder.append(charValue); // Append the value to the StringBuilder
            }
        }

        String valueString = valueBuilder.toString();
        long value = Long.parseLong(valueString); // Convert the value to a long

        return value;
    }

    // even/odd check
    public static boolean isValueEven(long distance) {
        return distance % 2 == 0; // Check if the distance is even
    }

    // sort with priority
    public static String sortWithPriority(String word, String prior) {
        List<Character> charList = new ArrayList<>();
        List<Character> remainingChars = new ArrayList<>();

        for (char c : word.toCharArray()) {
            if (prior.indexOf(c) != -1) { // Check if the character is present in the priority string
                charList.add(c); // Add the character to the list with priority
            } else {
                remainingChars.add(c); // Add the character to the list of remaining characters
            }
        }

        charList.sort(Comparator.comparingInt(prior::indexOf)); // Sort the list based on the index in the priority
                                                                // string
        charList.addAll(remainingChars); // Add the remaining characters to the sorted list

        StringBuilder sortedString = new StringBuilder();
        for (char c : charList) {
            sortedString.append(c); // Build the sorted string
        }

        return sortedString.toString();
    }

    // sort ascending order
    public static String sortLexicographically(String input) {
        char[] charArray = input.toCharArray();
        Arrays.sort(charArray); // Sort the characters in lexicographic order
        return new String(charArray); // Convert the sorted characters back to a string
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        ArrayList<String> arr1 = new ArrayList<String>();
        ArrayList<String> arr2 = new ArrayList<String>();

        String arr1Values = scanner.nextLine(); // Read input for arr1
        for (String word : arr1Values.split(" ")) // Split the input string by space and add words to arr1
            arr1.add(word);

        String arr2Values = scanner.nextLine(); // Read input for arr2
        for (String word : arr2Values.split(" ")) // Split the input string by space and add words to arr2
            arr2.add(word);

        scanner.close();

        int length = arr1.size();

        String[] sorted = new String[length]; // Create an array to store the sorted strings

        for (int i = 0; i < length; i++) {
            long distance = calculateStringDistance(arr1.get(i).toLowerCase(), arr2.get(i).toLowerCase()); // Calculate
                                                                                                           // the
                                                                                                           // distance
                                                                                                           // between
                                                                                                           // two
                                                                                                           // strings

            if (isValueEven(distance)) {
                sorted[i] = sortWithPriority(arr1.get(i), arr2.get(i)); // Sort the string with priority if distance is
                                                                        // even
            } else {
                sorted[i] = sortLexicographically(arr1.get(i)); // Sort the string in lexicographic order if distance is
                                                                // odd
            }
        }

        System.out.println("First Array:");
        // for (String word : arr1)
        // System.out.print(word + " ");

        System.out.println("Second Array:");
        // for (String word : arr2)
        // System.out.print(word + " ");

        System.out.println("Sorted Array:");
        for (String word : sorted)
            System.out.print(word + " "); // Print the sorted array
    }
}
