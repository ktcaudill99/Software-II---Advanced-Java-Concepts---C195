
package constructors;

/**
 *  Constructor for the Contact
 *  This class represents a Contact object with all the necessary fields
 *
 */
public class Contact {
    private int contactId;  // Unique identifier for a contact
    private String contactName;  // Name of the contact
    private String email;  // Email of the contact
    // This is the constructor for the Contact class
    public Contact(int contactId, String contactName, String email) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.email = email;
    }
}
