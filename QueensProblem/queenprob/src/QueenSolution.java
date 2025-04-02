import java.util.*;
import java.util.stream.*;

// NOT the best solution
// just wanted to try something different so went with streams since I haven't used them much
public class QueenSolution {

    public List<List<String>> solveNQueens(int n) {
        // If n doesn't follow constraint return an empty list
        if (n < 1 || n > 9) {
            return new ArrayList<>();
        }

        // create the board with '.' for empty spaces
        char[][] board = new char[n][n];
        for (char[] row : board) {
            Arrays.fill(row, '.');
        }

        // Find and return all valid solutions
        return solution(board, 0)
                .map(this::boardToList) // Convert to a list of strings
                .collect(Collectors.toList());
    }

    // attempts to place queens on the board with recursion
    private Stream<char[][]> solution(char[][] board, int row) {
        // If we reach the end of the board, return this arrangement as a valid solution
        if (row == board.length) {
            return Stream.of(new char[][][]{Arrays.stream(board) // Convert the board to a Stream of rows.
                    .map(char[]::clone) //make a copy
                    .toArray(char[][]::new)}); //make new 2d array
        }

        // Try placing a queen in each column of the current row, and check if it’s safe
        return IntStream.range(0, board.length)
                .filter(col -> isSafe(board, row, col)) // Check if it’s safe to place a queen here
                .mapToObj(col -> {
                    // make a copy of current board
                    char[][] newBoard = Arrays.stream(board)
                            .map(char[]::clone)
                            .toArray(char[][]::new);
                    newBoard[row][col] = 'Q'; // Place a queen in this position
                    return solution(newBoard, row + 1); // Try placing a queen in the next row
                })
                .flatMap(s -> s); // put all solutions into a single stream of possible boards
    }

    // Check to see if placing a queen is safe
    private boolean isSafe(char[][] board, int row, int col) {
        // Check for any queens that can attack this position
        return IntStream.range(0, row)
                .noneMatch(i -> board[i][col] == 'Q' || // Same column
                        (col - (row - i) >= 0 && board[i][col - (row - i)] == 'Q') || // Top-left diagonal
                        (col + (row - i) < board.length && board[i][col + (row - i)] == 'Q')); // Top-right diagonal
        //only checking top since it is being placed row by row going down
    }

    // turns the board into a list of strings for easier reading
    private List<String> boardToList(char[][] board) {
        return Arrays.stream(board)
                .map(String::new) // turn into a string
                .collect(Collectors.toList()); // turn into a list
    }

    public static void main(String[] args) {
        QueenSolution solution = new QueenSolution();

        // Loop through nxn from 1 to 9 and solve for each size
        IntStream.rangeClosed(1, 9)
                .forEach(n -> {
                    // Get all solutions for nxn
                    List<List<String>> solutions = solution.solveNQueens(n);
                    // Print the number of solutions for nxn
                    System.out.printf("N = %d: %d solutions%n", n, solutions.size());
                    // print each solution
                    if (n <= 9) {
                        solutions.forEach(s -> {
                            s.forEach(System.out::println);
                            System.out.println();
                        });
                    }
                });
    }
}

