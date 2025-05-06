package GameManager;

import CharacterManager.Armor;
import CharacterManager.CompositeDemonMinion;
import CharacterManager.Discipline;
import CharacterManager.GameCharacter;
import CharacterManager.GhoulMinion;
import CharacterManager.Gift;
import CharacterManager.HumanMinion;
import CharacterManager.Hunter;
import CharacterManager.Lycanthrope;
import CharacterManager.Minion;
import CharacterManager.Modifiers;
import CharacterManager.SpecialAbility;
import CharacterManager.Talent;
import CharacterManager.Vampire;
import CharacterManager.Weapon;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Game extends ScreenCleaner {
    private Player user;
    private SystemGame s;
    private Ranking ranking;

    public Game(SystemGame s, Player user) {
        this.s = s;
        this.user = user;
        this.ranking = new Ranking(s.getAdapter());
        firstScreen();
    }

    public void createChallenge(Player challenger, Player challenged, Admin admin, int bet) {
        Challenge challenge = new Challenge(challenger, challenged, admin, bet);
    }

    public void firstScreen() {
        cleanScreen();
        ArrayList<Challenge> userMail = user.getMail();
        if (userMail.size() > 0) {
            System.out.println("Tienes un desafío pendiente:");
            Scanner sc = new Scanner(System.in);
            Player challenger = null;
            for (int i = 0; i < userMail.size(); i++) {
                Challenge challenge = userMail.get(i);
                System.out.println("¿Quieres aceptar el desafío?: (1) Sí, (2) No");
                int option = sc.nextInt();
                if (option == 1) {
                    System.out.println("Desafío aceptado.");
                    System.out.println("Quieres editar tu personaneje?: (1) Sí, (2) No");
                    option = sc.nextInt();
                    if (option == 1) {
                        editCharacter(user.getCharacter());
                    }
                    challenger = challenge.getChallenger();

                    Battle battle = new Battle();
                    battle.startBattle(challenger, user, challenge.getBet());
                    System.out.println("Pulsa para continuar...");
                    sc.nextLine();
                } else if (option == 2) {
                    int moneyToGive = challenge.getBet() / 10;
                    challenger = challenge.getChallenger();
                    challenger.earnMoney(moneyToGive);
                    user.loseMoney(moneyToGive);
                } 
                user.removeFromMail(challenge);
                i--;
                s.changePlayer(challenger.getRegistrationNumber(), challenger);
                s.changePlayer(user.getRegistrationNumber(), user);
                
            }
        }
        System.out.println("Que quieres hacer?");
        System.out.println("1-. Mandar un desafio");
        System.out.println("2-. Ver tu historial de batallas");
        if (user.getCharacter() == null) {
            System.out.println("3-. Crear tu personaje");
        } else {
            System.out.println("3-. Editar tu personaje");
        }
        System.out.println("4-. Eliminar tu personaje");
        System.out.println("5-. Ver tu dinero");
        System.out.println("6-. Ver el ranking");
        System.out.println("7-. Salir del juego");
        Scanner scanner = new Scanner(System.in);
        int option = 16;
        while (option < 1 || option > 6) {
            option = scanner.nextInt();
            switch (option) {
                case 1:
                    List<Map.Entry<String, Player>> entries = new ArrayList<>(s.getUserList().entrySet());
                    entries.removeIf(nullEntry -> nullEntry.getValue().getRegistrationNumber().equals(user.getRegistrationNumber()));
                    int pageSize = 5;
                    int currentPage = 0;
                    boolean seguir = true;
                    Player player = null;
                    while (seguir && currentPage * pageSize < entries.size()) {
                        int start = currentPage * pageSize;
                        int end = Math.min(start + pageSize, entries.size());
                        for (int i = start; i < end; i++) {
                            player = entries.get(i).getValue();
                            if (player.getRegistrationNumber().equals(user.getRegistrationNumber())) {
                                continue; 
                            }
                            System.out.println(
                                    (i + 1) + ". " + player.getUserName() + " - " + player.getMoney() + " monedas");
                        }
                        System.out.println("A-. Ver siguientes 5 jugadores");
                        System.out.println("B-. Elegir un jugador por número");
                        System.out.println("C-. Salir");

                        String opcion = scanner.nextLine();

                        switch (opcion) {
                            case "A":
                                if (end < entries.size()) {
                                    currentPage++;
                                } else {
                                    System.out.println("No hay más jugadores.");
                                }
                                break;
                            case "B":
                                System.out.print("Ingrese el número del jugador que desea elegir: ");
                                try {
                                    int elegido = Integer.parseInt(scanner.nextLine());
                                    if (elegido >= 1 && elegido <= entries.size()) {
                                        Player seleccionado = entries.get(elegido - 1).getValue();
                                        System.out.println("Seleccionaste: " + seleccionado.getUserName() + " con "
                                                + seleccionado.getMoney() + " gold");
                                        System.out.println(
                                                "¿Cuánto quieres apostar? (Tienes " + user.getMoney() + " monedas)");
                                        int apuesta = scanner.nextInt();
                                        Challenge challenge = new Challenge(user, seleccionado, s.searchAdmin(),
                                                apuesta);
                                        challenge.sendChallengeToAdmin();
                                        s.updateAdminList();
                                        seguir = false;
                                    } else {
                                        System.out.println("Número inválido.");
                                    }
                                } catch (NumberFormatException e) {
                                    System.out.println("Entrada no válida.");
                                }
                                break;
                            case "C":
                                seguir = false;
                                break;
                            default:
                                System.out.println("Opción no válida.");
                        }
                    }
                    break;

                case 2:
                    ArrayList<Battle> history = user.getHistory();
                    for (Battle battle : history) {
                        System.out.println("Fecha: " + battle.getDate() + ",Rondas: " + battle.getRound()
                                + ", Apuesta: " + battle.getBet() + ", Ganador: " + battle.getResult());
                    }
                    break;
                case 3:
                    if (user.getCharacter() == null) {
                        GameCharacter newCharacter = createCharacter(scanner);
                        user.setCharacter(newCharacter);
                        System.out.println("¡Personaje creado exitosamente!");
                        s.changePlayer(user.getRegistrationNumber(), user);
                    } else {
                        editCharacter(user.getCharacter());
                        s.changePlayer(user.getRegistrationNumber(), user);

                    }
                    break;
                case 4:
                    if (user.getCharacter() != null) {
                        user.setCharacter(null);
                    }
                    break;
                case 5:
                    while (true) {
                        System.out.println("Tienes " + user.getMoney() + " monedas");
                        System.out.println("Escribe '0' para volver a lapantalla principal");
                        String entrada = scanner.next();
                        if (entrada.equals("0")) {
                            break;
                        }
                    }
                    break;
                case 6:
                    ranking.updateRanking(s.getUserList());
                    ranking.showRanking();
                    break;
                case 7:
                    s.logOut(user.getRegistrationNumber(), user);
                    break;
                default:
                    System.out.println("Opción inválida. Intenta de nuevo.");
                    break;

            }
            firstScreen();
        }

    }

    public void showRanking() {
        ranking.showRanking();
    }

    public void editCharacter(GameCharacter character) {
        Scanner scanner = new Scanner(System.in);
        boolean editing = true;

        while (editing) {
            System.out.println("Editar Personaje");
            System.out.println("1. Cambiar nombre");
            System.out.println("2. Editar habilidades especiales");
            System.out.println("3. Editar armas");
            System.out.println("4. Editar armaduras");
            System.out.println("5. Editar minions");
            System.out.println("6. Salir");
            System.out.println("(En caso de querer cambiar de clase, deberá crearse un nuevo personaje)");
            System.out.print("Selecciona una opción: ");
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    System.out.print("Introduce el nuevo nombre: ");
                    String newName = scanner.nextLine();
                    if (newName != null && !newName.isEmpty()) {
                        character.setName(newName);
                        System.out.println("Nombre actualizado a: " + newName);
                    } else {
                        System.out.println("El nombre no puede estar vacío.");
                    }
                    break;

                case 2:
                    System.out.println("Habilidades actuales:");
                    List<SpecialAbility> abilities = character.getSpecialAbility();
                    for (int i = 0; i < abilities.size(); i++) {
                        SpecialAbility ability = abilities.get(i);
                        System.out.println(i + ". " + ability.getName() + " (Ataque: " + ability.getAttack()
                                + ", Defensa: " + ability.getDefense() + ")");
                    }
                    System.out.println("¿Deseas añadir o eliminar habilidades? (1: Añadir, 2: Eliminar)");
                    int abilityOption = scanner.nextInt();
                    scanner.nextLine();
                    if (abilityOption == 1) {
                        System.out.print("Introduce el nombre de la nueva habilidad: ");
                        String newAbilityName = scanner.nextLine();
                        System.out.print("Introduce el valor de ataque: ");
                        int attack = scanner.nextInt();
                        System.out.print("Introduce el valor de defensa: ");
                        int defense = scanner.nextInt();

                        SpecialAbility newAbility = null;

                        if (character instanceof Hunter) {
                            newAbility = new Talent(newAbilityName, attack, defense);
                            System.out.println("Habilidad de tipo talento creada para Hunter.");
                        } else if (character instanceof Vampire) {
                            System.out.print("Introduce el costo de sangre para usar esta habilidad: ");
                            int bloodCost = scanner.nextInt();
                            newAbility = new Discipline(newAbilityName, attack, defense, bloodCost);
                            System.out.println("Habilidad de tipo disciplina creada para Vampire.");
                        } else if (character instanceof Lycanthrope) {
                            System.out.print("Introduce el valor de rabia necesario para usar esta habilidad: ");
                            int rageValue = scanner.nextInt();
                            newAbility = new Gift(newAbilityName, attack, defense, rageValue);
                            System.out.println("Habilidad de tipo don creada para Lycanthrope.");
                        } else {
                            System.out.println("El tipo de personaje no es compatible con habilidades especiales.");
                        }

                        if (newAbility != null) {
                            abilities.add(newAbility);
                            System.out.println("Habilidad añadida: " + newAbility.getName());
                        }
                    } else if (abilityOption == 2) {
                        System.out.println("Introduce el índice de la habilidad a eliminar: ");
                        int index = scanner.nextInt();
                        if (index >= 0 && index < abilities.size()) {
                            SpecialAbility removedAbility = abilities.remove(index);
                            System.out.println("Habilidad eliminada: " + removedAbility.getName());
                        } else {
                            System.out.println("Índice inválido.");
                        }
                    }
                    break;

                case 3:
                    System.out.println("Armas actuales:");
                    List<Weapon> weapons = character.getWeapons();
                    List<Weapon> activeWeapons = character.getActiveWeapon();

                    if (weapons.isEmpty()) {
                        System.out.println("No tienes armas.");
                    } else {
                        for (int i = 0; i < weapons.size(); i++) {
                            Weapon weapon = weapons.get(i);
                            System.out.println(i + ". " + weapon.getName() + " (Daño: " + weapon.getDamage()
                                    + ", Dos manos: " + weapon.isTwoHands() + ")");
                        }
                    }

                    System.out.println(
                            "¿Deseas añadir, eliminar o cambiar arma activa? (1: Añadir, 2: Eliminar, 3: Cambiar arma activa)");
                    int weaponOption = scanner.nextInt();
                    scanner.nextLine();

                    if (weaponOption == 1) {
                        System.out.print("Introduce el nombre del arma: ");
                        String newWeaponName = scanner.nextLine();
                        System.out.print("Introduce el daño del arma: ");
                        int damage = scanner.nextInt();
                        System.out.print("¿Es un arma de dos manos? (true/false): ");
                        boolean twoHands = scanner.nextBoolean();

                        Weapon newWeapon = new Weapon(twoHands, newWeaponName, damage);
                        weapons.add(newWeapon);
                        System.out.println("Arma añadida: " + newWeapon.getName());

                    } else if (weaponOption == 2) {
                        if (weapons.isEmpty()) {
                            System.out.println("No tienes armas para eliminar.");
                        } else {
                            System.out.println("Selecciona el índice del arma a eliminar:");
                            for (int i = 0; i < weapons.size(); i++) {
                                Weapon weapon = weapons.get(i);
                                System.out.println(i + ". " + weapon.getName() + " (Daño: " + weapon.getDamage()
                                        + ", Dos manos: " + weapon.isTwoHands() + ")");
                            }
                            int index = scanner.nextInt();
                            if (index >= 0 && index < weapons.size()) {
                                Weapon removedWeapon = weapons.remove(index);
                                System.out.println("Arma eliminada: " + removedWeapon.getName());

                                activeWeapons.remove(removedWeapon);
                            } else {
                                System.out.println("Índice inválido.");
                            }
                        }

                    } else if (weaponOption == 3) {
                        if (weapons.isEmpty()) {
                            System.out.println("No tienes armas para activar.");
                        } else {
                            System.out.println("Selecciona el índice del arma a activar:");
                            for (int i = 0; i < weapons.size(); i++) {
                                Weapon weapon = weapons.get(i);
                                System.out.println(i + ". " + weapon.getName() + " (Daño: " + weapon.getDamage()
                                        + ", Dos manos: " + weapon.isTwoHands() + ")");
                            }
                            int index = scanner.nextInt();
                            if (index >= 0 && index < weapons.size()) {
                                Weapon selectedWeapon = weapons.get(index);

                                if (selectedWeapon.isTwoHands()) {

                                    for (Weapon activeWeapon : activeWeapons) {
                                        weapons.add(activeWeapon);
                                    }
                                    activeWeapons.clear();
                                    activeWeapons.add(selectedWeapon);
                                    weapons.remove(selectedWeapon);
                                    System.out.println(
                                            "Arma activa actualizada: " + selectedWeapon.getName() + " (Dos manos)");
                                } else {
                                    if (activeWeapons.size() == 1 && !activeWeapons.get(0).isTwoHands()) {
                                        activeWeapons.add(selectedWeapon);
                                        weapons.remove(selectedWeapon);
                                        System.out.println("Ambas armas están activas: "
                                                + activeWeapons.get(0).getName() + " y " + selectedWeapon.getName());
                                    } else {
                                        for (Weapon activeWeapon : activeWeapons) {
                                            weapons.add(activeWeapon);
                                        }
                                        activeWeapons.clear();
                                        activeWeapons.add(selectedWeapon);
                                        weapons.remove(selectedWeapon);
                                        System.out.println("Arma activa actualizada: " + selectedWeapon.getName());
                                    }
                                }
                            } else {
                                System.out.println("Índice inválido.");
                            }
                        }
                    } else {
                        System.out.println("Opción inválida.");
                    }
                    break;

                case 4:
                    System.out.println("Armaduras actuales:");
                    List<Armor> armors = character.getArmor();
                    List<Armor> activeArmors = character.getActiveArmor();

                    if (armors.isEmpty()) {
                        System.out.println("No tienes armaduras.");
                    } else {
                        for (int i = 0; i < armors.size(); i++) {
                            Armor armor = armors.get(i);
                            System.out.println(i + ". " + armor.getName() + " (Defensa: " + armor.getPower()
                                    + ", Precio: " + armor.getPrice() + ")");
                        }
                    }

                    System.out.println(
                            "¿Deseas añadir, eliminar o cambiar armadura activa? (1: Añadir, 2: Eliminar, 3: Cambiar armadura activa)");
                    int armorOption = scanner.nextInt();
                    scanner.nextLine();

                    if (armorOption == 1) {
                        System.out.print("Introduce el nombre de la armadura: ");
                        String newArmorName = scanner.nextLine();
                        System.out.print("Introduce el valor de defensa de la armadura: ");
                        int power = scanner.nextInt();
                        System.out.print("Introduce el precio de la armadura: ");
                        int price = scanner.nextInt();

                        Armor newArmor = new Armor(power, price, newArmorName);
                        armors.add(newArmor);
                        System.out.println("Armadura añadida: " + newArmor.getName());

                    } else if (armorOption == 2) {
                        if (armors.isEmpty()) {
                            System.out.println("No tienes armaduras para eliminar.");
                        } else {
                            System.out.println("Selecciona el índice de la armadura a eliminar:");
                            for (int i = 0; i < armors.size(); i++) {
                                Armor armor = armors.get(i);
                                System.out.println(i + ". " + armor.getName() + " (Defensa: " + armor.getPower()
                                        + ", Precio: " + armor.getPrice() + ")");
                            }
                            int index = scanner.nextInt();
                            if (index >= 0 && index < armors.size()) {
                                Armor removedArmor = armors.remove(index);
                                System.out.println("Armadura eliminada: " + removedArmor.getName());
                                activeArmors.remove(removedArmor);
                            } else {
                                System.out.println("Índice inválido.");
                            }
                        }

                    } else if (armorOption == 3) {
                        if (armors.isEmpty()) {
                            System.out.println("No tienes armaduras para activar.");
                        } else {
                            System.out.println("Selecciona el índice de la armadura a activar:");
                            for (int i = 0; i < armors.size(); i++) {
                                Armor armor = armors.get(i);
                                System.out.println(i + ". " + armor.getName() + " (Defensa: " + armor.getPower()
                                        + ", Precio: " + armor.getPrice() + ")");
                            }
                            int index = scanner.nextInt();
                            if (index >= 0 && index < armors.size()) {
                                Armor selectedArmor = armors.get(index);

                                if (!activeArmors.isEmpty()) {
                                    Armor previousActiveArmor = activeArmors.get(0);
                                    armors.add(previousActiveArmor);
                                }

                                activeArmors.clear();
                                activeArmors.add(selectedArmor);
                                armors.remove(selectedArmor);
                                System.out.println("Armadura activa actualizada: " + selectedArmor.getName());
                            } else {
                                System.out.println("Índice inválido.");
                            }
                        }
                    } else {
                        System.out.println("Opción inválida.");
                    }
                    break;

                case 5:
                    System.out.println("Minions actuales:");
                    List<Minion> minions = character.getMinions();
                    if (minions.isEmpty()) {
                        System.out.println("No tienes minions.");
                    } else {
                        for (int i = 0; i < minions.size(); i++) {
                            Minion minion = minions.get(i);
                            System.out.println(i + ". " + minion.getName() + " (Salud: " + minion.getHealth() + ")");
                        }
                    }

                    System.out.println("¿Deseas añadir o eliminar minions? (1: Añadir, 2: Eliminar)");
                    int minionOption = scanner.nextInt();
                    scanner.nextLine();

                    if (minionOption == 1) {
                        System.out.println("Selecciona el tipo de minion a añadir:");
                        System.out.println("1. Composite Demon Minion");
                        System.out.println("2. Human Minion");
                        System.out.println("3. Ghoul Minion");
                        int minionType = scanner.nextInt();
                        scanner.nextLine();

                        if (minionType == 1) {
                            System.out.print("Introduce el nombre del minion: ");
                            String name = scanner.nextLine();
                            System.out.print("Introduce la salud del minion: ");
                            int health = scanner.nextInt();
                            scanner.nextLine();
                            System.out.print("Introduce el pacto del minion: ");
                            String pact = scanner.nextLine();

                            Minion newMinion = new CompositeDemonMinion(name, health, pact);
                            minions.add(newMinion);
                            System.out.println("Minion añadido: " + newMinion.getName());

                        } else if (minionType == 2) {
                            System.out.print("Introduce el nombre del minion: ");
                            String name = scanner.nextLine();
                            System.out.print("Introduce la salud del minion: ");
                            int health = scanner.nextInt();
                            scanner.nextLine();
                            System.out.print("Introduce la lealtad del minion: ");
                            String loyalty = scanner.nextLine();

                            Minion newMinion = new HumanMinion(name, health, loyalty, new ArrayList<>());
                            minions.add(newMinion);
                            System.out.println("Minion añadido: " + newMinion.getName());

                        } else if (minionType == 3) {
                            System.out.print("Introduce el nombre del minion: ");
                            String name = scanner.nextLine();
                            System.out.print("Introduce la salud del minion: ");
                            int health = scanner.nextInt();
                            System.out.print("Introduce la dependencia del minion: ");
                            int dependency = scanner.nextInt();
                            Minion newMinion = new GhoulMinion(name, health, dependency);
                            minions.add(newMinion);
                            System.out.println("Minion añadido: " + newMinion.getName());

                        } else {
                            System.out.println("Tipo de minion inválido.");
                        }

                    } else if (minionOption == 2) {
                        if (minions.isEmpty()) {
                            System.out.println("No tienes minions para eliminar.");
                        } else {
                            System.out.println("Selecciona el índice del minion a eliminar:");
                            for (int i = 0; i < minions.size(); i++) {
                                Minion minion = minions.get(i);
                                System.out
                                        .println(i + ". " + minion.getName() + " (Salud: " + minion.getHealth() + ")");
                            }
                            int index = scanner.nextInt();
                            if (index >= 0 && index < minions.size()) {
                                Minion removedMinion = minions.remove(index);
                                System.out.println("Minion eliminado: " + removedMinion.getName());
                            } else {
                                System.out.println("Índice inválido.");
                            }
                        }
                    } else {
                        System.out.println("Opción inválida.");
                    }
                    break;

                case 6:
                    editing = false;
                    System.out.println("Saliendo del editor de personaje...");
                    break;

                default:
                    System.out.println("Opción no válida. Intenta de nuevo.");
            }
        }
    }

    public static GameCharacter createCharacter(Scanner scanner) {
        System.out.println("=== Creación de Personaje ===");

        System.out.print("Introduce el nombre del personaje: ");
        String name = scanner.nextLine().trim();
        while (name.isEmpty()) {
            System.out.print("El nombre no puede estar vacío. Intenta de nuevo: ");
            name = scanner.nextLine().trim();
        }

        System.out.println("Selecciona el tipo de personaje:");
        System.out.println("1. Hunter");
        System.out.println("2. Vampire");
        System.out.println("3. Lycanthrope");
        int type = 0;
        while (type < 1 || type > 3) {
            System.out.print("Introduce un número válido (1-3): ");
            if (scanner.hasNextInt()) {
                type = scanner.nextInt();
            } else {
                scanner.next();
            }
        }
        scanner.nextLine();

        System.out.print("Introduce los puntos de vida iniciales: ");
        int health = scanner.nextInt();
        System.out.print("Introduce el poder principal del personaje: ");
        int power = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Introduce el nombre de la fortaleza: ");
        String strengthName = scanner.nextLine().trim();
        System.out.print("Introduce los puntos de la fortaleza (1-5): ");
        int strengthValue = 0;
        while (strengthValue < 1 || strengthValue > 5) {
            System.out.print("Introduce un valor válido (1-5): ");
            if (scanner.hasNextInt()) {
                strengthValue = scanner.nextInt();
            } else {
                scanner.next();
            }
        }
        scanner.nextLine();
        Modifiers strength = new Modifiers(strengthName, strengthValue);

        System.out.print("Introduce el nombre de la debilidad: ");
        String weaknessName = scanner.nextLine().trim();
        System.out.print("Introduce los puntos de la debilidad (1-5): ");
        int weaknessValue = 0;
        while (weaknessValue < 1 || weaknessValue > 5) {
            System.out.print("Introduce un valor válido (1-5): ");
            if (scanner.hasNextInt()) {
                weaknessValue = scanner.nextInt();
            } else {
                scanner.next();
            }
        }
        scanner.nextLine();
        Modifiers weakness = new Modifiers(weaknessName, weaknessValue);

        List<Modifiers> strengths = new ArrayList<>();
        strengths.add(strength);

        List<Modifiers> weaknesses = new ArrayList<>();
        weaknesses.add(weakness);

        System.out.println("Fortaleza añadida: " + strength.getName() + " (Valor: " + strength.getValue() + ")");
        System.out.println("Debilidad añadida: " + weakness.getName() + " (Valor: " + weakness.getValue() + ")");

        int goodness = 0, bloodPoints = 0, age = 0, height = 0, rage = 0;
        switch (type) {
            case 1:
                System.out.print("Introduce nivel de bondad (0-100): ");
                while (goodness <= 0 || goodness >= 100) {
                    if (scanner.hasNextInt()) {
                        goodness = scanner.nextInt();
                    } else {
                        scanner.next();
                    }
                }
                break;
            case 2:
                System.out.print("Introduce BloodPoints iniciales: ");
                bloodPoints = scanner.nextInt();
                System.out.print("Introduce edad del vampiro: ");
                age = scanner.nextInt();
                break;
            default:
                System.out.print("Introduce edad del licántropo: ");
                age = scanner.nextInt();
                System.out.print("Introduce altura del licántropo: ");
                height = scanner.nextInt();
                rage = 0;
                break;
        }
        scanner.nextLine();

        List<SpecialAbility> specialAbilities = new ArrayList<>();
        List<Weapon> weapons = new ArrayList<>();
        List<Weapon> activeWeapons = new ArrayList<>();
        List<Armor> armors = new ArrayList<>();
        List<Armor> activeArmors = new ArrayList<>();
        List<Minion> minions = new ArrayList<>();
        int gold = 0;

        GameCharacter character;
        if (type == 1) {
            character = new Hunter(name, specialAbilities, weapons, activeWeapons,
                    armors, activeArmors, minions, gold, health, power,
                    weaknesses, strengths, goodness);
        } else if (type == 2) {
            character = new Vampire(name, specialAbilities, weapons, activeWeapons,
                    armors, activeArmors, minions, gold, health, power,
                    weaknesses, strengths, bloodPoints, age);
        } else {
            character = new Lycanthrope(name, age, height, specialAbilities,
                    weapons, activeWeapons, armors, activeArmors, minions,
                    health, power, power, weaknesses, strengths);
        }

        System.out.println(
                "Personaje completado: " + character.getName() + " (" + character.getClass().getSimpleName() + ")");
        return character;
    }
}
