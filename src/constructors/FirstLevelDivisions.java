
package constructors;

import java.time.LocalDateTime;

/**
 * Represents the first-level division of a country.
 * This class encapsulates details such as division ID, division name, creation date, creator, last update date, last updated by, and country ID.
 */
public class FirstLevelDivisions {
    private int divisionId;
    private String division;
    private String createDate;
    private String createdBy;
    private String lastUpdated;
    private String lastUpdatedBy;
    private int countryId;

    /**
     * Constructs a FirstLevelDivisions object with all details.
     *
     * @param divisionId       the division's unique ID
     * @param division         the division's name
     * @param createDate       the division's creation date
     * @param createdBy        the creator of the division
     * @param lastUpdate       the division's last update date
     * @param lastUpdatedBy    the last person who updated the division
     * @param countryId        the division's country ID
     */
    public FirstLevelDivisions(int divisionId, String division, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy, int countryId) {
    }

    /**
     * Constructs a FirstLevelDivisions object with essential details.
     *
     * @param divisionId the division's unique ID
     * @param division   the division's name
     * @param countryId  the division's country ID
     */
    public FirstLevelDivisions(int divisionId, String division, int countryId) {
        this.divisionId = divisionId;
        this.division = division;
        this.countryId = countryId;
    }


    /**
     * Constructs a FirstLevelDivisions object with specific details.
     *
     * @param divisionId       the division's unique ID
     * @param division         the division's name
     * @param createDate       the division's creation date
     * @param createdBy        the creator of the division
     * @param lastUpdated      the division's last update date
     * @param lastUpdatedBy    the last person who updated the division
     * @param countryId        the division's country ID
     */
    public FirstLevelDivisions(int divisionId, String division, String createDate, String createdBy, String lastUpdated,
                               String lastUpdatedBy, int countryId) {
        this.divisionId = divisionId;
        this.division = division;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdated = lastUpdated;
        this.lastUpdatedBy = lastUpdatedBy;
        this.countryId = countryId;
    }
    /**
     * Gets the division's unique ID.
     *
     * @return the division ID
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * Gets the division's name.
     *
     * @return the division name
     */
    public String getDivision() {
        return division;
    }

    /**
     * Sets the division's name.
     *
     * @param division the division name
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * Returns the division's name as a string representation of the object.
     *
     * @return the division name
     */
    @Override
    public String toString() {
        return division;
    }

    /**
     * Gets the division's country ID.
     *
     * @return the country ID
     */
    public int getCountryId() {
        return countryId;
    }
}