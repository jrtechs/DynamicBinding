/*
    Set 8 programming project

    Dynamic Binding of Java

    code
    https://github.com/jrtechs/DynamicBinding
    
    hierarchy
    https://github.com/jrtechs/DynamicBinding/wiki/Class-hierarchy-for-DynamicBinding

    clone,push,pull
    https://github.com/jrtechs/DynamicBinding.git
*/
package dynamicbinding;

public class DynamicBinding 
{

    public static void main(String[] args) 
    {
        
    }
    
    
    /*
        inner classes that require the fields in DynamicBinding
    */
    
    
    private class Player extends Living
    {
        
    }
    
    private class Bullet extends RotationalElement
    {
        
    }
    
    private class Map extends DrawableElement
    {
        
    }
    
    private class Fly extends Enemy
    {
        
    }
    
    private class Item extends GameElement
    {
        
    }
    
    private class Obstacle extends MapItem
    {
        
    }
    
    
}
