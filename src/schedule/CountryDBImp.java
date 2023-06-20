/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schedule;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import schedule.Country;

/**
 *
 * @author Katie-BAMF
 */
//public class CountryDBImp {
public interface CountryDBImp{
    @FXML
    public ObservableList<Country> getAllCountries();
}