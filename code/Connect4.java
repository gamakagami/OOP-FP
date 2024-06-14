import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Connect4 extends Game {

    // Constants for game parameters
    private static final int ROWS = 6;
    private static final int COLS = 7;
    private static final char EMPTY = '-';
    private static final char PLAYER1 = 'X';
    private static final char PLAYER2 = 'O';

    // Game state variables
    private char[][] board;
    private Random random;
    private boolean player1Turn = true;
    private JButton[] buttons;
    private JLabel[][] slots;
    private JFrame frame;

    // Constructor to initialize game
    public Connect4() {
        board = new char[ROWS][COLS];
        random = new Random();
        // Initialize Board and GUI
        initializeBoard();
        initializeUI();
    }

    // Initialize the game board with empty slots
    private void initializeBoard() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                board[i][j] = EMPTY;
            }
        }
    }

    // Initialize the GUI
    private void initializeUI() {

        // Create main frame
        frame = new JFrame("Connect 4");
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel boardPanel = new JPanel();

        // Display welcome message
        JOptionPane.showMessageDialog(frame, "You got Connect 4!");

        // Shows the buttons per column and labels per slot
        boardPanel.setLayout(new GridLayout(ROWS + 1, COLS));
        buttons = new JButton[COLS];
        slots = new JLabel[ROWS][COLS];

        // Create buttons for each column
        for (int i = 0; i < COLS; i++) {
            buttons[i] = new JButton("Drop");
            int col = i;
            buttons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    handleButtonClick(col); // Button click event handler
                }
            });
            boardPanel.add(buttons[i]); // Add button to panel
        }

        // Create labels for each board slot
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                slots[i][j] = new JLabel(String.valueOf(EMPTY), SwingConstants.CENTER);
                slots[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                slots[i][j].setFont(new Font("Serif", Font.PLAIN, 24));
                boardPanel.add(slots[i][j]); // Add label to board panel
            }
        }

        // Add board panel to main frame and setting the frame
        frame.add(boardPanel, BorderLayout.CENTER);
        frame.setSize(700, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // Event handler for column drop buttons
    private void handleButtonClick(int col) {
        if (dropPiece(col, player1Turn ? PLAYER1 : PLAYER2)) { // Try to drop piece in column
            updateBoard();
            if (checkWin(player1Turn ? PLAYER1 : PLAYER2)) {

                // Win message and reset game state
                JOptionPane.showMessageDialog(frame, "Player " + (player1Turn ? "1" : "2") + " wins!");
                result = player1Turn ? 1 : 2;
                resetGame();
            } else if (isBoardFull()) {

                // Draw message and reset game state
                JOptionPane.showMessageDialog(frame, "It's a draw!");
                result = 0;
                resetGame();
            } else {
                player1Turn = !player1Turn; // Switch turns
            }
        }
    }

    // Attempts to drop piece into the specified column by the current player
    private boolean dropPiece(int col, char player) {
        if (col < 0 || col >= COLS) {
            JOptionPane.showMessageDialog(frame, "Invalid column. Please choose a column between 0 and 6.");
            return false;
        }

        // Check empty slot in the column
        for (int i = ROWS - 1; i >= 0; i--) {
            if (board[i][col] == EMPTY) {
                board[i][col] = player;
                return true;
            }
        }
        JOptionPane.showMessageDialog(frame, "Column is full. Please choose another column.");
        return false;
    }

    // Method to check if current player has won the game
    private boolean checkWin(char player) {

        // Check horizontally
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j <= COLS - 4; j++) {
                if (board[i][j] == player && board[i][j + 1] == player && board[i][j + 2] == player && board[i][j + 3] == player) {
                    frame.dispose();
                    return true;
                }
            }
        }

        // Check vertically
        for (int j = 0; j < COLS; j++) {
            for (int i = 0; i <= ROWS - 4; i++) {
                if (board[i][j] == player && board[i + 1][j] == player && board[i + 2][j] == player && board[i + 3][j] == player) {
                    frame.dispose();
                    return true;

                }
            }
        }

        // Check diagonally for both directions
        for (int i = 0; i <= ROWS - 4; i++) {
            for (int j = 0; j <= COLS - 4; j++) {
                if (board[i][j] == player && board[i + 1][j + 1] == player && board[i + 2][j + 2] == player && board[i + 3][j + 3] == player) {
                    frame.dispose();

                    return true;
                }
                if (board[i][j + 3] == player && board[i + 1][j + 2] == player && board[i + 2][j + 1] == player && board[i + 3][j] == player) {
                    frame.dispose();
                    return true;
                }
            }
        }
        return false;
    }

    // Update GUI to show the current state of the board
    private void updateBoard() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                slots[i][j].setText(String.valueOf(board[i][j]));
            }
        }
    }

    // Check if board is full
    private boolean isBoardFull() {
        for (int i = 0; i < COLS; i++) {
            if (board[0][i] == EMPTY) {
                return false; // Empty slot, board not full
            }
        }
        return true; // Board is full
    }

    // Reset game state
    private void resetGame() {

        // Clear game board and reset  to player 1 turn
        initializeBoard();
        updateBoard();
        player1Turn = true;
    }

    // Method for player vs player
    public void playGame() {

        // Reset game state to start a new game
        resetGame();
        boolean gameEnd = false;

        while (!gameEnd) {
            char currentPlayer = (player1Turn) ? PLAYER1 : PLAYER2; // Determine current player

            // Display turn information and ask for column selection
            JTextField textField = new JTextField();
            Object[] message = {
                    "Player " + (currentPlayer == PLAYER1 ? "1" : "2") +" ("+currentPlayer +") turn, Enter column (0-" + (COLS - 1) + "):",
                    textField
            };
            int option = JOptionPane.showOptionDialog(null, message, "Enter Column", JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE, null, null, null);

            if (option == JOptionPane.OK_OPTION) {
                try {
                    int col = Integer.parseInt(textField.getText().trim()); // Parse column input
                    if (dropPiece(col, currentPlayer)) { // Try to drop piece in chosen column
                        updateBoard(); // Update GUI
                    } else {
                        continue; // Invalid move, continue loop
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(frame, "Invalid input. Please enter a valid column number.");
                    continue; // Input parsing error, continue loop
                }
            } else {
                System.exit(0); // Exit the program if user clicked cancel or close the dialog
            }

            // Check win or draw condition after each move
            if (checkWin(currentPlayer)) {

                // Win message and reset game state
                JOptionPane.showMessageDialog(frame, "Player " + (player1Turn ? "1" : "2") + " wins!");
                result = player1Turn ? 1 : 2;
                gameEnd = true;
            } else if (isBoardFull()) {

                // Draw message and reset game state
                JOptionPane.showMessageDialog(frame, "It's a draw!");
                result = 0;
                gameEnd = true;
            } else {
                player1Turn = !player1Turn; // Switch turns
            }
        }
    }

    // Method for player vs AI
    public void playGameAgainstAI() {

        // Reset game state to start a new game
        resetGame();
        boolean gameEnd = false;

        while (!gameEnd) {
            char currentPlayer = (player1Turn) ? PLAYER1 : PLAYER2; // Determine current player

            if (player1Turn) {
                System.out.println("Player's turn (symbol: " + currentPlayer + ")");
                String input = JOptionPane.showInputDialog("Enter column (0-" + (COLS - 1) + "):"); // Ask for input
                try {
                    int col = Integer.parseInt(input); // Parse column input
                    if (dropPiece(col, currentPlayer)) { // Try to drop piece in chosen column
                        updateBoard(); // Update GUI
                    } else {
                        continue; // Invalid move, continue loop
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(frame, "Invalid input. Please enter a valid column number.");
                    continue; // Input parsing error, continue loop
                }
            } else {
                int col = random.nextInt(COLS); // AI chooses a random column
                while (!dropPiece(col, currentPlayer)) { // AI tries to drop piece in chosen column
                    col = random.nextInt(COLS); // Retry if column is full
                }
                updateBoard(); // Update GUI after AI moves
            }

            // Check win or draw condition after each move
            if (checkWin(currentPlayer)) {

                // Win message and reset game state
                JOptionPane.showMessageDialog(frame, (player1Turn ? "Player" : "AI") + " wins!");
                result = player1Turn ? 1 : 2;
                gameEnd = true;
            } else if (isBoardFull()) {

                // Draw message and reset game state
                JOptionPane.showMessageDialog(frame, "It's a draw!");
                result = 0;
                gameEnd = true;
            } else {
                player1Turn = !player1Turn; // Switch turns
            }
        }
    }
    }
