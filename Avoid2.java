//Avoids are entities the player needs to avoid colliding with.
//If a player collides with an avoid, it reduces the players Hit Points (HP).
public class Avoid2 extends Entity implements Consumable, Scrollable {
    
    //Location of image file to be drawn for an Avoid
    private static final String AVOID2_IMAGE_FILE = "assets/avoid2.gif";
    //Dimensions of the Avoid    
    private static final int AVOID2_WIDTH = 50;
    private static final int AVOID2_HEIGHT = 75;
    //Speed that the avoid moves each time the game scrolls
    private static final int AVOID2_SCROLL_SPEED = 5;
    
    public Avoid2(){
        this(0, 0);        
    }
    
    public Avoid2(int x, int y){
        super(x, y, AVOID2_WIDTH, AVOID2_HEIGHT, AVOID2_IMAGE_FILE);  
    }
    
    
    public int getScrollSpeed(){
        return AVOID2_SCROLL_SPEED;
    }
    
    //Move the avoid left by the scroll speed
    public void scroll(){
        setX(getX() - AVOID2_SCROLL_SPEED);
    }
    
    //Colliding with an Avoid does not affect the player's score
    public int getPointsValue(){
       return 0;
    }
    
    //Colliding with an Avoid Reduces players HP by 1
    public int getDamageValue(){
        return -1;
    }
    
}
