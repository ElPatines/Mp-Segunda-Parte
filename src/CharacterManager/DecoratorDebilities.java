package CharacterManager;

public class DecoratorDebilities extends CharacterDecorator {
    private static final long serialVersionUID = 1L;
    private Modifiers weakness;

    public DecoratorDebilities(GameCharacter character, Modifiers weakness) {
        super(character);
        this.weakness = weakness;
        if (this.character.getWeaknesses() != null) {
            this.character.getWeaknesses().add(weakness);
        }
    }

    @Override
    public String toString() {
        return character.toString() + "\nDebilidad añadida: " + weakness.getName() + " (Valor: " + weakness.getValue() + ")";
    }
}