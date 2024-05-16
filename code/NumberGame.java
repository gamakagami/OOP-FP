import java.util.Random;
import java.util.Scanner;

public class NumberGame {
    static int result = 0;
    public static void playTwoPlayers(Random random, Scanner scanner) {
        int loop = 0;
        int pg1 = 0;
        int pg12 = 0;
        pg1 = random.nextInt(21);
        System.out.println("Player 1, you got " + pg1);

        int pg4 = 0;
        while (loop < 2) {
            System.out.println("Add a guess? (Y/N)");
            String pg2 = scanner.next();
            pg2 = pg2.toUpperCase();
            if (pg2.equals("Y")) {
                System.out.println("Choose how to add the number:");
                System.out.println("1: Add 1 to 4");
                System.out.println("2: Add 5 to 7");
                System.out.println("3: Add 8 to 10");
                loop += 1;
                int pg3 = scanner.nextInt();
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
                        loop--;
                        continue;
                }
                pg1 += pg4;
                System.out.println("Player 1, your number is now: " + pg1);
            } else if (pg2.equals("N")) {
                System.out.println("Player 1, " + pg1 + " is your final number");
                loop += 100;
            } else {
                System.out.println("Invalid input, try again");
            }
        }

        // Repeat the process for Player 2
        loop = 0;
        pg12 = random.nextInt(21);
        System.out.println("Player 2, you got " + pg12);
        while (loop < 2) {
            System.out.println("Add a guess? (Y/N)");
            String pg2 = scanner.next();
            pg2 = pg2.toUpperCase();
            if (pg2.equals("Y")) {
                System.out.println("Choose how to add the number:");
                System.out.println("1: Add 1 to 4");
                System.out.println("2: Add 5 to 7");
                System.out.println("3: Add 8 to 10");
                loop += 1;
                int pg3 = scanner.nextInt();
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
                        loop--;
                        continue;
                }
                pg12 += pg4;
                System.out.println("Player 2, your number is now: " + pg12);
            } else if (pg2.equals("N")) {
                System.out.println("Player 2, " + pg12 + " is your final number");
                loop += 100;
            } else {
                System.out.println("Invalid input, try again");
            }
        }
        if ((pg1<=21)&&(pg12<=21)){
            if (pg1>pg12){
                System.out.println("Player 1 wins with "+pg1+" over player 2 with "+pg12);
                result = 1;
            }
            else if (pg12>pg1){
                System.out.println("Player 2 wins with "+pg12+" over player 1 with "+pg1);
                result = 2;
            }
            else{
                System.out.println("Draw");
            }
        }else if (pg1>21 && pg12<=21){
            System.out.println("Player 2 wins with "+pg12+" over player 1 with "+pg1);
            result = 2;
        }
        else if (pg1<=21 && pg12>21){
            System.out.println("Player 1 wins with "+pg1+" over player 2 with "+pg12);
            result = 1;
        }
        else{
            System.out.println("Both player loses (Draw)");
        }
    }

    public static void playVsAI(Random random, Scanner scanner) {
        int loop = 0;
        int pg1 = 0;
        pg1 = random.nextInt(21);
        System.out.println("You got " + pg1);

        int pg4 = 0;
        while (loop < 2) {
            System.out.println("Add a guess? (Y/N)");
            String pg2 = scanner.next();
            pg2 = pg2.toUpperCase();
            if (pg2.equals("Y")) {
                System.out.println("Choose how to add the number:");
                System.out.println("1: Add 1 to 4");
                System.out.println("2: Add 5 to 7");
                System.out.println("3: Add 8 to 10");
                loop += 1;
                int pg3 = scanner.nextInt();
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
                        continue; // Go to the next iteration of the loop
                }
                pg1 += pg4;
                System.out.println("Your number is now: " + pg1);
            } else if (pg2.equals("N")) {
                System.out.println(pg1 + " is your final number");
                loop += 100;
            } else {
                System.out.println("Invalid input, try again");
            }
        }

        // AI's turn
        loop = 0;
        int pgAI = random.nextInt(21);
        System.out.println("AI got " + pgAI);
        while (loop < 2 && pgAI < 21) {
            int pg3 = random.nextInt(3) + 1; // Randomly choose how to add the number
            switch (pg3) {
                case 1:
                    pg4 = random.nextInt(4) + 1;
                    pgAI += pg4;
                    if (pgAI>21){
                        pgAI-=pg4;
                        loop+=100;
                    }
                    break;
                case 2:
                    pg4 = random.nextInt(3) + 5;
                    pgAI += pg4;
                    if (pgAI>21){
                        pgAI-=pg4;
                        loop+=100;
                    }
                    break;
                case 3:
                    pg4 = random.nextInt(3) + 8;
                    pgAI += pg4;
                    if (pgAI>21){
                        pgAI-=pg4;
                        loop+=100;
                    }
                    break;
            }
            System.out.println("AI's number is now: " + pgAI);
            loop++;
        }
        if ((pg1<=21)&&(pgAI<=21)){
            if (pg1>pgAI){
                System.out.println("Player wins with "+pg1+" over AI with "+pgAI);
                result = 1;
            }
            else if (pgAI>pg1){
                System.out.println("AI wins with "+pgAI+" over player 1 with "+pg1);
                result = 2;
            }
            else{
                System.out.println("Draw");
            }
        }else if (pg1>21 && pgAI<=21){
            System.out.println("AI wins with "+pgAI+" over player with "+pg1);
            result = 2;
        }
        else if (pg1<=21 && pgAI>21){
            System.out.println("Player wins with "+pg1+" over AI with "+pgAI);
            result = 1;
        }
        else{
            System.out.println("Both player loses (Draw)");
        }
    }
    public int getResult(){
        return result;

    }
}
