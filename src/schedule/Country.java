package schedule;

import java.time.LocalDateTime;
import java.sql.Timestamp;

public class Country {
    private int countryId;
    private String country;
    private LocalDateTime createdDate;
    private String createdBy;
    private Timestamp lastUpdated;
    private String lastUpdatedBy;

    public Country(int countryId, String country, LocalDateTime createdDate, String createdBy, Timestamp lastUpdated, String lastUpdatedBy) {
        this.countryId = countryId;
        this.country = country;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.lastUpdated = lastUpdated;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    // getters and setters

    @Override
    public String toString() {
        return country;
    }
}
