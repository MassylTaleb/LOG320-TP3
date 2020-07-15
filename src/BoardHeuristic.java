public class BoardHeuristic {

    private int pawnsCount = 0;
    private int redPawnMultiplier = 1;
    private int blackPawnMultiplier = -1;
    private int riskValue;

    public int getBoardHeuristic(int[][] board, boolean isBlack){

        int boardScore = 0;
        if(getGameState(board, isBlack) == GameState.Lost){

            return -1000000;
        }
        else if(getGameState(board, isBlack) == GameState.Won){

            return 1000000;
        }

        else{

            if(isBlack){
                redPawnMultiplier = -1;
                blackPawnMultiplier = 1;
            }

            for(int i = 0; i < board.length; i++){
                for(int j = 0; j < board[i].length; j++){

                    int pawnType = board[i][j];
                    if(!isBlack && pawnType == CellType.RED.getValue()){
                        pawnsCount ++;
                    }

                    if(isBlack && pawnType == CellType.BLACK.getValue()){
                        pawnsCount ++;
                    }

                    if(isPawnAtRisk(board, i, j, isBlack)){

                        riskValue = 0;
                    }else{
                        riskValue = 1;
                    }

                    if(pawnType == CellType.BLACK.getValue()){
                        boardScore += Math.pow(2, i) * blackPawnMultiplier * riskValue;
                    }
                    else if(pawnType == CellType.RED.getValue()){
                        boardScore += Math.pow(2, board.length - 1 - i) * redPawnMultiplier * riskValue;
                    }

                    if (!isBlack && i == 7 && pawnType == CellType.RED.getValue()){
                        boardScore += 20;
                    }

                    if(isBlack && i == 0 && pawnType == CellType.BLACK.getValue()){
                        boardScore += 20;
                    }

                    if(!isBlack && i > 3 && pawnType == CellType.BLACK.getValue()){
                        boardScore -= 200;
                    }

                    if(isBlack && i < 4 && pawnType == CellType.RED.getValue()){
                        boardScore -= 200;
                    }

                }
            }

            boardScore += pawnsCount * 20;
        }

        return boardScore;
    }

    // Dont have a strategy for now
    public static boolean isPawnAtRisk(int[][] board, int i, int j, boolean isBlack){

        return false;
    }

    public GameState getGameState(int[][] board, boolean isBlack){

        if(!isBlack){

            for(int j = 0; j < board[0].length; j++){

                if(board[0][j] == CellType.RED.getValue())
                    return GameState.Won;
            }

            for(int j = 0; j < board[board.length - 1].length; j++){

                if(board[board.length - 1][j] == CellType.BLACK.getValue())
                    return GameState.Lost;
            }
        }

        else{

            for(int j = 0; j < board[board.length - 1].length; j++){

                if(board[board.length - 1][j] == CellType.BLACK.getValue())
                    return GameState.Won;
            }

            for(int j = 0; j < board[0].length; j++){

                if(board[0][j] == CellType.RED.getValue())
                    return GameState.Lost;
            }
        }

        return GameState.Playing;
    }
}
