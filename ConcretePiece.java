public abstract class ConcretePiece implements Piece {
    private Player Owner;
    private static int count =1;
    private int id;
    public ConcretePiece( Player owner){
        this.Owner = owner;
        this.id = count;
        count++;

    }
    @Override
    public Player getOwner() {
        return Owner;
    }

    @Override
    abstract public String  getType() ;

        // Add other methods specific to piece behavior if needed
    }



