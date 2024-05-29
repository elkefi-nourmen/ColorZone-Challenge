import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String ANSI_RESET = "\u001B[0m";
        String ANSI_MAGENTA = "\u001B[35m";
        String ANSI_CYAN = "\u001B[36m";

        Scanner s = new Scanner(System.in);
        int choice = 0;
        String[][] board = new String[9][9];
        String[] colors = {"w", "b", "r", "o", "m", "p", "g", "y", "s", "t", "a"};

        gameboard game = new gameboard(board, colors);
        String[][] board2 = copyBoard(board);

        gameboard game2 = new gameboard(board2, colors);
        long startTime, endTime;
        System.out.println(ANSI_CYAN+"                              Welcome to the Process Challenge Game"+ANSI_RESET);
        System.out.println("Player 1 Please enter your name ");
        String name = s.next();
        player player1 = new player(name, 0);

        System.out.println("Player 2 Please enter your name ");
        String name2 = s.next();
        player player2 = new player(name2, 0);

        System.out.println("The first level is strategic thinking : ");
        System.out.println(player1.getName() + " please select between these three strategies : ");
        do {
            System.out.println("Enter 1 for backtracking");
            System.out.println("Enter 2 for Dynamic programming");
            System.out.println("Enter 3 for Greedy Algorithm");

            choice = s.nextInt();
        } while (choice != 1 && choice != 2 && choice!=3);
        startTime = System.currentTimeMillis();

        if (choice == 1) {
            game.solveBacktracking(board);
            game.displayBoard();
        } else if (choice==2){
            game.solveDynamicProgramming(board);
            game.displayBoard();
        }else{
            game.solveGreedyAlgorithm();
            game.displayBoard();
        }
        endTime = System.currentTimeMillis();

        System.out.println("Player 2: ");
        choice = 0;
        System.out.println(player2.getName() + " please select between these three strategies : ");
        do {
            System.out.println("Enter 1 for backtracking");
            System.out.println("Enter 2 for Dynamic programming");
            System.out.println("Enter 3 for Greedy Algorithm");

            choice = s.nextInt();
        } while (choice != 1 && choice != 2 && choice!=3);
         startTime = System.currentTimeMillis();
        long player1Time = endTime - startTime;

        if (choice == 1) {
            game2.solveBacktracking(board2);
            game2.displayBoard();
        } else if (choice==2){
            game2.solveDynamicProgramming(board2);
            game2.displayBoard();
        }
        else {
            game2.solveGreedyAlgorithm();
            game2.displayBoard();
        }
        endTime = System.currentTimeMillis();
        long player2Time = endTime - startTime;


        boolean player1Filled = game.isfilled(board);
        boolean player2Filled = game2.isfilled(board2);

        if (player1Filled && player2Filled) {
            if (player2Time > player1Time) {
                player1.setScore(player1.getScore() + 10);
                System.out.println(ANSI_MAGENTA+player1.getName() + " you are the winner!! You got +10 to your score"+ANSI_RESET);
            } else {
                player2.setScore(player2.getScore() + 10);
                System.out.println(ANSI_MAGENTA+player2.getName() + " you are the winner!! You got +10 to your score"+ANSI_RESET);
            }
        } else if (player1Filled) {
            player1.setScore(player1.getScore() + 10);
            System.out.println(ANSI_MAGENTA+player1.getName() + " you are the winner!! You got +10 to your score"+ANSI_RESET);
        } else if (player2Filled) {
            player2.setScore(player2.getScore() + 10);
            System.out.println(ANSI_MAGENTA+player2.getName() + " you are the winner!! You got +10 to your score"+ANSI_RESET);
        } else {
            System.out.println("No winner, both boards are not filled!");
        }
    }

    private static String[][] copyBoard(String[][] original) {
        String[][] copy = new String[original.length][original[0].length];
        for (int i = 0; i < original.length; i++) {
            System.arraycopy(original[i], 0, copy[i], 0, original[i].length);
        }
        return copy;
    }
}
