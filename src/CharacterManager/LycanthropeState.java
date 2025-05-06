package CharacterManager;

public interface LycanthropeState extends java.io.Serializable {
    void attack();

    void takeDamage(Lycanthrope lycanthrope);

    void changeHeight();
}
