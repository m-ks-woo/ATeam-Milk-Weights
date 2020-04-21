package application;

import java.time.LocalDate;

/**
 * Defines a class to store the data for one line in the csv data file
 * @author Matthew Woo, Param Bhandare, Russell Cheng
 */
public class Entry {
  private LocalDate date;
  private String farmId;
  private int weight;
  
  /**
   * Initializes a new Entry object with a given date, farmId, and weight
   * @param date the date of the entry
   * @param farmId the id of the farm
   * @param weight the weight for the farm
   */
  public Entry(LocalDate date, String farmId, int weight) {
    this.date = date;
    this.farmId = farmId;
    this.weight = weight;
  }
  
  /**
   * Returns the date of this entry
   * @return the date of this entry
   */
  public LocalDate getDate() {
    return this.date;
  }
  
  /**
   * Returns the farm id for this entry
   * @return the farm id for this entry
   */
  public String getFarmId() {
    return this.farmId;
  }
  
  /**
   * Returns the weight for this entry
   * @return the weight for this entry
   */
  public int getWeight() {
    return this.weight;
  }
  
  /**
   * Changes this entry's date to the given date
   * @param date the new date for the entry
   */
  public void setDate(LocalDate date) {
    this.date = date;
  }
  
  /**
   * Changes this entry's farmId to the given farmId
   * @param farmId the new farm id for the entry
   */
  public void setFarmId(String farmId) {
    this.farmId = farmId;
  }
  
  /**
   * Changes this entry's weight to the given weight
   * @param weight the new weight for the entry
   */
  public void setWeight(int weight) {
    this.weight = weight;
  }
}
