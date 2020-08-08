public class BoardTools {

    public static String positionValue(int[][] board, int xPosition, int yPosition) {

        StringBuilder value = new StringBuilder();
        switch (yPosition) {
            case 0 -> value.append("A");
            case 1 -> value.append("B");
            case 2 -> value.append("C");
            case 3 -> value.append("D");
            case 4 -> value.append("E");
            case 5 -> value.append("F");
            case 6 -> value.append("G");
            case 7 -> value.append("H");
        }
        value.append(board.length - xPosition);
        return value.toString();
    }

    public static int getLeftDiagonalPosition(int[][] board, int xPosition, int yPosition) {
        int position = CellType.FORBIDDEN.getValue();

        if(yPosition < board.length - 1) {
            if (board[xPosition][yPosition] == CellType.BLACK.getValue() && xPosition < board.length - 1)
                position = board[xPosition + 1][yPosition + 1];
        }else if(yPosition > 0){
            if(board[xPosition][yPosition] == CellType.RED.getValue() && xPosition > 0)
                position = board[xPosition - 1][yPosition - 1];
        }
        return position;
    }

    public static void moveToLeftDiagonal(int[][] board, int xPosition, int yPosition){

        if(yPosition < board.length - 1) {

            if (board[xPosition][yPosition] == CellType.BLACK.getValue() && xPosition < board.length - 1) {

                board[xPosition + 1][yPosition + 1] = board[xPosition][yPosition];
                board[xPosition][yPosition] = CellType.EMPTY.getValue();
            }
        }else if(yPosition > 0){
            if(board[xPosition][yPosition] == CellType.RED.getValue() && xPosition > 0){

                board[xPosition-1][yPosition-1] = board[xPosition][yPosition];
                board[xPosition][yPosition] = CellType.EMPTY.getValue();
            }
        }
    }

    public static int getRightDiagonalCellPosition(int[][] board, int xPosition, int yPosition) {
        int position = CellType.FORBIDDEN.getValue();

        if(yPosition > 0){

            if(board[xPosition][yPosition] == CellType.BLACK.getValue() && xPosition < board.length - 1)
                position = board[xPosition + 1][yPosition - 1];
        }else if(yPosition < board.length - 1) {
            if (board[xPosition][yPosition] == CellType.RED.getValue() && xPosition > 0)
                position = board[xPosition - 1][yPosition + 1];
        }
        return position;
    }

    public static void moveToRightDiagonal(int[][] board, int xPosition, int yPosition){

        if(yPosition > 0){

            if(board[xPosition][yPosition] == CellType.BLACK.getValue() && xPosition < board.length - 1){

                board[xPosition+1][yPosition-1] = board[xPosition][yPosition];
                board[xPosition][yPosition] = CellType.EMPTY.getValue();
            }
        }else if(yPosition < board.length - 1) {
            if(board[xPosition][yPosition] == CellType.RED.getValue() && xPosition > 0){

                board[xPosition-1][yPosition+1] = board[xPosition][yPosition];
                board[xPosition][yPosition] = CellType.EMPTY.getValue();
            }
        }
    }

    public static int getFrontCellPosition(int[][] board, int xPosition, int yPosition) {
        int position = CellType.FORBIDDEN.getValue();
        if(board[xPosition][yPosition] == CellType.BLACK.getValue() && xPosition < board.length - 1)
            position = board[xPosition+1][yPosition];

        else if(board[xPosition][yPosition] == CellType.RED.getValue() && xPosition > 0)
            position = board[xPosition-1][yPosition];
        return position;
    }

    public static void moveToFront(int[][] board, int xPosition, int yPosition){

        if(board[xPosition][yPosition] == CellType.BLACK.getValue() && xPosition < board.length - 1){

            board[xPosition+1][yPosition] = board[xPosition][yPosition];
            board[xPosition][yPosition] = CellType.EMPTY.getValue();
        }

        else if(board[xPosition][yPosition] == CellType.RED.getValue() && xPosition > 0){

            board[xPosition-1][yPosition] = board[xPosition][yPosition];
            board[xPosition][yPosition] = CellType.EMPTY.getValue();
        }
    }

    public static int getDownLeftDiagonalCellPosition(int[][] boardState, int xPosition, int yPosition){

        int position = CellType.FORBIDDEN.getValue();

        if(yPosition > 0){

            if(boardState[xPosition][yPosition] == CellType.BLACK.getValue() && xPosition > 0)
                position = boardState[xPosition - 1][yPosition - 1];

            else if(boardState[xPosition][yPosition] == CellType.RED.getValue() && xPosition < boardState.length - 1)
                position = boardState[xPosition + 1][yPosition - 1];
        }

        return position;
    }

    public static int getDownRightDiagonalCellPosition(int[][] boardState, int xPosition, int yPosition){

        int position = CellType.FORBIDDEN.getValue();

        if(yPosition < boardState.length - 1){

            if(boardState[xPosition][yPosition] == CellType.BLACK.getValue() && xPosition > 0)
                position = boardState[xPosition - 1][yPosition + 1];

            else if(boardState[xPosition][yPosition] == CellType.RED.getValue() && xPosition < boardState.length - 1)
                position = boardState[xPosition + 1][yPosition + 1];
        }

        return position;
    }
}
