//The entity that the human player controls in the game window
//The player moves in reaction to player input
public class Player extends Entity {
    
    //Location of image file to be drawn for a Player
    protected static final String PLAYER_IMAGE_FILE = "assets/player.gif";
    protected static final String PLAYER_IMAGE_FILE_BAD = "assets/player1.gif";
    protected static final String PLAYER_IMAGE_FILE_GOOD = "assets/player3.gif";

    //Dimensions of the Player
    protected static final int PLAYER_WIDTH = 70;
    protected static final int PLAYER_HEIGHT = 110;
    //Default speed that the Player moves (in pixels) each time the user moves it
    protected static final int DEFAULT_MOVEMENT_SPEED = 7;
    //Starting hit points
    protected static final int STARTING_HP = 3;
     
     //Current movement speed
     private int movementSpeed;
     //Remaining Hit Points (HP) -- indicates the number of "hits" (ie collisions
     //with Avoids) that the player can take before the game is over
     private int hp;
     
     
     public Player(){
         this(0, 0);        
     }
     
     public Player(int x, int y){
         super(x, y, PLAYER_WIDTH, PLAYER_HEIGHT, PLAYER_IMAGE_FILE_GOOD);  
         this.hp = STARTING_HP;
         this.movementSpeed = DEFAULT_MOVEMENT_SPEED;
     }
     
     public void updateImage(){
        if (getHP() == 3 || getHP() == 2){
            super.setImageName(PLAYER_IMAGE_FILE);
        }
        else if (getHP() == 1){
            super.setImageName(PLAYER_IMAGE_FILE_BAD);
        }
        else if (getHP() == 4 || getHP() == 5){
            super.setImageName(PLAYER_IMAGE_FILE_GOOD);
        }
     }
     
     //Retrieve and set the Player's current movement speed 
     public int getMovementSpeed(){
         return this.movementSpeed;
     }
     
     public void setMovementSpeed(int newSpeed){
         this.movementSpeed = newSpeed;
     }  
     
     
     //Retrieve the Player's current HP
     public int getHP(){
         return hp;
     }
     
     //Set the player's HP to a specific value.
     //Returns an boolean indicating if Player still has HP remaining
     public boolean setHP(int newHP){
         this.hp = newHP;
         return (this.hp > 0);
     }
     
     //Set the player's HP to a specific value.
     //Returns an boolean indicating if Player still has HP remaining
     public boolean modifyHP(int delta){
        if (hp <= 4){
        this.hp += delta;
    }
         return (this.hp > 0);
     }    
     
     
 }
 