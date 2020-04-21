package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;

public class FarmTable {
  private HashMap<String, Entry> farms;
  
  public FarmTable() {
    this.farms = new HashMap<>();
  }
  
  public void loadData(File csvfile) {
    try {
      BufferedReader br = new BufferedReader(new FileReader(csvfile));
      String line = br.readLine(); // skip title line
      do {
        line = br.readLine();
        String[] data = line.split(",");
        Entry e = new Entry(LocalDate.parse(data[0]), data[1], Integer.parseInt(data[2]));
        farms.put(e.getFarmId(), e);
      } while (line != null);
      br.close();
    } catch (Exception e) {
      // Display error reading the file
    }
  }
}
