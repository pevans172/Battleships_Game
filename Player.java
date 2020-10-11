import java.util.Scanner;
import java.util.ArrayList;
public class Player
{
    private String name;
    private Board myShips;
    private Board myShots;
    private Board clone;
    
     /**
     * You should define a constructor with a single String parameter, which defines the display name of the player
     * 
     * 
     * This type represents individual players in the game of battleships.
     * The methods provided will be used by the Game class as the game is played. 
     * The game will initially call choosePlacement once for each ship. 
     * Once all ships are placed then the game will repeatedly (in turn) call the chooseShot method. 
     * After each chooseShot method the game will inform the player of the result of the shot with the shotResult method.
     * The player will also be informed of opponent's shots with the opponentShot method. This could be used to update 
     * a local copy of the game board for display to the user.
     * 
     */
    public Player(String name){
        this.name = name;
        myShips = new Board();
        myShots = new Board();
        clone = new Board();
    }
    
    public String getName(){
        return name;
    }
    public Board getMyShips(){
        return myShips;
    }
    public Board getMyShots(){
        return myShots;
    }
    public Board getClone(){
        return clone;
    }
    
    public void setMyShips(Board x){
        myShips = x;
    }
    public void setMyShots(Board x){
        myShots = x;
    }
    public void setClone(Board x){
        clone = x;
    }
    
    /**
     * @param ship The ship to be placed
     * 
     * @param board (A clone of) the current board state to which the ship will be added
     * 
     * @return The placement (position and orientation) of the ship specified by the player
     * 
     * @throws PauseException At any stage the user can choose to enter "pause" (case insensitive) to make the game 
     * return to 
     * the main menu
     * 
     */
    public void choosePlacement(Ship ship, Board board) throws PauseException{
        Scanner input = new Scanner(System.in);
        String temp;
        int x, y;
        Position position;
        boolean isVertical;
        
        while(true){
            try{
                // Enter coords
                System.out.println("Enter the x coordinate:"); 
                while(true){
                    try{
                        temp = input.nextLine();
                        if(temp.equals("PAUSE")){
                            throw new PauseException("The Game has been paused by a player.");
                        }
                        x = Integer.parseInt(temp);
                        break;
                    }
                    catch(PauseException e){
                        throw new PauseException("The Game has been paused by a player.");
                    }
                    catch(Exception e){
                        System.out.println("Invalid input, please enter an integer.");    
                    }
                }
                System.out.println("Enter the y coordinate:"); 
                while(true){
                    try{
                        temp = input.nextLine();
                        if(temp.equals("PAUSE")){
                            throw new PauseException("The Game has been paused by a player.");
                        }
                        y = Integer.parseInt(temp);
                        break;
                    }
                    catch(PauseException e){
                        throw new PauseException("The Game has been paused by a player.");
                    }
                    catch(Exception e){
                        System.out.println("Invalid input, please enter an integer.");   
                    }
                }
                position = new Position(x, y);
                // Choose orientation
                System.out.println("Is the ship vertical or horizontal? Enter 'yes' for vertical or 'no' for horizontal:");
                while(true){
                    temp = input.nextLine();
                    if(temp.equals("yes")){
                        isVertical = true;
                        break;
                    }
                    else if(temp.equals("no")){
                        isVertical = false;
                        break;
                    }
                    else if(temp.equals("PAUSE")){
                        throw new PauseException("The Game has been paused by a player.");
                    }
                    else{
                        System.out.println("Enter either 'yes' or 'no'.");    
                    }
                }
                // Place ship on the board
                board.placeShip(ship, position, isVertical);
                break;
            }
            catch(InvalidPositionException e){
                System.out.println(e); 
                System.out.println("Please try again.");    
            }
            catch(ShipOverlapException e){
                System.out.println(e); 
                System.out.println("Please try again.");    
            }
        }

    }
    
    /**
     * @return The shot chosen by the player
     * 
     * @throws PauseException At any stage the user can choose to enter "pause" (case insensitive) to make the game 
     * return to the main menu
     */
    public Position chooseShot() throws PauseException{
        Scanner input = new Scanner(System.in);
        String temp;
        int x, y;
        Position position;
        
        while(true){
            try{
                System.out.println("Enter the x coordinate:"); 
                while(true){
                    try{
                        temp = input.nextLine();
                        if(temp.equals("PAUSE")){
                            throw new PauseException("The Game has been paused by a player.");
                        }
                        x = Integer.parseInt(temp);
                        break;
                    }
                    catch(PauseException e){
                        throw e;
                    }
                    catch(Exception e){
                        System.out.println("Invalid input, please enter an integer.");    
                    }
                }
                System.out.println("Enter the y coordinate:"); 
                while(true){
                    try{
                        temp = input.nextLine();
                        if(temp.equals("PAUSE")){
                            throw new PauseException("The Game has been paused by a player.");
                        }
                        y = Integer.parseInt(temp);
                        break;
                    }
                    catch(PauseException e){
                        throw e;
                    }
                    catch(Exception e){
                        System.out.println("Invalid input, please enter an integer.");   
                    }
                }
                position = new Position(x, y);
                break;
            }
            catch(InvalidPositionException e){
                System.out.println(e); 
                System.out.println("Please try again.");    
            }
        }
        return position;
    }
    
    /**
     * After the game calls the chooseShot method, it then calls this method with the result of the shot.
     * The player may choose to keep track of the results of previous shots in its state
     * 
     * @param position The position of the shot
     * 
     * @param status The result of the shot
     */
    public void shotResult(Position position){
        try{
            myShots.shoot(position);
        }
        catch(InvalidPositionException e){
            System.out.println(e);
        }
    }
    
    /**
     * @return A string representation of the player i.e. the display name provided as 
     * a parameter to the constructor.
     */
    public String toString(){
        return "This player's name is " + name;
    }

}
