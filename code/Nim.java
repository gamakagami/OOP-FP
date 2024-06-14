import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Nim extends Game {

    private static class NimGUI extends JFrame {
        private JFrame frame = new JFrame("Nim Game");
        private JTextField heapIndexField;
        private JTextField objectsToRemoveField;
        private JButton submitButton;
        private JLabel heapsInfoLabel;
        private int heapIndex = -1;
        private int objectsToRemove = -1;

        public NimGUI() {
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(400, 300);
            setLocationRelativeTo(null); // Center the window

            JOptionPane.showMessageDialog(frame, "You got Nim!");
            JPanel inputPanel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5); // Add padding

            heapsInfoLabel = new JLabel("Heaps Information:");
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 2;
            inputPanel.add(heapsInfoLabel, gbc);
            gbc.gridwidth = 1; // Reset grid width

            heapIndexField = new JTextField(5);
            gbc.gridx = 0;
            gbc.gridy = 1;
            inputPanel.add(new JLabel("Heap Index (1-3):"), gbc);
            gbc.gridx = 1;
            inputPanel.add(heapIndexField, gbc);

            objectsToRemoveField = new JTextField(5);
            gbc.gridx = 0;
            gbc.gridy = 2;
            inputPanel.add(new JLabel("Objects to Remove (1-3):"), gbc);
            gbc.gridx = 1;
            inputPanel.add(objectsToRemoveField, gbc);

            submitButton = new JButton("Submit");
            submitButton.addActionListener(e -> handlePlayerInput());
            gbc.gridx = 0;
            gbc.gridy = 3;
            gbc.gridwidth = 2;
            gbc.anchor = GridBagConstraints.CENTER;
            inputPanel.add(submitButton, gbc);

            add(inputPanel, BorderLayout.CENTER);

            setVisible(true);
        }

        public void updateUI(ArrayList<Integer> heaps, boolean player1Turn, boolean vsAI) {
            StringBuilder message = new StringBuilder("<html>Current State:<br>");
            for (int i = 0; i < heaps.size(); i++) {
                message.append("Heap ").append(i + 1).append(": ").append(heaps.get(i)).append(" objects<br>");
            }
            message.append("<br>");
            if (vsAI) {
                message.append(player1Turn ? "Player 1's turn." : "AI's turn.");
            } else {
                message.append(player1Turn ? "Player 1's turn." : "Player 2's turn.");
            }
            message.append("</html>");
            heapsInfoLabel.setText(message.toString());
        }

        public void displayMessage(String message) {
            JOptionPane.showMessageDialog(this, message);
        }

        public boolean isInputReady() {
            return heapIndex >= 0 && objectsToRemove >= 0;
        }

        public int getHeapIndex() {
            return heapIndex;
        }

        public int getObjectsToRemove() {
            return objectsToRemove;
        }

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

        public void resetInput() {
            heapIndex = -1;
            objectsToRemove = -1;
        }
    }

    private NimGUI gui;
    private int result; // 1 for Player 1 win, 2 for Player 2 (or AI) win

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
                        gui.dispose();
                    } else {
                        gui.displayMessage("Player 1 wins!"); // Player 1 wins against Player 2
                        result = 1;
                        gui.dispose();
                    }
                } else {
                    if (vsAI) {
                        gui.displayMessage("AI wins!"); // AI wins
                        result = 2;
                        gui.dispose();
                    } else {
                        gui.displayMessage("Player 2 wins!"); // Player 2 wins
                        result = 2;
                        gui.dispose();
                    }
                }
                gameOver = true; // Set gameOver to true to exit the loop
                break; // Exit the loop when game is over
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
                int heapIndex = gui.getHeapIndex();
                int objectsToRemove = gui.getObjectsToRemove();

                if (heapIndex < 0 || heapIndex >= heaps.size()) {
                    gui.displayMessage("Invalid heap index. Please enter a number between 1 and " + heaps.size() + ".");
                    gui.resetInput();
                    continue;
                }
                if (heaps.get(heapIndex) == 0) {
                    gui.displayMessage("Heap " + (heapIndex + 1) + " is empty. Please choose another heap.");
                    gui.resetInput();
                    continue;
                }
                if (objectsToRemove < 1 || objectsToRemove > Math.min(heaps.get(heapIndex), 3)) {
                    gui.displayMessage("Invalid number of objects. Please enter a number between 1 and " + Math.min(heaps.get(heapIndex), 3) + ".");
                    gui.resetInput();
                    continue;
                }
                heaps.set(heapIndex, heaps.get(heapIndex) - objectsToRemove);
                gui.resetInput();
            } else {
                // AI's turn
                int heapIndex, objectsToRemove;
                do {
                    heapIndex = random.nextInt(heaps.size());
                } while (heaps.get(heapIndex) == 0); // Ensure selected heap is not empty
                objectsToRemove = random.nextInt(Math.min(3, heaps.get(heapIndex))) + 1;
                heaps.set(heapIndex, heaps.get(heapIndex) - objectsToRemove);
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

    public int getResult() {
        return result;
    }

    public static void main(String[] args) {
        Nim nim = new Nim();
        nim.startGame(1);
        System.out.println("Game result: " + nim.getResult());
    }
}
