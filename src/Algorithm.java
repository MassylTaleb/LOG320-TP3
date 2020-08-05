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

    static void expandChilds(BoardGame boardGame) {

        int depthTree = boardGame.isBlack() ? 1 : 0;
        int[][] boardCopy;
        int contentLeftDiagonalCell;
        int contentRightDiagonalCell;
        int contentFrontCell;
        String newMove;

        for(int i = 0; i < boardGame.getBoard().length; i++) {
            for(int j = 0; j < boardGame.getBoard()[0].length; j++) {

                boardCopy = cloneBoard(boardGame.getBoard());
                contentLeftDiagonalCell = BoardTools.getLeftDiagonalPosition(boardCopy, i, j);
                contentRightDiagonalCell = BoardTools.getRightDiagonalCellPosition(boardCopy, i, j);
                contentFrontCell = BoardTools.getFrontCellPosition(boardCopy, i, j);
                if(boardGame.getTreeDepth() % 2 == depthTree) {

                    if(boardCopy[i][j] == CellType.RED.getValue()) {
                        if(contentLeftDiagonalCell != CellType.FORBIDDEN.getValue() && contentLeftDiagonalCell != CellType.RED.getValue()) {
                            BoardTools.moveToLeftDiagonal(boardCopy, i, j);
                            newMove = BoardTools.positionValue(boardCopy,i, j) + BoardTools.positionValue(boardCopy,i - 1, j - 1);
                            boardGame.getChilds().add(new BoardGame(boardCopy, boardGame.isBlack(), boardGame.getTreeDepth() + 1, newMove, boardGame.getBoardScore()));
                        }

                        if(contentRightDiagonalCell != CellType.FORBIDDEN.getValue() && contentRightDiagonalCell != CellType.RED.getValue()) {
                            BoardTools.moveToRightDiagonal(boardCopy, i, j);
                            newMove = BoardTools.positionValue(boardCopy,i, j) + BoardTools.positionValue(boardCopy,i - 1, j + 1);
                            boardGame.getChilds().add(new BoardGame(boardCopy, boardGame.isBlack(), boardGame.getTreeDepth() + 1, newMove, boardGame.getBoardScore()));
                        }

                        if(contentFrontCell != CellType.FORBIDDEN.getValue() && contentFrontCell != CellType.BLACK.getValue()) {
                            BoardTools.moveToFront(boardCopy, i, j);
                            newMove = BoardTools.positionValue(boardCopy, i, j) + BoardTools.positionValue(boardCopy, i - 1, j);
                            boardGame.getChilds().add(new BoardGame(boardCopy, boardGame.isBlack(), boardGame.getTreeDepth() + 1, newMove, boardGame.getBoardScore()));
                        }
                    }
                } else {

                    if(boardCopy[i][j] == CellType.BLACK.getValue()) {
                        if(contentLeftDiagonalCell != CellType.FORBIDDEN.getValue() && contentLeftDiagonalCell != CellType.BLACK.getValue()) {
                            BoardTools.moveToLeftDiagonal(boardCopy, i, j);
                            newMove = BoardTools.positionValue(boardCopy,i, j) + BoardTools.positionValue(boardCopy, i + 1, j - 1);
                            boardGame.getChilds().add(new BoardGame(boardCopy, boardGame.isBlack(), boardGame.getTreeDepth() + 1, newMove, boardGame.getBoardScore()));
                        }

                        if(contentRightDiagonalCell != CellType.FORBIDDEN.getValue() && contentRightDiagonalCell != CellType.BLACK.getValue()) {
                            BoardTools.moveToRightDiagonal(boardCopy, i, j);
                            newMove = BoardTools.positionValue(boardCopy, i, j) + BoardTools.positionValue(boardCopy,i + 1, j + 1);
                            boardGame.getChilds().add(new BoardGame(boardCopy, boardGame.isBlack(), boardGame.getTreeDepth() + 1, newMove, boardGame.getBoardScore()));
                        }

                        if(contentFrontCell != CellType.FORBIDDEN.getValue() && contentFrontCell != CellType.RED.getValue()) {
                            BoardTools.moveToFront(boardCopy, i, j);
                            newMove = BoardTools.positionValue(boardCopy, i, j) + BoardTools.positionValue(boardCopy,i + 1, j);
                            boardGame.getChilds().add(new BoardGame(boardCopy, boardGame.isBlack(), boardGame.getTreeDepth() + 1, newMove, boardGame.getBoardScore()));
                        }
                    }
                }
            }
        }
    }

    static int[][] cloneBoard(int[][] board) {
        int[][] copy = new int[board.length][board[0].length];
        for(int i = 0; i < board.length; i++) {
            System.arraycopy(board[i], 0, copy[i], 0, board[0].length);
        }
        return copy;
    }
}