import java.util.ArrayList;

public abstract class ConcretePiece implements Piece {
    private ArrayList<Position> moves = new ArrayList<Position>();
    private Player Owner;
    private static int countP1 =1;
    private static int countP2 =1;
    private int countId;
    private String id;
    private int distance;
    public ConcretePiece(Player p){
        this.Owner = p;
        if(p.isPlayerOne()){
            if(countP1 == 7){
                this.id = "K" + countP1;
                this.countId = countP1;
            }else{
                this.id = "D" + countP1;
                this.countId = countP1;
            }
            countP1++;
        }else{
            this.id = "A" + countP2;
            this.countId = countP2;
            countP2++;
        }
    }
    public void addMove(Position pos){
        moves.add(pos);
    }
    public void removeMove(){
        if(!moves.isEmpty() && moves.size() > 1){
            moves.remove(moves.size()-1);
        }
    }
    public Position getLastMove(){
        if(!moves.isEmpty()){
            return moves.get(moves.size()-1);
        }
        return null;
    }
    public int getMoveSize(){
        return moves.size();
    }
    public String printMove() {
        if(moves == null){return "";}
        String s = "";
        for (int i = 0; i < moves.size(); i++) {
            if(i == 0){
                s = s  + moves.get(i).toString();
            }else{
                s = s + ", " + moves.get(i).toString();
            }
        }
        return s;
    }
    public int getDistance(){
        return this.distance;
    }
    public void addDistanceCount(int num){
        this.distance = this.distance + num;
    }
    @Override
    public Player getOwner() {
        return Owner;
    }

    @Override
    abstract public String  getType() ;

        // Add other methods specific to piece behavior if needed
    }



