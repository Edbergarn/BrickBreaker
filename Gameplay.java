import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.security.Key;

public class Gameplay extends JPanel implements KeyListener, ActionListener {
    public boolean play = false;
    public int score = 0;
    private boolean lose = false;



    public int totalbricks = 21;// Antal bricks

    public Timer timer;
    public int delay = 5;


    public static Timer timerLeft;
    public static Timer timerRight;

    public int playerX = 310;
    private static int stepSpeed = 2;
    private static final int timeSpeed = 1;
    private boolean movingRight = false;
    private boolean movingLeft = false;
    int xvalue = (int)(Math.random()*590);
    int dirY = (2*(int)(Math.pow(-1, (int)(Math.random()*2))));


    public Ball ball = new Ball( xvalue, 350, dirY, -2);

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
        g.fillOval(ball.xPos,ball.yPos,20,20);



        if (totalbricks == 0){
            g.setColor(Color.GREEN);
            g.setFont(new Font("serif", Font.BOLD, 60));
            g.drawString("You Win!", 230, 300);
        }
        if (lose){
            g.setColor(Color.RED);
            g.setFont(new Font("serif", Font.BOLD, 60));
            g.drawString("You Lose!", 230, 300);
        }
        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if(play){
            if(new Rectangle(ball.xPos, ball.yPos, 20,20).intersects(new Rectangle(playerX, 550, 100, 8))){ //När den studsar mot PlayerX
                if(ball.yDir > 0) {
                    ball.yDir = -ball.yDir;
                }

            }
        A:  for(int i = 0; i<map.map.length; i++){
               for (int j = 0; j<map.map[0].length; j++){
                    if (map.map[i][j]> 0){
                        int brickX = j* map.brickWidth + 80;
                        int brickY = i* map.brickHeight + 50;
                        int brickWidth = map.brickWidth;
                        int brickHeigth = map.brickHeight;

                        Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeigth);
                        Rectangle ballRect = new Rectangle(ball.xPos, ball.yPos, 20, 20);
                        Rectangle brickRect = rect;

                        if (ballRect.intersects(brickRect)){
                            map.setBrickValue(0, i, j);
                            totalbricks--;
                            score +=5;

                            if (ball.xPos + 19 <= brickRect.x || ball.xPos + 1 >= brickRect.x + brickRect.width){
                                ball.xDir = -ball.xDir;
                            }else {
                                ball.yDir = -ball.yDir;
                            }
                            break A;
                        }
                    }
                }
            }

            ball.xPos += ball.xDir;
            ball.yPos += ball.yDir;
            if(ball.xPos < 0){
                ball.xDir = -ball.xDir;
            }
            if(ball.yPos < 0){
                ball.yDir = -ball.yDir;
            }
            if(ball.xPos > 670){
                ball.xDir = -ball.xDir;
            }
            if (totalbricks == 0){
                play = false;
                stepSpeed = 0;

            }
            if (new Rectangle(ball.xPos, ball.yPos,20, 20).intersects(new Rectangle(0, 570, 600, 2))) {
                lose = true;
                play = false;
                stepSpeed = 0;
            }
        }
        repaint();

    }

    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT && !movingLeft){
            play = true;
            movingLeft = true;
            timerLeft = new Timer(timeSpeed, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                   if (playerX > 2){
                       playerX -= stepSpeed;
                   }
                }
            });
            timerLeft.start();
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT && !movingRight){
            play = true;
            movingRight = true;
            timerRight =  new Timer(timeSpeed, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (playerX < 590) {
                        playerX += stepSpeed;
                    }
                }
            });
            timerRight.start();
        }




    }
    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            movingLeft = false;
            timerLeft.stop();
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT){
            movingRight = false;
            timerRight.stop();
        }

    }



}
