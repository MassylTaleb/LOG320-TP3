import java.util.ArrayList;

public class Algorithm {

    public static void minimax(BoardGame board, boolean maximizingPlayer) {

        /*if(Timer.getTimeElapsed() > 4.0)
            return;*/

        if(board.getTreeDepth() < 4 && board.getGameState() == GameState.Playing) {
            // We're red, and we're trying to maximize alpha
            if(!board.isBlack() && board.getTreeDepth() % 2 == 0) {
                board.setMinMaxScore(-1000000);
            }
            // We're red, and we're trying to minimize beta
            if(!board.isBlack() && board.getTreeDepth() % 2 != 0) {
                board.setMinMaxScore(1000000);
            }
            // We're black, and we're trying to maximize alpha
            if(board.isBlack() && board.getTreeDepth() % 2 == 0) {
                board.setMinMaxScore(-1000000);
            }
            // We're black, and we're trying to minimize beta
            if(board.isBlack() && board.getTreeDepth() % 2 != 0) {
                board.setMinMaxScore(1000000);
            }

            board.setAlpha(-1000000);
            board.setBeta(1000000);
            expandChilds(board);
        }
        else {
            board.setMinMaxScore(board.getBoardScore());
            board.setAlpha(0);
            board.setBeta(0);
        }

        if(board.getChilds().isEmpty()) {
            return;
        }

        BoardGame bestPlay = null;
        if(maximizingPlayer) {
            for(BoardGame childBoard : board.getChilds()) {
                minimax(childBoard, false);

                if(bestPlay == null) {
                    bestPlay = childBoard;
                } else {
                    if(bestPlay.getMinMaxScore() < childBoard.getMinMaxScore()) {
                        bestPlay = childBoard;
                    }
                }

                if(board.getAlpha() < bestPlay.getMinMaxScore()) {
                    board.setAlpha(bestPlay.getMinMaxScore());
                }
                if(bestPlay.getMinMaxScore() > board.getBeta()) {
                    break;
                }
            }

        } else {
            for(BoardGame childBoard : board.getChilds()) {
                minimax(childBoard, true);

                if(bestPlay == null) {
                    bestPlay = childBoard;
                } else {
                    if(bestPlay.getMinMaxScore() > childBoard.getMinMaxScore()) {
                        bestPlay = childBoard;
                    }
                }

                if(board.getBeta() > bestPlay.getMinMaxScore()) {
                    board.setBeta(bestPlay.getMinMaxScore());
                }
                if(bestPlay.getMinMaxScore() > board.getAlpha()) {
                    break;
                }
            }

        }
        board.setMinMaxScore(bestPlay.getMinMaxScore());
        board.setChilds(board.getChilds());
    }

    /**
     *
     * @param boardGame Actual board or next board copy after iterations of tree
     *
     *
     */
    static void expandChilds(BoardGame boardGame) {

        int depthTree = boardGame.isBlack() ? 1 : 0; //depth tree is 1 if pawn black or 0 if pawn is red
        int[][] boardCopy; //deaclaration of board copy
        int contentLeftDiagonalCell; //decalaration of left diagonal move
        int contentRightDiagonalCell; //decalaration of right diagonal move
        int contentFrontCell; //decalaration of front move
        String newMove; //decalaration of new move

        //Go through whole board
        for(int i = 0; i < boardGame.getBoard().length; i++) {
            for(int j = 0; j < boardGame.getBoard()[0].length; j++) {

                boardCopy = cloneBoard(boardGame.getBoard()); //copy the actual board
                contentLeftDiagonalCell = BoardTools.getLeftDiagonalPosition(boardCopy, i, j); //get what is in the left diagonal position from actual pawn
                contentRightDiagonalCell = BoardTools.getRightDiagonalCellPosition(boardCopy, i, j);//get what is in the right diagonal position from actual pawn
                contentFrontCell = BoardTools.getFrontCellPosition(boardCopy, i, j);//get what is in the front position from actual pawn
                //If we(AI) are playing
                if(boardGame.getTreeDepth() % 2 == depthTree) {
                    //If pawn is red in actual board position
                    if(boardCopy[i][j] == CellType.RED.getValue()) {
                        //Verify if position diagonal left from actual board position is neither red or forbidden
                        if(contentLeftDiagonalCell != CellType.FORBIDDEN.getValue() && contentLeftDiagonalCell != CellType.RED.getValue()) {
                            BoardTools.moveToLeftDiagonal(boardCopy, i, j); //Simulate move to diagonal left?
                            newMove = BoardTools.positionValue(boardCopy,i, j) + BoardTools.positionValue(boardCopy,i - 1, j - 1); //Append values of current position and new postion
                            //TODO : Shouldn't we only add the newMove to the childsList and then make a new board and loop through minimax? Why are we adding a board to the childs? Unless we actually get the move of that board when we evaluate
                            boardGame.getChilds().add(new BoardGame(boardCopy, boardGame.isBlack(), boardGame.getTreeDepth() + 1, newMove, boardGame.getBoardScore()));
                        }
                        //Verify if position diagonal right from actual board position is neither red or forbidden
                        if(contentRightDiagonalCell != CellType.FORBIDDEN.getValue() && contentRightDiagonalCell != CellType.RED.getValue()) {
                            BoardTools.moveToRightDiagonal(boardCopy, i, j);
                            newMove = BoardTools.positionValue(boardCopy,i, j) + BoardTools.positionValue(boardCopy,i - 1, j + 1);
                            boardGame.getChilds().add(new BoardGame(boardCopy, boardGame.isBlack(), boardGame.getTreeDepth() + 1, newMove, boardGame.getBoardScore()));
                        }
                        //Verify if position front from actual board position is neither red, black or forbidden
                        if(contentFrontCell != CellType.FORBIDDEN.getValue() && contentFrontCell != CellType.RED.getValue() && contentFrontCell != CellType.BLACK.getValue()) {
                            BoardTools.moveToFront(boardCopy, i, j);
                            newMove = BoardTools.positionValue(boardCopy, i, j) + BoardTools.positionValue(boardCopy, i - 1, j);
                            boardGame.getChilds().add(new BoardGame(boardCopy, boardGame.isBlack(), boardGame.getTreeDepth() + 1, newMove, boardGame.getBoardScore()));
                        }
                    }
                    //If they are playing
                } else {
                    //If pawn is black in actual board position
                    if(boardCopy[i][j] == CellType.BLACK.getValue()) {
                        //Verify if position diagonal left from actual board position is neither black or forbidden
                        if(contentLeftDiagonalCell != CellType.FORBIDDEN.getValue() && contentLeftDiagonalCell != CellType.BLACK.getValue()) {
                            BoardTools.moveToLeftDiagonal(boardCopy, i, j);
                            newMove = BoardTools.positionValue(boardCopy,i, j) + BoardTools.positionValue(boardCopy, i + 1, j + 1);
                            boardGame.getChilds().add(new BoardGame(boardCopy, boardGame.isBlack(), boardGame.getTreeDepth() + 1, newMove, boardGame.getBoardScore()));
                        }
                        //Verify if position diagonal right from actual board position is neither black or forbidden
                        if(contentRightDiagonalCell != CellType.FORBIDDEN.getValue() && contentRightDiagonalCell != CellType.BLACK.getValue()) {
                            BoardTools.moveToRightDiagonal(boardCopy, i, j);
                            newMove = BoardTools.positionValue(boardCopy, i, j) + BoardTools.positionValue(boardCopy,i + 1, j - 1);
                            boardGame.getChilds().add(new BoardGame(boardCopy, boardGame.isBlack(), boardGame.getTreeDepth() + 1, newMove, boardGame.getBoardScore()));
                        }
                        //Verify if position front from actual board position is neither black, red or forbidden
                        if(contentFrontCell != CellType.FORBIDDEN.getValue() && contentFrontCell != CellType.BLACK.getValue() && contentFrontCell != CellType.RED.getValue()) {
                            BoardTools.moveToFront(boardCopy, i, j);
                            newMove = BoardTools.positionValue(boardCopy, i, j) + BoardTools.positionValue(boardCopy,i + 1, j);
                            boardGame.getChilds().add(new BoardGame(boardCopy, boardGame.isBlack(), boardGame.getTreeDepth() + 1, newMove, boardGame.getBoardScore()));
                        }
                    }
                }
            }
        }
    }

    /**
     *
     * @param board Actual playing board
     * @return copy of the actual board
     */
    static int[][] cloneBoard(int[][] board) {
        //TODO Are we well cloning the board??
        int[][] copy = new int[board.length][board[0].length];
        for(int i = 0; i < board.length; i++) {
            System.arraycopy(board[i], 0, copy[i], 0, board[0].length);
        }
        return copy;
    }
}