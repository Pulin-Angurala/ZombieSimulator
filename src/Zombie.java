/**
 * The Zombie Class is a subclass of the Human class
 * and implements the runnable interface and instantiates
 * the zombie objects that are to be visual on the panel.
 * @author Pulin Angurala 18045359
 */
import java.awt.*;
import java.util.ArrayList;


public class Zombie extends Human{
    /**
     *The zombie constructor takes in an arraylist of humans to which
     * all the human and zombie objects are added and sets the spawn position
     * of each zombie object.
     * @param others passed an ArrayList of humans to be added into.
     * @param x sets the x axis position of the zombie
     * @param y sets the y axis position of the zombie
     */
    public Zombie(ArrayList<Human> others, double x, double y) {
        super(others, x, y);
        super.max_speed = 3;
        super.dx = generator.nextInt()%max_speed+1;
        super.dy = generator.nextInt()%max_speed+1;
        isAlive = false;
    }

    /**
     * The run method sets the default free movement of zombies
     * to move around the panel in various directions and random
     * speeds within the range of the human class's set max speed.
     * The run method is also responsible for sleeping each zombie
     * thread. The method is override Zombies are only allowed to move
     * when not alive.
     */
    @Override
    public void run() {
        while(!isAlive) {
            x += dx;
            y += dy;

            if (x < 0) {
                dx = -dx;
            }
            if (y < 0) {
                dy = -dy;
            }
            if (x > world_width) {
                dx =  -dx;
            }
            if (y > world_height) {
                dy = -dy;
            }
            try{
                Thread.sleep(20);
            }
            catch (InterruptedException e){
            }
        }
    }

    /**
     * The draw method is override to draw the zombie objects
     * differently as apposed to humans by a square shape and red
     * colour.
     * @param g takes in a Graphics object to access functions
     *          needed to instantiate the zombie objects into
     *          visual objects on the panel.
     */
    @Override
    public void draw(Graphics g) {
        g.setColor(Color.red);
        g.fillRect((int)x, (int)y, (int)size, (int)size);
    }
}
