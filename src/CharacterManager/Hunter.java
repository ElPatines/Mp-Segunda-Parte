package CharacterManager;

import java.util.List;

public class Hunter extends GameCharacter{
    private static final long serialVersionUID = 1L;
    private int goodness;

    public Hunter(String name, List<SpecialAbility> specialAbility, List<Weapon> weapons, 
                 List<Weapon> activeWeapon, List<Armor> armor, List<Armor> activeArmor, List<Minion> minions,
                 int gold, int health, int power, List<Modifiers> weaknesses, List<Modifiers> strengths, int goodness){
        super(name, specialAbility, weapons, activeWeapon, armor, activeArmor, minions, gold,
              health, power, weaknesses, strengths);
        this.goodness = goodness;
    }

    public int getGoodness(){
        return goodness;
    }
    
}
