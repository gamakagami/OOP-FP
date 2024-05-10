import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Random random = new Random();
        Scanner scanner = new Scanner(System.in);
        int play = 0;
        int plays = 0;
        int result = 0;
        int choice = 0; // Variable to store the user's choice

        while (play == 0) { // Loop until the user exits the game
            if (plays == 0) {
                play = 1;
                int p1 = 0;
                int ai = 0;
                if (choice == 0) { // If it's the first iteration, prompt for the choice
                    System.out.println("Choose the game mode:");
                    System.out.println("1. Player vs Player");
                    System.out.println("2. Player vs AI");
                    System.out.print("Enter your choice (1 or 2): ");
                    choice = scanner.nextInt();
                    while (choice != 1 && choice != 2) {
                        System.out.println("Invalid choice. Please enter 1 or 2.");
                        choice = scanner.nextInt();
                    }
                }
                int dice = random.nextInt(3);
                System.out.println(dice);
                plays +=1;
                switch (dice) {
                    case 0:
                        System.out.println("You got Nim!");
                        Nim nim = new Nim();
                        nim.startGame(choice);
                        result = nim.getResult();
                        break;
                    case 1:
                        System.out.println("You got Connect 4!");

                        Connect4 game = new Connect4();
                        if (choice == 1) {
                            game.playGame();
                            result = game.getResult();
                        } else {
                            // Player vs AI mode
                            game.playGameAgainstAI();
                            result = game.getResult();
                        }
                        break;
                    case 2:
                        System.out.println("You got Number Game!");
                        NumberGame numberGame = new NumberGame();
                        if (choice == 1) {
                            numberGame.playTwoPlayers(new Random(), new Scanner(System.in));
                            result = numberGame.getResult();
                        } else {
                            numberGame.playVsAI(new Random(), new Scanner(System.in));
                            result = numberGame.getResult();
                        }
                        // Implement game 3 logic
                        break;
                }
                switch (choice){
                    case 1:
                        if (result == 2) {
                            // p2 turn
                            System.out.println("AI");
                        } else if (result == 1) {
                            // Player1 turn
                            System.out.println("AsI");
                        }
                        break;
                    case 2:
                        if (result == 2) {
                            // AI turn
                            System.out.println("AI");
                        } else if (result == 1) {
                            // Player1 turn
                            System.out.println("AsI");
                        }


                }

            }


            // TTT
        }
        scanner.close();

    }
}
