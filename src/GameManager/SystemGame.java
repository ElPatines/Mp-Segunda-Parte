package GameManager;

import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class SystemGame extends ScreenCleaner {
    private BinaryAdapter adapter = new BinaryAdapter();
    private LinkedHashMap<String, Player> userList = adapter.loadGameData();
    private ArrayList<Admin> adminList = adapter.loadAdminData();
    private static SystemGame instance = null;
    private String adminCode = "admin";
    private Credentials c = new Credentials(this);

    private SystemGame() {
        this.userList = adapter.loadGameData();
    }

    public static SystemGame getInstanceOfSystemGame() {
        if (instance == null) {
            instance = new SystemGame();
        }
        return instance;
    }

    public Player getUserWithOutCode(String userName, String password) {
        for (Player user : userList.values()) {
            if (user.getUserName().equals(userName) && user.getPassword().equals(password)) {
                System.out.println("Recuerda que tu codigo es: " + user.getRegistrationNumber());
                return user;
            }
        }
        return null;
    }

    public Admin getAdminWithOutCode(String userName, String password) {
        for (Admin user : adminList) {
            if (user.getUserName().equals(userName) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public User getUser(String userCode) {
        return userList.get(userCode);
    }
    public void addAdmin(Admin admin) {
        adminList.add(admin);
        adapter.saveAdminData(adminList);
    }
    public void deleteAdmin(Admin admin) {
        adminList.remove(admin);
        adapter.saveAdminData(adminList);
    }
    public void showStartScreen() throws InterruptedException{
        cleanScreen();
        System.out.println("Bienvenido al juego. Que quieres hacer?");
        System.out.println("1-. Iniciar sesión");
        System.out.println("2-. Crear un nuevo usuario");
        System.out.println("3-. Crear un nuevo admin");
        System.out.println("4-. Salir del juego");
        Scanner scanner = new Scanner(System.in);
        int option = 16;
        while (option < 1 || option > 4) {
            option = scanner.nextInt();
            switch (option) {
                case 1:
                   
                    showLoginScreen();
                    
                    break;
                case 2:
                    showNewPlayerScreen();
                    break;
                case 3:
                    showNewAdminScreen();

                    break;
                case 4:
                    adapter.saveGameData(userList);
                    break;
                default:
                    System.out.println("Opción no válida. Inténtalo de nuevo.");
            }
        }
    }

    public void showLoginScreen() throws InterruptedException {
        cleanScreen();
        System.out.println("Escribe tu código de usuario (si no te acuerdas escribe 0 y si eres admin escribe 1): ");
        Scanner scanner = new Scanner(System.in);
        String userCode = scanner.nextLine();
        if (userCode.equals("0")) {
            System.out.println("Escribe tu nombre de usuario");
            String userName = scanner.nextLine();
            System.out.println("Escribe tu contraseña");
            String password = scanner.nextLine();
            User user = getUserWithOutCode(userName, password);
            if (user != null) {
                Player p = (Player) user;
                if (p.isBlocked()) {
                    System.out.println("Usuario bloqueado. Se cerrara el juego");
                    Thread.sleep(3000);
                    System.exit(0);
                } else {
                    Game game = new Game(this, p);
                    game.firstScreen();
                }
            }

        }else if (userCode.equals("1")){
                System.out.println("Escribe tu nombre de usuario");
                String userName = scanner.nextLine();
                System.out.println("Escribe tu contraseña");
                String password = scanner.nextLine();
                User user = getAdminWithOutCode(userName, password);
                if (user != null) {
                    AdminSection adminSection = new AdminSection ((Admin) user, this);
                    adminSection.firstScreen();
                } else {
                    System.out.println("Usuario no válido.");
                    System.out.println("Pulsa cualquier tecla para continuar");
                    scanner.nextLine();
                    showStartScreen();
                }
            }
            
            else {
                Player user = (Player) getUser(userCode);
            if (user != null) {
                if (user.isBlocked()) {
                    System.out.println("Usuario bloqueado. Se cerrara el juego");
                    Thread.sleep(3000);
                    System.exit(0);
                } else {
                    Game game = new Game(this, user);
                    game.firstScreen();
                }

            } else {
                System.out.println("Código de usuario no válido.");
                Thread.sleep(3000);
                showStartScreen();
            }
        }

    }
    public void changePlayer(String userCode, Player user) {
        userList.put(userCode, user);
        adapter.saveGameData(userList);

    }
    public Adapter getAdapter() {
        return adapter;
    }

    public void showNewPlayerScreen() {
        cleanScreen();
        String validation = "-1";
        while (validation.equals("-1")) {
            System.out.println("Escribe el nombre de usuario que quieras usar:");
            Scanner scanner = new Scanner(System.in);
            String userName = scanner.nextLine();
            System.out.println("Escribe la contraseña que quieras usar:");
            String password = scanner.nextLine();
            validation = newPlayer(userName, password);
            if (validation.equals("-1")) {
                System.out.println("Hay algun fallo en la contraseña o el nombre. Prueba otro");

            } else {
                System.out.println("Usuario creado con éxito. Tu código de usuario es: " + validation);
                System.out.println("Pulsa cualquier tecla para continuar");
                scanner.nextLine();
                Game game = new Game(this, userList.get(validation));
                game.firstScreen();
            }
        }

    }

    public String newPlayer(String userName, String password) {
        String userCode = c.isValid(userName, password);
        if (userCode != "-1") {
            Player p = new Player(userName, password, userCode, showNewProfileScreen());
            userList.put(userCode, p);
            adapter.saveGameData(userList);
            return userCode;
        } else {
            return "-1";
        }
    }

    public Profile showNewProfileScreen() {
        cleanScreen();
        System.out.println("Escribe tu email:");
        Scanner scanner = new Scanner(System.in);
        String email = scanner.nextLine();
        System.out.println("Escribe tu pais:");
        String pais = scanner.nextLine();
        System.out.println("Escribe tu edad:");
        int edad = scanner.nextInt();
        return new Profile(email, pais, edad);

    }
    public void showNewAdminScreen() throws InterruptedException{
        cleanScreen();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Escribe la contraseña de admin");
        String adminPassword = scanner.nextLine();
        if (adminPassword.equals(adminCode)) {
            System.out.println("Escribe el nombre de usuario que quieras usar:");
            String userName = scanner.nextLine();
            System.out.println("Escribe la contraseña que quieras usar:");
            String password = scanner.nextLine();
            Admin admin = new Admin(userName, password, showNewProfileScreen());
            adminList.add(admin);
            adapter.saveAdminData(adminList);
            AdminSection adminSection = new AdminSection(admin, this);
            adminSection.firstScreen();
        } else {
            System.out.println("Contraseña incorrecta. No tienes permisos para crear un nuevo admin.");
            showStartScreen();
        }

    }

    public String newAdmin(String userName, String password) {
        String userCode = c.isValid(userName, password);
        if (userCode != "-1") {
            adminList.add(new Admin(userCode, password, showNewProfileScreen()));
            return userCode;
        } else {
            return "-1";
        }
    }

    public void logOut(String userCode, Player user) {
        cleanScreen();
        userList.put(userCode, user);
        adapter.saveGameData(userList);
        System.exit(0);
    }

    public LinkedHashMap<String, Player> getUserList() {
        return userList;
    }

    public Admin searchAdmin() {
        Random rand = new Random();
        int num = rand.nextInt(adminList.size());
        return (Admin) adminList.get(num);
    }
    public void updateUserList() {
        adapter.saveGameData(userList);
    }
    public void updateAdminList() {
        adapter.saveAdminData(adminList);
    }
}
