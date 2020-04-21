package application;

import java.time.LocalDate;

public class Entry {
  private LocalDate date;
  private String farmId;
  private int weight;
  
  public Entry(LocalDate date, String farmId, int weight) {
    this.date = date;
    this.farmId = farmId;
    this.weight = weight;
  }
  
  public LocalDate getDate() {
    return this.date;
  }
  
  public String getFarmId() {
    return this.farmId;
  }
  
  public int getWeight() {
    return this.weight;
  }
  
  public void setDate(LocalDate date) {
    this.date = date;
  }
  
  public void setFarmId(String farmId) {
    this.farmId = farmId;
  }
  
  public void setWeight(int weight) {
    this.weight = weight;
  }
}
