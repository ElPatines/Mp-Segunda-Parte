package GameManager;

import java.util.Scanner;

public class AdminSection extends ScreenCleaner {
    private Admin admin;
    private SystemGame s;
    public AdminSection(Admin admin, SystemGame s) {
        this.admin = admin;
        this.s = s;
    }
    public void firstScreen(){
        cleanScreen();
        System.out.println("Que quieres hacer?");
        System.out.println("1. Revisar desafios");
        System.out.println("2. Revisar lista de bloqueados");
        System.out.println("3. Salir");
        Scanner scanner = new Scanner(System.in);
        int option = 16;
        while (option < 1 || option > 3) {
            option = scanner.nextInt();
            switch (option) {
                case 1:
                    cleanScreen();
                    admin.validateChallenge(this.s);
                    s.deleteAdmin(admin);
                    s.addAdmin(admin);
                    s.updateUserList();
                    firstScreen();
                    break;
                case 2:
                    cleanScreen();
                    admin.unblockPlayers(s.getUserList(), s);
                    firstScreen();
                    break;
                case 3:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opción inválida. Intenta de nuevo.");
            }
        }

    }
}
