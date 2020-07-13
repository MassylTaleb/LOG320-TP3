import java.util.ArrayList;

public class BoardGame {

    private int[][] board;
    private int depth;
    private boolean isRed;
    private String strBoard;
    private int minMaxScore;
    private ArrayList<BoardGame> children;
    private GameState gameState;
    private String move;
    private int alpha;
    private int beta;
    private int score;

    public BoardGame(int[][] board, boolean isRed, int depth, String move, int parentScore){

        this.children = new ArrayList<BoardGame>();
        this.board = board;
        this.depth = depth;
        this.isRed = isRed;
        this.move = move;


    }
}

