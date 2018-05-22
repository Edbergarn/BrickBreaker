public class Ball {
    public int xDir, yDir, xPos, yPos;

    public Ball(int xPos, int yPos, int xDir, int yDir){
        this.xDir = xDir; //Bollens startPos
        this.yDir = yDir; //Bollens startPos
        this.xPos = xPos; // Bollens pixelförflyttning per delay
        this.yPos = yPos; // Bollens pixelförflyttning per delay

    }
}
