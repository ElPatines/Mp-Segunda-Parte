package CharacterManager;

public class BeastState implements LycanthropeState {
    private static final long serialVersionUID = 1L;
    @Override
    public void attack() {
        System.out.println("El licántropo ataca como bestia. Daño máximo.");
    }
    @Override
    public void takeDamage(Lycanthrope lycanthrope) {
        lycanthrope.incRage(); 
        System.out.println("Licántropo en forma bestia recibió daño. Rabia actual: " + lycanthrope.getRage());
    }
    @Override
    public void changeHeight() {
        System.out.println("¡El cuerpo del licántropo se agranda violentamente!");
    }
}
