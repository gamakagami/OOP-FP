import java.util.Scanner;
import java.util.Random;

// constants for the game board, empty spaces and players
public class Connect4 {
    private static final int ROWS = 6;
    private static final int COLS = 7;
    private static final char EMPTY = '-';
    private static final char PLAYER1 = 'X';
    private static final char PLAYER2 = 'O';

    // variables for game board and random generator
    private char[][] board;
    private Random random;

    int result = 0;

    // constructs and initializes the game board
    public Connect4() {
        board = new char[ROWS][COLS];
        random = new Random();
        initializeBoard();
    }

    // initialize the board with empty character
    private void initializeBoard() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                board[i][j] = EMPTY;
            }
        }
    }

    // prints the state of the board after every move
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
            System.out.print("Enter column (0-" + (COLS - 1) + "): ");
            col = scanner.nextInt();

            if (dropPiece(col, currentPlayer)) {
                printBoard();
                if (checkWin(currentPlayer)) {
                    if (player1Turn){
                        System.out.println("Player 1 wins");
                        result = 1;
                    }else{
                        System.out.println("Player 2 wins");
                        result = 2;
                    }
                    gameEnd = true;
                } else if (isBoardFull()) {
                    System.out.println("It's a draw!");
                    gameEnd = true;
                } else {
                    player1Turn = !player1Turn;
                }
            }
        }
        scanner.close();
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
                System.out.print("Enter column (0-" + (COLS - 1) + "): ");
                col = scanner.nextInt();
            } else {
                // AI chooses a column
                col = random.nextInt(COLS);
                System.out.println("AI chooses column: " + col);
            }

            if (dropPiece(col, currentPlayer)) {
                printBoard();
                if (checkWin(currentPlayer)) {
                    if (player1Turn) {
                        System.out.println("Player 1 wins!");
                        result = 1;
                    } else {
                        System.out.println("AI wins!");
                        result = 2;
                    }
                    gameEnd = true;
                } else if (isBoardFull()) {
                    System.out.println("It's a draw!");
                    gameEnd = true;
                } else {
                    player1Turn = !player1Turn;
                }
            }
        }
        scanner.close();
    }

    private boolean isBoardFull() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (board[i][j] == EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }
    public int getResult(){
        return result;

    }
}
