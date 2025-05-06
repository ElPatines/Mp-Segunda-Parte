package CharacterManager;


public interface Minion extends java.io.Serializable {

    public String getName();

    public int getHealth();

    public void subtractMinionLife(int damage);

    public void setHealth(int health);
}

