import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.Scanner;

final public class Main {

    // GUI components
    private JFrame frame;
    private JPanel panel;
    private JLabel label;
    private JButton start;
    private JButton quit;
    private static Random random = new Random();
    private static Scanner scanner = new Scanner(System.in);
    private int choice; // Game mode choice
    private char[][] ticTacToeBoard = new char[3][3]; // Tic-Tac-Toe board

    // Constructor to initialize GUI
    public Main() {

        // Create main frame
        frame = new JFrame("The Gam Game");
        panel = new JPanel();
        label = new JLabel("Welcome to the Gam game!");
        start = new JButton("Start");
        quit = new JButton("Quit");

        // Set panel border and layout
        panel.setBorder(BorderFactory.createEmptyBorder(200, 250, 200, 250));
        panel.setLayout(new GridLayout(0, 1));

        // Set font, size, and style
        label.setFont(new Font("Serif", Font.BOLD, 24));

        // Customize buttons and alignment
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

        // Add components to the panel
        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(0, 20))); // Add space between label and start button
        panel.add(start);
        panel.add(Box.createRigidArea(new Dimension(0, 10))); // Add space between start button and quit button
        panel.add(quit);

        // Add panel to the frame
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600); // Set fixed size for the initial frame
        frame.setVisible(true);
    }

    // Main method to run the program
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

        // Display instructions in a dialog box
        JOptionPane.showMessageDialog(null, "Welcome to the Gam game!\n\n" +
                "---------------------------------------\n"+
                "Play a randomized game and winner puts a block in tictactoe, game goes on until tictactoe is over\n\n" +
                "Game rules:\n" +
                "---------------------------------------\n"+
                "Nim:\n" + "The game is played with 3 piles of objects.\n" +
                "Two players take turns.\n" +
                "On each turn, a player must remove at least 1 and at most 3 object from a single pile.\n" +
                "The player forced to take the last object loses.\n" +
                "---------------------------------------\n"+
                "Connect 4:\n" + "Player puts a symbol within a grid with 7 columns and 6 rows.\n" +
                "Two players take turns.\nThe symbols occupies the lowest available space within the column.\n" +
                "A player wins by connecting four of their symbols in a row horizontally, vertically, or diagonally.\n" +
                "---------------------------------------\n"+
                "Number Game:\n" + "Each player gets a random number between 0 - 21\n" +
                "Players can add their numbers up to 2 times, ranging from 1-4, 5-7, 8-10\n" +
                "Players with the highest number <= 21 wins\n" +
                "---------------------------------------\n"+
                "Choose the game mode:\n" +
                "1. Player vs Player\n" +
                "2. Player vs AI", "Instructions", JOptionPane.INFORMATION_MESSAGE);

        boolean validInput = false;
        while (!validInput) {

            // Ask the user to enter the game mode
            String mode = JOptionPane.showInputDialog(null, "Please enter the game mode (1 (Player vs Player) or 2 (Player vs AI) ):");
            try { // Check input is valid or not
                choice = Integer.parseInt(mode);
                if (choice != 1 && choice != 2) { // Input out of range
                    JOptionPane.showMessageDialog(null, "Invalid choice. Please enter 1 or 2.", "Error", JOptionPane.ERROR_MESSAGE);
                } else { // Valid input
                    validInput = true;
                    // Start the game in a new thread to keep the GUI responsive
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            startGame();
                        }
                    }).start();
                }
            } catch (NumberFormatException ex) { // Invalid input
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid number (1 or 2).", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Method to start game
    private void startGame() {
        int play = 0;

        while (play == 0) { // Loop until the user exits the game
            // Play a random game (Nim, Connect 4, or Number Game)
            int dice = random.nextInt(3); // Randomly choose between Nim, Connect 4, or Number Game
            int result = 0;

            // Initialize games and get result
            try {
                switch (dice) {
                    case 0:
                        Nim nim = new Nim();
                        nim.startGame(choice); // Start Nim game with chosen mode (1 or 2)
                        result = nim.getResult(); // Get result
                        break;
                    case 1:

                        Connect4 game = new Connect4();
                        if (choice == 1) { // Player vs Player mode
                            game.playGame();
                        } else { // Player vs AI mode
                            game.playGameAgainstAI();
                        }
                        result = game.getResult(); // Get result
                        break;
                    case 2:

                        while (result == 0) {
                            NumberGame numberGame = new NumberGame();
                            if (choice == 1) { // Player vs Player mode
                                numberGame.setGameMode(1);
                                numberGame.startGame();
                            } else { // Player vs AI mode
                                numberGame.setGameMode(2);
                                numberGame.startGame();
                            }
                            result = numberGame.getResult(); // Get result
                        }
                        break;
                }

                // Check if there is a winner or a draw
                if (result == 1 || result == 2) {
                    char symbol = (result == 1) ? 'X' : 'O'; // Determine symbol based on result
                    boolean isAI = (choice == 2 && result == 2); // Check if AI won in Player vs AI mode

                    // Show the Tic-Tac-Toe GUI
                    placeBlockGUI(ticTacToeBoard, symbol, isAI);

                    // Check if the current player has won
                    if (isWinner(ticTacToeBoard, symbol)) {
                        if (isAI) { // AI win
                            JOptionPane.showMessageDialog(frame, "AI Wins!", "Game Result", JOptionPane.INFORMATION_MESSAGE);
                        } else { // Player win
                            JOptionPane.showMessageDialog(frame, "Player " + ((result == 1) ? '1' : '2') + " Wins!", "Game Result", JOptionPane.INFORMATION_MESSAGE);
                        }

                        // Reset tictactoe board and exit loop
                        resetBoard();
                        break;
                    } else if (isBoardFull(ticTacToeBoard)) { // Check if the board is full and it's a draw
                        JOptionPane.showMessageDialog(frame, "Draw! The board is full ", "Result", JOptionPane.INFORMATION_MESSAGE);

                        // Reset tictactoe board and exit loop
                        resetBoard();
                        break; // Exit the loop if the board is full
                    }
                }

            } catch (Exception e) { // Error handling
                JOptionPane.showMessageDialog(null, "An error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        scanner.close();
    }

    // Method to show the Tic-Tac-Toe GUI and place a block
    private void placeBlockGUI(char[][] board, char symbol, boolean isAI) {
        JFrame ticTacToeFrame = new JFrame("Tic-Tac-Toe");
        JPanel ticTacToePanel = new JPanel();
        ticTacToePanel.setLayout(new BorderLayout());

        // Set text to show whose turn it is
        JLabel statusLabel = new JLabel("Player " + (symbol == 'X' ? '1' : '2') + " (" + symbol + ") turn", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Serif", Font.BOLD, 24));
        ticTacToePanel.add(statusLabel, BorderLayout.NORTH);

        // Creates panel and initialize 3x3 array to hold buttons
        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(3, 3));
        JButton[][] buttons = new JButton[3][3];

        // Create buttons for tictactoe board
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton(board[i][j] == '\u0000' ? "" : String.valueOf(board[i][j])); // if not blank, show the string value (X or O)
                buttons[i][j].setFont(new Font("Serif", Font.PLAIN, 60));
                final int row = i;
                final int col = j;
                buttons[i][j].setEnabled(board[i][j] == '\u0000'); // Disable button if already occupied
                buttons[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (board[row][col] == '\u0000') {
                            board[row][col] = symbol;
                            buttons[row][col].setText(String.valueOf(symbol));
                            disableButtons(buttons); // Disable all buttons after one move
                        }
                    }
                });
                boardPanel.add(buttons[i][j]); // Show button placed in the GUI
            }
        }

        if (isAI) { // AI places move and show tictactoe board
            placeAIMove(board, buttons, symbol);
            showBoardGUI(ticTacToeBoard);
            return;
        }

        // Continue button to close tictactoe frame
        JButton continueButton = new JButton("Continue");
        continueButton.setFont(new Font("Serif", Font.PLAIN, 24));
        continueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ticTacToeFrame.dispose(); // Close tictactoe frame
            }
        });

        // Add components to Tic-Tac-Toe panel
        ticTacToePanel.add(boardPanel, BorderLayout.CENTER);
        ticTacToePanel.add(continueButton, BorderLayout.SOUTH);

        // Setup Tic-Tac-Toe frame properties
        ticTacToeFrame.add(ticTacToePanel);
        ticTacToeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ticTacToeFrame.setSize(400, 400); // Set fixed size for the Tic-Tac-Toe frame
        ticTacToeFrame.setVisible(true);

        // Wait for the Tic-Tac-Toe window to close before continuing
        while (ticTacToeFrame.isDisplayable()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Method to show the current state of the Tic-Tac-Toe board in the GUI (for when AI turn)
    private void showBoardGUI(char[][] board) {
        JFrame ticTacToeFrame = new JFrame("Tic-Tac-Toe Board");
        JPanel ticTacToePanel = new JPanel();
        ticTacToePanel.setLayout(new BorderLayout());

        // Show text to show it is AI's turn
        JLabel statusLabel = new JLabel("AI (O) turn", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Serif", Font.BOLD, 24));
        ticTacToePanel.add(statusLabel, BorderLayout.NORTH);

        // Creates panel and initialize 3x3 array to hold buttons
        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(3, 3));
        JButton[][] buttons = new JButton[3][3];

        // Show buttons placed in the GUI
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton(board[i][j] == '\u0000' ? "" : String.valueOf(board[i][j]));
                buttons[i][j].setFont(new Font("Serif", Font.PLAIN, 60));
                buttons[i][j].setEnabled(false); // Disable button for viewing only
                boardPanel.add(buttons[i][j]);
            }
        }

        // Add components to Tic-Tac-Toe panel
        ticTacToePanel.add(boardPanel, BorderLayout.CENTER);

        // Continue button to close the Tic-Tac-Toe frame
        JButton continueButton = new JButton("Continue");
        continueButton.setFont(new Font("Serif", Font.PLAIN, 24));
        continueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ticTacToeFrame.dispose(); // Close tictactoe frame
            }
        });

        // Add continue button to the panel
        ticTacToePanel.add(continueButton, BorderLayout.SOUTH);

        // Setup Tic-Tac-Toe frame properties
        ticTacToeFrame.add(ticTacToePanel);
        ticTacToeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ticTacToeFrame.setSize(400, 400); // Set fixed size for the Tic-Tac-Toe frame
        ticTacToeFrame.setVisible(true);

        // Wait for the Tic-Tac-Toe window to close before continuing
        while (ticTacToeFrame.isDisplayable()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Method for AI to place its move
    private void placeAIMove(char[][] board, JButton[][] buttons, char symbol) {
        // AI places the first available move
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '\u0000') {
                    board[i][j] = symbol;
                    buttons[i][j].setText(String.valueOf(symbol));
                    buttons[i][j].setEnabled(false);
                    return;
                }
            }
        }
    }

    // Method to reset the Tic-Tac-Toe board
    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                ticTacToeBoard[i][j] = '\u0000';
            }
        }
    }

    // Method to disable all buttons on the Tic-Tac-Toe board
    private void disableButtons(JButton[][] buttons) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setEnabled(false);
            }
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
        // Check diagonal for both directions
        if (board[0][0] == symbol && board[1][1] == symbol && board[2][2] == symbol) {
            return true;
        }
        if (board[0][2] == symbol && board[1][1] == symbol && board[2][0] == symbol) {
            return true;
        }
        return false;
    }
}

