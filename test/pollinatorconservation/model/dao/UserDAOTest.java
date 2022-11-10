package pollinatorconservation.model.dao;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import pollinatorconservation.model.pojo.Role;
import pollinatorconservation.model.pojo.User;
import pollinatorconservation.util.Constants;
import pollinatorconservation.util.Utilities;

public class UserDAOTest {

    static User user;
    
    @BeforeClass
    public static void setUp() throws NoSuchAlgorithmException {
        user = new User();
        user.setName("María José");
        user.setPaternalSurname("Torres");
        user.setMaternalSurname("Igartua");
        user.setUsername("majotigartua");
        String password = Utilities.computeSHA256Hash("torresigartua");
        user.setPassword(password);
        Role role = new Role();
        role.setIdRole(Constants.ID_SCIENTIFIC_RESEARCHER_ROLE);
        user.setRole(role);
    }

    @Test
    public void loginTest() throws SQLException {
        String username = user.getUsername();
        String password = user.getPassword();
        User test = UserDAO.login(username, password);
        assertEquals(test, user);
    }

}