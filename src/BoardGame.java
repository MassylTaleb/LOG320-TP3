import java.util.ArrayList;

public class BoardGame {

    private int[][] board;
    private int treeDepth;
    private boolean isBlack;
    private String strBoard;
    private int minMaxScore;
    private ArrayList<BoardGame> childs;
    private GameState gameState;
    private String move;
    private int alpha;
    private int beta;
    private int boardScore;
    private BoardHeuristic boardHeuristic;

    public BoardGame(int[][] board, boolean isBlack, int depth, String move, int parentScore){

        this.childs = new ArrayList<BoardGame>();
        this.board = board;
        this.treeDepth = depth;
        this.isBlack = isBlack;
        this.move = move;
        boardHeuristic = new BoardHeuristic();
        this.boardScore = boardHeuristic.getBoardHeuristic(board, isBlack);
        if(this.boardScore == 1000000){
            this.gameState = GameState.Won;
        }
        else if (this.boardScore == -1000000){
            this.gameState = GameState.Lost;
        }
        else{
            this.gameState = GameState.Playing;
        }
    }
}

