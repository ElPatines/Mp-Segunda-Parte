package CharacterManager;

public interface SpecialAbility extends java.io.Serializable {
    String getName();
    int getAttack();
    int getDefense();

    public boolean useAbility(GameCharacter character);
}