import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;

import java.util.Scanner;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class UserTest {
    private User testUser;
    private User testRepUser;
    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream outContent;

    // Minimal concrete subclass to test abstract User
    private static class TestUser extends User {
        public TestUser(String name, String role) {
            super(name, role);
        }

        @Override
        public void displayRole() {
            System.out.println(role);
        }
    }

    @BeforeEach
    void setUp() {
        testUser = new TestUser("Alice", "Scientist");
        testRepUser = new TestUser("Bob", "SpaceAgencyRepresentative");
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        testUser = null;
        testRepUser = null;
        System.setOut(originalOut);
    }

    @AfterAll
    static void afterAllTests() {
        System.out.println("All User tests completed.");
    }

    @Test
    void testGettersAndSetters() {
        // Scientist user
        assertEquals("Alice", testUser.getName());
        assertEquals("Scientist", testUser.getRole());
        assertNull(testUser.getPassword());

        //Change Alice to a different name and role, set password
        testUser.setName("AliceChanged");
        testUser.setRole("Admin");
        testUser.setPassword("pass123");

        assertEquals("AliceChanged", testUser.getName());
        assertEquals("Admin", testUser.getRole());
        assertEquals("pass123", testUser.getPassword());

        // Representative user
        assertEquals("Bob", testRepUser.getName());
        assertEquals("SpaceAgencyRepresentative", testRepUser.getRole());
        assertNull(testRepUser.getPassword());

        testRepUser.setName("BobChanged");
        testRepUser.setRole("PolicyMaker");
        testRepUser.setPassword("repPass");

        assertEquals("BobChanged", testRepUser.getName());
        assertEquals("PolicyMaker", testRepUser.getRole());
        assertEquals("repPass", testRepUser.getPassword());
    }

    @Test
    void testToCsv() {
        // Scientist
        assertEquals("Alice,Scientist,null", testUser.toCsv());
        testUser.setPassword("secret");
        assertEquals("Alice,Scientist,secret", testUser.toCsv());

        // Representative
        assertEquals("Bob,SpaceAgencyRepresentative,null", testRepUser.toCsv());
        testRepUser.setPassword("secRep");
        assertEquals("Bob,SpaceAgencyRepresentative,secRep", testRepUser.toCsv());
    }

    @Test
    void testPromptNewPassword() {
        // Scientist
        String inputUser = "newpass\n";
        Scanner scannerUser = new Scanner(new ByteArrayInputStream(inputUser.getBytes()));
        testUser.promptNewPassword(scannerUser);
        assertTrue(outContent.toString().contains("Please enter this user's password:"));
        assertEquals("newpass", testUser.getPassword());
        outContent.reset();

        // Representative
        String inputRep = "repNew\n";
        Scanner scannerRep = new Scanner(new ByteArrayInputStream(inputRep.getBytes()));
        testRepUser.promptNewPassword(scannerRep);
        assertTrue(outContent.toString().contains("Please enter this user's password:"));
        assertEquals("repNew", testRepUser.getPassword());
    }

    @Test
    void testDisplayRolePrint() {
        // Scientist
        testUser.displayRole();
        assertTrue(outContent.toString().contains("Scientist"));
        outContent.reset();

        // Representative
        testRepUser.displayRole();
        assertTrue(outContent.toString().contains("SpaceAgencyRepresentative"));
    }
}
