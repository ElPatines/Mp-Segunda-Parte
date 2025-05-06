package GameManager;

import CharacterManager.Armor;
import CharacterManager.GameCharacter;
import CharacterManager.Minion;
import CharacterManager.Modifiers;
import CharacterManager.SpecialAbility;
import CharacterManager.Weapon;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Battle extends ScreenCleaner implements Subject, Serializable {
    private static final long serialVersionUID = 1L;
    private List<Observer> observers;
    private int round;
    private int bet;
    private Player result;
    private LocalDate date;

    public Battle() {
        this.observers = new ArrayList<>();
        this.round = 0;
        this.result = null;
        this.date = LocalDate.now();
    }

    public void addObserver(User user) {
        this.observers.add(user);
    }

    public int getBet() {
        return bet;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getRound() {
        return round;
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(Player result) {
        for (Observer observer : observers) {
            observer.update(result);
        }
    }

    public Player startBattle(Player attacker, Player defender, int bet) {

        addObserver(attacker);
        addObserver(defender);
        this.bet = bet;
        cleanScreen();
        System.out.println("Empieza la batalla entre " + attacker.getUserName() + " y " + defender.getUserName()
                + " con la apuesta de " + bet + " monedas.");
        GameCharacter attackerCharacter = attacker.getCharacter();
        while (attackerCharacter.getHealth() > 0 && defender.getCharacter().getHealth() > 0) {
            round++;
            System.out.println("Round " + round);

           
            int attackerAttack = calculateAttackPotential(attacker.getCharacter());
            int defenderDefense = calculateDefensePotential(defender.getCharacter());
            int defenderAttack = calculateAttackPotential(defender.getCharacter());
            int attackerDefense = calculateDefensePotential(attacker.getCharacter());

          
            int attackerSuccesses = rollDice(attackerAttack);
            int defenderSuccesses = rollDice(defenderDefense);
            int defenderAttackSuccesses = rollDice(defenderAttack);
            int attackerDefenseSuccesses = rollDice(attackerDefense);

          
            if (attackerSuccesses >= defenderSuccesses) {
                applyDamage(defender.getCharacter(), 1);
                System.out.println(attacker.getUserName() + " ataca a " + defender.getUserName() + "!");
            }

            if (defenderAttackSuccesses >= attackerDefenseSuccesses) {
                applyDamage(attacker.getCharacter(), 1);
                System.out.println(defender.getUserName() + " ataca a " + attacker.getUserName() + "!");
            }

            if (attacker.getCharacter().getHealth() <= 0 && defender.getCharacter().getHealth() <= 0) {
                System.out.println("Empate");
                result = null;
                notifyObservers(result);
                return result;
            } else if (attacker.getCharacter().getHealth() <= 0) {
                System.out.println(defender.getUserName() + " gana la batalla!");
                result = defender;
                notifyObservers(result);
                return result;
            } else if (defender.getCharacter().getHealth() <= 0) {
                System.out.println(attacker.getUserName() + " gana la batalla!");
                result = attacker;
                notifyObservers(result);
                return attacker;
            }
        }

        return null;
    }

    private int calculateAttackPotential(GameCharacter character) {
        int potential = character.getPower();

        
        List<Weapon> activeWeapons = character.getActiveWeapon();
        if (activeWeapons != null && !activeWeapons.isEmpty()) {
            for (Weapon weapon : activeWeapons) {
                potential += weapon.getDamage(); 
            }
        }

       
        for (SpecialAbility ability : character.getSpecialAbility()) {
            ability.useAbility(character);
        }

        
        int strengthsValue = sumModifierValues(character.getStrengths());
        int weaknessesValue = sumModifierValues(character.getWeaknesses());
        potential += strengthsValue - weaknessesValue;
        return potential;
    }

    private int calculateDefensePotential(GameCharacter character) {
        int potential = character.getPower();

      
        List<Armor> activeArmors = character.getActiveArmor();
        if (activeArmors != null && !activeArmors.isEmpty()) {
            for (Armor armor : activeArmors) {
                potential += armor.getPower();
            }
        }

        
        for (SpecialAbility ability : character.getSpecialAbility()) {
            if (ability.useAbility(character)) { 
                potential += ability.getDefense(); 
            }
        }

       
        int strengthsValue = sumModifierValues(character.getStrengths());
        int weaknessesValue = sumModifierValues(character.getWeaknesses());
        potential += strengthsValue - weaknessesValue;
        return potential;
    }

    private int sumModifierValues(List<Modifiers> modifiers) {
        int sum = 0;
        for (Modifiers modifier : modifiers) {
            sum += modifier.getValue();
        }
        return sum;
    }

    private int rollDice(int potential) {
        Random random = new Random();
        int successes = 0;

        for (int i = 0; i < potential; i++) {
            int roll = random.nextInt(6) + 1; 
            if (roll == 5 || roll == 6) {
                successes++;
            }
        }

        return successes;
    }

    private void applyDamage(GameCharacter character, int damage) {
        int remainingDamage = damage;

        
        for (Minion minion : character.getMinions()) {
            if (remainingDamage <= 0)
                break;
            int minionHealth = minion.getHealth();
            if (minionHealth > remainingDamage) {
                minion.setHealth(minionHealth - remainingDamage);
                remainingDamage = 0;
            } else {
                remainingDamage -= minionHealth;
                minion.setHealth(0);
            }
        }

       
        if (remainingDamage > 0) {
            character.setHealth(character.getHealth() - remainingDamage);
        }
    }

    public User getResult() {
        return result;
    }

}
