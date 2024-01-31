import java.util.Comparator;

public class killSort implements Comparator<Pawn> {
    @Override
    public int compare(Pawn o1, Pawn o2) {
        if(o1.getKills() == o2.getKills()){
            if(o1.getCountId() == o2.getCountId()){
                if((!o1.getOwner().isPlayerOne() && GameLogic.Player2Won()) || (o1.getOwner().isPlayerOne() && !GameLogic.Player2Won())){
                    return -1;
                }else{
                    return 1;
                }
            }
            return o1.getCountId() - o2.getCountId();
        }
        return o2.getKills() - o1.getKills();
    }

}