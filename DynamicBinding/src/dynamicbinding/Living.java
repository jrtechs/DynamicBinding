/*
    super class for any object that is alive
    has health, and a boolean isAlive
    every living element can take damage from another Rotational Element
    determines damage by using instanceof
*/
package dynamicbinding;

public abstract class Living extends RotationalElement
{
    private int health;
    private boolean isAlive;
    
    
    public Living()
    {
        super();
        loadImage();
        health=100;
        isAlive=true;
    }
    
    
    public void takeDamage(RotationalElement elem)
    {
        if (elem instanceof Enemy)
        {
            health-=10;
            
            if (health==0)
            {
                isAlive=false;
            }
        }
    }
}
