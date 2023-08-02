
package constructors;

/**
 * Represents a user within the system.
 * The class encapsulates details such as user ID, username, and password.
 * Note: The userID, username, and password fields are defined as static, meaning they are shared across all instances of the class.
 */
public class User {
    //properties
    private static int userID; //auto incremented in database
    private static String username;
    private static String password;
    public User() {
        userID = 0;
        username = null;
        password = null;

    }

    /**
     * Default constructor that initializes the user with default values.
     */
    public User(int userID, String username, String password) {
        this.userID = userID;
        this.username = username;
        this.password = password;
    }


    /**
     * Gets the user's unique identifier.
     *
     * @return the user ID
     */
    public static int getUserID() {
        return userID;
    }
    /**
     * Gets the user's username.
     *
     * @return the username
     */
    public static String  getUsername() {
        return username;
    }
    /**
     * Gets the user's password.
     *
     * @return the password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Sets the user's unique identifier.
     *
     * @param userID the user ID
     */
    public static void setUserID(int userID) {
        User.userID = userID;
    }
    /**
     * Sets the user's username.
     *
     * @param username the username
     */
    public static void setUsername(String username) {
        User.username = username;
    }
    /**
     * Sets the user's password.
     *
     * @param password the password
     */
    public static void setPassword(String password) {
        User.password = password;
    }
    
}
