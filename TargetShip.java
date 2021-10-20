/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AISParticleFilter;

import java.time.LocalDateTime;
import java.awt.image.BufferedImage;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import javax.imageio.ImageIO;
import java.util.*;
import java.time.Duration;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Component;


public class TargetShip{
    private Ship ship;
    private String shipID;

    public TargetShip(Ship ship) {
        this.ship = ship;
        this.shipID = ship.getShipID(); 
    }

    public Ship getShip() {
        return ship;
    }
    
    
    

    public JPanel paintShip(LocalDateTime time){
        ShipLog log = ship.getLastLoc(time);
                    
        JPanel target = new JPanel(); 
        try {
        JLabel picLabel = new JLabel(new ImageIcon(ImageIO.read(new File("Ships/RedShip.png")).getScaledInstance(20, 20, 0)));
        target.add(picLabel);
        target.setOpaque(false);
        target.setSize(20,40);
        target.setLocation((int)((log.getLongitude()-103.11)*(1246/(104.87-103.11))-11), (757- (int)((log.getLatitude()-0.51)*(757/(1.58 - 0.51))))-7);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return target;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
        this.shipID = ship.getShipID(); 
    }
    
}
