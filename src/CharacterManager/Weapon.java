package CharacterManager;

public class Weapon implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    private boolean twoHands;
    private String name;
    private int damage;

    public Weapon(boolean twoHands, String name, int damage) {
        this.twoHands = twoHands;
        this.name = name;
        this.damage = damage;
    }

    public boolean isTwoHands(){
        return twoHands;
    }

    public String getName(){
        return name;
    }

    public int getDamage(){
        return damage;
    }

    public void setTwoHands(boolean twoHands){
        this.twoHands = twoHands;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setDamage(int damage){
        this.damage = damage;
    }

}
