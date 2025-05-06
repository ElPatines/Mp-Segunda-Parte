package CharacterManager;


public abstract class CharacterDecorator extends GameCharacter {
    private static final long serialVersionUID = 1L;
    protected GameCharacter character;

    public CharacterDecorator(GameCharacter character) {
        super(character.getName(), character.getSpecialAbility(), character.getWeapons(), character.getActiveWeapon(),
              character.getArmor(), character.getActiveArmor(), character.getMinions(), character.getGold(),
              character.getHealth(), character.getPower(), character.getWeaknesses(), character.getStrengths());
        this.character = character;
    }
}