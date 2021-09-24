/**
 * The Human Class implements the runnable interface and instantiates
 * the human objects that are to be visual on the panel.
 * @author Pulin Angurala 18045359
 */
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Human implements Runnable {
    protected double x, y;
    protected double dx, dy;
    protected double size;
    protected boolean isAlive;
    public static int world_width, world_height;
    protected double max_speed;
    protected int sight_distance;
    protected Random generator;
    protected ArrayList<Human> others;


    /**
     * The human constructor takes in an arraylist of humans to which
     * all human and zombie objects are added and sets the spawn position
     * of each human object.
     * @param others passed an ArrayList of humans to be added into.
     * @param x sets the x axis position of the human
     * @param y sets the y axis position of the human
     */
    public Human(ArrayList<Human> others, double x, double y){
        max_speed = 6;
        generator = new Random();
        size = 20;
        dx = generator.nextInt()%max_speed+1;
        dy = generator.nextInt()%max_speed+1;
        this.x = x;
        this.y = y;
        this.others = others;
        isAlive = true;
        world_height = 400;
        world_width = 400;
    }

    /**
     * The run method sets the default free movement of humans
     * to move around the panel in various directions and random
     * speeds within the range of the human class's set max speed.
     * The run method is also responsible for sleeping each human
     * thread. Humans are only allowed to move when alive.
     */
    public void run(){
        while(isAlive) {
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

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getSize() {
        return size;
    }

    /**
     * The kill method sets the isAlive value of a human object to false
     * to express that the human has died
     */
    public void kill(){
        isAlive = false;
    }

    /**
     * The draw method draws the look of each human
     * where the color is blue and the human is represented
     * by an oval shape.
     * @param g takes in a Graphics object to access functions
     *          needed to instantiate the human objects into
     *          visual objects on the panel.
     */
    public void draw(Graphics g){
        g.setColor(Color.blue);
        g.fillOval((int)x, (int)y, (int)size, (int)size);
    }
}
