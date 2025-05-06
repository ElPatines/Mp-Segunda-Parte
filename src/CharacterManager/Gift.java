package CharacterManager;

public class Gift implements SpecialAbility { 
    private static final long serialVersionUID = 1L;
    private String name;
    private int attack;
    private int defense;
    private int rageValue;

    public Gift(String name, int attack, int defense, int rageValue) {
        this.name = name;
        this.attack = attack;
        this.defense = defense;
        this.rageValue = rageValue;
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

    public int getRageValue() {
        return rageValue;
    }

    @Override
    public boolean useAbility(GameCharacter character) {
        if (character instanceof Lycanthrope) {
            Lycanthrope lycanthrope = (Lycanthrope) character;
            if (lycanthrope.getRage() >= rageValue) {
                System.out.println(lycanthrope.getAge() + " ha usado el don " + name +
                                ". Este don tiene un ataque de " + attack + " y una defensa de " + defense + ".");
                return true;
            } else {
                System.out.println(lycanthrope.getAge() + " no tiene suficiente rabia para usar el don " + name + ".");
                return false; 
            }
        }
        return false; 
    }
}