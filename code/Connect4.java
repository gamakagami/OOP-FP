import java.util.Random;
import java.util.Scanner;

public class Connect4 {

    // Constants to define the dimensions of the board and the player symbols
    private static final int ROWS = 6;
    private static final int COLS = 7;
    private static final char EMPTY = '-';
    private static final char PLAYER1 = 'X';
    private static final char PLAYER2 = 'O';

    private char[][] board; // 2D array to represent the board
    private Random random;  // Random object to generate random moves for AI
    private int result = 0; // Variable to store the result of the game

    // Constructor to initialize the board and random generator
    public Connect4() {
        board = new char[ROWS][COLS];
        random = new Random();
        initializeBoard();
    }

    // Method to initialize the board with empty slots
    private void initializeBoard() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                board[i][j] = EMPTY;
            }
        }
    }

    // Method to print the current state of the board
    private void printBoard() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("---------------");
    }

    // Method to drop a piece into a column
    private boolean dropPiece(int col, char player) {
        if (col < 0 || col >= COLS) {
            System.out.println("Invalid column. Please choose a column between 0 and " + (COLS - 1) + ".");
            return false; // Return false if the column index is out of bounds
        }
        for (int i = ROWS - 1; i >= 0; i--) { // Start from the bottom row
            if (board[i][col] == EMPTY) { // Find the first empty slot in the column
                board[i][col] = player; // Place the player's piece in the slot
                return true; // Return true indicating the piece was successfully placed
            }
        }
        System.out.println("Column is full. Please choose another column.");
        return false; // Return false if the column is full
    }

    // Method to check if a player has won the game
    private boolean checkWin(char player) {
        // Check horizontal win
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j <= COLS - 4; j++) {
                if (board[i][j] == player && board[i][j + 1] == player && board[i][j + 2] == player && board[i][j + 3] == player) {
                    return true;
                }
            }
        }

        // Check vertical win
        for (int j = 0; j < COLS; j++) {
            for (int i = 0; i <= ROWS - 4; i++) {
                if (board[i][j] == player && board[i + 1][j] == player && board[i + 2][j] == player && board[i + 3][j] == player) {
                    return true;
                }
            }
        }

        // Check diagonal win
        for (int i = 0; i <= ROWS - 4; i++) {
            for (int j = 0; j <= COLS - 4; j++) {
                if (board[i][j] == player && board[i + 1][j + 1] == player && board[i + 2][j + 2] == player && board[i + 3][j + 3] == player) {
                    return true;
                }
                if (board[i][j + 3] == player && board[i + 1][j + 2] == player && board[i + 2][j + 1] == player && board[i + 3][j] == player) {
                    return true;
                }
            }
        }
        return false;
    }

    // Method to play a game between two players
    public void playGame() {
        Scanner scanner = new Scanner(System.in); // Scanner to read player input
        boolean player1Turn = true; // Flag to track which player's turn it is
        boolean gameEnd = false; // Flag to track if the game has ended

        System.out.println("Welcome to Connect 4!");
        System.out.println("Player 1: X");
        System.out.println("Player 2: O");
        System.out.println("---------------");
        printBoard(); // Print the initial state of the board

        while (!gameEnd) {
            // Determine the current player based on the player1Turn flag
            char currentPlayer = (player1Turn) ? PLAYER1 : PLAYER2;
            // Print which player's turn it is and their symbol
            System.out.println("Player " + (player1Turn ? "1" : "2") + "'s turn (symbol: " + currentPlayer + ")");

            int col;
            while (true) {
                System.out.print("Enter column (0-" + (COLS - 1) + "): ");
                if (scanner.hasNextInt()) { // Check if the input is an integer
                    col = scanner.nextInt();
                    // Check if the column is within valid range and if a piece can be successfully dropped
                    if (col >= 0 && col < COLS && dropPiece(col, currentPlayer)) {
                        break; // Exit loop if valid column and piece is successfully dropped
                    } else {
                        System.out.println("Invalid input. Please enter a valid column number.");
                    }
                } else {
                    System.out.println("Invalid input. Please enter a valid column number.");
                    scanner.next(); // Consume invalid input
                }
            }

            printBoard(); // Print the updated board

            if (checkWin(currentPlayer)) {
                System.out.println("Player " + (player1Turn ? "1" : "2") + " wins!");
                result = (player1Turn) ? 1 : 2; // Set result to the winning player
                gameEnd = true; // Set game end flag to true
            } else if (isBoardFull()) {
                System.out.println("It's a draw!");
                gameEnd = true; // Set game end flag to true if board is full
            } else {
                player1Turn = !player1Turn; // Switch turns
            }
        }
    }

    // Method to play a game against an AI
    public void playGameAgainstAI() {
        Scanner scanner = new Scanner(System.in); // Scanner to read player input
        boolean player1Turn = true; // Flag to track which player's turn it is
        boolean gameEnd = false; // Flag to track if the game has ended

        System.out.println("Welcome to Connect 4 (Player vs AI)!");
        System.out.println("Player: X");
        System.out.println("AI: O");
        System.out.println("---------------");
        printBoard(); // Print the initial state of the board

        while (!gameEnd) {
            char currentPlayer = (player1Turn) ? PLAYER1 : PLAYER2;
            System.out.println("Player " + (player1Turn ? "1" : "2") + "'s turn (symbol: " + currentPlayer + ")");

            int col;
            if (player1Turn) {

                // Player's turn
                while (true) {
                    System.out.print("Enter column (0-" + (COLS - 1) + "): ");
                    if (scanner.hasNextInt()) {
                        col = scanner.nextInt();
                        if (col >= 0 && col < COLS && dropPiece(col, currentPlayer)) {
                            break; // Exit loop if valid column and piece is successfully dropped
                        } else {
                            System.out.println("Invalid input. Please enter a valid column number.");
                        }
                    } else {
                        System.out.println("Invalid input. Please enter a valid column number.");
                        scanner.next(); // Consume invalid input
                    }
                }
            } else {

                // AI's turn
                col = random.nextInt(COLS);
                while (!dropPiece(col, currentPlayer)) { // Randomly choose a column until a valid move is found
                    col = random.nextInt(COLS);
                }
                System.out.println("AI chose column: " + col);
            }

            printBoard(); // Print the updated board

            if (checkWin(currentPlayer)) {
                System.out.println((player1Turn ? "Player" : "AI") + " wins!");
                result = (player1Turn) ? 1 : 2; // Set result to the winning player (1 for player, 2 for AI)
                gameEnd = true; // Set game end flag to true
            } else if (isBoardFull()) {
                System.out.println("It's a draw!");
                gameEnd = true; // Set game end flag to true if board is full
            } else {
                player1Turn = !player1Turn; // Switch turns
            }
        }
    }

    // Check if board is full
    private boolean isBoardFull() {
        for (int i = 0; i < COLS; i++) {
            if (board[0][i] == EMPTY) {
                return false;
            }
        }
        return true;
    }

    // Getter method for result
    public int getResult() {
        return result;
    }
}
