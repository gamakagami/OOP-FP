import java.util.Random;
import java.util.Scanner;
import java.util.InputMismatchException;


final public class Main {

    public static void main(String[] args) {
        Random random = new Random();
        Scanner scanner = new Scanner(System.in);
        int play = 0;
        int plays = 0;
        int result = 0;
        int choice = 0; // Variable to store the user's choice
        char[][] ticTacToeBoard = new char[3][3]; // Tic-Tac-Toe board

        while (play == 0) { // Loop until the user exits the game

            // Explain the game rule
            if (plays == 0) {
                if (choice == 0) { // If it's the first iteration, prompt for the choice
                    System.out.println("Welcome to the Gam game!");
                    System.out.println("------------------------");
                    System.out.println("Play a randomized game and winner puts a block in tictactoe, game goes on until tictactoe is over");
                    System.out.println("------------------------");
                    System.out.println("Game rules:");

                    //Nim rules
                    System.out.println("Nim:\n"+"The game is played with 3 piles of objects.\n" +
                            "Two players take turns.\n" +
                            "On each turn, a player must remove at least 1 and at most 3 object from a single pile.\n" +
                            "The player forced to take the last object loses.");
                    System.out.println("------------------------");

                    //Connect 4 rules
                    System.out.println("Connect 4:\nPlayer puts a symbol within a grid with 7 columns and 6 rows.\n" +
                            "Two players take turns.\nThe symbols occupies the lowest available space within the column.\n" +
                            "A player wins by connecting four of their symbols in a row horizontally, vertically, or diagonally.");
                    System.out.println("------------------------");

                    //Number Game rules
                    System.out.println("Number Game:");
                    System.out.println("Each player gets a random number between 0 - 21");
                    System.out.println("Players can add their numbers up to 2 times, ranging from 1-4, 5-7, 8-10");
                    System.out.println("Players with the highest number <= 21 wins");
                    System.out.println("------------------------");

                    // Ask user to enter game mode
                    System.out.println("Choose the game mode:");
                    System.out.println("1. Player vs Player");
                    System.out.println("2. Player vs AI");
                    System.out.println("------------------------");
                    System.out.print("Please enter your choice (1 or 2): ");
                    choice = scanner.nextInt();

                    // Validation until user enters a valid game mode
                    while (choice != 1 && choice != 2) {
                        System.out.println("Invalid choice. Please enter 1 or 2.");
                        choice = scanner.nextInt();
                    }
                }

                // exit choosing game mode
                plays ++;
            }

            // Play a random game
            int dice = random.nextInt(3);

            // initialize games and get result
            switch (dice) {
                case 0:

                    // Starting Nim game and get result
                    System.out.println("You got Nim!");
                    Nim nim = new Nim();
                    nim.startGame(choice, scanner);
                    result = nim.getResult();
                    break;

                case 1:

                    // Starting Connect 4 game and get result
                    System.out.println("You got Connect 4!");
                    Connect4 game = new Connect4();
                    if (choice == 1) {

                        // Player vs Player mode
                        game.playGame();
                        result = game.getResult();
                    } else {

                        // Player vs AI mode
                        game.playGameAgainstAI();
                        result = game.getResult();
                    }
                    break;

                case 2:

                    // Starting Number game and get result
                    System.out.println("You got Number Game!");
                    NumberGame numberGame = new NumberGame();
                    if (choice == 1) {

                        // Player vs Player mode
                        numberGame.playTwoPlayers(random, scanner);
                        result = numberGame.getResult();
                    } else {

                        // Player vs AI mode
                        numberGame.playVsAI(random, scanner);
                        result = numberGame.getResult();
                    }
                    break;
            }

            // Check if the result of the previous game indicates a winner
            if (result == 1 || result == 2) {
                // Print the current state of the Tic-Tac-Toe board
                printBoard(ticTacToeBoard);
                // Place a block on the Tic-Tac-Toe board for the winning player
                placeBlock(scanner, ticTacToeBoard, (result == 1) ? 'X' : 'O', choice);

                // Check if the current player has won
                if (isWinner(ticTacToeBoard, (result == 1) ? 'X' : 'O')) {
                    System.out.println("Player " + ((result == 1) ? '1' : '2') + " wins!");
                    break; // Exit the loop if there's a winner
                } else if (isBoardFull(ticTacToeBoard)) { // Check if the board is full and it's a draw
                    System.out.println("Draw! The board is full.");
                    break; // Exit the loop if the board is full
                }
                result = 0; // Reset result for the next game
            }
        }
        scanner.close();
    }

    // Method to place a block on the Tic-Tac-Toe board
    public static void placeBlock(Scanner scanner, char[][] board, char symbol, int choice) {
        try {
            if (symbol == 'X') { // Handle placing a block for Player X
                System.out.println("Player 1 (X) turn.");
                int row = -1, col = -1;
                boolean validInput = false;

                // Loop to get valid row input from the user
                while (!validInput) {
                    System.out.print("Enter the row (0-2) to place " + symbol + ": ");
                    if (scanner.hasNextInt()) {
                        row = scanner.nextInt();
                        if (row >= 0 && row <= 2) {
                            validInput = true; // Valid row
                        } else {
                            System.out.println("Invalid row. Please enter a number between 0 and 2.");
                        }
                    } else {
                        System.out.println("Invalid input. Please enter a valid number.");
                        scanner.next(); // Consume invalid input
                    }
                }

                validInput = false; // Reset validInput flag for column input

                // Loop to get valid column input from the user
                while (!validInput) {
                    System.out.print("Enter the column (0-2) to place " + symbol + ": ");
                    if (scanner.hasNextInt()) {
                        col = scanner.nextInt();
                        if (col >= 0 && col <= 2) {
                            validInput = true; // Valid column
                        } else {
                            System.out.println("Invalid column. Please enter a number between 0 and 2.");
                        }
                    } else {
                        System.out.println("Invalid input. Please enter a valid number.");
                        scanner.next(); // Consume invalid input
                    }
                }

                // Check if the cell is empty before placing the symbol
                if (board[row][col] == '\u0000') {
                    board[row][col] = symbol;
                } else {
                    // Show current board state and ask for input again
                    System.out.println("The cell is already occupied. Please choose another cell.");
                    printBoard(board);
                    placeBlock(scanner, board, symbol, choice);
                }
                printBoard(board); // Print the board after placing the block
            } else {  // Handle placing a block for Player O
                if (choice == 1) { // If player vs player mode
                    System.out.println("Player 2 (O) turn.");
                    int row = -1, col = -1;
                    boolean validInput = false;

                    // Loop to get valid row input from the user
                    while (!validInput) {
                        System.out.print("Enter the row (0-2) to place " + symbol + ": ");
                        if (scanner.hasNextInt()) {
                            row = scanner.nextInt();
                            if (row >= 0 && row <= 2) {
                                validInput = true; // Valid row
                            } else {
                                System.out.println("Invalid row. Please enter a number between 0 and 2.");
                            }
                        } else {
                            System.out.println("Invalid input. Please enter a valid number.");
                            scanner.next(); // Consume invalid input
                        }
                    }

                    validInput = false; // Reset validInput flag for column input

                    // Loop to get valid column input from the user
                    while (!validInput) {
                        System.out.print("Enter the column (0-2) to place " + symbol + ": ");
                        if (scanner.hasNextInt()) {
                            col = scanner.nextInt();
                            if (col >= 0 && col <= 2) {
                                validInput = true; // Valid column
                            } else {
                                System.out.println("Invalid column. Please enter a number between 0 and 2.");
                            }
                        } else {
                            System.out.println("Invalid input. Please enter a valid number.");
                            scanner.next(); // Consume invalid input
                        }
                    }

                    // Check if the cell is empty before placing the symbol
                    if (board[row][col] == '\u0000') {
                        board[row][col] = symbol;
                    } else {
                        System.out.println("The cell is already occupied. Please choose another cell.");
                        printBoard(board); // Show current board state
                        placeBlock(scanner, board, symbol, choice); // Ask for input again
                    }
                    printBoard(board); // Print the board after placing the block
                } else { // If player vs AI mode
                    System.out.println("AI's (O) turn");
                    Random random = new Random();
                    int row, col;

                    // Generate random moves until an empty cell is found
                    do {
                        row = random.nextInt(3);
                        col = random.nextInt(3);
                    } while (board[row][col] != '\u0000'); // Keep generating random moves until an empty cell is found
                    board[row][col] = symbol;
                    System.out.println("AI placed " + symbol + " at row " + row + ", column " + col);
                    printBoard(board); // Print the board after placing the block
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid number.");
            scanner.next(); // Consume invalid input
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    // Method to print the Tic-Tac-Toe board
    public static void printBoard(char[][] board) {
        System.out.println("\nTic-Tac-Toe Board:");
        for (char[] row : board) {
            for (char cell : row) {
                if (cell == '\u0000') {
                    System.out.print("_ "); // Print underscore for empty cells
                } else {
                    System.out.print(cell + " "); // Print symbol for occupied cells
                }
            }
            System.out.println();
        }
    }

    // Method to check for a winner in Tic-Tac-Toe
    public static boolean isWinner(char[][] board, char symbol) {

        // Check rows and columns
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == symbol && board[i][1] == symbol && board[i][2] == symbol) {
                return true; // Row win
            }
            if (board[0][i] == symbol && board[1][i] == symbol && board[2][i] == symbol) {
                return true; // Column win
            }
        }

        // Check diagonals
        if (board[0][0] == symbol && board[1][1] == symbol && board[2][2] == symbol) {
            return true; // Diagonal win (top-left to bottom-right)
        }
        if (board[0][2] == symbol && board[1][1] == symbol && board[2][0] == symbol) {
            return true; // Diagonal win (top-right to bottom-left)
        }

        return false; // No win found
    }
    // Method to check if the Tic-Tac-Toe board is full
    public static boolean isBoardFull(char[][] board) {
        for (char[] row : board) {
            for (char cell : row) {
                if (cell == '\u0000') {
                    return false; // Found an empty cell
                }
            }
        }
        return true; // All cells are occupied
    }

}
