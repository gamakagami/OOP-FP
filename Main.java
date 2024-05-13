import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Random random = new Random();
        Scanner scanner = new Scanner(System.in);
        int play = 0;
        int plays = 0;
        int result = 0;
        int choice = 0; // Variable to store the user's choice
        char[][] ticTacToeBoard = new char[3][3]; // Tic-Tac-Toe board

        while (play == 0) { // Loop until the user exits the game
            if (plays == 0) {
                play = 1;
                if (choice == 0) { // If it's the first iteration, prompt for the choice
                    System.out.println("Choose the game mode:");
                    System.out.println("1. Player vs Player");
                    System.out.println("2. Player vs AI");
                    System.out.print("Enter your choice (1 or 2): ");
                    choice = scanner.nextInt();
                    while (choice != 1 && choice != 2) {
                        System.out.println("Invalid choice. Please enter 1 or 2.");
                        choice = scanner.nextInt();
                    }
                }
            }

            // Play a random game
            int dice = random.nextInt(3);
            plays += 1;
            switch (dice) {
                case 0:
                    System.out.println("You got Nim!");
                    Nim nim = new Nim();
                    nim.startGame(choice);
                    result = nim.getResult();
                    break;
                case 1:
                    System.out.println("You got Connect 4!");

                    Connect4 game = new Connect4();
                    if (choice == 1) {
                        game.playGame();
                        result = game.getResult();
                    } else {
                        // Player vs AI mode
                        game.playGameAgainstAI();
                        result = game.getResult();
                    }
                    break;
                case 2:
                    System.out.println("You got Number Game!");
                    NumberGame numberGame = new NumberGame();
                    if (choice == 1) {
                        numberGame.playTwoPlayers(random, scanner);
                        result = numberGame.getResult();
                    } else {
                        numberGame.playVsAI(random, scanner);
                        result = numberGame.getResult();
                    }
                    // Implement game 3 logic
                    break;
            }

            // Tic-Tac-Toe
            if (result == 1) { // Player wins
                placeBlock(scanner, ticTacToeBoard, 'X');
                printBoard(ticTacToeBoard);
                result = 0; // Reset result for the next game
            } else if (result == 2) { // AI wins
                placeBlock(scanner, ticTacToeBoard, 'O');
                printBoard(ticTacToeBoard);
                result = 0; // Reset result for the next game
            }
        }
        scanner.close(); // Close the scanner only once at the end of the main method
    }

    // Method to place a block on the Tic-Tac-Toe board
    public static void placeBlock(Scanner scanner, char[][] board, char symbol) {
        int row = -1, col = -1;
        boolean validInput = false;
        while (!validInput) {
            System.out.print("Enter the row (0-2) to place " + symbol + ": ");
            if (scanner.hasNextInt()) {
                row = scanner.nextInt();
                if (row >= 0 && row <= 2) {
                    validInput = true;
                } else {
                    System.out.println("Invalid row. Please enter a number between 0 and 2.");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.next(); // Consume invalid input
            }
        }
        validInput = false; // Reset validInput flag for column input
        while (!validInput) {
            System.out.print("Enter the column (0-2) to place " + symbol + ": ");
            if (scanner.hasNextInt()) {
                col = scanner.nextInt();
                if (col >= 0 && col <= 2) {
                    validInput = true;
                } else {
                    System.out.println("Invalid column. Please enter a number between 0 and 2.");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.next(); // Consume invalid input
            }
        }
        board[row][col] = symbol;
    }

    // Method to print the Tic-Tac-Toe board
    public static void printBoard(char[][] board) {
        System.out.println("\nTic-Tac-Toe Board:");
        for (char[] row : board) {
            for (char cell : row) {
                if (cell == '\u0000') {
                    System.out.print("_ ");
                } else {
                    System.out.print(cell + " ");
                }
            }
            System.out.println();
        }
    }
}
