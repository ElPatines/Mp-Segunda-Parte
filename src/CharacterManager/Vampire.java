package CharacterManager;

import java.util.List;

public class Vampire extends GameCharacter {
    private static final long serialVersionUID = 1L;
    private int bloodPoints;
    private int age;

    public Vampire(String name, List<SpecialAbility> specialAbility, List<Weapon> weapons, List<Weapon> activeWeapon,
                   List<Armor> armor, List<Armor> activeArmor, List<Minion> minions, int gold, int health, int power,
                   List<Modifiers> weaknesses, List<Modifiers> strengths, int bloodPoints, int age) {
        super(name, specialAbility, weapons, activeWeapon, armor, activeArmor, minions, gold, health, power, weaknesses, strengths);
        this.bloodPoints = bloodPoints;
        this.age = age;
    }

    public int getBloodPoints() {
        return bloodPoints;
    }

    public void setBloodPoints(int bloodPoints) {
        this.bloodPoints = bloodPoints;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public void subtractCharacterLife() {
        if (this.getHealth() > 0) {
            super.subtractCharacterLife();
            System.out.println("Vampire loses 1 health point.");
        }
    }

    public void drinkBlood(int amount) {
        this.bloodPoints += amount;
        System.out.println("Vampire drinks " + amount + " blood points.");
    }
}