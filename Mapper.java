
package AISParticleFilter;

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
import javax.swing.JPanel;
/**
 *
 * @author seanh
 */
public class Mapper extends JPanel {
        private BufferedImage img, img1;
        private HashMap<String, Ship> ships;
        private LocalDateTime time;
        private Color color;
        
        public Mapper(LocalDateTime time, HashMap<String, Ship> ships, Color color) {
            this.setBackground(new Color(0,0,0,0));
            this.color = color;
            this.setOpaque(false);      
            setSize(1247,757);
            setLocation(0,0);
            setBounds(0, 0, 1246, 757);
            try {
                img = ImageIO.read(new File("mapSingapore.png"));
                img1 = ImageIO.read(new File("Ships/blackShipN.png"));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            this.time = time;
            this.ships = ships;
        }

        @Override
        public Dimension getPreferredSize() {
            return img == null ? new Dimension(200, 200) : new Dimension(img.getWidth(), img.getHeight());
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
//                if (img != null) {
//                    int x = (getWidth() - img.getWidth()) / 2;
//                    int y = (getHeight() - img.getHeight()) / 2;
//                    g.drawImage(img, x, y, this);
//                    
//                }
            
                for (Ship s: ships.values()) {
                    ShipLog log = s.getLastLoc(time);
                    if (log != null) {
                        Duration duration = Duration.between(log.getTimeStamp(), time);    
                        if (duration.toMinutes() < 60){
                            //g.drawImage(img1, , log.getLatitude(), this)
                            g.setColor(color);
                            //System.out.println((int)((log.getLongitude()-103.11)*(getWidth()/(104.87-103.11))-1));
                            //System.out.println((getHeight() - (int)((log.getLatitude()-0.51)*(getHeight()/(1.58 - 0.51))))+3);
                            g.drawOval((int)((log.getLongitude()-103.11)*(getWidth()/(104.87-103.11))-1), (getHeight() - (int)((log.getLatitude()-0.51)*(getHeight()/(1.58 - 0.51))))+3, 3, 3);                        
                        }

                    }
                }
            
        }

    }

