/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AISParticleFilter;

import java.time.LocalDateTime;
/**
 *
 * @author seanh
 */
public class ShipLog {
    private LocalDateTime timeStamp;
    private double latitude;
    private double longitude;
    private double speed;
    private double course;
    private String shipID;

    public ShipLog(LocalDateTime timeStamp, double latitude, double longitude, double speed, double course, String shipID) {
        this.timeStamp = timeStamp;
        this.latitude = latitude;
        this.longitude = longitude;
        this.speed = speed;
        this.course = course;
        this.shipID = shipID;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getCourse() {
        return course;
    }

    public double getSpeed() {
        return speed;
    }

    public String getShipID() {
        return shipID;
    }
    
    
}
