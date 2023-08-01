
package constructors;

import java.time.LocalDateTime;

/**
 * FirstLevelDivisions
 * Definitions to be used by the system for the First_Level_Divisions table
 */
public class FirstLevelDivisions {
    private int divisionId;
    private String division;
    private String createDate;
    private String createdBy;
    private String lastUpdated;
    private String lastUpdatedBy;
    private int countryId;
// This is the constructor for the FirstLevelDivisions class
    public FirstLevelDivisions(int divisionId, String division, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy, int countryId) {
    }

    public FirstLevelDivisions(int divisionId, String division, int countryId) {
        this.divisionId = divisionId;
        this.division = division;
        this.countryId = countryId;
    }
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
// getters and setters
    public int getDivisionId() {
        return divisionId;
    }
   public String getDivision() {
        return division;
    }
    public void setDivision(String division) {
        this.division = division;
    }
    @Override
    public String toString() {
        return division;
    }
    public int getCountryId() {
        return countryId;
    }
}
