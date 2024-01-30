import org.junit.jupiter.api.parallel.Resources;

public class GameLogic implements PlayableLogic {
    private ConcretePlayer Defender;
    private ConcretePlayer Attacker;
    private boolean gameFinished =false;
    private static final int[][] startboard = {{0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0}, {0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {1, 0, 0, 0, 0, 2, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 2, 2, 2, 0, 0, 0, 1}, {1, 1, 0, 2, 2, 3, 2, 2, 0, 1, 1}, {1, 0, 0, 0, 2, 2, 2, 0, 0, 0, 1}, {1, 0, 0, 0, 0, 2, 0, 0, 0, 0, 1}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0}, {0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0}};
    private static ConcretePiece[][] board = new ConcretePiece[11][11];
    private static boolean secondPlayerTurn;
    public GameLogic(int size) {
        Attacker = new ConcretePlayer(false);
        Defender = new ConcretePlayer(true);
        reset();

    }


    @Override
    public boolean move(Position a, Position b) {
        ConcretePiece piece = board[a.getRow()][a.getCol()];
        if(piece.getType().equals("♙")&& b.isCorner()){return false;}
        if (piece != null&& piece.getOwner().isPlayerOne()!=secondPlayerTurn) {
            int rowDiff = Math.abs(b.getRow() - a.getRow());
            int colDiff = Math.abs(b.getCol() - a.getCol());

            // Check if the movement is either vertical or horizontal
            if ((rowDiff > 0 && colDiff == 0) || (rowDiff == 0 && colDiff > 0)) {
                // Check for any pieces in the movement path
                int rowDirection = Integer.compare(b.getRow(), a.getRow());
                int colDirection = Integer.compare(b.getCol(), a.getCol());

                int currentRow = a.getRow();
                int currentCol = a.getCol();

                while (currentRow != b.getRow() || currentCol != b.getCol()) {
                    currentRow += rowDirection;
                    currentCol += colDirection;

                    if (board[currentRow][currentCol] != null) {
                        // There's a piece in the movement path, invalid move
                        return false;
                    }
                }

                // If the movement path is clear, update the board
                board[b.getRow()][b.getCol()] = piece;
                System.out.println(a.toString()+" "+b.toString());
                isGameFinished();
                board[a.getRow()][a.getCol()] = null;
                secondPlayerTurn=!secondPlayerTurn;
                return true;
            }
        }


        return false;
    }
    
    // Helper method to check if the path is clear for the pawn's move
    private boolean isPathClear(Position a, Position b) {
        int rowDiff = b.getRow() - a.getRow();
        int colDiff = b.getCol() - a.getCol();

        int rowDirection = Integer.compare(rowDiff, 0);
        int colDirection = Integer.compare(colDiff, 0);

        int currentRow = a.getRow() + rowDirection;
        int currentCol = a.getCol() + colDirection;

        while (currentRow != b.getRow() || currentCol != b.getCol()) {
            if (board[currentRow][currentCol] != null) {
                // There is a piece in the path
                return false;
            }

            currentRow += rowDirection;
            currentCol += colDirection;
        }

        return true;
    }



    @Override
    public Piece getPieceAtPosition(Position position) {
        if(position==null){return null;} 
        return board[position.getRow()][position.getCol()];
    }

    @Override
    public Player getFirstPlayer() {
//        if (Attacker == null) {
//            Attacker = new ConcretePlayer(false);
//        }
        return Defender;

    }

    @Override
    public Player getSecondPlayer() {
//            if (Defender == null) {
//                Defender = new ConcretePlayer(false);
//            }
            return Attacker;
        }

    @Override
    public boolean isGameFinished() {
        return gameFinished;
    }

    @Override
    public boolean isSecondPlayerTurn() {
        return secondPlayerTurn;
    }


    @Override
    public void reset() {
        GameLogic.secondPlayerTurn=true;
        Player Def = new ConcretePlayer(true);
        Player Atk = new ConcretePlayer(false);
    for (int j=0;j<11;j++){
        for (int i=0;i<11;i++) {
            ConcretePiece p = null;
            if (startboard[i][j] == 1) {
                p = new Pawn(Atk);
            }
            if (startboard[i][j] == 2) {
                p = new Pawn(Def);
            }
            if (startboard[i][j] == 3) {
                p = new King(Def);
            }
            board[i][j] = p;
        }
    }}

    @Override
    public void undoLastMove() {

    }

    @Override
    public int getBoardSize() {
        return 11;
    }
}

