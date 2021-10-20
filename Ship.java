package AISParticleFilter;

import java.time.LocalDateTime;
import java.util.TreeMap; 
import java.time.Duration;
import java.util.Random;

/**
 *
 * @author seanh
 */
public class Ship {
    private String shipID;
    private TreeMap<LocalDateTime, ShipLog> shipData;
    private Integer logNum;
    private Random random = new Random();
    
    public Ship(String shipID) {
        this.shipID = shipID;
        TreeMap<LocalDateTime, ShipLog> shipData = new TreeMap<LocalDateTime, ShipLog>();
        this.shipData = shipData;
        this.logNum = 0;

    }
    
    public void addRecord(LocalDateTime timeStamp, double latitude, double longitude, double speed, double course){
        ShipLog newlog = new ShipLog(timeStamp, latitude,longitude,speed,course, shipID);
        shipData.put(timeStamp, newlog);
        logNum++;
    }

    public ShipLog getLastLoc(LocalDateTime time) {
        if (shipData.floorKey(time) == null){
            return null;
        }
        return shipData.get(shipData.floorKey(time));
        
        
    }
    public void copyRecord(ShipLog s){
        shipData.put(s.getTimeStamp(), s);
        
    }
    
    public TreeMap<LocalDateTime, ShipLog> getShipData() {
        return shipData;
    }
    
    public ShipLog getnextLoc(ShipLog s){
        if (shipData.ceilingKey(s.getTimeStamp()) == null){
            return null;
        }
        return shipData.get(shipData.ceilingKey(s.getTimeStamp()));  
    }

    public String getShipID() {
        return shipID;
    }
    
    public void stepShip(Duration duration, ShipLog lastLog, LocalDateTime time) {
        Double lati = lastLog.getLatitude();
        Double longi = lastLog.getLongitude();
        Double speed = lastLog.getSpeed();
        Double course = lastLog.getCourse();
        long timeLength = duration.toSeconds();
        Double dist = (speed * 0.514444) * timeLength;
        Double distLati = dist / 110.574;
        Double distLongi = dist / 111.320;
        Double newLat = (lati +(float)Math.cos(Math.toRadians(course))*(distLati+((float)random.nextGaussian()*0.01)));
        Double newLong = (longi +(float)Math.sin(Math.toRadians(course))*(distLongi+((float)random.nextGaussian()*0.01)));
        addRecord(time, newLat, newLong, speed, course);
    }
}
