
package AISParticleFilter;

import java.util.HashMap;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.time.LocalDateTime;
import java.time.Month;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
/**
 *
 * @author seanh
 */
public class NewTestMain {
    private HashMap<String, Ship> ships = new HashMap<String, Ship>();
    private JLayeredPane frame = new JLayeredPane();
    private TargetShip target;
   
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        HashMap<String, Ship> ships = new HashMap<String, Ship>();
        //create dummy target ship.
        Ship targetShip = new Ship("00000000");
        LocalDateTime dummyRecord = LocalDateTime.of(2021, Month.FEBRUARY, 16, 00, 00);
        targetShip.addRecord(dummyRecord, 1.00, 104.00, 0, 0);
//        TargetShip target = new TargetShip(targetShip, "00000000");
        
        
        
        
        
        
        
        JLayeredPane frame = new JLayeredPane();
        frame.setSize(1246, 757);
        JFrame window = new JFrame("AIS Ship Tracking");
        window.setSize(1246, 1200);
        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        try {
            //loading data in.
            System.out.println("Extracting Data....");
            for(int i=0; i<10;i++){
                PopulateData dataInsert = new PopulateData("Spire_SingaporeAOI_AIS_Feb2021/Spire_SingaporeAOI_AIS_Feb2021_00000000000" + i + ".csv", ships); 
            }
            System.out.println("Data Extraction Successful!");
        
            for (ShipLog s: ships.get("564147000").getShipData().values()) {
                //System.out.println(s.getSpeed());
                //System.out.println(s.getLatitude() + " " + s.getLongitude());
            }
            JLabel picLabel = new JLabel(new ImageIcon(ImageIO.read(new File("mapSingapore.png"))));
            JPanel jp = new JPanel();
            jp.setOpaque(false);
            jp.setSize(1247,757);
            jp.setLocation(0,0);
            jp.setBounds(0, 0, 1246, 757);
            jp.add(picLabel);
            frame.add(jp, new Integer(0));
            LocalDateTime time = LocalDateTime.of(2021, Month.FEBRUARY, 16, 00, 00);
            JLabel label1 = new JLabel(time.toString());
            JPanel timeText = new JPanel();
            timeText.setOpaque(false);
            timeText.setSize(200, 50);
            timeText.setLocation(1077,727);
            timeText.add(label1);
            frame.add(timeText, new Integer(2));
            for (int i=0;i<840;i++){
                Mapper x;
                JPanel y;
                frame.add(x = new Mapper(time, ships, Color.black), new Integer(1));
                //frame.add(y = target.paintShip(time), new Integer(3));
                frame.setPreferredSize(new Dimension(1247,757));
                window.setContentPane(frame);
                window.pack();
                window.setVisible(true);
                Thread.sleep(100);
                if (i!=839){
                    frame.remove(x);
                }
                time = time.plusMinutes(12);
                label1.setText(time.toString());
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
}
