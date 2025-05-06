package CharacterManager;

import java.util.List;

public class HumanMinion implements Minion {
    private static final long serialVersionUID = 1L;
    private String name;
    private int health;
    private String loyalty;
    private List<Minion> minions;

    public HumanMinion(String name, int health, String loyalty, List<Minion> minions) {
        this.name = name;
        this.health = health;
        this.loyalty = loyalty;
        this.minions = minions;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getHealth() {
        return health;
    }

    public String getLoyalty() {
        return loyalty;
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