import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.Scanner;
import java.util.InputMismatchException;

final public class Main {

    private JFrame frame;
    private JPanel panel;
    private JLabel label;
    private JButton start;
    private JButton quit;
    private static Random random = new Random();
    private static Scanner scanner = new Scanner(System.in);
    private int choice;

    public Main() {
        frame = new JFrame("The Gam Game");
        panel = new JPanel();
        label = new JLabel("Welcome to the Gam game!");
        start = new JButton("Start");
        quit = new JButton("Quit");

        panel.setBorder(BorderFactory.createEmptyBorder(200, 250, 200, 250));
        panel.setLayout(new GridLayout(0, 1));

        // Set font, size, and style
        label.setFont(new Font("Serif", Font.BOLD, 24));
        // Customize buttons
        start.setFont(new Font("Serif", Font.PLAIN, 18));
        start.setAlignmentX(Component.CENTER_ALIGNMENT);
        quit.setFont(new Font("Serif", Font.PLAIN, 18));
        quit.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add action listeners to buttons
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayInstructions(); // Display instructions in a dialog
            }
        });

        quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Exit the application
            }
        });

        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(0, 20))); // Add space between label and start button
        panel.add(start);
        panel.add(Box.createRigidArea(new Dimension(0, 10))); // Add space between start button and quit button
        panel.add(quit);

        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main();
            }
        });
    }

    // Method to display instructions
    private void displayInstructions() {
        JOptionPane.showMessageDialog(null, "Welcome to the Gam game!\n\n" +
                "Play a randomized game and winner puts a block in tictactoe, game goes on until tictactoe is over\n\n" +
                "Game rules:\n\n" +
                "Nim:\n" + "The game is played with 3 piles of objects.\n" +
                "Two players take turns.\n" +
                "On each turn, a player must remove at least 1 and at most 3 object from a single pile.\n" +
                "The player forced to take the last object loses.\n\n" +
                "Connect 4:\n" + "Player puts a symbol within a grid with 7 columns and 6 rows.\n" +
                "Two players take turns.\nThe symbols occupies the lowest available space within the column.\n" +
                "A player wins by connecting four of their symbols in a row horizontally, vertically, or diagonally.\n\n" +
                "Number Game:\n" + "Each player gets a random number between 0 - 21\n" +
                "Players can add their numbers up to 2 times, ranging from 1-4, 5-7, 8-10\n" +
                "Players with the highest number <= 21 wins\n\n" +
                "Choose the game mode:\n" +
                "1. Player vs Player\n" +
                "2. Player vs AI", "Instructions", JOptionPane.INFORMATION_MESSAGE);

        boolean validInput = false;
        while (!validInput) {
            String mode = JOptionPane.showInputDialog(null, "Please enter the game mode (1 (Player vs Player) or 2 (Player vs AI) ):");
            try {
                choice = Integer.parseInt(mode);
                if (choice != 1 && choice != 2) {
                    JOptionPane.showMessageDialog(null, "Invalid choice. Please enter 1 or 2.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    validInput = true;
                    // Start the game in a new thread to keep the GUI responsive
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            startGame();
                        }
                    }).start();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid number (1 or 2).", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void startGame() {
        int play = 0;
        int result = 0;
        char[][] ticTacToeBoard = new char[3][3]; // Tic-Tac-Toe board

        while (play == 0) { // Loop until the user exits the game
            // Play a random game
            int dice =2;

            // initialize games and get result
            switch (dice) {
                case 0:
                    // Starting Nim game and get result
                    System.out.println("You got Nim!");
                    Nim nim = new Nim();
                    if (choice == 1) {
                        // Player vs Player mode
                        nim.startGame(1);
                    } else {
                        // Player vs AI mode
                        nim.startGame(2);
                    }
                    result = nim.getResult();
                    break;

                case 1:
                    // Starting Connect 4 game and get result
                    System.out.println("You got Connect 4!");
                    Connect4 game = new Connect4();
                    if (choice == 1) {
                        // Player vs Player mode
                        game.playGame();
                    } else {
                        // Player vs AI mode
                        game.playGameAgainstAI();
                    }
                    result = game.getResult();
                    break;

                case 2:
                    // Starting Number game and get result
                    System.out.println("You got Number Game!");
                    NumberGame numberGame = new NumberGame();
                    if (choice == 1) {
                        // Player vs Player mode
                        numberGame.setGameMode(1);
                        numberGame.startGame();

                    } else {
                        // Player vs AI mode
                        numberGame.setGameMode(2);
                        numberGame.startGame();
                    }
                    result = numberGame.getResult();
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

                if (board[row][col] == '\u0000') {
                    board[row][col] = symbol; // Place the symbol on the board
                } else {
                    System.out.println("Position already occupied. Choose another position.");
                    placeBlock(scanner, board, symbol, choice); // Retry placing the block
                }

            } else { // Handle placing a block for Player O
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

                if (board[row][col] == '\u0000') {
                    board[row][col] = symbol; // Place the symbol on the board
                } else {
                    System.out.println("Position already occupied. Choose another position.");
                    placeBlock(scanner, board, symbol, choice); // Retry placing the block
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid number.");
            scanner.next(); // Consume invalid input
            placeBlock(scanner, board, symbol, choice); // Retry placing the block
        }
    }

    // Method to print the Tic-Tac-Toe board
    public static void printBoard(char[][] board) {
        System.out.println("Current Tic-Tac-Toe board:");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j] == '\u0000' ? "_" : board[i][j]);
                if (j < 2) System.out.print("|");
            }
            System.out.println();
        }
    }

    // Method to check if the board is full
    public static boolean isBoardFull(char[][] board) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '\u0000') {
                    return false;
                }
            }
        }
        return true;
    }

    // Method to check if the current player has won
    public static boolean isWinner(char[][] board, char symbol) {
        // Check rows
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == symbol && board[i][1] == symbol && board[i][2] == symbol) {
                return true;
            }
        }
        // Check columns
        for (int i = 0; i < 3; i++) {
            if (board[0][i] == symbol && board[1][i] == symbol && board[2][i] == symbol) {
                return true;
            }
        }
        // Check diagonals
        if (board[0][0] == symbol && board[1][1] == symbol && board[2][2] == symbol) {
            return true;
        }
        if (board[0][2] == symbol && board[1][1] == symbol && board[2][0] == symbol) {
            return true;
        }
        return false;
    }
}
