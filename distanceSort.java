import java.util.Comparator;

public class distanceSort implements Comparator<ConcretePiece> {

    @Override
    public int compare(ConcretePiece o1, ConcretePiece o2) {
        if(o1.getDistance() == o2.getDistance()){
            if(o1.getCountId() == o2.getCountId()){
                if((!o1.getOwner().isPlayerOne() && GameLogic.Player2Won()) || (o1.getOwner().isPlayerOne() && !GameLogic.Player2Won())){
                    return -1;
                }else{
                    return 1;
                }
            }
            return o1.getCountId() - o2.getCountId();
        }
        return o2.getDistance() - o1.getDistance();
    }
}