/*
    super class for any enemy in the game
    a enemy can spawn at a random location along the border of the map
    a enemy can also calculate the distance and angle to the player
*/
package dynamicbinding;

import javax.swing.JFrame;

public abstract class Enemy extends Living
{
    public void spawn(JFrame j) 
    {
        x = (int) (Math.random() * j.getWidth());
        y = (int) (Math.random() * j.getHeight());
        
    }
    public void spawn(Living l)
    {
        x = l.x;
        y = l.y;
    }
    public double distanceToPlayer(RotationalElement e) 
    {
        int xdif = x - e.x;
        int ydif = y - e.y;
        return (Math.sqrt(Math.pow(xdif,2) + Math.pow(ydif,2)));
    }
    public double angleToPlayer(RotationalElement e) 
    {
        return (Math.atan2((y - e.y),(x - e.x)));
    }
    public abstract void move();
}
