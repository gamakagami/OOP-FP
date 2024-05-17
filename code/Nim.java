import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Nim extends Game {

    // Method to start the game
    public void startGame(int choice, Scanner scanner) {
        Random random = new Random();

        System.out.println("Welcome to Nim!");

        // Set up initial state for each heap
        ArrayList<Integer> heaps = new ArrayList<>();
        heaps.add(random.nextInt(2) + 3);
        heaps.add(random.nextInt(2) + 4);
        heaps.add(random.nextInt(2) + 5);

        // Main game loop
        boolean player1Turn = true; // Variable to track whose turn it is
        boolean vsAI = choice == 2; // Determine if the game is against AI based on the user's choice
        while (true) {
            // Display current state of heaps
            System.out.println("\nCurrent State:");
            for (int i = 0; i < heaps.size(); i++) {
                System.out.println("Heap " + (i + 1) + ": " + heaps.get(i) + " objects");
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
                    System.out.print("Enter the heap index (1-" + heaps.size() + "): ");
                    heapIndex = scanner.nextInt() - 1; // Adjust for zero-based index
                    if (heapIndex < 0 || heapIndex >= heaps.size()) {
                        System.out.println("Invalid heap index. Please enter a number between 1 and " + heaps.size() + ".");
                        // Display current state of heaps
                        System.out.println("\nCurrent State:");
                        for (int i = 0; i < heaps.size(); i++) {
                            System.out.println("Heap " + (i + 1) + ": " + heaps.get(i) + " objects");
                        }
                        continue;
                    }
                    if (heaps.get(heapIndex) == 0) {
                        System.out.println("Heap " + (heapIndex + 1) + " is empty. Please choose another heap.");
                        // Display current state of heaps
                        System.out.println("\nCurrent State:");
                        for (int i = 0; i < heaps.size(); i++) {
                            System.out.println("Heap " + (i + 1) + ": " + heaps.get(i) + " objects");
                        }
                        continue;
                    }
                    System.out.print("Enter the number of objects to remove (1-" + Math.min(heaps.get(heapIndex), 3) + "): ");
                    objectsToRemove = scanner.nextInt();
                    if (objectsToRemove < 1 || objectsToRemove > Math.min(heaps.get(heapIndex), 3)) {
                        System.out.println("Invalid number of objects. Please enter a number between 1 and " + Math.min(heaps.get(heapIndex), 3) + ".");
                        // Display current state of heaps
                        System.out.println("\nCurrent State:");
                        for (int i = 0; i < heaps.size(); i++) {
                            System.out.println("Heap " + (i + 1) + ": " + heaps.get(i) + " objects");
                        }
                        continue;
                    }
                    break; // Break out of the loop if valid input is provided
                } while (true); // Infinite loop until valid input is provided
                heaps.set(heapIndex, heaps.get(heapIndex) - objectsToRemove);
            } else {
                // AI's turn
                int heapIndex, objectsToRemove;
                do {
                    heapIndex = random.nextInt(heaps.size());
                } while (heaps.get(heapIndex) == 0); // Ensure selected heap is not empty
                // AI randomly chooses a number of objects to remove (1-3 or the remaining objects in the heap)
                objectsToRemove = random.nextInt(Math.min(3, heaps.get(heapIndex))) + 1;
                heaps.set(heapIndex, heaps.get(heapIndex) - objectsToRemove);
                System.out.println("\nAI removes " + objectsToRemove + " objects from heap " + (heapIndex + 1));
            }

            player1Turn = !player1Turn; // Switch turns
        }
    }

    // Check if the game is over (all heaps are empty)
    public static boolean isGameOver(ArrayList<Integer> heaps) {
        for (int heap : heaps) {
            if (heap > 0) {
                return false; // Game is not over if any heap still has objects
            }
        }
        return true; // Game is over if all heaps are empty
    }
}
