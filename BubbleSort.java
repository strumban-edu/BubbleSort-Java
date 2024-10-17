import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;

import java.io.File;
import java.io.FileNotFoundException;

import java.io.FileWriter;
import java.io.IOException;

public class BubbleSort {
    public static Scanner scan = new Scanner(System.in);

    // Given arrayLength as argument, create an array of random integers between 0 and 100; return the created array.
    public static int[] createRandomArray(int arrayLength) {
        Random rand = new Random();
        
        int[] arrayInts = new int[arrayLength];
        for (Integer i = 0; i < arrayInts.length; i++) {
            arrayInts[i] = rand.nextInt(100);
        }

        return arrayInts;
    }
    
    // Given an integer array and filename, write the array to the file, with each line containing one integer in the array.
    public static void writeArrayToFile(int[] array, String filename) {
        try {
            FileWriter fileCreator = new FileWriter(filename, false);
            fileCreator.close();
        } catch (IOException e) {
            System.out.println("An error occured while creating the output file.");
            System.out.println();
        }
        
        try {
            FileWriter fileWriter = new FileWriter(filename, true);
            
            for (Integer intVar : array) {
                fileWriter.write(intVar + "\n");
            }

            System.out.println();
            System.out.println("File '" + filename + "' created!");
            System.out.println();
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("An error occured while writing a value to the output file '" + filename + "'.");
            System.out.println();
        }
    }

    // This is the reverse of writeArrayToFile; Given the filename, read the integers (one line per integer) to an array, and return the array
    public static int[] readFileToArray(String filename) {        
        while (true) {
            ArrayList<Integer> tempArray = new ArrayList<>();
            try {
                File file = new File(filename);
                Scanner fileReader = new Scanner(file);

                while (fileReader.hasNextLine()) {
                    String rawRow = fileReader.nextLine();
                    tempArray.add(Integer.parseInt(rawRow));
                }

                fileReader.close();

                Integer arrSize = tempArray.size();
                int[] array = new int[arrSize];
                for (Integer i = 0; i < arrSize; i++) {
                    array[i] = tempArray.get(i);
                }

                return array;
            } catch (FileNotFoundException f) {
                System.out.println("File '" + filename + "' could not be found or read.");
                System.out.println("Please ensure that file name is entered correctly and that the file exists.");
                System.out.println();
                return new int[0];
            } catch (ArrayIndexOutOfBoundsException a) {
                System.out.println("File '" + filename + "' is not a properly formatted text file.");
                System.out.println("Please ensure that the given file contains a properly formatted list of integers (with one integer per line) in text format.");
                System.out.println();
                return new int[0];
            } catch (NumberFormatException n) {
                System.out.println("File '" + filename + "' does not contain a valid list of integers.");
                System.out.println("Please ensure that the given file contains a properly formatted list of integers (with one integer per line) in text format.");
                System.out.println();
                return new int[0];
            }
        }
    }

    // Given an integer array, sort it in-place, i.e., the order of the elements will be changed so that the final array is in sorted order.
    public static void bubbleSort(int[] array) {
        for (Integer i = 0; i < array.length; i++) {
            Boolean altered = false;

            for (Integer j = 0; j < array.length - 1; j++) {
                if (array[j] > array[j + 1]) {
                    altered = true;
                    Integer temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }

            if (!altered) {
                break;
            }
        }
    }

    // The main function will handle a user's keyboard input specified in the next session
    public static void main(String[] args) {
        System.out.println("----------------------------------------");
        System.out.println("--                                    --");
        System.out.println("--           BUBBLE  SORTER           --");
        System.out.println("--         highly  efficient!         --");
        System.out.println("--                                    --");
        System.out.println("----------------------------------------");
        System.out.println();

        while (true) {
            if (args.length == 0) {
                System.out.println("Options:");
                System.out.println("1. Enter an integer and a file name (n f). An array of n random integers will then be generated and stored in a text file 'f'.");
                System.out.println("2. Enter a file name (f). This string should be the name of a text file that contains a list of integers (with one integer per line). This array will then be sorted in ascending order and stored in a seperate text file.");
                System.out.println("3. Enter 'exit'. This will quit the program.");
                System.out.println();
                System.out.print("Input: ");
                String usrInput = scan.nextLine();
                args = usrInput.split(" ");
            }

            if (args.length == 2) {
                Integer mainInt;
                try {
                    mainInt = Integer.parseInt(args[0]);
                } catch (NumberFormatException n) {
                    System.out.println("Input '" + String.join(" ", args) + "' is not a invalid input.");
                    System.out.println();
                    args = new String[]{};
                    continue;
                }

                int[] generatedArray = createRandomArray(mainInt);
                writeArrayToFile(generatedArray, args[1]);
            } else if (args.length == 1) {
                if (args[0].equals("exit")) {
                    System.out.println();
                    System.out.println("Thank you for using the Bubble Sorter!");
                    scan.close();
                    break;
                }

                int[] readArray = readFileToArray(args[0]);
                if (readArray.length == 0) {
                    args = new String[]{};
                    continue;
                }
                bubbleSort(readArray);

                String[] filenameArray = args[0].split("\\.");
                filenameArray[filenameArray.length - 2] += " (sorted)";
                String filename = String.join(".", filenameArray);
                writeArrayToFile(readArray, filename);
            } else {
                System.out.println("Input '" + String.join(" ", args) + "' is not a invalid input.");
                System.out.println();
            }

            args = new String[]{};
        }
    }
}
