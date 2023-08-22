
package constructors;

/**
 * Represents a contact with an ID, name, and email address.
 * This class encapsulates the information related to a contact in the system.
 */
public class Contact {
    private int contactId; // Unique identifier for a contact
    private String contactName; // Name of the contact
    private String email; // Email of the contact

    /**
     * Creates a Contact object with the specified ID, name, and email.
     *
     * @param contactId    the unique identifier for the contact
     * @param contactName  the name of the contact
     * @param email        the email address of the contact
     */
    public Contact(int contactId, String contactName, String email) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.email = email;
    }
}

