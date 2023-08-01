
package constructors;

import java.time.LocalDateTime;
import java.sql.Timestamp;

public class Country {
    private int countryId;
    private String country;
    private LocalDateTime createdDate;
    private String createdBy;
    private Timestamp lastUpdated;
    private String lastUpdatedBy;
// This is the constructor for the Country class
    public Country(int countryId, String country, LocalDateTime createdDate, String createdBy, Timestamp lastUpdated, String lastUpdatedBy) {
        this.countryId = countryId;
        this.country = country;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.lastUpdated = lastUpdated;
        this.lastUpdatedBy = lastUpdatedBy;
    }
    public Country(int countryId, String countryName) {
    }
    // getters and setters
    public int getCountryId() {
        return countryId;
    }
    @Override
    public String toString() {
        return country;
    }
}
