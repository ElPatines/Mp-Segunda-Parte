package test;

import GameManager.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedHashMap;
import java.util.List;

public class SystemGameTest {

    private SystemGame systemGame;

    @BeforeEach
    public void setUp() {
        systemGame = SystemGame.getInstanceOfSystemGame();
    }

    @Test
    public void testInsertNewPlayer() {
        String userName = "testUser";
        String password = "Test123!";
        String userCode = systemGame.newPlayer(userName, password);

        assertNotEquals("-1", userCode, "El código de usuario no debe ser -1");
        Player insertedPlayer = (Player) systemGame.getUser(userCode);
        assertNotNull(insertedPlayer, "El jugador debería haberse insertado en userList");
        assertEquals(userName, insertedPlayer.getUserName());
        assertEquals(password, insertedPlayer.getPassword());
    }

    @Test
    public void testInsertNewAdmin() {
        String userName = "adminTest";
        String password = "AdminPass123!";
        String userCode = systemGame.newAdmin(userName, password);

        assertNotEquals("-1", userCode, "El código de admin no debe ser -1");

        List<Admin> admins = systemGame.getAdapter().loadAdminData();
        boolean found = admins.stream()
                .anyMatch(admin -> admin.getUserName().equals(userName) && admin.getPassword().equals(password));
        assertTrue(found, "El nuevo admin debería haberse insertado en adminList");
    }

    @Test
    public void testChangePlayerUpdatesList() {
        String userName = "changeTest";
        String password = "Change123!";
        String userCode = systemGame.newPlayer(userName, password);

        Player player = (Player) systemGame.getUser(userCode);
        player.setPassword("NewPassword123!");
        systemGame.changePlayer(userCode, player);

        Player updatedPlayer = (Player) systemGame.getUser(userCode);
        assertEquals("NewPassword123!", updatedPlayer.getPassword(), "La contraseña del jugador debe actualizarse");
    }

    @Test
    public void testAdminSearchReturnsValidAdmin() {
        Admin admin = systemGame.searchAdmin();
        assertNotNull(admin, "searchAdmin debe devolver un administrador no nulo");
    }
}
