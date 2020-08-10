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

    /**
     * Get the content on the diagonal in the left side of the screen.
     *
     * @param board
     * @param row
     * @param column
     * @return
     */
    public static int getLeftDiagonalPosition(int[][] board, int row, int column) {
        int position = CellType.FORBIDDEN.getValue();

        if(column > 0) {

            if (board[row][column] == CellType.RED.getValue() && row > 0)
                position = board[row - 1][column - 1];

            else if (board[row][column] == CellType.BLACK.getValue() && row < board.length - 1)
                position = board[row + 1][column - 1];
        }
        return position;
    }

    public static void moveToLeftDiagonal(int[][] board, int row, int column){


        if(column > 0) {

            if(board[row][column] == CellType.RED.getValue() && row > 0) {

                board[row - 1][column - 1] = board[row][column];
                board[row][column] = CellType.EMPTY.getValue();
            }

            else if (board[row][column] == CellType.BLACK.getValue() && row < board.length - 1) {

                board[row + 1][column - 1] = board[row][column];
                board[row][column] = CellType.EMPTY.getValue();
            }
        }
    }

    /**
     * Get the content on the diagonal in the right side of the screen.
     *
     * @param board
     * @param row
     * @param column
     * @return
     */
    public static int getRightDiagonalCellPosition(int[][] board, int xPosition, int yPosition) {
        int position = CellType.FORBIDDEN.getValue();

        if(yPosition < board.length - 1){

            if(board[xPosition][yPosition] == CellType.BLACK.getValue() && xPosition < board.length - 1)
                position = board[xPosition + 1][yPosition + 1];

            else if (board[xPosition][yPosition] == CellType.RED.getValue() && xPosition > 0)
                position = board[xPosition - 1][yPosition + 1];
        }
        return position;
    }

    public static void moveToRightDiagonal(int[][] board, int xPosition, int yPosition){

        if(yPosition < board.length - 1){

            if(board[xPosition][yPosition] == CellType.BLACK.getValue() && xPosition < board.length - 1){

                board[xPosition + 1][yPosition + 1] = board[xPosition][yPosition];
                board[xPosition][yPosition] = CellType.EMPTY.getValue();
            }

            else if(board[xPosition][yPosition] == CellType.RED.getValue() && xPosition > 0){

                board[xPosition - 1][yPosition + 1] = board[xPosition][yPosition];
                board[xPosition][yPosition] = CellType.EMPTY.getValue();
            }
        }
    }

    public static int getFrontCellPosition(int[][] board, int xPosition, int yPosition) {
        int position = CellType.FORBIDDEN.getValue();

        if(board[xPosition][yPosition] == CellType.BLACK.getValue() && xPosition < board.length - 1)
            position = board[xPosition + 1][yPosition];

        else if(board[xPosition][yPosition] == CellType.RED.getValue() && xPosition > 0)
            position = board[xPosition - 1][yPosition];

        return position;
    }

    public static void moveToFront(int[][] board, int xPosition, int yPosition){

        if(board[xPosition][yPosition] == CellType.BLACK.getValue() && xPosition < board.length - 1){

            board[xPosition + 1][yPosition] = board[xPosition][yPosition];
            board[xPosition][yPosition] = CellType.EMPTY.getValue();
        }

        else if(board[xPosition][yPosition] == CellType.RED.getValue() && xPosition > 0){

            board[xPosition - 1][yPosition] = board[xPosition][yPosition];
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

    public static void buildNewBoardWithMove(String s, int[][] board)
    {
        int startAndEnd = Integer.parseInt(s.replaceAll("[^0-9]", ""));
        int fromDigit = Integer.parseInt(Integer.toString(startAndEnd).substring(0, 1));
        int toDigit = startAndEnd % 10;
        String fromLetter = s.substring(1, 2);
        String toLetter = s.substring(6, 7);

        int fromLetterDigit = 0;
        switch (fromLetter) {
            case "A":  fromLetterDigit = 0;
                break;
            case "B":  fromLetterDigit = 1;
                break;
            case "C":  fromLetterDigit = 2;
                break;
            case "D":  fromLetterDigit = 3;
                break;
            case "E":  fromLetterDigit = 4;
                break;
            case "F":  fromLetterDigit = 5;
                break;
            case "G":  fromLetterDigit = 6;
                break;
            case "H":  fromLetterDigit = 7;
                break;
        }

        int toLetterDigit = 0;
        switch (toLetter) {
            case "A":  toLetterDigit = 0;
                break;
            case "B":  toLetterDigit = 1;
                break;
            case "C":  toLetterDigit = 2;
                break;
            case "D":  toLetterDigit = 3;
                break;
            case "E":  toLetterDigit = 4;
                break;
            case "F":  toLetterDigit = 5;
                break;
            case "G":  toLetterDigit = 6;
                break;
            case "H":  toLetterDigit = 7;
                break;
        }
        board[8-toDigit][toLetterDigit] = board[8-fromDigit][fromLetterDigit];
        board[8-fromDigit][fromLetterDigit] = 0;
    }
}
