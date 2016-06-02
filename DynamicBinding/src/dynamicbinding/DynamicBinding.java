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

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Graphics;

public class DynamicBinding 
{
    //fields
    public JFrame frame;
    public JPanel panel;
    ArrayList<Enemy> enemy;
    ArrayList<Bullet> bullets;
    Player p;
  //  Map map;
    Room [][] rooms;
    
    Timer move;//moves elements
    Timer time;
            

    public static void main(String[] args) 
    {
        
    }
    
    
    /*
        inner classes that require the fields in DynamicBinding
    */
    
    
    private class Player extends Living
    {
        private Arraylist<Item> items;
        boolean up = false;
        boolean down = false;
        boolean left = false;
        boolean right = false;
        
        public Player()
        {
            width = 50;
            height = 50;
            x=frame.getWidth()/2-this.width/2;
            y=frame.getHeight()/2-this.height/2;
            imageLocation = "player.png";
            super.loadImage();
        }
        public void move()
        {
            if(down == true)
            {
                direction = 270;
                super.move(-1);
            }
            if(up == true)
            {
                direction = 90;
                super.move(-1);
            }
            if(left == true)
            {
                direction = 180;
                super.move(-1);
            }
            if(right == true)
            {
                direction = 0;
                super.move(-1);
            }
        }
        public void updateDirection(KeyEvent e, boolean b)
        {
            if(e.getKeyCode()== KeyEvent.VK_W)
            {
                up = b;
            }
            if(e.getKeyCode()== KeyEvent.VK_A)
            {
                left = b;
            }
            if(e.getKeyCode()== KeyEvent.VK_S)
            {
                down = b;
            }
            if(e.getKeyCode()== KeyEvent.VK_D)
            {
                right = b;
            }
            if(e.getKeyCode()==KeyEvent.VK_UP)
            {
               shoot(90);
            }
            if(e.getKeyCode()==KeyEvent.VK_DOWN)
            {
                shoot(270);
            }
            if(e.getKeyCode()==KeyEvent.VK_LEFT)
            {
                shoot(180);
            }
            if(e.getKeyCode()==KeyEvent.VK_RIGHT)
            {
                shoot(0);
            }
            
        }
        
        public void shoot(int b)
        {
            Bullet bu = new Bullet();
            bu.direction = b;
            bullets.add(bu);
        }
            
             
            
            
        
    }
    
    private class Bullet extends RotationalElement
    {
        
    }
    
    
    /* 
    private class Map extends DrawableElement
    {
        ArrayList<Obstacle> obstacles;
        ArrayList<Item> items;
        ArrayList<Doorway> doors;
        
        public Map() {
            //max could also be player level?
            int randObs = (int)(Math.random() * 7);
            int randItems = (int)(Math.random() * 3);
            int randDoors = (int)(Math.random() * 4) + 1;
            //(int)(Math.random() * (max - min)) + min
            
            //constructors for these aren't made yet
            for(int i = 0; i < randObs; i++) {
                obstacles.add(new Obstacle());
            }
            for(int i = 0; i < randItems; i++) {
                items.add(new Item());
            }
            for(int i = 0; i < randDoors; i++) {
                doors.add(new Doorway());
            }
        }
        
        public Map(ArrayList<Obstacle> o, ArrayList<Item> i, ArrayList<Doorway> d) {
            obstacles = o;
            items = i;
            doors = d;
        }

        public void draw(Graphics g) {
            
            for(Obstacle o: obstacles) {
                o.draw(g);
            }
            for(Item i: items) {
                i.draw(g);
            }
            for(Doorway d: doors) {
                d.draw(g);
            }
        }
        
        //check(player) method???
    }
     */
    
    //rooms change
    private class Room extends DrawableElement {
        
        ArrayList<Obstacle> obstacles;
        ArrayList<Item> items;
        ArrayList<Doorway> doors;
        
        public Room(ArrayList<Obstacle> o, ArrayList<Item> i, ArrayList<Doorway> d) {
            obstacles = o;
            items = i;
            doors = d;
        }
        
        public Room() {
            //max could also be player level?
            int randObs = (int)(Math.random() * 7);
            int randItems = (int)(Math.random() * 3);
            int randDoors = (int)(Math.random() * 4) + 1;
            //(int)(Math.random() * (max - min)) + min
            
            //constructors for these aren't made yet
            for(int i = 0; i < randObs; i++) {
                obstacles.add(new Obstacle());
            }
            for(int i = 0; i < randItems; i++) {
                items.add(new Item());
            }
            for(int i = 0; i < randDoors; i++) {
                doors.add(new Doorway());
            }
        }
        
        public void draw(Graphics g) {
            
            for(Obstacle o: obstacles) {
                o.draw(g);
            }
            for(Item i: items) {
                i.draw(g);
            }
            for(Doorway d: doors) {
                d.draw(g);
            }
        }
    }
    
    
    
    /*
    Fly moves with slight variation of angle each move,
    collisions damage player in an "allahu ackbar" sort of way
    */
    private class Fly extends Enemy
    {
        public void move() {
            int randDir = (int) (Math.random() * 91) - 45;
            this.direction += randDir;
            this.move(1);
            if (checkCollision(p)) {
                p.takeDamage(p);
                enemy.remove(this);
            }
        }
    }
    
    private class Item extends GameElement
    {
        String name;
        public Item(String n)
        {
            name = n;
        }
        
        public void pickUp(Player p)
        {
            if (p.checkCollision(this))
            {
                p.items.add(this);
            }
        }
        
    }
    
    private class Obstacle extends MapItem
    {
        
    }
    
    private class Doorway extends MapItem {
        
        public void onCollission() {
            
            if(enemy.size() == 0) {
                //new room
            }
        }
    }
    
    
}
