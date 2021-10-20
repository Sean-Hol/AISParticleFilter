package AISParticleFilter;

import java.util.*;
import java.lang.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
/**
 *
 * @author seanh
 */
public class PopulateData {
    private String filename;
    private HashMap<String, Ship> ships;

    public PopulateData(String filename, HashMap<String, Ship> ships) {
        this.filename = filename;
        this.ships = ships;
        Integer newShips = 0;
        Integer newEntries = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            DateTimeFormatter ft = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                String timeValues = values[0].substring(0,19);
                if (!values[3].equals("5") && !values[3].equals("4") && !values[3].equals("24")) {
                    if (ships.containsKey(values[2])) {
                        LocalDateTime recordTime = LocalDateTime.parse(timeValues, ft);
                        Ship currShip = ships.get(values[2]);
                        currShip.addRecord(recordTime, Double.parseDouble(values[4]), Double.parseDouble(values[5]), Double.parseDouble(values[6]), Double.parseDouble(values[7]));
                        newEntries++;
                    } else {
                        Ship newShip = new Ship(values[2]);
                        LocalDateTime recordTime = LocalDateTime.parse(timeValues, ft);
                        ships.put(values[2], newShip);
                        newShip.addRecord(recordTime, Double.parseDouble(values[4]), Double.parseDouble(values[5]), Double.parseDouble(values[6]), Double.parseDouble(values[7]));
                        newShips++;
                        newEntries++;
                        
                    }
                }
            }
           // System.out.println("New ships added: " + newShips);
           // System.out.println("Entries added: " + newEntries);
        } catch (Exception e){
            System.out.println(e.getMessage());
           // System.out.println("New ships added: " + newShips);
           // System.out.println("Entries added: " + newEntries);
        }
        
    }
    
    
    
}
