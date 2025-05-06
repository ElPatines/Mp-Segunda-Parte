package GameManager;

import java.util.ArrayList;

import CharacterManager.GameCharacter;

public class Player extends User {
    private static final long serialVersionUID = 1L;  

    private GameCharacter character = null;
    private ArrayList<Challenge> mail = new ArrayList<Challenge>();
    private ArrayList<Battle> battleHistory = new ArrayList<Battle>();
    private int money = 100;
    private boolean blocked = false;
    private String registrationNumber;

    public Player(String name, String password, String code, Profile profile) {
        super(name, password, profile);
        this.registrationNumber = code;
    }


    public Player() {
        super();
    }

    public void setMail(ArrayList<Challenge> mail) {
        this.mail = mail;
    }

    public void setBattleHistory(ArrayList<Battle> battleHistory) {
        this.battleHistory = battleHistory;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public boolean isBlocked() {
        return blocked;
    }


    public void addToMail(Challenge challenge) {
        if (mail == null) {
            mail = new ArrayList<Challenge>();
        }
        mail.add(challenge);
    }

    public boolean getBlocked() {
        return blocked;
    }


    public String getRegistrationNumber() {
        return registrationNumber;
    }


    public ArrayList<Challenge> getMail() {
        return mail;
    }

    public void blockPlayer() {
        blocked = true;
    }


    public void unblockPlayer() {
        blocked = false;
    }

    public GameCharacter getCharacter() {
        return character;
    }


    public int getMoney() {
        return money;
    }


    public ArrayList<Battle> getHistory() {
        return battleHistory;
    }


    public void removeFromMail(Challenge challenge) {
        if (mail != null && mail.contains(challenge)) {
            mail.remove(challenge);
        }
    }


    public void addToHistory(Battle battle) {
        if (battleHistory == null) {
            battleHistory = new ArrayList<Battle>();
        }
        battleHistory.add(battle);
    }

    public void setCharacter(GameCharacter character) {
        this.character = character;
    }


    public void loseMoney(int m) {
        money -= m;
    }


    public void earnMoney(int m) {
        money += m;
    }
}

