/*
    super class for any object that is alive
    has health, and a boolean isAlive
    every living element can take damage from another Rotational Element
    determines damage by using instanceof
*/
package dynamicbinding;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public abstract class Living extends RotationalElement
{
    public double health;
    public boolean isAlive;
    public boolean takeDamage;
    public int cool; //Me
    
    public Living()
    {
       ActionListener cooldown = new ActionListener()
       {
           public void actionPerformed(ActionEvent e)
           {
               if(takeDamage == false)
               {
                   cool ++;
                   if(cool == 2)
                   {
                       takeDamage = true;
                       cool = 0;
                   }
               }
           }
       };
       Timer t = new Timer(1000, cooldown);
       t.start();
               
       isAlive= true;
       takeDamage=true;
    }
    
    public void takeDamage(RotationalElement elem)
    {
        if(isAlive && takeDamage)
        {

            health-=elem.damageGive;
            takeDamage = false;
            
            if(health <=0)
            {
                isAlive = false;
            }
        }
    }
}
