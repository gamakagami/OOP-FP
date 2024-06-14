import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Connect4 extends Game {

    private static final int ROWS = 6;
    private static final int COLS = 7;
    private static final char EMPTY = '-';
    private static final char PLAYER1 = 'X';
    private static final char PLAYER2 = 'O';

    private char[][] board;
    private Random random;
    private boolean player1Turn = true;
    private JButton[] buttons;
    private JLabel[][] slots;
    private JFrame frame;


    public Connect4() {
        board = new char[ROWS][COLS];
        random = new Random();
        initializeBoard();
        initializeUI();
    }

    private void initializeBoard() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                board[i][j] = EMPTY;
            }
        }
    }

    private void initializeUI() {
        frame = new JFrame("Connect 4");
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Display welcome message
        JOptionPane.showMessageDialog(frame, "You got Connect 4!");

        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(ROWS + 1, COLS));
        buttons = new JButton[COLS];
        slots = new JLabel[ROWS][COLS];

        for (int i = 0; i < COLS; i++) {
            buttons[i] = new JButton("Drop");
            int col = i;
            buttons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    handleButtonClick(col);
                }
            });
            boardPanel.add(buttons[i]);
        }

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                slots[i][j] = new JLabel(String.valueOf(EMPTY), SwingConstants.CENTER);
                slots[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                slots[i][j].setFont(new Font("Serif", Font.PLAIN, 24));
                boardPanel.add(slots[i][j]);
            }
        }

        frame.add(boardPanel, BorderLayout.CENTER);
        frame.setSize(700, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


    private void handleButtonClick(int col) {
        if (dropPiece(col, player1Turn ? PLAYER1 : PLAYER2)) {
            updateBoard();
            if (checkWin(player1Turn ? PLAYER1 : PLAYER2)) {
                JOptionPane.showMessageDialog(frame, "Player " + (player1Turn ? "1" : "2") + " wins!");
                result = player1Turn ? 1 : 2;
                resetGame();
            } else if (isBoardFull()) {
                JOptionPane.showMessageDialog(frame, "It's a draw!");
                result = 0; // Draw
                resetGame();
            } else {
                player1Turn = !player1Turn;
            }
        }
    }

    private boolean dropPiece(int col, char player) {
        if (col < 0 || col >= COLS) {
            JOptionPane.showMessageDialog(frame, "Invalid column. Please choose a column between 0 and " + (COLS - 1) + ".");
            return false;
        }
        for (int i = ROWS - 1; i >= 0; i--) {
            if (board[i][col] == EMPTY) {
                board[i][col] = player;
                return true;
            }
        }
        JOptionPane.showMessageDialog(frame, "Column is full. Please choose another column.");
        return false;
    }

    private boolean checkWin(char player) {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j <= COLS - 4; j++) {
                if (board[i][j] == player && board[i][j + 1] == player && board[i][j + 2] == player && board[i][j + 3] == player) {
                    frame.dispose();
                    return true;
                }
            }
        }
        for (int j = 0; j < COLS; j++) {
            for (int i = 0; i <= ROWS - 4; i++) {
                if (board[i][j] == player && board[i + 1][j] == player && board[i + 2][j] == player && board[i + 3][j] == player) {
                    frame.dispose();
                    return true;

                }
            }
        }
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

    private void updateBoard() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                slots[i][j].setText(String.valueOf(board[i][j]));
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

    private void resetGame() {
        initializeBoard();
        updateBoard();
        player1Turn = true;
    }

    public void playGame() {
        resetGame();
        boolean gameEnd = false;

        while (!gameEnd) {
            char currentPlayer = (player1Turn) ? PLAYER1 : PLAYER2;
            if (player1Turn) {
                System.out.println("Player 1's turn (symbol: " + currentPlayer + ")");
            } else {
                System.out.println("Player 2's turn (symbol: " + currentPlayer + ")");
            }

            JTextField textField = new JTextField();
            Object[] message = {
                    "Player " + (currentPlayer == PLAYER1 ? "1" : "2") +" ("+currentPlayer +") turn, Enter column (0-" + (COLS - 1) + "):",
                    textField
            };
            int option = JOptionPane.showOptionDialog(null, message, "Enter Column", JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE, null, null, null);

            if (option == JOptionPane.OK_OPTION) {
                try {
                    int col = Integer.parseInt(textField.getText().trim());
                    if (dropPiece(col, currentPlayer)) {
                        updateBoard();
                    } else {
                        continue;
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(frame, "Invalid input. Please enter a valid column number.");
                    continue;
                }
            } else {
                // Handle cancellation or closing of the dialog (user clicked Cancel or closed the dialog)
                System.exit(0); // Exit the program
            }

            if (checkWin(currentPlayer)) {
                JOptionPane.showMessageDialog(frame, "Player " + (player1Turn ? "1" : "2") + " wins!");
                result = player1Turn ? 1 : 2;
                gameEnd = true;
            } else if (isBoardFull()) {
                JOptionPane.showMessageDialog(frame, "It's a draw!");
                result = 0; // Draw
                gameEnd = true;
            } else {
                player1Turn = !player1Turn;
            }
        }
    }


    public void playGameAgainstAI() {
        resetGame();
        boolean gameEnd = false;

        while (!gameEnd) {
            char currentPlayer = (player1Turn) ? PLAYER1 : PLAYER2;
            if (player1Turn) {
                System.out.println("Player's turn (symbol: " + currentPlayer + ")");
                String input = JOptionPane.showInputDialog("Enter column (0-" + (COLS - 1) + "):");
                try {
                    int col = Integer.parseInt(input);
                    if (dropPiece(col, currentPlayer)) {
                        updateBoard();
                    } else {
                        continue;
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(frame, "Invalid input. Please enter a valid column number.");
                    continue;
                }
            } else {
                int col = random.nextInt(COLS);
                while (!dropPiece(col, currentPlayer)) {
                    col = random.nextInt(COLS);
                }
                updateBoard();
                System.out.println("AI chose column: " + col);
            }

            if (checkWin(currentPlayer)) {
                JOptionPane.showMessageDialog(frame, (player1Turn ? "Player" : "AI") + " wins!");
                result = player1Turn ? 1 : 2;
                gameEnd = true;
            } else if (isBoardFull()) {
                JOptionPane.showMessageDialog(frame, "It's a draw!");
                result = 0; // Draw
                gameEnd = true;
            } else {
                player1Turn = !player1Turn;
            }
        }
    }
    }
