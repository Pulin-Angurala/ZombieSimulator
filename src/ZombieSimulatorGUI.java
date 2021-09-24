/**
 * The ZombieSimulatorGUI class is the main class
 * of the GUI application. This class is responsible for
 * implement all GUI components such as panels, graphics, buttons
 * as well as instantiating and starting threads. This class actively
 * repaints the panel to create a live GUI.
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.*;

public class ZombieSimulatorGUI extends JPanel implements ActionListener
{
    private JButton addZombie, addHuman;
    private JPanel drawPanel;
    private Timer timer;
    ArrayList<Human> humans;


    /**
     * The ZombieSimulatorGUI Constructor initialises all
     * components and functions used in the GUI, such as
     * the buttons and button listeners, the panel layout and
     * borders, etc.
     */
    public ZombieSimulatorGUI(){
        super(new BorderLayout());

        humans = new ArrayList<>();

        addZombie = new JButton("Add Zombie");
        addZombie.addActionListener(this);

        addHuman = new JButton("Add Human");
        addHuman.addActionListener(this);

        JPanel southPanel = new JPanel();
        southPanel.add(addZombie);
        southPanel.add(addHuman);
        add(southPanel, BorderLayout.SOUTH);

        drawPanel = new DrawPanel();
        add(drawPanel, BorderLayout.CENTER);

        timer = new Timer(5, this);
        timer.start();
    }

    /**
     * The actionPerformed method implements all panel functionality
     * such as repainting the panel actively to create live moving graphics
     * as well as the instantiation of zombie and human objects and threads
     * through the click of the buttons. this method also implements the functionality
     * for the zombies to actively chase humans and the humans to actively run away
     * as the panel actively updates and repaints.
     * @param e takes in an action event to determine the correct consequence
     *          of a condition being true.
     */
    public void actionPerformed(ActionEvent e){
        if (e.getSource() == timer){
            for (Human z: humans){
                ArrayList<Integer> distance = new ArrayList<>();
                for (Human h: humans) {
                    if (!z.isAlive && h.isAlive) {
                        z.sight_distance = (int) Math.sqrt(((Math.pow(z.x - h.x, 2) + (Math.pow(z.y - h.y, 2)))));
                        distance.add(z.sight_distance);
                        int smallestDistance = Collections.min(distance);
                        if (z.sight_distance == smallestDistance) {
                            z.dx = ((h.x - z.x) / smallestDistance) * z.max_speed;
                            z.dy = ((h.y - z.y) / smallestDistance) * z.max_speed;
                        }
                        if (z.sight_distance < 100){
                            if (h.x > 10 && h.x < 390 && h.y < 390 && h.y > 10) {
                                h.dx = z.dx;
                                h.dy = z.dy;
                            }
                            else if  (h.x == 10 || h.x == 390){
                                h.dx = -h.dx;
                            }
                            else if (h.y == 10 || h.y == 390){
                                h.dx = -h.dy;
                            }
                        }
                        if (z.sight_distance < 15){
                            h.kill();
                        }
                        if (!h.isAlive){
                            Zombie zombie = new Zombie(humans, h.x, h.y);
                            humans.set(humans.indexOf(h), zombie);
                            Thread thread = new Thread(zombie);
                            thread.start();
                        }
                    }
                }
            }
            drawPanel.repaint();
        }
        if (e.getSource() == addZombie){
            Zombie zombie = new Zombie(humans, (double)Zombie.world_width/2, (double)Zombie.world_height/2);
            Thread thread = new Thread(zombie);
            thread.start();
            humans.add(zombie);
        }
        if (e.getSource() == addHuman){
            Human human = new Human(humans, (double)Zombie.world_width/2, (double)Zombie.world_height/2);
            Thread thread = new Thread(human);
            thread.start();
            humans.add(human);
        }
    }

    /**
     * The Draw panel class is a private class that extends
     * the JPanel class to instantiate a separate panel to
     * sit on top the ZombieSimulatorGUI Panel. This panel
     * is repainted to to show live graphical movement
     */
    private class DrawPanel extends JPanel{

        public DrawPanel(){
            super();
            setPreferredSize(new Dimension(400, 400));
            setBackground(Color.WHITE);
        }
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            for (Human h: humans){
                h.draw(g);
            }
        }
    }

    /**
     * The main method instantiates The GUI frame and Panel to
     * instantiate in total the GUI application.
     * @param args
     */
    public static void main(String[] args){
        JFrame frame = new JFrame("Main and Event Queue Thread Example");
        // kill all threads when frame closes
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new ZombieSimulatorGUI());
        frame.pack();
        // position the frame in the middle of the screen
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screenDimension = tk.getScreenSize();
        Dimension frameDimension = frame.getSize();
        frame.setLocation((screenDimension.width-frameDimension.width)/2,
                (screenDimension.height-frameDimension.height)/2);
        frame.setVisible(true);
    }
}
