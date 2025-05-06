package CharacterManager;

import java.util.List;

public class GameCharacter implements java.io.Serializable{
    private static final long serialVersionUID = 1L;
    private String name;
    private List<SpecialAbility> specialAbility;
    private List<Weapon> weapons;
    private List<Weapon> activeWeapon;
    private List<Armor> armor;
    private List<Armor> activeArmor;
    private List<Minion> minions;
    private int gold;
    private int health;
    private int initialHealth;
    private int power;
    private List<Modifiers> weaknesses;
    private List<Modifiers> strengths;

    public GameCharacter(String name, List<SpecialAbility> specialAbility, List<Weapon> weapons, List<Weapon> activeWeapon,
                     List<Armor> armor, List<Armor> activeArmor, List<Minion> minions, int gold, int health, int power,
                     List<Modifiers> weaknesses, List<Modifiers> strengths) {
        this.name = name;
        this.specialAbility = specialAbility;
        this.weapons = weapons;
        this.activeWeapon = activeWeapon;
        this.armor = armor;
        this.activeArmor = activeArmor;
        this.minions = minions;
        this.gold = gold;
        this.health = health;
        this.initialHealth = health;
        this.power = power;
        this.weaknesses = weaknesses;
        this.strengths = strengths;
    }

    public int getHealth() {
        return health;
    }

    public String getName() {
        return name;
    }

    public List<SpecialAbility> getSpecialAbility() {
        return specialAbility;
    }

    public List<Weapon> getWeapons() {
        return weapons;
    }

    public List<Weapon> getActiveWeapon() {
        return activeWeapon;
    }

    public List<Armor> getArmor() {
        return armor;
    }

    public List<Armor> getActiveArmor() {
        return activeArmor;
    }

    public List<Minion> getMinions() {
        return minions;
    }

    public int getGold() {
        return gold;
    }

    public int getPower() {
        return power;
    }

    public List<Modifiers> getWeaknesses() {
        return weaknesses;
    }

    public List<Modifiers> getStrengths() {
        return strengths;
    }

    public void subtractCharacterLife() {
        if (this.health > 0) {
            this.health--;
        }
    }

    public void resetLife() {
        this.health = this.initialHealth;
    }

    public boolean validateName() {
        return name != null && !name.isEmpty();
    }

    public void setHealth(int health) {
        this.health = health;
    }
    public void setName(String name) {
        this.name = name;
    }
}
