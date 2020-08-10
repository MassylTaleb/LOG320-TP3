
public class BoardHeuristic {

    public static int getBoardHeuristic(int[][] board, boolean isBlack){

        int boardScore = 0;

        // Verify status of game, and we affect points depending of the game status
        if(getGameState(board, isBlack) == GameState.Won){

            return GameScores.SCORE_WIN.getValue();
        }
        else if(getGameState(board, isBlack) == GameState.Lost){

            return GameScores.SCORE_LOST.getValue();
        }

        else{

            int pawnsCount = 0;
            int redPawnMultiplier = 1;
            int blackPawnMultiplier = -1;

            if(isBlack){
                redPawnMultiplier = -1;
                blackPawnMultiplier = 1;
            }

            for(int i = 0; i < board.length; i++){
                for(int j = 0; j < board[i].length; j++){

                    // Current pawn
                    int currentPawnType = board[i][j];

                    // Count how many pawn is left on the board
                    if(isBlack && currentPawnType == CellType.BLACK.getValue())
                        pawnsCount ++;

                    if(!isBlack && currentPawnType == CellType.RED.getValue())
                        pawnsCount ++;


                    // Check if token is at risk of being killed
                    int riskValue = isPawnAtRisk(board, i, j, isBlack) ? 0 : 1;

                    // Check how far is the pawn from the finish line
                    if(currentPawnType == CellType.BLACK.getValue())
                        boardScore += Math.pow(2, i) * blackPawnMultiplier * riskValue;
                    else if(currentPawnType == CellType.RED.getValue())
                        boardScore += Math.pow(2, board.length - 1 - i) * redPawnMultiplier * riskValue;

                    // For each pawn on their first line, we give 10 point for defending
                    if (isBlack && i == 0 && currentPawnType == CellType.BLACK.getValue())
                        boardScore += 10;
                    else if(!isBlack && i == 7 && currentPawnType == CellType.RED.getValue())
                        boardScore += 10;

                    // If the opponent pawn is over our middle line,
                    if(isBlack && i < 4 && currentPawnType == CellType.RED.getValue())
                        boardScore -= 100;

                    if(!isBlack && i > 3 && currentPawnType == CellType.BLACK.getValue())
                        boardScore -= 100;

                }
                boardScore += pawnBackupScore(board, i, isBlack);
            }

            // For each pawn still alive, we give points
            boardScore += pawnsCount * 20;
        }

        return boardScore;
    }

    /**
     * We check if enemies are in both diagonals of the current pawn
     *
     * @param board
     * @param i
     * @param j
     * @param isBlack
     * @return
     */
    public static boolean isPawnAtRisk(int[][] board, int i, int j, boolean isBlack){

        int contentLeftDiagonalCell = BoardTools.getLeftDiagonalPosition(board, i, j);
        int contentRightDiagonalCell = BoardTools.getRightDiagonalCellPosition(board, i, j);
        boolean isInDanger;

        if(isBlack && board[i][j] == CellType.BLACK.getValue()) {
            isInDanger = contentLeftDiagonalCell == CellType.RED.getValue() || contentRightDiagonalCell == CellType.RED.getValue();

            if(isInDanger && isPositionSecured(board, i, j, isBlack))
                isInDanger = false;

            return isInDanger;
        }

        else if(!isBlack && board[i][j] == CellType.RED.getValue()) {
            isInDanger = contentLeftDiagonalCell == CellType.BLACK.getValue() || contentRightDiagonalCell == CellType.BLACK.getValue();

            if(isInDanger && isPositionSecured(board, i, j, isBlack))
                isInDanger = false;

            return isInDanger;
        }

        return false;
    }

    public static boolean isPositionSecured(int[][] board, int i, int j, boolean isBlack) {
        int enemiesHelpingPawnInDanger = 0;

        if(!isBlack) {
            if(BoardTools.getLeftDiagonalPosition(board, i, j) == CellType.BLACK.getValue()) {
                enemiesHelpingPawnInDanger++;
            }
            if(BoardTools.getRightDiagonalCellPosition(board, i, j) == CellType.BLACK.getValue()) {
                enemiesHelpingPawnInDanger++;
            }
            return (BoardTools.getDownLeftDiagonalCellPosition(board, i, j) == CellType.RED.getValue() ||
                    BoardTools.getDownRightDiagonalCellPosition(board, i, j) == CellType.RED.getValue()) &&
                    enemiesHelpingPawnInDanger != 2;
        }
        else {
            if(BoardTools.getLeftDiagonalPosition(board, i, j) == CellType.RED.getValue()) {
                enemiesHelpingPawnInDanger++;
            }
            if(BoardTools.getRightDiagonalCellPosition(board, i, j) == CellType.RED.getValue()) {
                enemiesHelpingPawnInDanger++;
            }
            return (BoardTools.getDownLeftDiagonalCellPosition(board, i, j) == CellType.BLACK.getValue() ||
                    BoardTools.getDownRightDiagonalCellPosition(board, i, j) == CellType.BLACK.getValue()) &&
                    enemiesHelpingPawnInDanger != 2;
        }
    }

    /**
     * We give points for every pawn who has a connection in the same line
     *
     * @param board
     * @param row
     * @param isBlack
     * @return
     */
    private static int pawnBackupScore(int[][] board, int row, boolean isBlack) {

        int score = 0;
        boolean atLeastOne = false;

        if(!isBlack) {
            for(int column = 0; column >= board[row].length; column++){
                if(board[row][column] == CellType.RED.getValue() && !atLeastOne)
                    atLeastOne = true;
                else if(board[row][column] == CellType.RED.getValue() && atLeastOne)
                    score += 100;
                else
                    atLeastOne = false;
            }
        }
        else {
            for(int column = 0; column >= board[row].length; column++){
                if(board[row][column] == CellType.BLACK.getValue() && !atLeastOne)
                    atLeastOne = true;
                else if(board[row][column] == CellType.BLACK.getValue() && atLeastOne)
                    score += 100;
                else
                    atLeastOne = false;
            }
        }
        return score;
    }

    public static GameState getGameState(int[][] board, boolean isBlack){

        // If it's red
        if(!isBlack){

            // Check game status in finish line, if there's a red, we won.
            for(int j = 0; j < board[0].length; j++){

                if(board[0][j] == CellType.RED.getValue())
                    return GameState.Won;
            }

            // Check game status in beginning line, if there's a black, we lost.
            for(int j = 0; j < board[board.length - 1].length; j++){

                if(board[board.length - 1][j] == CellType.BLACK.getValue())
                    return GameState.Lost;
            }
        }

        // If it's black
        else{

            // Check game status in finish line, if there's a black, we won.
            for(int j = 0; j < board[board.length - 1].length; j++){

                if(board[board.length - 1][j] == CellType.BLACK.getValue())
                    return GameState.Won;
            }

            // Check game status in beginning line, if there's a red, we lost.
            for(int j = 0; j < board[0].length; j++){

                if(board[0][j] == CellType.RED.getValue())
                    return GameState.Lost;
            }
        }

        // If there is nothing, the game is still going on
        return GameState.Playing;
    }
}
