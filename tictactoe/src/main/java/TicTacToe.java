import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TicTacToe {
    private String[] board;
    private String currentPlayer;
    private static Random random = new Random();

    public TicTacToe() {
        this.board = init();
        this.currentPlayer = "X";
    }

    public String[] init() {
        String[] board = new String[9];
        for (int i = 0; i < board.length; i++) {
            board[i] = "-";
        }
        return board;
    }

    public void updateClientMove(int clientMove) {
        board[clientMove] = "X";
    }

    public boolean checkValidMove(int move) {
        if (board[move].equalsIgnoreCase("-")) {
            return true;
        }
        return false;
    }

    public void makeServerMove() {
        List<Integer> emptyPositions = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            if (board[i].equalsIgnoreCase("-")) {
                emptyPositions.add(i);
            }
        }
        int serverMove = -1;
        if (!emptyPositions.isEmpty()) {
            serverMove = emptyPositions.get(random.nextInt(emptyPositions.size()));
        }
        board[serverMove] = "O";
    }

    public boolean checkWinner(String player) {
        for (int i = 0; i < 8; i++) {
            String line = null;
            switch (i) {
                case 0:
                    line = "" + board[0] + board[1] + board[2];
                    break;
                case 1:
                    line = "" + board[3] + board[4] + board[5];
                    break;
                case 2:
                    line = "" + board[6] + board[7] + board[8];
                    break;
                case 3:
                    line = "" + board[0] + board[3] + board[6];
                    break;
                case 4:
                    line = "" + board[1] + board[4] + board[7];
                    break;
                case 5:
                    line = "" + board[2] + board[5] + board[8];
                    break;
                case 6:
                    line = "" + board[0] + board[4] + board[8];
                    break;
                case 7:
                    line = "" + board[2] + board[4] + board[6];
                    break;
            }
            if (line.equals(player)) {
                return true;
            }
        }
        return false;
    }

    public boolean isBoardFull() {
        for (String c : board) {
            if (c.equalsIgnoreCase("-")) {
                return false;
            }
        }
        return true;
    }

    public String getCurrentBoard() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < board.length; i++) {
            if (i==board.length-1) {
                sb.append(board[i]);
            } else {
                sb.append(board[i]);
                sb.append(" ");
            }
        }
        return sb.toString();
    }

    public void drawBoard(String boardGame) {
        String[] tempBoard = boardGame.split(" ");
        for (int i = 0; i < board.length; i++) {
            board[i] = tempBoard[i];
        }
    }
}
