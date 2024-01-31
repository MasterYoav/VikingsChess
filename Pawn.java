public class Pawn extends ConcretePiece{
    private int kills;

    public Pawn(Player owner) {
        super(owner);
        kills=0;
    }
    public String getType(){return "♙";}
    public void addKill(int num){
        this.kills = num;
    }
    public void setKill(int num){
        this.kills = num;
    }
}
