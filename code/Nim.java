import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Nim extends Game {

    // GUI class for the game
    private static class NimGUI implements In{

        // GUI components
        private JFrame frame;
        private JTextField heapIndexField;
        private JTextField objectsToRemoveField;
        private JButton submitButton;
        private JLabel heapsInfoLabel;

        // Variable to store the heap index and number of objects to remove input
        private int heapIndex = -1;
        private int objectsToRemove = -1;

        // Constructor to set up the GUI
        public NimGUI() {

            // Create main frame
            frame = new JFrame("Nim Game");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 300);
            frame.setLocationRelativeTo(null); // Center the window

            // Display welcome message
            JOptionPane.showMessageDialog(frame, "You got Nim!");
            JPanel inputPanel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5); // Add padding

            // Label for heap information
            heapsInfoLabel = new JLabel("Heaps Information:");
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 2;
            inputPanel.add(heapsInfoLabel, gbc);
            gbc.gridwidth = 1; // Reset grid width

            // Input field for heap index
            heapIndexField = new JTextField(5);
            gbc.gridx = 0;
            gbc.gridy = 1;
            inputPanel.add(new JLabel("Heap Index (1-3):"), gbc);
            gbc.gridx = 1;
            inputPanel.add(heapIndexField, gbc);

            // Input field for number of objects to remove
            objectsToRemoveField = new JTextField(5);
            gbc.gridx = 0;
            gbc.gridy = 2;
            inputPanel.add(new JLabel("Objects to Remove (1-3):"), gbc);
            gbc.gridx = 1;
            inputPanel.add(objectsToRemoveField, gbc);

            // Submit button for player input
            submitButton = new JButton("Enter");
            submitButton.addActionListener(e -> handlePlayerInput());
            gbc.gridx = 0;
            gbc.gridy = 3;
            gbc.gridwidth = 2;
            gbc.anchor = GridBagConstraints.CENTER;
            inputPanel.add(submitButton, gbc);

            frame.add(inputPanel, BorderLayout.CENTER);
            frame.setVisible(true);
        }

        // Method to update the GUI
        public void updateUI(ArrayList<Integer> heaps, boolean player1Turn, boolean vsAI) {

            // Displays the current state
            StringBuilder message = new StringBuilder("<html>Current State:<br>");
            for (int i = 0; i < heaps.size(); i++) {
                message.append("Heap ").append(i + 1).append(": ").append(heaps.get(i)).append(" objects<br>");
            }
            message.append("<br>");

            // Displays whose turn it is
            if (vsAI) {
                message.append(player1Turn ? "Player 1's turn." : "AI's turn.");
            } else {
                message.append(player1Turn ? "Player 1's turn." : "Player 2's turn.");
            }
            message.append("</html>");
            heapsInfoLabel.setText(message.toString());
        }

        // Method to display message
        public void displayMessage(String message) {
            JOptionPane.showMessageDialog(frame, message);
        }

        // Method to check if player input is ready
        public boolean isInputReady() {
            return heapIndex >= 0 && objectsToRemove >= 0;
        }

        // Getter for player input
        public int getHeapIndex() {
            return heapIndex;
        }

        // Getter for objects to remove
        public int getObjectsToRemove() {
            return objectsToRemove;
        }

        // Error handling method to handle player input from text fields
        private void handlePlayerInput() {
            try {
                heapIndex = Integer.parseInt(heapIndexField.getText()) - 1;
                objectsToRemove = Integer.parseInt(objectsToRemoveField.getText());
                heapIndexField.setText("");
                objectsToRemoveField.setText("");
            } catch (NumberFormatException e) {
                displayMessage("Invalid input. Please enter numbers only.");
            }
        }

        // Reset player input
        public void resetInput() {
            heapIndex = -1;
            objectsToRemove = -1;
        }

        // Close frame
        public void dispose() {
            frame.dispose();
        }

        @Override
        public void initializeUI() {

        }
    }

    private NimGUI gui;

    // Method to initialize UI
    public void initializeUI() {
        gui = new NimGUI();
    }

    // Method to start the game
    public void startGame(int choice) {
        initializeUI();

        // Set up initial state for each heap
        ArrayList<Integer> heaps = new ArrayList<>();
        Random random = new Random();

        // Each heap with random numbers
        heaps.add(random.nextInt(2) + 3);
        heaps.add(random.nextInt(2) + 4);
        heaps.add(random.nextInt(2) + 5);

        // Main game loop
        boolean player1Turn = true; // Variable to track whose turn it is
        boolean vsAI = choice == 2; // Determine if the game is against AI based on the user's choice
        boolean gameOver = false; // Flag to track if the game is over

        while (!gameOver) {
            // Update GUI with current state of heaps
            gui.updateUI(heaps, player1Turn, vsAI);

            // Check if the game is over
            if (isGameOver(heaps)) {
                if (player1Turn) { // If it's player 1's turn when game ends
                    if (vsAI) {
                        gui.displayMessage("You win!"); // Player 1 wins against AI
                        result = 1;
                    } else {
                        gui.displayMessage("Player 1 wins!"); // Player 1 wins against Player 2
                        result = 1;
                    }
                } else { // If it's not player 1's turn when game ends
                    if (vsAI) {
                        gui.displayMessage("AI wins!"); // AI wins
                        result = 2;
                    } else {
                        gui.displayMessage("Player 2 wins!"); // Player 2 wins
                        result = 2;
                    }
                }

                // Exit game (Close frame and exit loop)
                gui.dispose();
                gameOver = true;
                break;
            }

            // Player's turn
            if (player1Turn || !vsAI) {
                while (!gui.isInputReady()) {
                    // Wait for player input
                    try {
                        Thread.sleep(100); // Sleep briefly to reduce CPU usage
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                // Get player's input for heap index and objects to remove
                int heapIndex = gui.getHeapIndex();
                int objectsToRemove = gui.getObjectsToRemove();

                // Validate player input
                if (heapIndex < 0 || heapIndex >= heaps.size()) { // Invalid heap input
                    gui.displayMessage("Invalid heap index. Please enter a number between 1 and " + heaps.size() + ".");
                    gui.resetInput();
                    continue;
                }

                // Invalid number of objects
                if (heaps.get(heapIndex) == 0) { // Heap is empty
                    gui.displayMessage("Heap " + (heapIndex + 1) + " is empty. Please choose another heap.");
                    gui.resetInput();
                    continue;
                }
                if (objectsToRemove < 1 || objectsToRemove > Math.min(heaps.get(heapIndex), 3)) { // Input out of available range
                    gui.displayMessage("Invalid number of objects. Please enter a number between 1 and " + Math.min(heaps.get(heapIndex), 3) + ".");
                    gui.resetInput();
                    continue;
                }

                // Update heap with player input
                heaps.set(heapIndex, heaps.get(heapIndex) - objectsToRemove);
                gui.resetInput();
            } else {
                // AI's turn
                int heapIndex, objectsToRemove;
                do {
                    heapIndex = random.nextInt(heaps.size()); // Random heap to remove from
                } while (heaps.get(heapIndex) == 0); // Ensure selected heap is not empty

                // Random objects from 1-3 to remove and update heap
                objectsToRemove = random.nextInt(Math.min(3, heaps.get(heapIndex))) + 1;
                heaps.set(heapIndex, heaps.get(heapIndex) - objectsToRemove);

                // Update GUI and display AI removes how many objects from which heap
                gui.updateUI(heaps, player1Turn, vsAI);
                gui.displayMessage("AI removes " + objectsToRemove + " objects from heap " + (heapIndex + 1));
            }

            player1Turn = !player1Turn; // Switch turns
        }
    }

    // Check if the game is over (all heaps are empty)
    public static boolean isGameOver(ArrayList<Integer> heaps) {
        for (int heap : heaps) {
            if (heap > 0) {
                return false; // Game is not over if any heap still has objects
            }
        }
        return true; // Game is over if all heaps are empty
    }
}
