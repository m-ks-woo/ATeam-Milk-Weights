package application;

import java.time.LocalDate;
import java.util.ArrayList;

public class Farm {
  private String id;
  private ArrayList<Entry> entries;
  
  public Farm(String id) {
    this.id = id;
    entries = new ArrayList<>();
  }
  
  public int getMonthTotal(int month, int year) {
    int total = 0;
    for (Entry e : entries) {
      if (e.getDate().getYear() == year && e.getDate().getMonthValue() == month) {
        total += e.getWeight();
      }
    }
    return total;
  }
  
  public void addEntry(Entry entry) {
    entries.add(entry);
  }
  
  public void addEntry(LocalDate date, int weight) {
    entries.add(new Entry(date, this.id, weight));
  }
  
  public void removeEntry(Entry entry) {
    entries.remove(entry);
  }
  
  public void removeEntry(LocalDate date) {
    for (int i = 0; i < entries.size(); i++) {
      if (entries.get(i).getDate().equals(date)) {
        entries.remove(i);
      }
    }
  }
  
  public void editEntry(LocalDate date, int weight) {
    for (Entry e : entries) {
      if (e.getDate().equals(date)) {
        e.setWeight(weight);
      }
    }
  }
}
