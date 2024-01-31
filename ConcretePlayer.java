public class ConcretePlayer implements Player{
    private boolean Player;
    private int wins;
    public ConcretePlayer(boolean player){
        this.Player= player;
        wins =0;
    }
    @Override
    public boolean isPlayerOne() {
        return Player;
    }

    @Override
    public int getWins() {
        return wins;
    }
    public void win(){
        this.wins++;
    }
}
