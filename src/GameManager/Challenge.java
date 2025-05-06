package GameManager;
import java.io.Serializable;
public class Challenge implements Serializable {
    private static final long serialVersionUID = 1L;  // Versión de serialización
    private Admin admin;
    private Player challenger;
    private Player challenged;
    private int bet;

    public Challenge(Player challenger, Player challenged, Admin admin, int bet){
        this.challenger = challenger;
        this.challenged = challenged;
        this.admin = admin;
        this.bet = bet;
    }

    public void sendChallengeToAdmin(){
        admin.receiveChallenge(this);
    }

    public Admin getAdmin() {
        return admin;
    }

    public Player getChallenger() {
        return challenger;
    }

    public Player getChallenged() {
        return challenged;
    }

    public int getBet() {
        return bet;
    }
}