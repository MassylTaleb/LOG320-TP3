import java.io.*;
import java.net.Socket;

class Client {

    private static boolean isBlack;

    public static void main(String[] args) {

        Socket MyClient;
        BufferedInputStream input;
        BufferedOutputStream output;
        int[][] board = new int[8][8];

        try {
            MyClient = new Socket("localhost", 8888);

            input    = new BufferedInputStream(MyClient.getInputStream());
            output   = new BufferedOutputStream(MyClient.getOutputStream());
            BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
            while(true){
                char cmd = 0;

                cmd = (char)input.read();
                System.out.println(cmd);

                // Debut de la partie en joueur rouge
                if(cmd == '1') {
                    byte[] aBuffer = new byte[1024];

                    int size = input.available();
                    input.read(aBuffer,0, size);
                    String s = new String(aBuffer).trim();
                    String[] boardValues = s.split(" ");

                    int x = 0, y = 0;
                    for (String boardValue : boardValues) {
                        board[x][y] = Integer.parseInt(boardValue);
                        System.out.printf("%d", board[x][y]);
                        y++;
                        if (y == 8) {
                            y = 0;
                            x++;
                            System.out.print("\n");
                        }
                    }

                    Timer.resetTimer();
                    Timer.startTimer();

                    isBlack = false;
                    BoardGame boardGame = new BoardGame(board, isBlack, 0, null, 0);
                    Algorithm.minimax(boardGame, true);
                    System.out.println("Nouvelle partie! Vous jouer rouge, entrez votre premier coup : ");
                    BoardGame bestMove = null;

                    // We're trying to find the best move through its highest score
                    for(BoardGame child : boardGame.getChilds()) {
                        if(bestMove == null)
                            bestMove = child;
                        else
                            if(bestMove.getMinMaxScore() < child.getMinMaxScore())
                                bestMove = child;
                    }

                    board = bestMove.getBoard();
                    output.write(bestMove.getMove().getBytes(),0, bestMove.getMove().length());
                    output.flush();
                }

                // Debut de la partie en joueur Noir
                if(cmd == '2'){

                    System.out.println("Nouvelle partie! Vous jouer noir, attendez le coup des rouges");
                    byte[] aBuffer = new byte[1024];

                    int size = input.available();
                    input.read(aBuffer,0, size);
                    String s = new String(aBuffer).trim();
                    String[] boardValues = s.split(" ");
                    isBlack = true;

                    int x = 0, y = 0;
                    for (String boardValue : boardValues) {
                        board[x][y] = Integer.parseInt(boardValue);
                        System.out.printf("%d", board[x][y]);
                        y++;
                        if (y == 8) {
                            y = 0;
                            x++;
                            System.out.print("\n");
                        }
                    }
                }

                // Le serveur demande le prochain coup
                // Le message contient aussi le dernier coup joue.
                if(cmd == '3'){
                    byte[] aBuffer = new byte[16];
                    int size = input.available();
                    input.read(aBuffer,0, size);

                    String s = new String(aBuffer);
                    System.out.println("Dernier coup :" + s);
                    System.out.println("Entrez votre coup : ");

                    BoardTools.buildNewBoardWithMove(s, board);

                    Timer.resetTimer();
                    Timer.startTimer();
                    BoardGame boardGame = new BoardGame(board, isBlack, 0, null, 0);
                    Algorithm.minimax(boardGame, true);
                    BoardGame bestMove = null;

                    for(BoardGame child : boardGame.getChilds()) {
                        if(bestMove == null)
                            bestMove = child;
                        else
                            if(bestMove.getMinMaxScore() < child.getMinMaxScore())
                                bestMove = child;
                    }

                    board = bestMove.getBoard();
                    System.out.println(bestMove.getMove());
                    System.out.println("Elapsed Time: " + Timer.getTimeElapsed());
                    output.write(bestMove.getMove().getBytes(),0, bestMove.getMove().length());
                    output.flush();
                }

                // Manage when the last play was an error
                if(cmd == '4') {
                    System.out.println("Coup invalide, entrez un nouveau coup : ");
                    String lastMove = null;
                    lastMove = console.readLine();
                    output.write(lastMove.getBytes(),0,lastMove.length());
                    output.flush();
                }

                // The game is over
                if(cmd == '5') {
                    byte[] aBuffer = new byte[16];
                    int size = input.available();
                    input.read(aBuffer,0, size);
                    String s = new String(aBuffer);
                    System.out.println("Partie Terminé. Le dernier coup joué est: " + s);
                    return;
                }
            }
        }
        catch (IOException e) {
            System.out.println(e);
        }

    }
}