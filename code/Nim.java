import java.util.Random;
import java.util.Scanner;

public class Nim {
    private int result; // Declare result as an instance variable

    // Method to start the game
    public void startGame(int choice, Scanner scanner) {
        Random random = new Random();

        System.out.println("Welcome to Nim!");

        // Set up initial state for each heap
        int[] heaps = {random.nextInt(2)+3, random.nextInt(2)+4, random.nextInt(2)+5};

        // Main game loop
        boolean player1Turn = true; // Variable to track whose turn it is
        boolean vsAI = choice == 2; // Determine if the game is against AI based on the user's choice
        while (true) {
            // Display current state of heaps
            System.out.println("\nCurrent State:");
            for (int i = 0; i < heaps.length; i++) {
                System.out.println("Heap " + (i + 1) + ": " + heaps[i] + " objects");
            }

            // Check if the game is over
            if (isGameOver(heaps)) {
                System.out.println("\nGame Over!");
                if (player1Turn) { // If it's player 1's turn when game ends
                    if (vsAI) {
                        System.out.println("You win!"); //Player 1 wins against AI
                        result = 1;
                    } else {
                        System.out.println("Player 1 wins!"); // Player 1 wins against Player 2
                        result = 1;
                    }
                } else {
                    if (vsAI) {
                        System.out.println("AI wins!"); // AI wins
                        result = 2;
                    } else {
                        System.out.println("Player 2 wins!"); // Player 2 wins
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
                    heapIndex = scanner.nextInt() - 1; // Adjust for zero-based index
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
                // AI randomly chooses a number of objects to remove (1-3 or the remaining objects in the heap)
                objectsToRemove = random.nextInt(Math.min(3, heaps[heapIndex])) + 1;
                heaps[heapIndex] -= objectsToRemove;
                System.out.println("\nAI removes " + objectsToRemove + " objects from heap " + (heapIndex + 1));
            }

            player1Turn = !player1Turn; // Switch turns
        }
    }


    // Check if the game is over (all heaps are empty)
    public static boolean isGameOver(int[] heaps) {
        for (int heap : heaps) {
            if (heap > 0) {
                return false; // Game is not over if any heap still has objects
            }
        }
        return true; // Game is over if all heaps are empty
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
