import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class AdministratorTest {
    private Administrator admin;
    private TestDataManager testManager;
    private Map<String, User> users;
    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream outContent;

    // Stub DataManager to satisfy manager.setUsers calls without file I/O
    private static class TestDataManager extends DataManager {
        public TestDataManager() {
            super("users"); // calls DataManager(String) constructor
        }

        @Override
        public void setUsers(Map<String, User> users) {
            // no-op stub
        }
    }

    @BeforeEach
    void setUp() {
        admin = new Administrator("AdminName");
        testManager = new TestDataManager();
        admin.setManager(testManager);
        users = new HashMap<>();
        admin.setUsers(users);
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @AfterAll
    static void afterAllTests() {
        System.out.println("All Administrator tests completed.");
    }

    @Test
    void testCreateUserWithNullsThrows() {
        assertThrows(IllegalArgumentException.class, () -> admin.createUser(null, "Name", new Scanner("")));
        assertThrows(IllegalArgumentException.class, () -> admin.createUser("Scientist", null, new Scanner("")));
    }

    @Test
    void testCreateScientistUser() {
        String input = "pass\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        admin.createUser("Scientist", "DrX", scanner);
        assertTrue(users.containsKey("DrX"));
        User u = users.get("DrX");
        assertEquals("DrX", u.getName());
        assertEquals("Scientist", u.getRole());
        assertEquals("pass", u.getPassword());
    }

    // User doesn't have control over this spacing mismatch, however we could implement a fix for a more robust method in the future.
    @Test
    void testCreateSpaceAgencyRepresentativeUser() {
        String input = "repPass\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        admin.createUser("Space Agency Representative", "RepY", scanner);
        assertTrue(users.containsKey("RepY"));
        User u = users.get("RepY");
        assertEquals("RepY", u.getName());
        assertEquals("SpaceAgencyRepresentative", u.getRole());
        assertEquals("repPass", u.getPassword());
    }

    @Test
    void testCreateAdministratorUser() {
        String input = "adminPass\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        admin.createUser("Administrator", "SubAdmin", scanner);
        assertTrue(users.containsKey("SubAdmin"));
        User u = users.get("SubAdmin");
        assertEquals("SubAdmin", u.getName());
        assertEquals("Administrator", u.getRole());
        assertEquals("adminPass", u.getPassword());
    }

    @Test
    void testCreateInvalidUserTypeThrows() {
        assertThrows(IllegalArgumentException.class,
            () -> admin.createUser("InvalidType", "NameZ", new Scanner("")));
    }

    @Test
    void testManageUserChangeNameRolePasswordAndInvalidInput() {
        // create initial user
        String initialPass = "old\n";
        Scanner initScanner = new Scanner(new ByteArrayInputStream(initialPass.getBytes()));
        admin.createUser("Scientist", "ManageMe", initScanner);

        // simulate invalid input then changes
        StringBuilder sb = new StringBuilder();
        sb.append("abc\n"); // invalid choice
        sb.append("1\nNewName\n"); // change name
        sb.append("2\nNewRole\n"); // change role
        sb.append("3\nNewPass\n"); // change password
        sb.append("4\n"); // exit
        Scanner manageScanner = new Scanner(new ByteArrayInputStream(sb.toString().getBytes()));

        admin.manageUser("ManageMe", manageScanner);
        String output = outContent.toString();
        assertTrue(output.contains("Invalid input. Please enter a number 1-4"));

        User u = admin.getUserByName("ManageMe");
        assertNotNull(u);
        assertEquals("NewName", u.getName());
        assertEquals("NewRole", u.getRole());
        assertEquals("NewPass", u.getPassword());
    }

    @Test
    void testManageUserNotFound() {
        admin.manageUser("NoSuchUser", new Scanner(""));
        assertTrue(outContent.toString().contains("No user found with Name: NoSuchUser"));
    }

    @Test
    void testDeleteUserSuccessAndFailure() {
        admin.createUser("Scientist", "ToDelete", new Scanner(new ByteArrayInputStream("pwd\n".getBytes())));
        admin.deleteUser("ToDelete");
        assertFalse(users.containsKey("ToDelete"));
        assertTrue(outContent.toString().contains("Deleted user with Name: ToDelete"));

        outContent.reset();
        admin.deleteUser("Ghost");
        assertTrue(outContent.toString().contains("No user found with Name: Ghost"));
    }

    @Test
    void testDisplayRoleAndGetUserByName() {
        outContent.reset();
        admin.displayRole();
        assertTrue(outContent.toString().contains("Administrator: AdminName"));

        assertNull(admin.getUserByName("Anyone"));
        admin.createUser("Scientist", "FindMe", new Scanner(new ByteArrayInputStream("pw\n".getBytes())));
        assertNotNull(admin.getUserByName("FindMe"));
    }
}
