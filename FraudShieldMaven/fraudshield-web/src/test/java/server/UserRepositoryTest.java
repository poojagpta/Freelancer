package server;



import org.junit.Before;
import org.junit.Test;

import com.googlecode.objectify.ObjectifyService;
import com.stern.fraudshields.model.Customer;
import com.stern.fraudshields.server.UserRepository;

/**
 * Tests {@link MessageRepository} class.
 *
 * @author androns
 */
public class UserRepositoryTest extends LocalDatastoreTest {

    private UserRepository userRepository;

    /**
     * @see LocalDatastoreTest#setUp()
     */
    @Before
    @Override
    public void setUp() {
        super.setUp();
        this.userRepository = new UserRepository();
        ObjectifyService.register(Customer.class);
    }

    /**
     *
     */
    @Test
    public void smokeTest() {
        //assertNotNull(this.userRepository);

        // create
   /*     final User message = null;//new User("Test message");

        this.userRepository.create(message);

        // read
        Collection<User> users = this.userRepository.getAll();

        //assertNotNull(users);
        //assertEquals(1, users.size());
        //final User user = users.iterator().next();

        //assertNotNull(user.getId());
        //assertEquals(message.getText(), storedMessage.getText());

        // delete
        //this.userRepository.deleteById(user.getId());

        users = this.userRepository.getAll();
        //assertEquals(0, users.size());*/
    }
}
