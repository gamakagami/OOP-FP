import java.util.Random;
import java.util.Scanner;


public class NumberGame extends Game{

    // Method to play a game between two players
    public static void playTwoPlayers(Random random, Scanner scanner) {
        int loop = 0;
        int pg1; // Player 1's initial number
        int pg12; // Player 2's initial number
        int pg4 = 0; // Variable to store the random number added to the player's number

        // Player 1's turn to get an initial number
        pg1 = random.nextInt(21); // Generate a random number between 0 and 20
        System.out.println("Player 1, you got " + pg1);

        // Player 1's turn to make guesses and add to their number
        while (loop < 2) {
            System.out.println("Add a guess? (Y/N)");
            // Read input and convert to uppercase
            String pg2 = scanner.next();
            pg2 = pg2.toUpperCase();

            if (pg2.equals("Y")) { // If player chooses to add a guess
                System.out.println("Choose how to add the number:");
                System.out.println("1: Add 1 to 4");
                System.out.println("2: Add 5 to 7");
                System.out.println("3: Add 8 to 10");
                loop += 1;  // Increment the loop counter

                int pg3 = scanner.nextInt(); // Read player's choice

                //Generate random number based on player's choice
                switch (pg3) {
                    case 1:
                        pg4 = random.nextInt(4) + 1;
                        break;
                    case 2:
                        pg4 = random.nextInt(3) + 5;
                        break;
                    case 3:
                        pg4 = random.nextInt(3) + 8;
                        break;
                    default:
                        System.out.println("Invalid choice, try again");
                        loop--; // Decrement loop counter for invalid choice
                        continue;
                }
                pg1 += pg4;  // Add the generated number to Player 1's total
                System.out.println("Player 1, your number is now: " + pg1);
            } else if (pg2.equals("N")) {  // If player chooses not to add a guess
                System.out.println("Player 1, " + pg1 + " is your final number");
                loop += 100; // Exit the loop
            } else {
                System.out.println("Invalid input, try again"); // Invalid input case
            }
        }

        // Reset loop counter for Player 2's turn
        loop = 0;
        pg12 = random.nextInt(21); // Generate a random number between 0 and 20 for Player 2
        System.out.println("Player 2, you got " + pg12);

        // Player 2's turn to make guesses and add to their number
        while (loop < 2) {
            System.out.println("Add a guess? (Y/N)");
            // Read input and convert to uppercase
            String pg2 = scanner.next();
            pg2 = pg2.toUpperCase();
            if (pg2.equals("Y")) { // If player chooses to add a guess
                System.out.println("Choose how to add the number:");
                System.out.println("1: Add 1 to 4");
                System.out.println("2: Add 5 to 7");
                System.out.println("3: Add 8 to 10");
                loop += 1; // Increment the loop counter

                int pg3 = scanner.nextInt(); // Read player's choice

                //Generate random number based on player's choice
                switch (pg3) {
                    case 1:
                        pg4 = random.nextInt(4) + 1;
                        break;
                    case 2:
                        pg4 = random.nextInt(3) + 5;
                        break;
                    case 3:
                        pg4 = random.nextInt(3) + 8;
                        break;
                    default:
                        System.out.println("Invalid choice, try again");
                        loop--; // Decrement loop counter for invalid choice
                        continue;
                }
                pg12 += pg4; // Add the generated number to Player 2's total
                System.out.println("Player 2, your number is now: " + pg12);
            } else if (pg2.equals("N")) { // If player chooses not to add a guess
                System.out.println("Player 2, " + pg12 + " is your final number");
                loop += 100; // Exit the loop
            } else {
                System.out.println("Invalid input, try again"); // Invalid input case
            }
        }

        // Determine the winner or a draw
        if ((pg1<=21)&&(pg12<=21)){ // Both players are within the limit
            if (pg1>pg12){
                System.out.println("Player 1 wins with "+pg1+" over player 2 with "+pg12);
                result = 1; // Set result to Player 1 wins
            }
            else if (pg12>pg1){
                System.out.println("Player 2 wins with "+pg12+" over player 1 with "+pg1);
                result = 2; // Set result to Player 2 wins
            }
            else{
                System.out.println("Draw");
            }
        }else if (pg1>21 && pg12<=21){ // Player 1 exceeds limit, Player 2 wins
            System.out.println("Player 2 wins with "+pg12+" over player 1 with "+pg1);
            result = 2; // Set result to Player 2 wins
        }
        else if (pg1<=21 && pg12>21){ // Player 2 exceeds limit, Player 1 wins
            System.out.println("Player 1 wins with "+pg1+" over player 2 with "+pg12);
            result = 1; // Set result to Player 1 wins
        }
        else{ // Both players exceed limit, it's a draw
            System.out.println("Both player loses (Draw)");
        }
    }

    // Method to play a game against AI
    public static void playVsAI(Random random, Scanner scanner) {
        int loop = 0; // Loop counter for the number of guesses
        int pg1 = 0; // Player's initial number
        int pg4 = 0; // Variable to store the random number added to the player's number

        // Player's turn to get an initial number
        pg1 = random.nextInt(21); // Generate a random number between 0 and 20
        System.out.println("You got " + pg1);

        // Player's turn to make guesses and add to their number
        while (loop < 2) {
            System.out.println("Add a guess? (Y/N)");
            // Read input and convert to uppercase
            String pg2 = scanner.next();
            pg2 = pg2.toUpperCase();
            if (pg2.equals("Y")) { // If player chooses to add a guess
                System.out.println("Choose how to add the number:");
                System.out.println("1: Add 1 to 4");
                System.out.println("2: Add 5 to 7");
                System.out.println("3: Add 8 to 10");
                loop += 1; // Increment the loop counter
                int pg3 = scanner.nextInt(); // Read player's choice

                //Generate random number based on player's choice
                switch (pg3) {
                    case 1:
                        pg4 = random.nextInt(4) + 1;
                        break;
                    case 2:
                        pg4 = random.nextInt(3) + 5;
                        break;
                    case 3:
                        pg4 = random.nextInt(3) + 8;
                        break;
                    default:
                        System.out.println("Invalid choice, try again");
                        continue; // Invalid choice, repeat the loop
                }
                pg1 += pg4; // Add the generated number to Player's total
                System.out.println("Your number is now: " + pg1);
            } else if (pg2.equals("N")) { // If player chooses not to add a guess
                System.out.println(pg1 + " is your final number");
                loop += 100; // Exit the loop
            } else {
                System.out.println("Invalid input, try again"); // Invalid input case
            }
        }

        // AI's turn
        loop = 0; // Reset loop counter for AI
        int pgAI = random.nextInt(21); // Generate a random number between 0 and 20 for AI
        System.out.println("AI got " + pgAI);

        // AI's turn to make guesses and add to its number
        while (loop < 2 && pgAI < 21) { // Loop for AI's guesses
            int pg3 = random.nextInt(3) + 1; // AI randomly chooses how to add the number
            switch (pg3) {
                case 1:
                    pg4 = random.nextInt(4) + 1; // Generate a random number between 1 and 4
                    pgAI += pg4;
                    if (pgAI > 21) { // If AI exceeds limit, revert the addition and exit loop
                        pgAI -= pg4;
                        loop += 100;
                    }
                    break;
                case 2:
                    pg4 = random.nextInt(3) + 5; // Generate a random number between 5 and 7
                    pgAI += pg4;
                    if (pgAI > 21) { // If AI exceeds limit, revert the addition and exit loop
                        pgAI -= pg4;
                        loop += 100;
                    }
                    break;
                case 3:
                    pg4 = random.nextInt(3) + 8; // Generate a random number between 8 and 10
                    pgAI += pg4;
                    if (pgAI > 21) { // If AI exceeds limit, revert the addition and exit loop
                        pgAI -= pg4;
                        loop += 100;
                    }
                    break;
            }
            System.out.println("AI's number is now: " + pgAI);
            loop+=1; // Increment the loop counter
        }

        // Determine the winner or a draw
        if ((pg1<=21)&&(pgAI<=21)){ // Both player and AI are within the limit
            if (pg1>pgAI){
                System.out.println("Player wins with "+pg1+" over AI with "+pgAI);
                result = 1; // Set result to Player wins
            }
            else if (pgAI>pg1){
                System.out.println("AI wins with "+pgAI+" over player 1 with "+pg1);
                result = 2; // Set result to AI wins
            }
            else{
                System.out.println("Draw");
            }
        }else if (pg1>21 && pgAI<=21){ // Player exceeds limit, AI wins
            System.out.println("AI wins with "+pgAI+" over player with "+pg1);
            result = 2; // Set result to AI wins
        }
    }
}
