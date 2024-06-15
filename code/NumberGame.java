import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class NumberGame extends Game {

    // GUI components
    private JFrame frame;
    private JPanel panel;
    private JLabel label1;
    private JButton roll;
    private JLabel topLeftLabel;
    private JLabel topRightLabel;

    // Mode flag and player's value
    private boolean isPvP;
    private int player1Value;
    private int player2Value;
    public int pgAI;

    // Constructor to initialize GUI
    public NumberGame() {
        initializeUI();
    }

    // Method to set up the GUI
    private void initializeUI() {

        // Create main frame
        frame = new JFrame("Number Game");
        panel = new JPanel(new BorderLayout());

        // Display welcome message
        JOptionPane.showMessageDialog(frame, "You got Number Game!");

        // Create and set up the top panel with player labels
        JPanel topPanel = new JPanel(new BorderLayout());
        topLeftLabel = new JLabel("Player 1: 0");
        topRightLabel = new JLabel("Player 2: 0", SwingConstants.RIGHT);
        topPanel.add(topLeftLabel, BorderLayout.WEST);
        topPanel.add(topRightLabel, BorderLayout.EAST);

        // Content label and button
        label1 = new JLabel("Welcome to the Number game!", SwingConstants.CENTER);
        roll = new JButton("Roll");

        // Add action listener to the roll button
        roll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame(); // Start the game logic when roll button is clicked
            }
        });

        // Set up main panel layout and components
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

    // Method to set game mode
    public void setGameMode(int mode) {
        if (mode == 1) {
            // Player vs Player
            isPvP = true;
            topRightLabel.setText("Player 2: 0");
        } else {
            // Player vs AI
            isPvP = false;
            topRightLabel.setText("AI: 0"); // Update the label to "AI" for AI mode
        }
    }

    // Method to start game
    public void startGame() {
        Random random = new Random();

        // Decide player vs player or player vs AI
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

        // Loop counter for player turn and variable to store the random number added to the player's number
        int loop = 0;
        int pg4 = 0;

        // Player 1's turn to get an initial number
        player1Value = random.nextInt(21); // Generate a random number between 0 and 20

        // Update value and display what number did player 1 get
        updatePlayerValues();
        JOptionPane.showMessageDialog(frame, "Player 1 got "+player1Value+ "!");

        // Player 1's turn to make guesses and add to their number
        while (loop < 2) {
            // Ask player 1 to add guess or not and display remaining chance to add
            int response = JOptionPane.showConfirmDialog(frame, "Player 1: Add a guess?\nRemaining chance to add: "+(2-loop), "Player 1 Turn", JOptionPane.YES_NO_OPTION);

            if (response == JOptionPane.YES_OPTION) { // Display and add number guess to player 1 value
                pg4 = getPlayerGuess();
                player1Value += pg4;
                updatePlayerValues();
                JOptionPane.showMessageDialog(frame, "Player 1 got "+pg4+ "!\nTotal: "+player1Value);

            } else if (response == JOptionPane.NO_OPTION) { // Exit if player 1 don't want to add guesses
                break;
            } else { // Error handling
                JOptionPane.showMessageDialog(frame, "Invalid input, try again");
            }
            loop++;
        }

        // Reset loop counter and start player 2's turn
        loop = 0;

        // Player 2's turn to get an initial number
        player2Value = random.nextInt(21); // Generate a random number between 0 and 20

        // Update value and display what number did player 1 get
        updatePlayerValues();
        JOptionPane.showMessageDialog(frame, "Player 2 got "+player2Value+ "!");

        // Player 2's turn to make guesses and add to their number
        while (loop < 2) {
            // Ask player 2 to add guess or not and display remaining chance to add
            int response = JOptionPane.showConfirmDialog(frame, "Player 2: Add a guess?\nRemaining chance to add: "+(2-loop), "Player 2 Turn", JOptionPane.YES_NO_OPTION);

            if (response == JOptionPane.YES_OPTION) { // Display and add number guess to player 2 value
                pg4 = getPlayerGuess();
                player2Value += pg4;
                updatePlayerValues();
                JOptionPane.showMessageDialog(frame, "Player 2 got "+pg4+ "!\nTotal: "+player2Value);

            } else if (response == JOptionPane.NO_OPTION) { // Exit if player 2 don't want to add guesses
                break;
            } else { // Error handling
                JOptionPane.showMessageDialog(frame, "Invalid input, try again");
            }
            loop++;
        }

        // Determine the winner or a draw
        if ((player1Value <= 21) && (player2Value <= 21)) { // Both player's value is within a valid range
            if (player1Value > player2Value) {
                topLeftLabel.setText("Player 1 wins with " + player1Value);
                topRightLabel.setText("Player 2 loses with " + player2Value);

            } else if (player2Value > player1Value) {
                topLeftLabel.setText("Player 1 loses with " + player1Value);
                topRightLabel.setText("Player 2 wins with " + player2Value);

            } else {
                topLeftLabel.setText("Draw!");
                topRightLabel.setText("Draw!");
                frame.dispose();
                result = 0;
                resetGame();
            }

        } else if (player1Value > 21 && player2Value <= 21) { // Player 1 exceed range and player 2 within a valid range
            topLeftLabel.setText("Player 1 loses with " + player1Value);
            topRightLabel.setText("Player 2 wins with " + player2Value);

        } else if (player1Value <= 21 && player2Value > 21) { // Player 2 exceed range and player 1 within a valid range
            topLeftLabel.setText("Player 1 wins with " + player1Value);
            topRightLabel.setText("Player 2 loses with " + player2Value);

        } else { // Both player exceed valid range
            topLeftLabel.setText("Both players lose (Draw)");
            topRightLabel.setText("Both players lose (Draw)");
            result = 0;
            frame.dispose();
            resetGame();
        }
    }

    // Method to play a game against AI
    public void playVsAI(Random random) {

        // Loop counter for player turn and variable to store the random number added to the player's number
        int loop = 0;
        int pg4 = 0;

        // Player 1's turn to get an initial number
        player1Value = random.nextInt(21); // Generate a random number between 0 and 20

        // Update value and display what number did player 1 get
        updatePlayerValues();
        JOptionPane.showMessageDialog(frame, "Player 1 got "+player1Value+ "!");

        // Player 1's turn to make guesses and add to their number
        while (loop < 2) {

            // Ask player 1 to add guess or not and display remaining chance to add
            int response = JOptionPane.showConfirmDialog(frame, "Player 1: Add a guess?\nRemaining chance to add: "+(2-loop), "Player 1 Turn", JOptionPane.YES_NO_OPTION);

            if (response == JOptionPane.YES_OPTION) { // Display and add number guess to player 1 value
                pg4 = getPlayerGuess();
                player1Value += pg4;
                updatePlayerValues();
                JOptionPane.showMessageDialog(frame, "Player 1 got "+pg4+ "!\nTotal: "+player1Value);

            } else if (response == JOptionPane.NO_OPTION) { // Exit if player 1 don't want to add guesses
                break;
            } else { // Error handling
                JOptionPane.showMessageDialog(frame, "Invalid input, try again");
            }
            loop++;
        }

        // Reset loop counter and start AI's turn
        loop = 0;

        pgAI = random.nextInt(21); // Generate a random number between 0 and 20

        // Update value and display what number did AI get
        topRightLabel.setText("AI: " + pgAI);
        JOptionPane.showMessageDialog(frame, "AI got "+pgAI+ "!");

        // AI's turn to make guesses and add to their number
        while (loop < 2 && pgAI <= 21) { // While AI still have chance to add number and number within valid range
            int pg3 = random.nextInt(3); // Randomly chooses addition range
            switch (pg3) {
                case 0:
                    pg4 = random.nextInt(4) + 1;
                    pgAI += pg4;
                    break;
                case 1:
                    pg4 = random.nextInt(3) + 5;
                    pgAI += pg4;
                    break;
                case 2:
                    pg4 = random.nextInt(3) + 8;
                    pgAI += pg4;
                    break;
            }

            topRightLabel.setText("AI: " + pgAI);
            // Handling for AI number to not exceed valid range
            if ((pgAI)<21) { // If total AI number is within valid range
                JOptionPane.showMessageDialog(frame, "AI got " + pg4 + "!\nTotal: " + pgAI);
            }else { // If not within valid range, AI's number - guess number and display AI got 0 as the guess number
                pgAI -= pg4;
                JOptionPane.showMessageDialog(frame, "AI got 0" + "!\nTotal: " + pgAI);
            }
            loop++;
        }

        // Determine the winner or a draw
        if ((player1Value <= 21) && (pgAI <= 21)) { // Both player's value is within a valid range
            if (player1Value > pgAI) {
                topLeftLabel.setText("Player 1 wins with " + player1Value);
                topRightLabel.setText("AI loses with " + pgAI);

            } else if (pgAI > player1Value) {
                topLeftLabel.setText("Player 1 loses with " + player1Value);
                topRightLabel.setText("AI wins with " + pgAI);
            }
            else{ // Both player and AI's value are equal, reset game
                frame.dispose();
                result = 0;
                resetGame();
            }
        } else if (player1Value > 21 && pgAI <= 21) { // Player's value exceed valid range
            topLeftLabel.setText("Player 1 loses with " + player1Value);
            topRightLabel.setText("AI wins with " + pgAI);
        }
    }

    // Method to get player guess from input using GUI
    private int getPlayerGuess() {

        // Options for addition range
        Object[] options = {"Add 1 to 4", "Add 5 to 7", "Add 8 to 10"};
        int choice = JOptionPane.showOptionDialog(frame, "Choose how to add the number:", "Choose Addition Range",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        Random random = new Random();

        // Variable to store the random number that is added to the players value
        int pg4 = 0;
        switch (choice) { // Random to get number within addition specified range
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
        String resultMessage;

        // Get result and close frame if any player wins
        if (topLeftLabel.getText().contains("wins")) {

            // Player 1 wins
            if (isPvP) { // Player 1 wins in Player vs Player mode
                result = 1;
                resultMessage = ("Player 1 wins with " + player1Value + " over Player 2 with "+player2Value);
                frame.dispose();
            } else{ // Player 1 wins in Player vs AI mode
                result = 1;
                resultMessage = ("Player 1 wins with " + player1Value + " over AI with "+pgAI);
                frame.dispose();
            }

            // Player 1 loses
        } else if (topRightLabel.getText().contains("wins")) {
            if (isPvP) { // PLayer 1 loses in Player vs Player mode
                result = 2;
                resultMessage = ("Player 2 wins with " + player2Value + " over Player 1 with "+player1Value);
                frame.dispose();
            }
            else { // Player 1 loses in Player vs AI mode
                result = 2;
                resultMessage = ("AI wins with " + pgAI + " over Player 1 with "+player1Value);
                frame.dispose();
            }
        } else { // Draw and reset game
            result = 0;
            resultMessage = "It's a draw!";
            resetGame();
        }

        JOptionPane.showMessageDialog(frame, resultMessage, "Game Result", JOptionPane.INFORMATION_MESSAGE);
    }

    // Method to reset game and player's value
    private void resetGame() {
        roll.setEnabled(true);
        player1Value = 0;
        player2Value = 0;
        topLeftLabel.setText("Player 1: 0");
        if (isPvP) {
            topRightLabel.setText("Player 2: 0");
        } else {
            topRightLabel.setText("AI: 0");
        }
    }
}
