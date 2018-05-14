import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Gameplay extends JPanel implements KeyListener, ActionListener {
    public boolean play = false;
    public int score = 0;


    public int totalbricks = 21;// Antal bricks

    public Timer timer;
    public int delay = 6;

    public int playerX = 310;

    public Ball ball = new Ball(120, 350, -2, -3);

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
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if(play){
            if(new Rectangle(ball.xPos, ball.yPos, 20,20).intersects(new Rectangle(playerX, 550, 100, 8))){ //När den studsar mot PlayerX
                ball.yDir = -ball.yDir;

            }
         A: for(int i =0; i<map.map.length; i++){
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

                            if (ball.xPos + 19 <= brickRect.x || ball.xPos + 1 >= brickRect.width){
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
            if(playerX <= 10){
                playerX=10;
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
