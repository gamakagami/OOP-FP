import java.util.Random;
import java.util.Scanner;

public class Connect4 {
    private static final int ROWS = 6;
    private static final int COLS = 7;
    private static final char EMPTY = '-';
    private static final char PLAYER1 = 'X';
    private static final char PLAYER2 = 'O';

    private char[][] board;
    private Random random;
    private int result = 0;

    public Connect4() {
        board = new char[ROWS][COLS];
        random = new Random();
        initializeBoard();
    }

    private void initializeBoard() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                board[i][j] = EMPTY;
            }
        }
    }

    private void printBoard() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("---------------");
    }

    private boolean dropPiece(int col, char player) {
        if (col < 0 || col >= COLS) {
            System.out.println("Invalid column. Please choose a column between 0 and " + (COLS - 1) + ".");
            return false;
        }
        for (int i = ROWS - 1; i >= 0; i--) {
            if (board[i][col] == EMPTY) {
                board[i][col] = player;
                return true;
            }
        }
        System.out.println("Column is full. Please choose another column.");
        return false;
    }

    private boolean checkWin(char player) {
        // Check rows
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j <= COLS - 4; j++) {
                if (board[i][j] == player && board[i][j + 1] == player && board[i][j + 2] == player && board[i][j + 3] == player) {
                    return true;
                }
            }
        }
        // Check columns
        for (int j = 0; j < COLS; j++) {
            for (int i = 0; i <= ROWS - 4; i++) {
                if (board[i][j] == player && board[i + 1][j] == player && board[i + 2][j] == player && board[i + 3][j] == player) {
                    return true;
                }
            }
        }
        // Check diagonals
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

    public void playGame() {
        Scanner scanner = new Scanner(System.in);
        boolean player1Turn = true;
        boolean gameEnd = false;

        System.out.println("Welcome to Connect 4!");
        System.out.println("Player 1: X");
        System.out.println("Player 2: O");
        System.out.println("---------------");
        printBoard();

        while (!gameEnd) {
            char currentPlayer = (player1Turn) ? PLAYER1 : PLAYER2;
            System.out.println("Player " + (player1Turn ? "1" : "2") + "'s turn (symbol: " + currentPlayer + ")");

            int col;
            while (true) {
                System.out.print("Enter column (0-" + (COLS - 1) + "): ");
                if (scanner.hasNextInt()) {
                    col = scanner.nextInt();
                    if (col >= 0 && col < COLS && dropPiece(col, currentPlayer)) {
                        break;
                    } else {
                        System.out.println("Invalid input. Please enter a valid column number.");
                    }
                } else {
                    System.out.println("Invalid input. Please enter a valid column number.");
                    scanner.next(); // Consume invalid input
                }
            }

            printBoard();

            if (checkWin(currentPlayer)) {
                System.out.println("Player " + (player1Turn ? "1" : "2") + " wins!");
                result = (player1Turn) ? 1 : 2;
                gameEnd = true;
            } else if (isBoardFull()) {
                System.out.println("It's a draw!");
                gameEnd = true;
            } else {
                player1Turn = !player1Turn;
            }
        }
    }

    public void playGameAgainstAI() {
        Scanner scanner = new Scanner(System.in);
        boolean player1Turn = true;
        boolean gameEnd = false;

        System.out.println("Welcome to Connect 4 (Player vs AI)!");
        System.out.println("Player: X");
        System.out.println("AI: O");
        System.out.println("---------------");
        printBoard();

        while (!gameEnd) {
            char currentPlayer = (player1Turn) ? PLAYER1 : PLAYER2;
            System.out.println("Player " + (player1Turn ? "1" : "2") + "'s turn (symbol: " + currentPlayer + ")");

            int col;
            if (player1Turn) {
                while (true) {
                    System.out.print("Enter column (0-" + (COLS - 1) + "): ");
                    if (scanner.hasNextInt()) {
                        col = scanner.nextInt();
                        if (col >= 0 && col < COLS && dropPiece(col, currentPlayer)) {
                            break;
                        } else {
                            System.out.println("Invalid input. Please enter a valid column number.");
                        }
                    } else {
                        System.out.println("Invalid input. Please enter a valid column number.");
                        scanner.next(); // Consume invalid input
                    }
                }
            } else {
                col = random.nextInt(COLS);
                while (!dropPiece(col, currentPlayer)) {
                    col = random.nextInt(COLS);
                }
                System.out.println("AI chose column: " + col);
            }

            printBoard();

            if (checkWin(currentPlayer)) {
                System.out.println((player1Turn ? "Player" : "AI") + " wins!");
                result = (player1Turn) ? 1 : 2;
                gameEnd = true;
            } else if (isBoardFull()) {
                System.out.println("It's a draw!");
                gameEnd = true;
            } else {
                player1Turn = !player1Turn;
            }
        }
    }

    private boolean isBoardFull() {
        for (int i = 0; i < COLS; i++) {
            if (board[0][i] == EMPTY) {
                return false;
            }
        }
        return true;
    }

    public int getResult() {
        return result;
    }
}
