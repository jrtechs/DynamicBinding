/*
    super class for any object that is alive
    has health, and a boolean isAlive
    every living element can take damage from another Rotational Element
    determines damage by using instanceof
*/
package dynamicbinding;

public abstract class Living extends RotationalElement
{
    private double health;
    private boolean isAlive;
    
    
    public Living()
    {
       health = 3;
       isAlive= true;
    }
    
    public void takeDamage(RotationalElement elem)
    {
        if(isAlive)
        {
            health-=.5;
            if(health <=0)
            {
                isAlive = false;
            }
        }
    }
}
