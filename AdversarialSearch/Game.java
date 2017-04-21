import javax.crypto.BadPaddingException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Game {
    public static void main(String[] args) throws IOException {

        File inputFile = new File("input.txt");
        Scanner in = new Scanner(inputFile);

        int boardSize = in.nextInt();
        String mode = in.next();
        String youplay = in.next();
        int player = 0;
        if (youplay.equals("X")) {
            player = -1;
        }
        else if (youplay.equals("O")) {
            player = 1;
        }
        int depth = in.nextInt();
        int [][] cellValue = new int[boardSize][boardSize];
        for (int i = 0; i < cellValue.length; i++) {
            for (int j = 0; j < cellValue[0].length; j++) {
                cellValue[i][j] = in.nextInt();
            }
        }

        int [][] board = new int[boardSize][boardSize];

        for (int i = 0; i < boardSize; i++) {
            String oneLine = in.next();
            for (int j = 0; j < boardSize; j++) {
                if (oneLine.charAt(j) == 'X') {
                    board[i][j] = -1;
                }
                else if (oneLine.charAt(j) == 'O') {
                    board[i][j] = 1;
                }
                else if (oneLine.charAt(j) == '.') {
                    board[i][j] = 0;
                }
            }
        }

        Board oneGame = new Board(mode, player, depth, cellValue, board);
        Action action = oneGame.playGame();
        int[][] newBoard = oneGame.doAction(board, action, player);

        FileWriter output = new FileWriter("output.txt");
        char col = (char)(int)(action.col + 65);
        int row = action.row + 1;
        String move = Character.toString(col) + row + " " + action.moveType + "\n";
        output.write(move);
        for (int i = 0; i < boardSize; i++) {
            String oneLine = "";
            for (int j = 0; j < boardSize; j++) {
                if (newBoard[i][j] == -1) {
                    oneLine += "X";
                }
                else if (newBoard[i][j] == 1) {
                    oneLine += "O";
                }
                else if (newBoard[i][j] == 0) {
                    oneLine += ".";
                }
            }
            output.write(oneLine);
            if (i < boardSize - 1) {
                output.write("\n");
            }
        }
        output.close();
        in.close();
    }
}
