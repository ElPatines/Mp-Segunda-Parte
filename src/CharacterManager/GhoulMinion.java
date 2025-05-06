package CharacterManager;

public class GhoulMinion implements Minion {  
    private static final long serialVersionUID = 1L;
    private String name;
    private int health;
    private int dependency;

    public GhoulMinion(String name, int health, int dependency) {
        this.name = name;
        this.health = health;
        this.dependency = dependency;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getHealth() {
        return health;
    }

    public int getDependency() {
        return dependency;
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