import java.awt.*;
import java.awt.event.*;
import java.awt.geom.GeneralPath;
import java.util.*;

//A Simple version of the scrolling game, featuring Avoids, Gets, and RareGets
//Players must reach a score threshold to win
//If player runs out of HP (via too many Avoid collisions) they lose
public class BasicGame extends ScrollingGameEngine {
    
    //Dimensions of game window
    protected static final int DEFAULT_WIDTH = 900;
    protected static final int DEFAULT_HEIGHT = 600;  
    
    //Starting Player coordinates
    protected static final int STARTING_PLAYER_X = 0;
    protected static final int STARTING_PLAYER_Y = 100;
    
    //Score needed to win the game
    protected static final int SCORE_TO_WIN = 300;
    
    //Maximum that the game speed can be increased to
    //(a percentage, ex: a value of 300 = 300% speed, or 3x regular speed)
    protected static final int MAX_GAME_SPEED = 300;
    //Interval that the speed changes when pressing speed up/down keys
    protected static final int SPEED_CHANGE = 20;    
    
    protected static final String INTRO_SPLASH_FILE = "assets/splash.gif";  
    protected static final String INSTRUCTIONS_SPLASH_FILE = "assets/instructions.gif";  
    protected static final String INITIAL_BACKGROUND_IMAGE = "assets/3hearts.gif";
    protected static final String FIVE_HEARTS_BACKGROUND_IMAGE = "assets/5hearts.gif";              
    protected static final String FOUR_HEARTS_BACKGROUND_IMAGE = "assets/4hearts.gif";
    protected static final String TWO_HEARTS_BACKGROUND_IMAGE = "assets/2hearts.gif";              
    protected static final String ONE_HEART_BACKGROUND_IMAGE = "assets/1heart.gif";              
              
              
    protected static final String WIN_IMAGE = "assets/win.gif";
    protected static final String LOST_IMAGE = "assets/lose.gif";                            

    //Key pressed to advance past the splash screen
    public static final int ADVANCE_SPLASH_KEY = KeyEvent.VK_ENTER;
    
    //Interval that Entities get spawned in the game window
    //ie: once every how many ticks does the game attempt to spawn new Entities
    protected static final int SPAWN_INTERVAL = 45;
    
    protected static final double O_RATE = 0.45;
    protected static final double RG_RATE = 0.15;
    protected static final double G_RATE = 0.50;
    protected static final int W_width = 485;

    protected boolean SPLASH_SCREEN = false;
    
    //A Random object for all your random number generation needs!
    protected static final Random rand = new Random();


    //Player's current score
    protected int score;
    
    //Stores a reference to game's Player object for quick reference
    //(This Player will also be in the displayList)
    protected Player player;
    
    
    
    public BasicGame(){
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }
    
    public BasicGame(int gameWidth, int gameHeight){
        super(gameWidth, gameHeight);
    }
    
    
    //Performs all of the initialization operations that need to be done before the game starts
    protected void pregame(){
        this.setSplashImage(INTRO_SPLASH_FILE);
        this.setBackgroundImage(INITIAL_BACKGROUND_IMAGE);
        player = new Player(STARTING_PLAYER_X, STARTING_PLAYER_Y);
        displayList.add(player); 
        score = 0;
    }
    
    
    //Called on each game tick
    protected void updateGame(){

        //scroll all scrollable Entities on the game board
    
        scrollEntities();  
        //Spawn new entities only at a certain interval
        if (ticksElapsed % SPAWN_INTERVAL == 0){            
            spawnEntities();
            garbageCollectOffscreenEntities();
        }
        for (int i = 1; i < displayList.size(); i++){
            if (player.isCollidingWith(displayList.get(i))){
                handlePlayerCollision((Consumable)displayList.get(i));
                displayList.remove(i);
            }
        }
        //Update the title text on the top of the window
        setTitleText("HP: " + player.getHP() + ", Score: " + score);
        
        changeBackGround();
        
        player.updateImage();

        if (isGameOver()){
            postgame();
        }    
    }
    
    
    protected void changeBackGround(){
        if (player.getHP() == 5){
            setBackgroundImage(FIVE_HEARTS_BACKGROUND_IMAGE);
        }    
        else if (player.getHP() == 4){
            setBackgroundImage(FOUR_HEARTS_BACKGROUND_IMAGE);
        }    
        else if (player.getHP() == 3){
            setBackgroundImage(INITIAL_BACKGROUND_IMAGE);
        }
        else if (player.getHP() == 2){
            setBackgroundImage(TWO_HEARTS_BACKGROUND_IMAGE);
        }
        else if (player.getHP() == 1){
            setBackgroundImage(ONE_HEART_BACKGROUND_IMAGE);
        } 
    }
    //Scroll all scrollable entities per their respective scroll speeds

    protected void visibility(int entity){
        displayList.get(entity).setVisible(false);
    }

    protected void scrollEntities(){
        for (int i = 1; i < displayList.size(); i++){
                if (displayList.get(i) instanceof Avoid){
                    ((Avoid)displayList.get(i)).scroll();
                    if(((Avoid)displayList.get(i)).getX() < 0){
                        visibility(i);
                    }
                }
            
                else if (displayList.get(i) instanceof Avoid2){
                    ((Avoid2)displayList.get(i)).scroll();
                    if(((Avoid2)displayList.get(i)).getX() < 0){
                        visibility(i);
                    }
                }
                else if (displayList.get(i) instanceof Get){
                    ((Get)displayList.get(i)).scroll();
                    if(((Get)displayList.get(i)).getX() < 0){
                        visibility(i);
                    }
                }
                
                else if (displayList.get(i) instanceof RareGet){
                    ((RareGet)displayList.get(i)).scroll();
                    if(((RareGet)displayList.get(i)).getX() < 0){
                        visibility(i);
                    }
                }
            }   
                      
        }
            
    //Handles "garbage collection" of the displayList
    //Removes entities from the displayList that have scrolled offscreen
    //(i.e. will no longer need to be drawn in the game window).
    protected void garbageCollectOffscreenEntities(){
        
        for (int i = 0; i < displayList.size(); i++){
            if (!displayList.get(i).isVisible()){
                displayList.remove(i);
            }
        }       
       
    }
    
    //Called whenever it has been determined that the Player collided with a consumable
    protected void handlePlayerCollision(Consumable collidedWith){
        // if (player.getHP() <= 4 || player.isCollidingWith(Avoid)){ // question
        int hp2 = player.getHP() + collidedWith.getDamageValue();
        if (hp2 >= 5){
            player.setHP(5);
        }
        else{
            player.setHP(hp2);
        }
        score += collidedWith.getPointsValue();
    }
    
    protected int randomNum(int range){
        return rand.nextInt(range)+1;
    }
/*
    protected void addToScreen(Entity arr){
        int items = 3;
        for (int i = 0 ; i < items ; i++){
      
        }
    }
    */ 
    //Spawn new Entities on the right edge of the game board
    protected void spawnEntities(){
      


        Avoid avoid = new Avoid(DEFAULT_WIDTH,randomNum(W_width));
        Avoid2 avoid2 = new Avoid2(DEFAULT_WIDTH,randomNum(W_width));
        RareGet rget = new RareGet(DEFAULT_WIDTH,randomNum(W_width));
        Get get = new Get(DEFAULT_WIDTH,randomNum(W_width));

     //   Entity[] array = {avoid, rget, get};
        if (Math.random() < O_RATE){

            while (avoid.isCollidingWith(rget) || avoid.isCollidingWith(get) || avoid.isCollidingWith(avoid2)){
                avoid = new Avoid(DEFAULT_WIDTH,randomNum(W_width));
            }
            displayList.add(avoid);
        }
        for(int i = 0; i < 2; i++){
        if (Math.random() < O_RATE){

            while (avoid2.isCollidingWith(rget) || avoid2.isCollidingWith(get) || avoid2.isCollidingWith(avoid) ){
                avoid2 = new Avoid2(DEFAULT_WIDTH,randomNum(W_width));
            }
            displayList.add(avoid2);
        }
    }
        if (Math.random() < RG_RATE){
            while (rget.isCollidingWith(avoid) || rget.isCollidingWith(get) || rget.isCollidingWith(avoid2)){
                rget = new RareGet(DEFAULT_WIDTH,randomNum(W_width));
            }
            displayList.add(rget); 
        }

        if (Math.random() < G_RATE){
            while (get.isCollidingWith(avoid) || get.isCollidingWith(rget) || get.isCollidingWith(avoid2)){
                get = new Get(DEFAULT_WIDTH, randomNum(W_width));
            }
            displayList.add(get); 
            
        }

    
           
    }

   
 
    
    //Called once the game is over, performs any end-of-game operations
    protected void postgame(){
        if (score >= SCORE_TO_WIN){
            super.setTitleText("Game is over! You Win!");
            super.setSplashImage(WIN_IMAGE);
        }
        else{
            super.setTitleText("Game is over! You Lose!");
            super.setSplashImage(LOST_IMAGE);
        }
    }
    
    
    //Determines if the game is over or not
    //Game can be over due to either a win or lose state
    protected boolean isGameOver(){
        if (score >= SCORE_TO_WIN || player.getHP() < 1){
            return true;
        }
        return false;
}
    
    
    
    //Reacts to a single key press on the keyboard
    protected void reactToKey(int key){
        
        setDebugText("Key Pressed!: " + KeyEvent.getKeyText(key) + ",  DisplayList size: " + displayList.size());
        //if a splash screen is active, only react to the "advance splash" key... nothing else!
        
        if (key == ADVANCE_SPLASH_KEY && getSplashImage() == INTRO_SPLASH_FILE){
                super.setSplashImage(INSTRUCTIONS_SPLASH_FILE);
        }
        
        else if (getSplashImage() == INSTRUCTIONS_SPLASH_FILE  && key == ADVANCE_SPLASH_KEY){
            super.setSplashImage(null);
        }
        
        else if (key == SPEED_UP_KEY){
            int speed =  getGameSpeed() + SPEED_CHANGE;
            if (speed <= MAX_GAME_SPEED){
                setGameSpeed(speed);
            }
        }
        else if (key == SPEED_DOWN_KEY){
            int speed =  getGameSpeed() - SPEED_CHANGE;
            if (speed > 0){
                setGameSpeed(speed);
            }
        }

        else if (key == KEY_PAUSE_GAME){
          isPaused = !isPaused;
        }

        else{
            if (player.getX() < 800 && key == RIGHT_KEY && !isPaused){
                player.setX(player.getMovementSpeed() + player.getX());
               
            }
            else if (player.getX() > 0  && key == LEFT_KEY && !isPaused){
                player.setX(player.getX() - player.getMovementSpeed());

            }
            else if (player.getY() > 0 && key == UP_KEY && !isPaused){
                player.setY(player.getY() - player.getMovementSpeed());
            }
            else if (player.getY() < 485 && key == DOWN_KEY && !isPaused){
                player.setY(player.getY() + player.getMovementSpeed());
            }
        }

    }    
    
    //Handles reacting to a single mouse click in the game window
    //Won't be used in Simple Game... you could use it in Creative Game though!
    protected MouseEvent reactToMouseClick(MouseEvent click){
        if (click != null){ //ensure a mouse click occurred
            int clickX = click.getX();
            int clickY = click.getY();
            setDebugText("Click at: " + clickX + ", " + clickY);
        }
        return click;//returns the mouse event for any child classes overriding this method
    }
    
    
    
    
}
