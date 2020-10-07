

import java.awt.AWTException;
import java.awt.Component;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;

public class MouseMover implements ActionListener, MouseMotionListener {
  static boolean run = false;
  
  static Robot r;
  
  static JFrame jf = new JFrame("Mouse mover");
  
  static JPanel jp1 = new JPanel();
  
  JToggleButton jb = new JToggleButton("Start");
  
  static JLabel jll = new JLabel("Select the period (sec): ");
  
  static JTextArea jta = new JTextArea("60", 1, 3);
  
  public MouseMover() throws AWTException {
    r = new Robot();
    this.jb.setSelected(false);
    jp1.add(jll);
    jp1.add(jta);
    jp1.add(this.jb);
    jf.add(jp1);
    jf.setDefaultCloseOperation(3);
    jf.pack();
    jf.setVisible(true);
    jf.setAlwaysOnTop(true);
    jf.setLocationRelativeTo((Component)null);
    this.jb.addActionListener(this);
  }
  
  public Point getML() {
    return MouseInfo.getPointerInfo().getLocation();
  }
  
  public void move(int x, int y) {
    r.mouseMove((int)getML().getX() + x, (int)getML().getY() + y);
  }
  
  public static void main(String[] args) throws AWTException, InterruptedException {
    MouseMover gc = new MouseMover();
  }
  
  public void actionPerformed(ActionEvent e) {
    if (this.jb.getText() == "Start") {
      run = true;
      this.jb.setText("Stop");
      Thread t = new Thread() {
          public void run() {
            while (MouseMover.run) {
              try {
                Thread.sleep(Long.valueOf(MouseMover.jta.getText()).longValue() * 1000L);
              } catch (InterruptedException ex) {
                Logger.getLogger(MouseMover.class.getName()).log(Level.SEVERE, (String)null, ex);
              } 
              if (MouseMover.run) {
                MouseMover.this.move(1, 1);
                try {
                  Thread.sleep(Long.valueOf(500L).longValue());
                } catch (InterruptedException ex) {
                  Logger.getLogger(MouseMover.class.getName()).log(Level.SEVERE, (String)null, ex);
                } 
                MouseMover.this.move(-1, -1);
                System.out.println("has been moved! " + Calendar.getInstance().getTime());
              } 
            } 
          }
        };
      t.setPriority(1);
      t.start();
    } else if (this.jb.getText() == "Stop") {
      run = false;
      this.jb.setText("Start");
    } 
  }
  
  public void mouseDragged(MouseEvent e) {}
  
  public void mouseMoved(MouseEvent e) {}
}
