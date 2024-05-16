import java.util.Random;
import java.util.Scanner;

public class Nim {
    private int result; // Declare result as an instance variable

    public void startGame(int choice, Scanner scanner) {
        Random random = new Random();

        System.out.println("Welcome to Nim!");

        // Set up initial state
        int[] heaps = {3, 4, 5}; // Initial number of objects in each heap

        // Main game loop
        boolean player1Turn = true;
        boolean vsAI = choice == 2;
        while (true) {
            // Display current state
            System.out.println("\nCurrent State:");
            for (int i = 0; i < heaps.length; i++) {
                System.out.println("Heap " + (i + 1) + ": " + heaps[i] + " objects");
            }

            // Check if the game is over
            if (isGameOver(heaps)) {
                System.out.println("\nGame Over!");
                if (player1Turn) {
                    if (vsAI) {
                        System.out.println("You win!");
                        result = 1;
                    } else {
                        System.out.println("Player 1 wins!");
                        result = 1;
                    }
                } else {
                    if (vsAI) {
                        System.out.println("AI wins!");
                        result = 2;
                    } else {
                        System.out.println("Player 2 wins!");
                        result = 2;
                    }
                }
                break;
            }

            // Player's turn
            if (player1Turn || !vsAI) {
                int heapIndex, objectsToRemove;
                do {
                    System.out.println("\nPlayer " + (player1Turn ? "1" : "2") + "'s turn:");
                    System.out.print("Enter the heap index (1-" + heaps.length + "): ");
                    heapIndex = scanner.nextInt() - 1;
                    System.out.print("Enter the number of objects to remove (1-3): "); // Prompt for 1 to 3 objects
                    objectsToRemove = scanner.nextInt();
                } while (!isValidMove(heaps, heapIndex, objectsToRemove) || objectsToRemove < 1 || objectsToRemove > 3); // Ensure the number is valid and within the range of 1 to 3
                heaps[heapIndex] -= objectsToRemove;
            } else {
                // AI's turn
                int heapIndex, objectsToRemove;
                do {
                    heapIndex = random.nextInt(heaps.length);
                } while (heaps[heapIndex] == 0); // Ensure selected heap is not empty

                objectsToRemove = random.nextInt(Math.min(3, heaps[heapIndex])) + 1; // Limit AI's choice to a maximum of 3 or the remaining objects in the heap
                heaps[heapIndex] -= objectsToRemove;
                System.out.println("\nAI removes " + objectsToRemove + " objects from heap " + (heapIndex + 1));
            }

            player1Turn = !player1Turn;
        }
    }


    // Check if the game is over
    public static boolean isGameOver(int[] heaps) {
        for (int heap : heaps) {
            if (heap > 0) {
                return false;
            }
        }
        return true;
    }

    // Check if a move is valid
    public static boolean isValidMove(int[] heaps, int heapIndex, int objectsToRemove) {
        return heapIndex >= 0 && heapIndex < heaps.length && heaps[heapIndex] >= objectsToRemove && objectsToRemove > 0;
    }

    // Getter method for result
    public int getResult() {
        return result;
    }
}
