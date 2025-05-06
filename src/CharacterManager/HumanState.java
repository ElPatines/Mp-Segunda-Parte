package CharacterManager;

public class HumanState implements LycanthropeState {
    private static final long serialVersionUID = 1L;
    @Override
    public void attack() {
        System.out.println("El licántropo ataca como humano. Daño reducido.");
    }
    @Override
    public void takeDamage(Lycanthrope lycanthrope) {
        lycanthrope.incRage(); 
        System.out.println("Licántropo en forma humana recibió daño. Rabia aumentada a: " + lycanthrope.getRage());
        if (lycanthrope.getRage() >= 5) {
            lycanthrope.setState(new BeastState());
            System.out.println("¡El licántropo se transforma en bestia!");
        }
    }

    @Override
    public void changeHeight() {
        System.out.println("El humano se estira un poco, pero nada dramático.");
    }
}
