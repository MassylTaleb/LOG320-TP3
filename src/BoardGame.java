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

    /**
     * Save all borad information and checking the current state of the board.
     *
     * @param board Current board
     * @param isBlack Which colour is playing
     * @param depth Tree depth reference for the board (copy or initial board)
     * @param move Move currently evaluated
     * @param parentScore Score of the parent node
     */
    public BoardGame(int[][] board, boolean isBlack, int depth, String move, int parentScore) {

        this.childs = new ArrayList<>();
        this.board = board;
        this.treeDepth = depth;
        this.isBlack = isBlack;
        this.move = move;

        // Affect the score of the current board
        this.boardScore = new BoardHeuristic().getBoardHeuristic(board, isBlack);

        // Set game status depending on score
        if (this.boardScore == GameScores.SCORE_WIN.getValue()) {

            this.gameState = GameState.Won;

        } else if (this.boardScore == GameScores.SCORE_LOST.getValue()) {

            this.gameState = GameState.Lost;

        } else {

            this.gameState = GameState.Playing;
        }
    }

    public int getTreeDepth() {
        return this.treeDepth;
    }

    public boolean isBlack() {
        return isBlack;
    }

    public GameState getGameState() {
        return gameState;
    }

    public int getBoardScore() {
        return boardScore;
    }

    public int getMinMaxScore() {
        return minMaxScore;
    }

    public void setMinMaxScore(int minMaxScore) {
        this.minMaxScore = minMaxScore;
    }

    public int getAlpha() {
        return alpha;
    }

    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }

    public int getBeta() {
        return beta;
    }

    public void setBeta(int beta) {
        this.beta = beta;
    }

    public int[][] getBoard() {
        return board;
    }

    public ArrayList<BoardGame> getChilds() {
        return childs;
    }

    public void setChilds(ArrayList<BoardGame> childs) {
        this.childs = childs;
    }

    public String getMove() {
        return move;
    }
}

