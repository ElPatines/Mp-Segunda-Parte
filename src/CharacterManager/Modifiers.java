package CharacterManager;

public class Modifiers implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private int value;

    public Modifiers(String name, int value) {
        if (value < 1 || value > 5) {
            throw new IllegalArgumentException("El valor debe estar entre 1 y 5.");
        }
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        if (value < 1 || value > 5) {
            throw new IllegalArgumentException("El valor debe estar entre 1 y 5.");
        }
        this.value = value;
    }

    @Override
    public String toString() {
        return "Modifiers{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}