package CharacterManager;

import java.util.ArrayList;
import java.util.List;

public class CompositeDemonMinion implements Minion {
    private static final long serialVersionUID = 1L;
    private String name;
    private int health;
    private String pact;
    private List<Minion> minions;

    public CompositeDemonMinion(String name, int health, String pact) {
        this.name = name;
        this.health = health;
        this.pact = pact;
        this.minions = new ArrayList<>();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getHealth() {
        return health;
    }

    public String getPact() {
        return pact;
    }

    public void newMinion(Minion minion) {
        minions.add(minion);
    }

    @Override
    public void subtractMinionLife(int damage) {
        health -= damage;
    }
    @Override
    public void setHealth(int health) {
        this.health = health;
    }
}