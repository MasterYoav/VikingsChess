import org.junit.jupiter.api.parallel.Resources;

import java.util.ArrayList;
import java.util.Stack;

public class GameLogic implements PlayableLogic {
    private ConcretePlayer Defender;
    private ConcretePlayer Attacker;
    private static boolean gameFinished =false;
    private static final int[][] startboard = {{0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0}, {0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {1, 0, 0, 0, 0, 2, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 2, 2, 2, 0, 0, 0, 1}, {1, 1, 0, 2, 2, 3, 2, 2, 0, 1, 1}, {1, 0, 0, 0, 2, 2, 2, 0, 0, 0, 1}, {1, 0, 0, 0, 0, 2, 0, 0, 0, 0, 1}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0}, {0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0}};
    private static ConcretePiece[][] board = new ConcretePiece[11][11];
    private static boolean secondPlayerTurn;
    private static Stack<ConcretePiece[][]> BoardHistory = new Stack<ConcretePiece[][]>();
    private static Stack<ConcretePiece> lastPieceMoved = new Stack<ConcretePiece>();
    private static ArrayList<ConcretePiece> p1Pieces = new ArrayList<ConcretePiece>();
    private static ArrayList<ConcretePiece> p2Pieces = new ArrayList<ConcretePiece>();
    private static ArrayList<ConcretePiece> allPieces = new ArrayList<ConcretePiece>();
    private static ArrayList<Pawn> allPawns = new ArrayList<Pawn>();
    private static ArrayList<ConcretePiece>[][] allPiecesAtPosition = new ArrayList[11][11];

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
                System.out.println("start position ("+a.toString()+")  end position ("+b.toString() +")");
                board[b.getRow()][b.getCol()] = piece;
                board[a.getRow()][a.getCol()] = null;
                eatPiece(b);
                piece.addMove(b);
                piece.addDistanceCount(a.distance(b));
                allPiecesAtPosition[b.getRow()][b.getCol()].add(piece);
                ConcretePiece[][] currentBoard = copyBoard();
                GameLogic.BoardHistory.push(currentBoard);
                lastPieceMoved.push(piece);
                isGameFinished();
                secondPlayerTurn=!secondPlayerTurn;
                if(piece.getType().equals("♔")&&b.isCorner()){
                    gameFinished=true;
                    Defender.win();
                }
                return true;
            }
        }


        return false;
    }
    private void eatPiece(Position pivot){
        if (pivot==null){return;}
        ConcretePiece pivotP=(ConcretePiece) getPieceAtPosition(pivot);
        if (pivotP==null){return;}
        if(pivotP.getType().equals("♔")){return;} // king cant eat
        Position left =Position.getLeft(pivot);
        Position right =Position.getRight(pivot);
        Position up=Position.getUp(pivot);
        Position down=Position.getDown(pivot);

        //checking the 4 possible affected ConcretePices
        //Left check
        if(left!=null){
            ConcretePiece leftP=(ConcretePiece)getPieceAtPosition(left);
            if(leftP!=null){
                if (leftP.getType().equals("♔")){//checking if our move killed the king
                    if(isKingDead(left)){
                        GameLogic.gameFinished=true;
                    }
                }
                if(left.isLeftSide()&&!leftP.getType().equals("♔")){//checking if our move kill a pawn next to the left wall
                    if(leftP.getOwner()!= pivotP.getOwner()){
                        board[left.getRow()][left.getCol()] = null;
                    }
                }
                else {//we need to check the left side of "left"
                    Position leftOFleft=Position.getLeft(left);
                    if (leftOFleft!=null){
                        ConcretePiece leftOFleftP=(ConcretePiece)getPieceAtPosition(leftOFleft);
                        if(leftOFleftP!=null && !leftP.getType().equals("♔")){
                            if (!leftOFleftP.getType().equals("♔")) {
                                if (leftOFleftP.getOwner() == pivotP.getOwner() && pivotP.getOwner() != leftP.getOwner()) {
                                    board[left.getRow()][left.getCol()] = null;
                                }
                            }
                        }
                    }
                }
            }
        }
        //Right check
        if(right!=null){
            ConcretePiece rightP=(ConcretePiece)getPieceAtPosition(right);
            if(rightP!=null){
                if (rightP.getType().equals("♔")){//checking if our move killed the king
                    if(isKingDead(right)){
                        GameLogic.gameFinished=true;
                    }
                }
                if(right.isRightSide()&&!rightP.getType().equals("♔")){//checking if our move kill a pawn next to the left wall
                    if(rightP.getOwner()!= pivotP.getOwner()){
                        board[right.getRow()][right.getCol()] = null;
                    }
                }
                else {//we need to check the right side of "right"
                    Position rightOFright=Position.getRight(right);
                    if (rightOFright!=null){
                        ConcretePiece rightOFrightP=(ConcretePiece)getPieceAtPosition(rightOFright);
                        if(rightOFrightP!=null&& !rightP.getType().equals("♔")){
                            if(!rightOFrightP.getType().equals("♔")) {
                                if (rightOFrightP.getOwner() == pivotP.getOwner() && pivotP.getOwner() != rightP.getOwner()) {
                                    board[right.getRow()][right.getCol()] = null;
                                }
                            }
                        }
                    }
                }
            }
        }

        //Down check
        if(down!=null){
            ConcretePiece downP=(ConcretePiece)getPieceAtPosition(down);
            if(downP!=null){
                if (downP.getType().equals("♔")){//checking if our move killed the king
                    if(isKingDead(down)){
                        GameLogic.gameFinished=true;
                    }
                }
                if(down.isDownSide()&&!downP.getType().equals("♔")){//checking if our move kill a pawn next to the left wall
                    if(downP.getOwner()!= pivotP.getOwner()){
                        board[down.getRow()][down.getCol()] = null;
                    }
                }
                else {//we need to check the down side of "down"
                    Position downOFdown=Position.getDown(down);
                    if (downOFdown!=null){
                        ConcretePiece downOFdownP=(ConcretePiece)getPieceAtPosition(downOFdown);
                        if(downOFdownP!=null && !downP.getType().equals("♔")){
                            if(!downOFdownP.getType().equals("♔")){
                                if(downOFdownP.getOwner()==pivotP.getOwner()&&pivotP.getOwner()!=downP.getOwner()){
                                    board[down.getRow()][down.getCol()] = null;
                                }
                            }
                        }
                    }
                }
            }
        }
        //Up check
        if(up!=null){
            ConcretePiece upP=(ConcretePiece)getPieceAtPosition(up);
            if(upP!=null){
                if (upP.getType().equals("♔")){//checking if our move killed the king
                    if(isKingDead(up)){
                        GameLogic.gameFinished=true;
                    }
                }
                if(up.isUpSide()&&!upP.getType().equals("♔")){//checking if our move kill a pawn next to the left wall
                    if(upP.getOwner()!= pivotP.getOwner()){
                        board[up.getRow()][up.getCol()] = null;
                    }
                }
                else {//we need to check the up side of "up"
                    Position upOFup=Position.getUp(up);
                    if (upOFup!=null){
                        ConcretePiece upOFupP=(ConcretePiece)getPieceAtPosition(upOFup);
                        if(upOFupP!=null && !upP.getType().equals("♔")){
                            if(!upOFupP.getType().equals("♔")){
                                if(upOFupP.getOwner()==pivotP.getOwner()&&pivotP.getOwner()!=upP.getOwner()){
                                    board[up.getRow()][up.getCol()] = null;
                                }
                            }
                        }
                    }
                }
            }
        }


    }

    private boolean isKingDead(Position pivot) {
        Player p = getPieceAtPosition(pivot).getOwner();
        Position left = Position.getLeft(pivot);
        Position right = Position.getRight(pivot);
        Position up = Position.getUp(pivot);
        Position down = Position.getDown(pivot);
        int countPiece = 0;
        if(left != null && getPieceAtPosition(left) != null && getPieceAtPosition(left).getOwner() != p){
            countPiece++;
        }
        if(right != null && getPieceAtPosition(right) != null && getPieceAtPosition(right).getOwner() != p){
            countPiece++;
        }
        if(up != null && getPieceAtPosition(up) != null && getPieceAtPosition(up).getOwner() != p){
            countPiece++;
        }
        if(down != null && getPieceAtPosition(down) != null && getPieceAtPosition(down).getOwner() != p){
            countPiece++;
        }
        if((pivot.isSide() && countPiece == 3) || countPiece == 4){
            Attacker.win();
            return true;
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
    public boolean isSecondPlayerTurn() {
        return secondPlayerTurn;
    }
    public boolean isGameFinished(){
        return GameLogic.gameFinished;
    }


    @Override
    public void reset() {
        GameLogic.secondPlayerTurn=true;
        GameLogic.gameFinished=false;
        Player Def = new ConcretePlayer(true);
        Player Atk = new ConcretePlayer(false);
    for (int j=0;j<11;j++){
        for (int i=0;i<11;i++) {
            ConcretePiece p = null;
            if (startboard[i][j] == 1) {
                p = new Pawn(Atk);
                Position pos = new Position(i,j);
                p.addMove(pos);
                p2Pieces.add(p);
                allPawns.add((Pawn) p);
                allPieces.add(p);
            }
            if (startboard[i][j] == 2) {
                p = new Pawn(Def);
                Position pos = new Position(i,j);
                p.addMove(pos);
                p1Pieces.add(p);
                allPawns.add((Pawn) p);
                allPieces.add(p);
            }
            if (startboard[i][j] == 3) {
                p = new King(Def);
                Position pos = new Position(i,j);
                p.addMove(pos);
                p1Pieces.add(p);
                allPieces.add(p);
            }
            board[i][j] = p;
            allPiecesAtPosition[i][j] = new ArrayList<ConcretePiece>();
            if(p != null) {
                allPiecesAtPosition[i][j].add(p);
            }
        }
        ConcretePiece[][] currentBoard = copyBoard();
        GameLogic.BoardHistory.push(currentBoard);
    }}
    private void switchBoard(ConcretePiece[][] currentB) {
        for (int row = 0; row < 11; row++) {
            for (int col = 0; col < 11; col++) {
                board[row][col] = currentB[row][col];
            }
        }
    }
    private ConcretePiece[][] copyBoard() {
        ConcretePiece[][] ans = new ConcretePiece[11][11];
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                ans[i][j] = board[i][j];
            }
        }
        return ans;
    }
    @Override
    public void undoLastMove() {
        if(GameLogic.BoardHistory != null && GameLogic.BoardHistory.size() > 1){
            ConcretePiece[][] last = GameLogic.BoardHistory.pop();
            ConcretePiece[][] current = GameLogic.BoardHistory.peek();
            changeStats(current, last);
            switchBoard(current);
            GameLogic.secondPlayerTurn = !GameLogic.secondPlayerTurn;
        }
    }
    private void changeStats(ConcretePiece[][] current, ConcretePiece[][] last) {
        ConcretePiece pieceStatistics = lastPieceMoved.pop();
        Position dest = pieceStatistics.getLastMove();
        Position src = pieceStatistics.getLastMove();
        pieceStatistics.removeMove();

        if(pieceStatistics instanceof Pawn){
            int count = killCount(current, last);
            ((Pawn) pieceStatistics).addKill(-count);
        }

        int distance = src.distance(dest);
        pieceStatistics.addDistanceCount(-distance);

        if(!allPiecesAtPosition[dest.getRow()][dest.getCol()].isEmpty()){
            allPiecesAtPosition[dest.getRow()][dest.getCol()].remove(allPiecesAtPosition[dest.getRow()][dest.getCol()].size()-1);
        }
    }
    private int killCount(ConcretePiece[][] current, ConcretePiece[][] last) {
        int countCurr = 0;
        int countLast = 0;
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                if(current[i][j] != null){countCurr++;}
                if(last[i][j] != null){countLast++;}
            }
        }
        return Math.abs(countCurr - countLast);
    }

    @Override
    public int getBoardSize() {
        return 11;
    }
}

