package application;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Defines a single farm with an ArrayList of entries to store milk weights for one farm id over 
 * specific dates.
 * @author Matthew Woo, Param Bhandare, Russell Cheng
 */
public class Farm {
  private String id;
  private ArrayList<Entry> entries;
  
  /**
   * Initializes a new farm with the given farm id
   * @param id the String id of the farm
   */
  public Farm(String id) {
    this.id = id;
    entries = new ArrayList<>();
  }
  
  /**
   * Calculates and returns the total milk weight for a given month in a given year for this farm.
   * @param month the month to calculate the total for
   * @param year the year for the month total
   * @return the total weight for the given month in the given year
   */
  public int getMonthTotal(int month, int year) {
    int total = 0;
    for (Entry e : entries) {
      if (e.getDate().getYear() == year && e.getDate().getMonthValue() == month) {
        total += e.getWeight();
      }
    }
    return total;
  }
  
  /**
   * Adds an entry to this Farm's Entry ArrayList
   * @param entry the Entry to add
   */
  public void addEntry(Entry entry) {
    for (Entry e : entries) {
      if (e.getDate().equals(entry.getDate())) {
        this.editEntry(entry.getDate(), entry.getWeight());
        return;
      }
    }
    entries.add(entry);
  }
  
  /**
   * Adds an entry to this Farm's Entry ArrayList
   * @param date the date of the entry
   * @param weight the recorded weight on the date
   */
  public void addEntry(LocalDate date, int weight) {
    for (Entry e : entries) {
      if (e.getDate().equals(date)) {
        this.editEntry(date, weight);
        return;
      }
    }
    entries.add(new Entry(date, this.id, weight));
  }
  
  /**
   * Removes a given entry from this Farm's Entry ArrayList
   * @param entry the Entry to remove
   * @return true if the entry was successfully removed and false otherwise
   */
  public boolean removeEntry(Entry entry) {
    return entries.remove(entry);
  }
  
  /**
   * Removes the entry at a given date from this Farm's Entry ArrayList
   * @param date the date of the Entry to remove
   * @return true if the entry was successfully removed and false otherwise
   */
  public boolean removeEntry(LocalDate date) {
    for (int i = 0; i < entries.size(); i++) {
      if (entries.get(i).getDate().equals(date)) {
        entries.remove(i);
        return true;
      }
    }
    return false;
  }
  
  /**
   * Modifies an entry at a given date to have a new given weight
   * @param date the date of the Entry to modify
   * @param weight the new weight for the Entry at the given date
   */
  public void editEntry(LocalDate date, int weight) {
    for (Entry e : entries) {
      if (e.getDate().equals(date)) {
        e.setWeight(weight);
      }
    }
  }
  
  /**
   * Returns the ArrayList of entries associated with this farm
   * @return the ArrayList of entries associated with this farm
   */
  public ArrayList<Entry> getEntries() {
    return entries;
  }
}
