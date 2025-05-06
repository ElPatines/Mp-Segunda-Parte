package CharacterManager;

public class DecoratorStrengths extends CharacterDecorator {
    private static final long serialVersionUID = 1L;
    private Modifiers strength;

    public DecoratorStrengths(GameCharacter character, Modifiers strength) {
        super(character);
        this.strength = strength;
        if (this.character.getStrengths() != null) {
            this.character.getStrengths().add(strength);
        }
    }

    @Override
    public String toString() {
        return character.toString() + "\nFortaleza añadida: " + strength.getName() + " (Valor: " + strength.getValue() + ")";
    }
}