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

    public static Position getUp (Position pivot){
        if (pivot==null){return null;}
        if (pivot.getCol()==0){return null;}
        return new Position(pivot.row,pivot.col-1);
    }

    public static Position getDown (Position pivot){
        if (pivot==null){return null;}
        if (pivot.getCol()==10){return null;}
        return new Position(pivot.row,pivot.col+1);
    }
    public static Position getLeft (Position pivot){
        if (pivot==null){return null;}
        if (pivot.getRow()==0){return null;}
        return new Position(pivot.row-1,pivot.col);
    }
    public static Position getRight (Position pivot){
        if (pivot==null){return null;}
        if (pivot.getRow()==10){return null;}
        return new Position(pivot.row+1,pivot.col);
    }
    public boolean isSide(){
        return this.row==0||this.row==10||this.col==0||this.col==10;
    }
    public boolean isLeftSide(){
        return this.row == 0 || (this.row == 1 && this.col == 0) || (this.row == 1 && this.col == 10);
    }
    public boolean isRightSide(){
        return this.row == 10 || (this.row == 9 && this.col == 0) || (this.row == 9 && this.col == 10);
    }
    public boolean isUpSide(){
        return this.col == 0 || (this.col == 1 && this.row == 0) || (this.col == 1 && this.row == 10);
    }
    public boolean isDownSide(){
        return this.col == 10 || (this.col == 9 && this.row == 0) || (this.col == 9 && this.row == 10);
    }
    public boolean isCorner(){
        if(this.col == 0 && this.row == 0){return true;}
        if(this.col == 0 && this.row == 10){return true;}
        if(this.col == 10 && this.row == 0){return true;}
        if(this.col == 10 && this.row == 10){return true;}
        return false;
    }

    @Override
    public String toString() {
        return (Integer.toString(row)+" "+Integer.toString(col));
    }
}
