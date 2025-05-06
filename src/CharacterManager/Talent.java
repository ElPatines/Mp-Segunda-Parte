package CharacterManager;

public class Talent implements SpecialAbility {   
    private String name;
    private static final long serialVersionUID = 1L;
    private int attack;
    private int defense;

    public Talent(String name, int attack, int defense) {
        this.name = name;
        this.attack = attack;
        this.defense = defense;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getAttack() {
        return attack;
    }

    @Override
    public int getDefense() {
        return defense;
    }

    @Override
    public boolean useAbility(GameCharacter character) {
        if (character instanceof Hunter) {
            Hunter hunter = (Hunter) character;
            System.out.println(hunter.getName() + " ha usado el talento " + name +
                            ". Este talento tiene un ataque de " + attack + " y una defensa de " + defense + ".");
            return true; 
        } else {
            System.out.println("El talento " + name + " solo puede ser usado por cazadores.");
            return false;
        }
}
}