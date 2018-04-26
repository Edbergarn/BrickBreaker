import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Gameplay extends JPanel implements KeyListener, ActionListener {
    public boolean play = false;
    public int score = 0;

    public int totalbricks = 21;

    public Timer timer;
    public int delay = 8;

    public int playerX = 310;

    public int ballposX = 120;
    public int ballposY = 350;
    public int ballXdir = -1;
    public int ballYdir = -2;

    public MapGenerator map;

    public Gameplay(){
        map = new MapGenerator(3,7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay,this);
        timer.start();
    }

    public void paint(Graphics g){
        //Bakgrunden
        g.setColor(Color.black);
        g.fillRect(1,1,692,592);

        //Draw map
        map.draw((Graphics2D)g);

        //Border
        g.setColor(Color.yellow);
        g.fillRect(0,0,3,592);
        g.fillRect(0,0,692,3);
        g.fillRect(692,0,3,592);

        //Studsarn
        g.setColor(Color.GREEN);
        g.fillRect(playerX, 550,100,8);

        //Bollen
        g.setColor(Color.yellow);
        g.fillOval(ballposX,ballposY,20,20);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if(play){
            if(new Rectangle(ballposX, ballposY, 20,20).intersects(new Rectangle(playerX, 550, 100, 8))){
                ballYdir = -ballYdir;

            }
            
            ballposX += ballXdir;
            ballposY += ballYdir;
            if(ballposX < 0){
                ballXdir = -ballXdir;
            }
            if(ballposY < 0){
                ballYdir = -ballYdir;
            }
            if(ballposX > 670){
                ballXdir = -ballXdir;
            }
        }
        repaint();

    }

    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            if(playerX >= 600){
                playerX=600;
            }else{
                moveRight();
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            if(playerX >= 10){
                playerX-=10;
            }else{
                moveLeft();
            }
        }


    }

    public void moveLeft() {
        play = true;
        playerX-=20;
    }

    public void moveRight() {
        play = true;
        playerX+=20;
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}
