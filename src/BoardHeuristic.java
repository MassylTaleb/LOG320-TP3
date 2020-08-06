
public class BoardHeuristic {

    private int redPawnMultiplier = 1;
    private int blackPawnMultiplier = -1;
    private int pawnsCount = 0;
    private int riskValue;

    public int getBoardHeuristic(int[][] board, boolean isBlack){

        int boardScore = 0;

        // Verify status of game, and we affect points depending of the game status
        if(getGameState(board, isBlack) == GameState.Lost){

            return GameScores.SCORE_LOST.getValue();
        }
        else if(getGameState(board, isBlack) == GameState.Won){

            return GameScores.SCORE_WIN.getValue();
        }

        else{

            if(isBlack){
                redPawnMultiplier = -1;
                blackPawnMultiplier = 1;
            }

            for(int i = 0; i < board.length; i++){
                for(int j = 0; j < board[i].length; j++){

                    // Count how many token is on board
                    int pawnType = board[i][j];
                    if(!isBlack && pawnType == CellType.RED.getValue()){
                        pawnsCount ++;
                    }

                    if(isBlack && pawnType == CellType.BLACK.getValue()){
                        pawnsCount ++;
                    }

                    // Check if token is at risk of being killed
                    if(isPawnAtRisk(board, i, j, isBlack)){
                        riskValue = 0;
                    } else {
                        riskValue = 1;
                    }

                    // Check how far is the pawn from the finish line
                    if(pawnType == CellType.BLACK.getValue()){
                        boardScore += Math.pow(2, i) * blackPawnMultiplier * riskValue;
                    }
                    else if(pawnType == CellType.RED.getValue()){
                        boardScore += Math.pow(2, board.length - 1 - i) * redPawnMultiplier * riskValue;
                    }

                    // TODO: Pas compris !
                    if (!isBlack && i == 7 && pawnType == CellType.RED.getValue()){
                        boardScore += 10;
                    }

                    if(isBlack && i == 0 && pawnType == CellType.BLACK.getValue()){
                        boardScore += 10;
                    }

                    if(!isBlack && i > 3 && pawnType == CellType.BLACK.getValue()){
                        boardScore -= 100;
                    }

                    if(isBlack && i < 4 && pawnType == CellType.RED.getValue()){
                        boardScore -= 100;
                    }

                }
            }

            // For each pawn still alive, we give points
            boardScore += pawnsCount * 20;
        }

        return boardScore;
    }

    // Dont have a strategy for now
    public static boolean isPawnAtRisk(int[][] board, int i, int j, boolean isBlack){

        return true;
    }

    public GameState getGameState(int[][] board, boolean isBlack){

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
