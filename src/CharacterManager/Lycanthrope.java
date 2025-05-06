package CharacterManager;

import java.util.List;

public class Lycanthrope extends GameCharacter{
    private static final long serialVersionUID = 1L;
    private int age;
    private int height;
    private int rage;
    private LycanthropeState state;

    public Lycanthrope(String name, int age, int height, List<SpecialAbility> specialAbilities,
                   List<Weapon> primaryWeapons, List<Weapon> secondaryWeapons, List<Armor> primaryArmors,
                   List<Armor> secondaryArmors, List<Minion> minions, int health, int attack, int defense,
                   List<Modifiers> weaknesses, List<Modifiers> strengths) {
    super(name, specialAbilities, primaryWeapons, secondaryWeapons, primaryArmors, secondaryArmors, minions,
          health, attack, defense, weaknesses, strengths);
    this.age = age;
    this.height = height;
    this.rage = 0;
    this.state = new HumanState();
}

    public int getRage() {
        return rage;
    }

    public void incRage() {
        rage++;
    }

    public void setState(LycanthropeState state) {
        this.state = state;
    }

    public int getAge() {
        return age;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void attack() {
        state.attack();
    }

    public void takeDamage() {
        state.takeDamage(this);
    }

    public void changeHeight() {
        state.changeHeight();
    }

    public int calcDamage() {
        if (state instanceof BeastState) {
            return 50;
        } else {
            return 20;
        }
    }
}
