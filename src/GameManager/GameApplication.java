package GameManager;
public class GameApplication {
    public static void main(String[] args) throws InterruptedException {
        SystemGame s = SystemGame.getInstanceOfSystemGame();
        s.showStartScreen();
    }
}