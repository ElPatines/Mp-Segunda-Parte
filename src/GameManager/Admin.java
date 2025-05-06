package GameManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class Admin extends User implements Serializable {
    private static final long serialVersionUID = 1L;  

    private ArrayList<Challenge> toValidate = new ArrayList<Challenge>();

    public Admin(String userName, String password, Profile profile) {
        super(userName, password, profile);
    }

    public Admin() {
        super();
    }

    public ArrayList<Challenge> getToValidate() {
        return toValidate;
    }

    public void setToValidate(ArrayList<Challenge> toValidate) {
        this.toValidate = toValidate;
    }

    public void receiveChallenge(Challenge challenge) {
        if (toValidate == null) {
            toValidate = new ArrayList<Challenge>();
        }
        toValidate.add(challenge);
    }

    public void validateChallenge(SystemGame s) {
        Scanner scanner = new Scanner(System.in);
        Iterator<Challenge> iterator = toValidate.iterator();

        while (iterator.hasNext()) {
            Challenge challenge = iterator.next();
            Player challenger = challenge.getChallenger();
            Player challenged = challenge.getChallenged();
            int bet = challenge.getBet();

            if (challenger.getMoney() >= bet && challenged.getMoney() >= bet && challenger.getCharacter()!= null && challenged.getCharacter() != null) {
                challenger.loseMoney(bet);
                challenged.loseMoney(bet);

                System.out.println("El desafío es matemáticamente válido entre " 
                    + challenger.getUserName() + " y " + challenged.getUserName());
                System.out.println("Quieres enviarlo? (1) Sí, (2) No");

                int option = scanner.nextInt();
                if (option == 1) {
                    challenged.addToMail(challenge);
                    s.changePlayer(challenged.getRegistrationNumber(), challenged);
                    System.out.println("Desafío enviado a " + challenged.getUserName());
                } else {
                    System.out.println("Desafío rechazado");
                }
            } else {
                System.out.println("Es un desafio, se bloqueara al retador");
                challenger.blockPlayer();
                s.changePlayer(challenger.getRegistrationNumber(), challenger);
            }
            iterator.remove(); 
            System.out.println("Quieres seguir revisando desafíos? (1) Sí, (2) No");
            int continueOption = scanner.nextInt();
            if (continueOption == 2) {
                break;
            }
        }
        System.out.println("No hay más desafíos pendientes.");
        System.out.println("Pulsa cualquier tecla para salir.");
        scanner.nextLine(); 
    }

    public void unblockPlayers(LinkedHashMap<String, Player> playerList, SystemGame s) {
        for (Player player : playerList.values()) {
            if (player.isBlocked()) {
                System.out.println("Quieres desbloquear a " + player.getUserName() + "? (1) Si, (2) No");
                Scanner sc = new Scanner(System.in);
                int option = sc.nextInt();
                if (option == 1) {
                    player.unblockPlayer();
                    System.out.println("Jugador desbloqueado: " + player.getUserName());
                    s.changePlayer(player.getRegistrationNumber(), player);
                } else {
                    System.out.println("Jugador no desbloqueado: " + player.getUserName());
                }
                System.out.println("Quieres seguir desbloqueando o prefieres salir a la pantalla principal? (1) Si, (2) No");
                option = sc.nextInt();
                if (option == 2) {
                    break;
                }
            }
        }
        System.out.println("No hay jugadores bloqueados.");
    }
}
