
package constructors;

import java.time.LocalDateTime;
import java.sql.Timestamp;

/**
 * Represents a country with associated metadata.
 * This class encapsulates the information related to a country, including its ID, name, creation date, creator, last update timestamp, and last updated by information.
 */

public class Country {
    private int countryId; // Unique identifier for the country
    private String country; // Name of the country
    private LocalDateTime createdDate; // Date and time the country was created
    private String createdBy; // The user who created the country
    private Timestamp lastUpdated; // Timestamp of the last update
    private String lastUpdatedBy; // The user who last updated the country

    /**
     * Creates a Country object with the specified details.
     *
     * @param countryId      the unique identifier for the country
     * @param country        the name of the country
     * @param createdDate    the date and time the country was created
     * @param createdBy      the user who created the country
     * @param lastUpdated    the timestamp of the last update
     * @param lastUpdatedBy  the user who last updated the country
     */
    public Country(int countryId, String country, LocalDateTime createdDate, String createdBy, Timestamp lastUpdated, String lastUpdatedBy) {
        this.countryId = countryId;
        this.country = country;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.lastUpdated = lastUpdated;
        this.lastUpdatedBy = lastUpdatedBy;
    }
    /**
     * Creates a Country object with the specified ID and name.
     *
     * @param countryId    the unique identifier for the country
     * @param countryName  the name of the country
     */
    public Country(int countryId, String countryName) {
    }

    /**
     * Retrieves the unique identifier for the country.
     *
     * @return the country ID
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * Returns the name of the country.
     *
     * @return the country name
     */
    @Override
    public String toString() {
        return country;
    }
}