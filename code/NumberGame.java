import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class NumberGame extends Game {

    private JFrame frame;
    private JPanel panel;
    private JLabel label1;
    private JButton roll;
    private JLabel topLeftLabel;
    private JLabel topRightLabel;
    private boolean isPvP;
    private int player1Value;
    private int player2Value;

    public NumberGame() {
        initializeUI();
    }

    private void initializeUI() {
        frame = new JFrame("Number Game");
        panel = new JPanel(new BorderLayout());

        JOptionPane.showMessageDialog(frame, "You got Number Game!");
        // Panel for the top section
        JPanel topPanel = new JPanel(new BorderLayout());

        // Labels for top-left and top-right corners
        topLeftLabel = new JLabel("Player 1: 0");
        topRightLabel = new JLabel("Player 2: 0", SwingConstants.RIGHT);

        // Add labels to the top panel
        topPanel.add(topLeftLabel, BorderLayout.WEST);
        topPanel.add(topRightLabel, BorderLayout.EAST);

        // Main content label and button
        label1 = new JLabel("Welcome to the Number game!", SwingConstants.CENTER);
        roll = new JButton("Roll");

        // Add action listener to the roll button
        roll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame(); // Start the game logic when roll button is clicked
            }
        });

        // Main panel settings
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setLayout(new BorderLayout());
        panel.add(topPanel, BorderLayout.PAGE_START); // Add top panel to the main panel
        panel.add(label1, BorderLayout.CENTER);
        panel.add(roll, BorderLayout.SOUTH);

        // Add the main panel to the frame
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(800, 600));
        frame.pack();
        frame.setVisible(true);
    }

    public void setGameMode(int mode) {
        if (mode == 1) {
            isPvP = true;
            topRightLabel.setText("Player 2: 0");
        } else {
            isPvP = false;
            topRightLabel.setText("AI: 0"); // Update the label to "AI" for AI mode
        }
    }

    public void startGame() {
        Random random = new Random();

        if (isPvP) {
            playTwoPlayers(random);
        } else {
            playVsAI(random);
        }

        // Update the UI after game is finished
        updateUIAfterGame();
    }

    // Method to play a game between two players
    public void playTwoPlayers(Random random) {
        int loop = 0;
        int pg4 = 0; // Variable to store the random number added to the player's number

        // Player 1's turn to get an initial number
        player1Value = random.nextInt(21); // Generate a random number between 0 and 20
        updatePlayerValues(); // Update UI instead of console print
        JOptionPane.showMessageDialog(frame, "Player 1 got "+player1Value+ "!");

        // Player 1's turn to make guesses and add to their number
        while (loop < 2) {
            int response = JOptionPane.showConfirmDialog(frame, "Player 1: Add a guess?\nRemaining chance to add: "+(2-loop), "Player 1 Turn", JOptionPane.YES_NO_OPTION);

            if (response == JOptionPane.YES_OPTION) {
                pg4 = getPlayerGuess();
                player1Value += pg4;

                updatePlayerValues();
                JOptionPane.showMessageDialog(frame, "Player 1 got "+pg4+ "!\nTotal: "+player1Value);
            } else if (response == JOptionPane.NO_OPTION) {
                break;
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid input, try again");
            }
            loop++;
        }

        loop = 0;
        player2Value = random.nextInt(21);
        updatePlayerValues();

        JOptionPane.showMessageDialog(frame, "Player 2 got "+player2Value+ "!");
        // Player 2's turn to make guesses and add to their number

        while (loop < 2) {
            int response = JOptionPane.showConfirmDialog(frame, "Player 2: Add a guess?\nRemaining chance to add: "+(2-loop), "Player 2 Turn", JOptionPane.YES_NO_OPTION);

            if (response == JOptionPane.YES_OPTION) {
                pg4 = getPlayerGuess();
                player2Value += pg4;
                updatePlayerValues();
                JOptionPane.showMessageDialog(frame, "Player 2 got "+pg4+ "!\nTotal: "+player2Value);
            } else if (response == JOptionPane.NO_OPTION) {
                break;
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid input, try again");
            }
            loop++;
        }

        // Determine the winner or a draw
        if ((player1Value <= 21) && (player2Value <= 21)) {
            if (player1Value > player2Value) {
                topLeftLabel.setText("Player 1 wins with " + player1Value);
                topRightLabel.setText("Player 2 loses with " + player2Value);
            } else if (player2Value > player1Value) {
                topLeftLabel.setText("Player 1 loses with " + player1Value);
                topRightLabel.setText("Player 2 wins with " + player2Value);
            } else {
                topLeftLabel.setText("Draw!");
                topRightLabel.setText("Draw!");
            }
        } else if (player1Value > 21 && player2Value <= 21) {
            topLeftLabel.setText("Player 1 loses with " + player1Value);
            topRightLabel.setText("Player 2 wins with " + player2Value);
        } else if (player1Value <= 21 && player2Value > 21) {
            topLeftLabel.setText("Player 1 wins with " + player1Value);
            topRightLabel.setText("Player 2 loses with " + player2Value);
        } else {
            topLeftLabel.setText("Both players lose (Draw)");
            topRightLabel.setText("Both players lose (Draw)");
        }
    }

    // Method to play a game against AI
    public void playVsAI(Random random) {
        int loop = 0;
        int pg4 = 0;

        player1Value = random.nextInt(21);
        updatePlayerValues();
        JOptionPane.showMessageDialog(frame, "You got "+player1Value+ "!");
        while (loop < 2) {
            int response = JOptionPane.showConfirmDialog(frame, "Player 1: Add a guess?\nRemaining chance to add: "+(2-loop), "Player 1 Turn", JOptionPane.YES_NO_OPTION);

            if (response == JOptionPane.YES_OPTION) {
                pg4 = getPlayerGuess();
                player1Value += pg4;
                updatePlayerValues();
                JOptionPane.showMessageDialog(frame, "Player 1 got "+pg4+ "!\nTotal: "+player1Value);
            } else if (response == JOptionPane.NO_OPTION) {
                break;
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid input, try again");
            }
            loop++;
        }

        loop = 0;
        int pgAI = random.nextInt(21);
        topRightLabel.setText("AI: " + pgAI);
        JOptionPane.showMessageDialog(frame, "AI got "+pgAI+ "!");


        while (loop < 2 && pgAI < 21) {
            int pg3 = random.nextInt(3) + 1;
            switch (pg3) {
                case 1:
                    pg4 = random.nextInt(4) + 1;
                    pgAI += pg4;
                    break;
                case 2:
                    pg4 = random.nextInt(3) + 5;
                    pgAI += pg4;
                    break;
                case 3:
                    pg4 = random.nextInt(3) + 8;
                    pgAI += pg4;
                    break;
            }
            topRightLabel.setText("AI: " + pgAI);
            if ((pgAI)<21) {
                JOptionPane.showMessageDialog(frame, "AI got " + pg4 + "!\nTotal: " + pgAI);
            }else {
                pgAI -= pg4;
                JOptionPane.showMessageDialog(frame, "AI got 0" + "!\nTotal: " + pgAI);
            }
            loop++;
        }

        if ((player1Value <= 21) && (pgAI <= 21)) {
            if (player1Value > pgAI) {
                topLeftLabel.setText("Player 1 wins with " + player1Value);
                topRightLabel.setText("AI loses with " + pgAI);
            } else if (pgAI > player1Value) {
                topLeftLabel.setText("Player 1 loses with " + player1Value);
                topRightLabel.setText("AI wins with " + pgAI);
            } else {
                topLeftLabel.setText("Draw!");
                topRightLabel.setText("Draw!");
            }
        } else if (player1Value > 21 && pgAI <= 21) {
            topLeftLabel.setText("Player 1 loses with " + player1Value);
            topRightLabel.setText("AI wins with " + pgAI);
        } else if (player1Value <= 21 && pgAI > 21) {
            topLeftLabel.setText("Player 1 wins with " + player1Value);
            topRightLabel.setText("AI loses with " + pgAI);
        } else {
            topLeftLabel.setText("Both players lose (Draw)");
            topRightLabel.setText("Both players lose (Draw)");
        }
    }

    // Helper method to get player's guess from input using GUI
    private int getPlayerGuess() {
        Object[] options = {"Add 1 to 4", "Add 5 to 7", "Add 8 to 10"};
        int choice = JOptionPane.showOptionDialog(frame, "Choose how to add the number:", "Choose Addition Range",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        Random random = new Random();
        int pg4 = 0;
        switch (choice) {
            case 0:
                pg4 = random.nextInt(4) + 1;
                break;
            case 1:
                pg4 = random.nextInt(3) + 5;
                break;
            case 2:
                pg4 = random.nextInt(3) + 8;
                break;
        }

        return pg4;
    }

    // Update the labels with the current values of the players
    private void updatePlayerValues() {
        topLeftLabel.setText("Player 1: " + player1Value);
        if (isPvP) {
            topRightLabel.setText("Player 2: " + player2Value);
        } else {
            topRightLabel.setText("AI: " + player2Value);
        }
    }

    // Update UI after game ends
    private void updateUIAfterGame() {
        // Disable the roll button after game ends
        roll.setEnabled(false);

        // Show a dialog or update label for game result
        String resultMessage = "";
        if (topLeftLabel.getText().contains("wins")) {
            resultMessage = topLeftLabel.getText();
        } else if (topRightLabel.getText().contains("wins")) {
            resultMessage = topRightLabel.getText();
        } else {
            resultMessage = "It's a draw!";
        }

        JOptionPane.showMessageDialog(frame, resultMessage, "Game Result", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                NumberGame game = new NumberGame();
                game.setGameMode(1); // Assuming PvP mode for example
            }
        });
    }
}
