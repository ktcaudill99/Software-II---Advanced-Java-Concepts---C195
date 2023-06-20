/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schedule;

import java.security.Timestamp;
import java.time.LocalDateTime;

/**
 *
 * @author Katie-BAMF
 */
public class Country {
   private int countryId;
    private String country;
    private LocalDateTime createdDate;
    private String createdBy;
    private Timestamp lastUpdated;
    private String lastUpdatedBy;

    /**
     * Country constructor
      * @param countryId country id
     * @param country country name
     * @param createdDate date and time created
     * @param createdBy  user's name who created the country
     * @param lastUpdated date and time country was last updated
     * @param lastUpdatedBy user's name who last updated the country
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
     * setCountryId
     * @param countryId sets the country id
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /**
     * getCountryId
     * @return returns the country id
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * setCountry
     * @param country sets the country name
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * getCountry
     * @return returns the country name
     */
    public String getCountry() {
        return country;
    }

    /**
     * setCreatedDate
     * @param createdDate set the date and time the country was created in the db
     */
    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * getCreatedDate
     * @return returns the date and time the country was created in the db
     */
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    /**
     * setCreatedBy
     * @param createdBy sets the user's name that created the country db entry
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * getCreatedBy
     * @return returns the user's name that created the country db entry
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * setLastUpdated
     * @param lastUpdated sets the date and time if the country info is updated/changed (default is the createdDate)
     */
    public void setLastUpdated(Timestamp lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    /**
     * getLastUpdated
     * @return returns the date and time if the country info is updated/changed (default is the createdDate)
     */
    public Timestamp getLastUpdated() {
        return lastUpdated;
    }

    /**
     * setLastUpdatedBy
     * @param lastUpdatedBy sets the user's name who updated/changed the country info
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * getLastUpdatedBy
     * @return returns the user's name who updated/changed the country info
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * toString
     * @return Overrides the default toString() method to return the country name.
     */
    @Override
    public String toString() {
        return country;
    }

}