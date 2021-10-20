package AISParticleFilter;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import java.awt.*;  
import java.awt.event.*; 
import java.time.LocalDateTime;
import java.time.Month;
import java.util.LinkedList; 
import java.time.Duration;

/**
 *
 * @author seanh
 */
public class ParticleFilter {
    Ship Target;
    private HashMap<String, Ship> ships = new HashMap<String, Ship>();
    private JLayeredPane frame = new JLayeredPane();
    private JFrame window;
    private TargetShip target;
    private HashMap<String, Ship> particleShips = new HashMap<String, Ship>();
    private Integer particleNum = 20;
    LocalDateTime time = null;
    JLabel label1 = new JLabel("Loading Data...");
    private boolean filtering;
    private LinkedList<ShipLog> closeShips;


    public ParticleFilter() {
        paintMap();
        window.setContentPane(frame);
        window.pack();
        window.setVisible(true);
        fillTables();
        target = new TargetShip(ships.get("563071630"));
        target.setShip(ships.get("563071630"));
       
    }
    
    
    public void makeWindow() {
        window = new JFrame("AIS Ship Tracking");
        window.setSize(1246, 1200);
        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }
    
    
    
    
    public static void main(String[] args) {
        ParticleFilter pF= new ParticleFilter();
        for (int i=0; i<200; i++) {
            try {
            pF.time = pF.stepTime(pF.time);
            Mapper shipsMapped; 
            JPanel targetMapped;
            pF.paintShips(shipsMapped = new Mapper(pF.time, pF.ships,Color.black));
            if (!pF.filtering){
                pF.paintTarget(targetMapped = pF.target.paintShip(pF.time));
                Thread.sleep(100);
                pF.removeAllShips(shipsMapped, targetMapped);
            } else {
                //filtering
                pF.initializeFilter();
                //pF.stepParticles();
                Mapper particlesMapped;
                pF.weight();
                pF.resample();
                System.out.println(pF.particleShips.toString());
                pF.paintShips(particlesMapped = new Mapper(pF.time, pF.particleShips,Color.red));
                Thread.sleep(1000);
                pF.removeAllShips(shipsMapped, particlesMapped);
            }
            pF.closeShips = pF.getCloseShips();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
    
    public void initializeFilter() {
        for (Integer i=1;i<particleNum;i++) {
            Ship newShip = new Ship(i.toString());
            newShip.copyRecord(target.getShip().getLastLoc(time));
            particleShips.put(newShip.getShipID(), newShip);
        }
    }
    
    public void stepParticles() {
        for (Ship s: particleShips.values()) {
            s.stepShip(Duration.between(target.getShip().getLastLoc(time).getTimeStamp(),time), target.getShip().getLastLoc(time), time);
        }
    }
    
    public void weight(){
        
    }
    
    public void resample(){
        
    }
    
    public void fillTables() {
        System.out.println("Extracting Data....");
            for(int i=0; i<1;i++){
                PopulateData dataInsert = new PopulateData("Spire_SingaporeAOI_AIS_Feb2021/Spire_SingaporeAOI_AIS_Feb2021_00000000000" + i + ".csv", ships); 
            }
        System.out.println("Data Extraction Successful!");
    }
    
    
    public void paintMap() {
        makeWindow();
        try {
        JLabel picLabel = new JLabel(new ImageIcon(ImageIO.read(new File("mapSingapore.png"))));
            JPanel jp = new JPanel();
            jp.setOpaque(false);
            jp.setSize(1247,757);
            jp.setLocation(0,0);
            jp.setBounds(0, 0, 1246, 757);
            jp.add(picLabel);
            JPanel timeText = new JPanel();
            timeText.setOpaque(false);
            timeText.setSize(200, 50);
            timeText.setLocation(10,10);
            timeText.add(label1);
            frame.add(timeText, new Integer(2));
            frame.add(jp, new Integer(0));
            frame.setPreferredSize(new Dimension(1247,757));
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }    
    
    public LinkedList<ShipLog> getCloseShips() {
        LinkedList<ShipLog> closeShipsTemp = new LinkedList<ShipLog>();
        for (Ship s: ships.values()) {
            if (s.getShipID().equals(target.getShip().getShipID())) {
                for (ShipLog log: s.getShipData().values()) {
                    if(!log.getTimeStamp().isAfter(time)) {
                        if ( 0.2 <= Math.sqrt(Math.pow(log.getLatitude() - target.getShip().getLastLoc(time).getLatitude(),2) - Math.pow(log.getLongitude() - target.getShip().getLastLoc(time).getLongitude(),2))) {
                            closeShipsTemp.add(log);
                        }
                    }
                }
            }
        }
        return closeShipsTemp;
    }
    
    public double getClosestShip() {
        double closestDist = 2 ;
        for (Ship s: ships.values()) {
            if (s.getShipID().equals(target.getShip().getShipID())) {
                for (ShipLog log: s.getShipData().values()) {
                    if(!log.getTimeStamp().isAfter(time)) {
                        double currDist = Math.sqrt(Math.pow(log.getLatitude() - target.getShip().getLastLoc(time).getLatitude(),2) - Math.pow(log.getLongitude() - target.getShip().getLastLoc(time).getLongitude(),2));
                        if (closestDist >= currDist) {
                            closestDist = currDist;
                        }
                    }
                }
            }
        }
        return closestDist;
    }
    
    public LocalDateTime stepTime(LocalDateTime previous) {
        if(previous == null) {
            return target.getShip().getShipData().firstKey();
        } else {
            Duration duration = Duration.between(previous, target.getShip().getShipData().ceilingKey(previous.plusSeconds(1)));
            if (duration.toMinutes() > 30){
                filtering = true;
                return previous.plusMinutes(15);
            } else {
                filtering = false;
               return target.getShip().getShipData().ceilingKey(previous.plusSeconds(1)); 
            }
  
        }
        
    }
    
    public void paintShips(Mapper x) {
        frame.add(x, new Integer(1));
        frame.setPreferredSize(new Dimension(1247,757));
        if (this.filtering) {
            label1.setForeground(Color.red);
        } else {
            label1.setForeground(Color.black);
        }
        label1.setText(this.time.toString());
        window.setContentPane(frame);
        window.pack();
        window.setVisible(true);
    }
    
    public void paintTarget(JPanel x) {
        frame.add(x, new Integer(2));
        frame.setPreferredSize(new Dimension(1247,757));
        window.setContentPane(frame);
        window.pack();
        window.setVisible(true);
    }
    
    public void removeAllShips(Mapper x, JPanel y){
        frame.remove(x);
        if (y!=null){
            frame.remove(y);    
        }
        
    }
    
    
}
