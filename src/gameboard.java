import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Arrays;
public class gameboard {
    private String[][] board;
    private String[] colors;
    private int maxColors;

    public gameboard(String[][] board, String[] colors) {
        this.board = board;
        this.colors = colors;
        this.maxColors = colors.length;
        initializeBoard();
    }

    public String[][] getBoard() {
        return board;
    }

    public void setBoard(String[][] board) {
        this.board = board;
    }

    public String[] getColors() {
        return colors;
    }

    public void setColors(String[] colors) {
        this.colors = colors;
    }

    public int getMaxColors() {
        return maxColors;
    }

    public void setMaxColors(int maxColors) {
        this.maxColors = maxColors;
    }

    private void initializeBoard() {
        Random random = new Random();
        ArrayList <String> usedColors = new ArrayList<>();

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board[i][j] =" ";
            }
        }

        int numCells = random.nextInt(20) + 1;
        for (int cell = 0; cell < numCells; cell++) {
            int x = random.nextInt(9);
            int y = random.nextInt(9);

            String color = uniqueColor(usedColors);
            board[x][y] = color;
        }
    }

    private String uniqueColor(ArrayList<String> usedColors) {
        Random random = new Random();
        String color;
        do {
            color = colors[random.nextInt(maxColors)];
        } while (usedColors.contains(color));

        usedColors.add(color);
        return color;
    }

   //backtracking
    public boolean solveBacktracking(String[][] board) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (board[row][col].equals(" ")) {
                    for (String color : colors) {
                        if (isValid(board, row, col, color)) {
                            board[row][col] = color;
                            displayBoard();
                            System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

                            if (solveBacktracking(board)) {
                                return true;
                            } else {
                                board[row][col] = " ";
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }



    private boolean isValid(String[][] board, int row, int col, String color) {
        // Check if color is unique in its row and column
        for (int i = 0; i < 9; i++) {
            if (board[row][i].equals(color) || board[i][col].equals(color)) {
                return false;
            }
        }

        int startRow = row - row % 3;
        int startCol = col - col % 3;
        for (int i = startRow; i < startRow + 3; i++) {
            for (int j = startCol; j < startCol + 3; j++) {
                if (board[i][j].equals(color)) {
                    return false;
                }
            }
        }
        return true;
    }
    // Dynamic programming
    public boolean solveDynamicProgramming(String[][] board) {
        boolean[][][] dp = new boolean[9][9][9];

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Arrays.fill(dp[i][j], true);
            }
        }

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (!board[i][j].equals(" ")) {
                    int colorIdx = indexOf(colors, board[i][j]);
                    for (int k = 0; k < 9; k++) {
                        if (k != colorIdx) {
                            dp[i][j][k] = false;
                        }
                    }
                }
            }
        }

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                for (int k = 0; k < 9; k++) {
                    if (dp[i][j][k]) {
                        if (checkConstraints(board, i, j, colors[k])) {


                            updateDP(dp, i, j, k);
                            board[i][j] = colors[k];
                        }
                    }
                }
            }
        }

        displayBoard();

        return true;
    }

    private boolean checkConstraints(String[][] board, int row, int col, String color) {
        for (int i = 0; i < 9; i++) {
            if (board[row][i].equals(color) || board[i][col].equals(color)) {
                return false;
            }
        }
        int startRow = row - row % 3;
        int startCol = col - col % 3;
        for (int i = startRow; i < startRow + 3; i++) {
            for (int j = startCol; j < startCol + 3; j++) {
                if (board[i][j].equals(color)) {
                    return false;
                }
            }
        }
        return true;
    }

    private void updateDP(boolean[][][] dp, int row, int col, int colorIdx) {
        for (int r = 0; r < 9; r++) {
            dp[row][r][colorIdx] = false;
            dp[r][col][colorIdx] = false;
        }
        int startRow = row - row % 3;
        int startCol = col - col % 3;
        for (int r = startRow; r < startRow + 3; r++) {
            for (int c = startCol; c < startCol + 3; c++) {
                dp[r][c][colorIdx] = false;
            }
        }
    }




    private int indexOf(String[] arr, String target) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].equals(target)) {
                return i;
            }
        }
        return -1;
    }

// Greedy algorithm
    public boolean solveGreedyAlgorithm() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (board[row][col].equals(" ")) {
                    String bestColor = findBestColor(row, col);
                    if (bestColor != null) {
                        board[row][col] = bestColor;
                        displayBoard();
                        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                    } else {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private String findBestColor(int row, int col) {
        ArrayList<String> availableColors = new ArrayList<>();
        Collections.addAll(availableColors, colors);

        for (int i = 0; i < 9; i++) {
            availableColors.remove(board[row][i]);
            availableColors.remove(board[i][col]);
        }

        int startRow = row - row % 3;
        int startCol = col - col % 3;
        for (int i = startRow; i < startRow + 3; i++) {
            for (int j = startCol; j < startCol + 3; j++) {
                availableColors.remove(board[i][j]);
            }
        }

        if (!availableColors.isEmpty()) {
            return availableColors.get(0);
        } else {
            return null;
        }
    }
// display the board
    public void displayBoard() {
        System.out.println("+-------+-------+-------+");
        for (int i = 0; i < 9; i++) {
            if (i > 0 && i % 3 == 0) {
                System.out.println("|-------+-------+-------|");
            }
            for (int j = 0; j < 9; j++) {
                if (j > 0 && j % 3 == 0) {
                    System.out.print("| ");
                }
                if (!board[i][j].equals("")) {
                    System.out.print(colorize(board[i][j]) + board[i][j] + "\u001B[0m");
                } else {
                    System.out.print("  ");
                }
                System.out.print(" ");
            }
            System.out.println("|");
        }
        System.out.println("+-------+-------+-------+");
    }
    // check if the board is filled
    public boolean isfilled(String[][] board) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (board[row][col].equals(" ")) {
                    return false;
                }
            }
        }
        return true;
    }

    private String colorize(String color) {
        switch (color) {
            case "w":
                return "\u001B[37m"; // White
            case "b":
                return "\u001B[30m"; // Black
            case "r":
                return "\u001B[31m"; // Red
            case "o":
                return "\u001B[33m"; // Orange
            case "m":
                return "\u001B[33m"; // Brown
            case "p":
                return "\u001B[35m"; // Purple
            case "g":
                return "\u001B[32m"; // Green
            case "y":
                return "\u001B[33m"; // Yellow
            case "s":
                return "\u001B[34m"; // Blue
            case "t":
                return "\u001B[37m"; // Grey
            case "a":
                return "\u001B[35m"; // Pink
            default:
                return "";
        }
    }

}