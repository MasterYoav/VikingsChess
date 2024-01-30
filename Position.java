public class Position {
    private int row;
    private int col;

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    @Override
    public String toString() {
        // Convert row and column indices to chessboard coordinates (e.g., "A1", "B2")
        char colChar = (char) ('A' + col);
        return colChar + Integer.toString(row + 1);
    }
}
