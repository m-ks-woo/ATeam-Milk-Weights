package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

/**
 * Defines a FarmTable class for storing all of the Farms in a HashMap where the keys are the ids 
 * of the farms
 * @author Matthew Woo, Param Bhandare, Russell Cheng
 */
public class FarmTable {
  private HashMap<String, Farm> farms;
  
  /**
   * Initializes the HashMap of farms
   */
  public FarmTable() {
    this.farms = new HashMap<>();
  }
  
  /**
   * Loads the data for a given csvfile that was selected from the main screen.
   * @param csvfile the csv file to load data from
   * @throws IOException if there was an error reading the file
   */
  public void loadData(File csvfile) throws Exception  {
    BufferedReader br = new BufferedReader(new FileReader(csvfile));
    String line = br.readLine(); // skip title line
    while ((line = br.readLine()) != null) {
      String[] data = line.split(",");
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d");
      Entry e = new Entry(LocalDate.parse(data[0], formatter), data[1], Integer.parseInt(data[2]));
      if (farms.keySet().contains(e.getFarmId())) {
        farms.get(e.getFarmId()).addEntry(e);
      } else {
        Farm farm = new Farm(e.getFarmId());
        farm.addEntry(e);
        farms.put(e.getFarmId(), farm);
      }
    }
    br.close();
  }
  
  /**
   * Returns the HashMap of farms
   * @return the HashMap of farms
   */
  public HashMap<String, Farm> getFarms() {
    return farms;
  }
  
  /**
   * Returns the Farm associated with the given farmId
   * @param farmId the id of the farm to get
   * @return the Farm associated with the given farmId
   */
  public Farm getFarm(String farmId) {
    return farms.get(farmId);
  }
  
  /**
   * Adds a given entry to the correct Farm in the FarmTable
   * @param entry the Entry to add
   */
  public void addEntry(Entry entry) {
    String farmId = entry.getFarmId();
    if (!farms.keySet().contains(farmId)) {
      farms.put(farmId, new Farm(farmId));
    }
    farms.get(farmId).addEntry(entry);
  }
  
  /**
   * Adds a new entry to the correct farm in the FarmTable
   * @param date the date of the entry
   * @param farmId the Farm to add the entry to
   * @param weight the recorded weight on the given date
   */
  public void addEntry(LocalDate date, String farmId, int weight) {
    if (!farms.keySet().contains(farmId)) {
      farms.put(farmId, new Farm(farmId));
    }
    farms.get(farmId).addEntry(date, weight);
  }
  
  /**
   * Removes a given entry from the correct farm in the FarmTable
   * @param entry the entry to remove
   * @return true if the entry was successfully removed and false otherwise
   */
  public boolean removeEntry(Entry entry) {
    String farmId = entry.getFarmId();
    if (!farms.keySet().contains(farmId)) return false;
    return farms.get(farmId).removeEntry(entry);
  }
  
  /**
   * Removes an entry at the given date from the given farmId in the FarmTable
   * @param date the date of the entry to remove
   * @param farmId the Farm to remove the entry from 
   * @return true if the entry was successfully removed and false otherwise
   */
  public boolean removeEntry(LocalDate date, String farmId) {
    if (!farms.keySet().contains(farmId)) return false;
    return farms.get(farmId).removeEntry(date);
  }
  
  /**
   * Edits all of the attributes of a given entry
   * @param entry the entry to modify
   * @param newDate the new date for the entry
   * @param newFarmId the new farmId for the entry
   * @param newWeight the new weight for the entry
   * @return true if the entry was successfully edited and false otherwise
   */
  public boolean editEntry(Entry entry, LocalDate newDate, String newFarmId, int newWeight) {
    String farmId = entry.getFarmId();
    if (!farms.keySet().contains(farmId)) {
      return false;
    }
    farms.get(farmId).removeEntry(entry);
    this.addEntry(newDate, newFarmId, newWeight);
    return true;
  }
  
  /**
   * Edits the date of a given entry
   * @param entry the entry to modify
   * @param newDate the new date for the entry
   * @return true if the entry was successfully edited and false otherwise
   */
  public boolean editEntry(Entry entry, LocalDate newDate) {
    String farmId = entry.getFarmId();
    if (!farms.keySet().contains(farmId)) {
      return false;
    }
    farms.get(farmId).editEntry(newDate, entry.getWeight());
    return true;
  }
  
  /**
   * Edits the weight of a given entry
   * @param entry the entry to modify
   * @param newWeight the new weight for the entry
   * @return true if the entry was successfully edited and false otherwise
   */
  public boolean editEntry(Entry entry, int newWeight) {
    String farmId = entry.getFarmId();
    if (!farms.keySet().contains(farmId)) {
      return false;
    }
    farms.get(farmId).editEntry(entry.getDate(), newWeight);
    return true;
  }
  
  /**
   * Edits the farmId of the entry, moving it to a different farm in the FarmTable
   * @param entry the entry to modify
   * @param newFarmId the new farmId for the entry
   * @return true if the entry was successfully edited and false otherwise
   */
  public boolean editEntry(Entry entry, String newFarmId) {
    String farmId = entry.getFarmId();
    if (!farms.keySet().contains(farmId)) {
      return false;
    }
    if (!farms.keySet().contains(newFarmId)) {
      farms.put(newFarmId, new Farm(newFarmId));
    }
    farms.get(farmId).removeEntry(entry);
    entry.setFarmId(newFarmId);
    farms.get(newFarmId).addEntry(entry);
    return true;
  }
}
