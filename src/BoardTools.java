public class BoardTools {

    public static String positionValue(int xPosition, int yPosition) {

        StringBuilder value = new StringBuilder();
        switch (xPosition) {
            case 0 -> value.append("A");
            case 1 -> value.append("B");
            case 2 -> value.append("C");
            case 3 -> value.append("D");
            case 4 -> value.append("E");
            case 5 -> value.append("F");
            case 6 -> value.append("G");
            case 7 -> value.append("H");
        }
        value.append(yPosition);
        return value.toString();
    }

    public static int getLeftDiagonalCellPosition(int[][] board, int xPosition, int yPosition) {
        int position = CellType.FORBIDDEN.getValue();
        if(board[xPosition][yPosition] == CellType.RED.getValue() && xPosition > 0 && yPosition < board.length - 1) {
            position = board[xPosition - 1][yPosition + 1];
        }
        else if(board[xPosition][yPosition] == CellType.BLACK.getValue() && xPosition < board.length - 1 && yPosition > 0) {
            position = board[xPosition + 1][yPosition - 1];
        }
        return position;
    }

    public static void moveToLeftDiagonal(int[][] board, int xPosition, int yPosition){

        if(board[xPosition][yPosition] == CellType.RED.getValue()){

            board[xPosition - 1][yPosition + 1] = board[xPosition][yPosition];
            board[xPosition][yPosition] = CellType.EMPTY.getValue();
        }

        else if(board[xPosition][yPosition] == CellType.BLACK.getValue()){

            board[xPosition + 1][yPosition - 1] = board[xPosition][yPosition];
            board[xPosition][yPosition] = CellType.EMPTY.getValue();
        }
    }

    public static int getRightDiagonalCellPosition(int[][] board, int xPosition, int yPosition) {
        int position = CellType.FORBIDDEN.getValue();
        if(board[xPosition][yPosition] == CellType.RED.getValue() && xPosition < board.length - 1 && yPosition < board.length - 1) {
            position = board[xPosition + 1][yPosition + 1];
        }
        else if(board[xPosition][yPosition] == CellType.BLACK.getValue() && xPosition > 0 && yPosition > 0) {
            position = board[xPosition - 1][yPosition - 1];
        }
        return position;
    }

    public static void moveToRightDiagonal(int[][] board, int xPosition, int yPosition){

        if(board[xPosition][yPosition] == CellType.RED.getValue()){

            board[xPosition + 1][yPosition + 1] = board[xPosition][yPosition];
            board[xPosition][yPosition] = CellType.EMPTY.getValue();
        }

        else if(board[xPosition][yPosition] == CellType.BLACK.getValue()){

            board[xPosition - 1][yPosition - 1] = board[xPosition][yPosition];
            board[xPosition][yPosition] = CellType.EMPTY.getValue();
        }
    }

    public static int getFrontCellPosition(int[][] board, int xPosition, int yPosition) {
        int position = CellType.FORBIDDEN.getValue();
        if(board[xPosition][yPosition] == CellType.RED.getValue() && yPosition < board.length - 1) {
            position = board[xPosition][yPosition + 1];
        }
        else if(board[xPosition][yPosition] == CellType.BLACK.getValue() && yPosition > 0) {
            position = board[xPosition][yPosition - 1];
        }
        return position;
    }

    public static void moveToFront(int[][] board, int xPosition, int yPosition){

        if(board[xPosition][yPosition] == CellType.RED.getValue()){

            board[xPosition][yPosition + 1] = board[xPosition][yPosition];
            board[xPosition][yPosition] = CellType.EMPTY.getValue();
        }

        else if(board[xPosition][yPosition] == CellType.BLACK.getValue()){

            board[xPosition][yPosition - 1] = board[xPosition][yPosition];
            board[xPosition][yPosition] = CellType.EMPTY.getValue();
        }
    }
}
