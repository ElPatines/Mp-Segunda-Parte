package CharacterManager;

public class Discipline implements SpecialAbility {
    private static final long serialVersionUID = 1L;
    private String name;
    private int attack;
    private int defense;
    private int bloodCost;

    public Discipline(String name, int attack, int defense, int bloodCost) {
        this.name = name;
        this.attack = attack;
        this.defense = defense;
        this.bloodCost = bloodCost;
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

    public int getBloodCost() {
        return bloodCost;
    }

    @Override
    public boolean useAbility(GameCharacter character) {
        if (character instanceof Vampire) {
            Vampire vampire = (Vampire) character;
            if (vampire.getBloodPoints() >= bloodCost) {
                vampire.setBloodPoints(vampire.getBloodPoints() - bloodCost);
                System.out.println(vampire.getName() + " ha usado la disciplina " + name +
                                ". Se han restado " + bloodCost + " puntos de sangre.");
                return true; 
            } else {
                System.out.println(vampire.getName() + " no tiene suficientes puntos de sangre para usar " + name + ".");
                return false; 
            }
        }
        return false; 
    }
}