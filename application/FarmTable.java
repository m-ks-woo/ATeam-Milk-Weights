package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
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
    do {
      line = br.readLine();
      String[] data = line.split(",");
      Entry e = new Entry(LocalDate.parse(data[0]), data[1], Integer.parseInt(data[2]));
      if (farms.keySet().contains(e.getFarmId())) {
        farms.get(e.getFarmId()).addEntry(e);
      } else {
        Farm farm = new Farm(e.getFarmId());
        farm.addEntry(e);
        farms.put(e.getFarmId(), farm);
      }
    } while (line != null);
    br.close();
  }
}
