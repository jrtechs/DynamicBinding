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

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class DynamicBinding implements KeyListener
{
    //fields
    public JFrame frame;
    public JPanel panel;
    ArrayList<Enemy> enemy;
    ArrayList<Bullet> bullets;
    ArrayList<Obstacle> obstacles;
    ArrayList<Item> items;
    Player p;
    Map map;
    
    Timer move;//moves elements
    Timer time;
            

    public static void main(String[] args) 
    {
        DynamicBinding gameOfIsaac = new DynamicBinding();
    }
    
    public DynamicBinding()
    {
        frame = new JFrame("Alex's Group - DynamicFrame");
        frame.setBounds(200,100,1000,700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        p = new Player();
        map = new Map(obstacles,items,0,0,"map_picture");
        enemy = new ArrayList<Enemy>();
        bullets = new ArrayList<Bullet>();
        obstacles = new ArrayList<Obstacle>();
        items = new ArrayList<Item>();
        panel=new JPanel() 
        {
            public void paintComponent(Graphics g)
            {
                g.setColor(Color.BLACK);
                g.fillRect(0,0,frame.getWidth(),frame.getHeight());
                for(int e=0; e<enemy.size(); e++)
                {
                    enemy.get(e).draw(g);
                }
                for(int b=0; b<bullets.size(); b++)
                {
                    bullets.get(b).draw(g);
                }
                for(int o=0; o<obstacles.size(); o++)
                {
                    obstacles.get(o).draw(g);
                }
                p.draw(g);
                map.draw(g);
            }   
	};
	frame.add(panel);
        ActionListener a = new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                p.move();
                for(int i = 0; i<enemy.size();i++)
                {
                    enemy.get(i).move();
                }
                panel.repaint();
            }
        };
        Timer move = new Timer(10,a);
        move.start();  //moves elements
        frame.addKeyListener(this);
        frame.setVisible(true);
    }
    public void keyPressed(KeyEvent e)
    {
        p.updateDirection(e,true);
    }
    public void keyReleased(KeyEvent e)
    {
        p.updateDirection(e,false);
    }
    public void keyTyped(KeyEvent e) {
        
    }
    /*
        inner classes that require the fields in DynamicBinding
    */
    
    
    private class Player extends Living
    {
        private ArrayList<Item> items;
        boolean up = false;
        boolean down = false;
        boolean left = false;
        boolean right = false;
        
        public Player()
        {
            width = 50;
            height = 50;
            speed = 10;
            health = 10;
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
            Bullet bu = new Tear(x, y, b);
            bullets.add(bu);
        }
            
             
            
            
        
    }
    
    private abstract class Bullet extends RotationalElement
    {
        
         public Bullet(int xLoc, int yLoc, double dir) {
            direction = dir;
            width = 10;
            height = 10;
            x = xLoc;
            y = yLoc;
         }
         
         public void move()
         {
             if(this.x > frame.getWidth() || this.x < 0 || this.y > frame.getHeight() || this.y < 0)
             {
                 this.removeBullet();
             }
             for(Obstacle o: obstacles)
             {
                 if(checkCollision(o))
                 {
                    this.removeBullet();
                 }
             }
         }
         
         public void removeBullet()
         {
            bullets.remove(this);
         }
    }
    private class Blood extends Bullet 
    {
        public Blood(int xLoc, int yLoc, double dir)
        {
            super(xLoc, yLoc, dir);
            imageLocation = "CBlood.png";
            loadImage();
            //call RotationalElement's move
        }
        
        public void move()
        {
            super.move();
            if(checkCollision(p /*p is player*/))
            {                
                p.takeDamage(this);
                this.removeBullet();
            }
            for(Enemy e: enemy)
            {
                if(checkCollision(e))
                {
                    this.removeBullet();
                }
            }
        }              
    }
        
    private class Tear extends Bullet
    {
        public Tear(int xLoc, int yLoc, double dir)
        {
            super(xLoc, yLoc, dir);
            imageLocation = "CTear.png";
            loadImage();
        }
        
        public void move()
        {
            super.move();
            for(Enemy e: enemy)
            {
                if(checkCollision(e))
                {
                    e.takeDamage(this);
                    this.removeBullet();
                }
            }
            for(Bullet b: bullets)
            {
                if(b instanceof Blood)
                {
                    if(checkCollision(this))
                    {
                        
                    }
                }
            }
        }
        
    }
    
    
     
    private class Map extends DrawableElement
    {
        ArrayList<Obstacle> obstacles;
        ArrayList<Item> items;
        
        public Map(ArrayList<Obstacle> o, ArrayList<Item> i, int mx, int my, String iloc) {
            obstacles = o;
            items = i;
            x = mx;
            y = my;
            imageLocation = iloc;
        }
        
      /*  
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
        */
        //check(player) method???
    }
    
    /*
    Fly moves with slight variation of angle each move,
    collisions damage player in an "allahu ackbar" sort of way
    */
    private class Fly extends Enemy
    {
        public Fly() {
            //this.imageLocation = "";
        }
        public void move() {
            int randDir = (int) (Math.random() * 91) - 45;
            this.direction += randDir;
            this.move(-1);
            if (checkCollision(p)) {
                p.takeDamage(this);
                enemy.remove(this);
            }
        }
    }
    
    private class Bee extends Enemy
    {
        public Bee() {
            this.imageLocation = "CBee2.png";
        }
        public void move() {
            this.direction = angleToPlayer(p);
            this.move(-1);
            if (checkCollision(p)) {
                p.takeDamage(this);
                enemy.remove(this);
            }
        }
    }
    
    private class Spider extends Enemy {
        private Timer jump;
        public Spider() 
        {
            this.speed = 8;
            ActionListener tick = new ActionListener() 
            {
                public void actionPerformed(ActionEvent e) 
                {
                    direction = angleToPlayer(p);
                    move(-1);
                }
            };
            jump = new Timer(70,tick);
            jump.start();
        }
        public void move() {
            
        }
            //this.imageLocation = "";
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
}
