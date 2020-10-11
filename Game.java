import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
/**
 * GameInterface represents the game state including the boards and the players
 *
 * Requires a constructor with two parameters: the two player objects. 
 *
 * Also requires a main method which allows the user to choose the player names and types and start the game. 
 * The main method menu should allow users to: create the players (human or computer); load a game; continue a 
 * game; save the game; start a new game; exit the program.
 *
 * If providing a GUI then the same options need to be available through the GUI.
 **/
public class Game
{   
    private Player playerOne;
    private Player playerTwo;
    
    public Game(Player playerOne, Player playerTwo){
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
    }
    
    public Game(){
        this.playerOne = new Player("Phil");
        this.playerTwo = new Player("Tim");
    }
    
    /**
     * Play the game until completion or pause. Should work either for a new game or the continuation of a paused game. 
     * This method should get ask players for ship placements. The board that is passed to the players when choosing 
     * placements should be a clone of the game board so that they can try out moves without affecting the state of the game. 
     * Once ships are all placed the players should be asked one after another for their choice of shot via their getShot 
     * method. 
     * When a shot has been accepted by the game the player should be informed of the result of the shot. 
     * 
     * At any stage during game play a player can choose to pause a game (with a PauseException), which should cause the 
     * play method to return
     * 
     * @return the winning player if there is one, or null if not (the game has been paused by a player). 
     * If a player tries to take an illegal shot or place a ship illegally then they forfeit the game and the 
     * other player immediately wins.
     *
     **/
    public Player play(){
        Scanner input = new Scanner(System.in);
        System.out.println("");
        System.out.println("----------------------");
        System.out.println("Welcome to Battleship.");
        System.out.println("----------------------");
        
        // playerOnes choices
        System.out.println("Player 1 to choose.");
        System.out.println("");
        System.out.println("This is your board:");
        System.out.println(playerOne.getMyShips().toString());
        
        int step = 0;
        ArrayList<Ship> shipsOne = new ArrayList<Ship>();
        shipsOne.add(new Ship(5));
        shipsOne.add(new Ship(4));
        shipsOne.add(new Ship(3));
        shipsOne.add(new Ship(3));
        shipsOne.add(new Ship(2));
        while(true){
            try{
                if(step < 5){
                    playerOne.setClone(playerOne.getMyShips().clone());
                    playerOne.choosePlacement(shipsOne.get(step), playerOne.getClone());
                    playerOne.setMyShips(playerOne.getClone().clone());
                    step++;
                    System.out.println("");
                    System.out.println("This is your board:");
                    System.out.println(playerOne.getMyShips().toString());
                }
                else{
                    break;
                }
            }
            catch(PauseException e){
                System.out.println(e);
                while(true){
                    System.out.println("Type 'RESUME' to resume, or 'EXIT' to exit.");
                    String temp = input.nextLine();
                    if(temp.equals("RESUME")){
                        break;
                    }
                    else if(temp.equals("EXIT")){
                        return playerOne;
                    }
                }
            }
        }
        
        // playerTwos choices
        System.out.println("Player 2 to choose.");
        System.out.println("");
        System.out.println("This is your board:");
        System.out.println(playerTwo.getMyShips().toString());
        
        step = 0;
        ArrayList<Ship> shipsTwo = new ArrayList<Ship>();
        shipsTwo.add(new Ship(5));
        shipsTwo.add(new Ship(4));
        shipsTwo.add(new Ship(3));
        shipsTwo.add(new Ship(3));
        shipsTwo.add(new Ship(2));
        while(true){
            try{
                if(step < 5){
                    playerTwo.setClone(playerTwo.getMyShips().clone());
                    playerTwo.choosePlacement(shipsTwo.get(step), playerTwo.getClone());
                    playerTwo.setMyShips(playerTwo.getClone().clone());
                    step++;
                    System.out.println("");
                    System.out.println("This is your board:");
                    System.out.println(playerTwo.getMyShips().toString());
                }
                else{
                    break;
                }
            }
            catch(PauseException e){
                System.out.println(e);
                while(true){
                    System.out.println("Type 'RESUME' to resume, or 'EXIT' to exit.");
                    String temp = input.nextLine();
                    if(temp.equals("RESUME")){
                        break;
                    }
                    else if(temp.equals("EXIT")){
                        return playerOne;
                    }
                }
            }
        }
        
        System.out.println("playerOne Board:");
        System.out.println(playerOne.getMyShips().toString());
        System.out.println("playerTwo Board:");
        System.out.println(playerTwo.getMyShips().toString());
        
        // add shiplists to the shot boards
        playerOne.getMyShots().setShipList(playerTwo.getMyShips().getShipList());
        playerTwo.getMyShots().setShipList(playerOne.getMyShips().getShipList());
        
        int turn = 1;
        while(true)
        {   
            while(true){
                try{
                    System.out.println("Turn " + turn);
                    System.out.println("-------------------------------");
                    System.out.println("playerOne Ship Board:");
                    System.out.println(playerOne.getMyShips().toString());
                    System.out.println("playerOne Shot Board:");
                    System.out.println(playerOne.getMyShots().toString());
        
                    Position shotOne = playerOne.chooseShot();
                    
                    playerTwo.setClone(playerTwo.getMyShips());
                    playerTwo.getClone().shoot(shotOne);
                    playerTwo.setMyShips(playerTwo.getClone());
                    
                    playerOne.shotResult(shotOne);
                    
                    System.out.println("playerOne Shot Board:");
                    System.out.println(playerOne.getMyShots().toString());
                    System.out.println("-------------------------------");
                    
                    break;
                }
                catch(InvalidPositionException e){
                    System.out.println(e);
                    System.out.println("Please try again.");   
                }
                catch(PauseException e){
                    System.out.println(e);
                    while(true){
                        System.out.println("Type 'RESUME' to resume, or 'EXIT' to exit.");
                        String temp = input.nextLine();
                        if(temp.equals("RESUME")){
                            break;
                        }
                        else if(temp.equals("EXIT")){
                            return playerOne;
                        }
                    }
                }
            }
            
            if(playerOne.getMyShips().allSunk()){
                return playerOne;
            }
            else if(playerTwo.getMyShips().allSunk()){
                return playerTwo;
            }
            
            while(true){
                try{
                    System.out.println("Turn " + turn);
                    System.out.println("-------------------------------");
                    System.out.println("playerTwo Ship Board:");
                    System.out.println(playerTwo.getMyShips().toString());
                    System.out.println("playerTwo Shot Board:");
                    System.out.println(playerTwo.getMyShots().toString());
        
                    Position shotOne = playerTwo.chooseShot();
                    
                    playerOne.setClone(playerOne.getMyShips());
                    playerOne.getClone().shoot(shotOne);
                    playerOne.setMyShips(playerOne.getClone());
                    
                    playerTwo.shotResult(shotOne);
                    
                    System.out.println("playerTwo Shot Board:");
                    System.out.println(playerTwo.getMyShots().toString());
                    System.out.println("-------------------------------");
                    
                    break;
                }
                catch(InvalidPositionException e){
                    System.out.println(e);
                    System.out.println("Please try again.");
                }
                catch(PauseException e){
                    System.out.println(e);
                    while(true){
                        System.out.println("Type 'RESUME' to resume, or 'EXIT' to exit.");
                        String temp = input.nextLine();
                        if(temp.equals("RESUME")){
                            break;
                        }
                        else if(temp.equals("EXIT")){
                            return playerOne;
                        }
                    }
                }
            }
            
            turn++;
            if(playerOne.getMyShips().allSunk()){
                return playerOne;
            }
            else if(playerTwo.getMyShips().allSunk()){
                return playerTwo;
            }
        }
    }
    
    
    public Player playTest(){
        Scanner input = new Scanner(System.in);
        System.out.println("");
        System.out.println("----------------------");
        System.out.println("Welcome to Battleship.");
        System.out.println("----------------------");
        
        // playerOnes choices
        System.out.println("Player 1 to choose.");
        System.out.println("");
        System.out.println("This is your board:");
        System.out.println(playerOne.getMyShips().toString());
        
        int step = 0;
        ArrayList<Ship> shipsOne = new ArrayList<Ship>();
        shipsOne.add(new Ship(5));
        shipsOne.add(new Ship(4));
        shipsOne.add(new Ship(3));
        shipsOne.add(new Ship(3));
        shipsOne.add(new Ship(2));
        while(true){
            try{
                if(step < 5){
                    playerOne.setClone(playerOne.getMyShips().clone());
                    playerOne.choosePlacement(shipsOne.get(step), playerOne.getClone());
                    playerOne.setMyShips(playerOne.getClone().clone());
                    step++;
                    System.out.println("");
                    System.out.println("This is your board:");
                    System.out.println(playerOne.getMyShips().toString());
                }
                else{
                    break;
                }
            }
            catch(PauseException e){
                System.out.println(e);
                while(true){
                    System.out.println("Type 'RESUME' to resume, or 'EXIT' to exit.");
                    String temp = input.nextLine();
                    if(temp.equals("RESUME")){
                        break;
                    }
                    else if(temp.equals("EXIT")){
                        return playerOne;
                    }
                }
            }
        }
        
        // get a copy of player one as player 2
        playerTwo.setMyShips(playerOne.getMyShips());
        playerTwo.setMyShots(playerOne.getMyShots());
        playerTwo.setClone(playerOne.getClone());
        
        System.out.println("playerOne Board:");
        System.out.println(playerOne.getMyShips().toString());
        System.out.println("playerTwo Board:");
        System.out.println(playerTwo.getMyShips().toString());
            
        // now each player has a board with their ships
        // and a shot board
        playerOne.getMyShots().setShipList(playerTwo.getMyShips().getShipList());
        playerTwo.getMyShots().setShipList(playerOne.getMyShips().getShipList());
        
        int turn = 1;
        while(true)
        {   
            while(true){
                try{
                    System.out.println("Turn " + turn);
                    System.out.println("-------------------------------");
                    System.out.println("playerOne Ship Board:");
                    System.out.println(playerOne.getMyShips().toString());
                    System.out.println("playerOne Shot Board:");
                    System.out.println(playerOne.getMyShots().toString());
        
                    Position shotOne = playerOne.chooseShot();
                    
                    playerTwo.setClone(playerTwo.getMyShips());
                    playerTwo.getClone().shoot(shotOne);
                    playerTwo.setMyShips(playerTwo.getClone());
                    
                    playerOne.shotResult(shotOne);
                    
                    System.out.println("playerOne Shot Board:");
                    System.out.println(playerOne.getMyShots().toString());
                    System.out.println("-------------------------------");
                    
                    break;
                }
                catch(InvalidPositionException e){
                    System.out.println(e);
                    System.out.println("Please try again.");   
                }
                catch(PauseException e){
                    System.out.println(e);
                    while(true){
                        System.out.println("Type 'RESUME' to resume, or 'EXIT' to exit.");
                        String temp = input.nextLine();
                        if(temp.equals("RESUME")){
                            break;
                        }
                        else if(temp.equals("EXIT")){
                            return playerOne;
                        }
                    }
                }
            }
            
            while(true){
                try{
                    System.out.println("Turn " + turn);
                    System.out.println("-------------------------------");
                    System.out.println("playerTwo Ship Board:");
                    System.out.println(playerTwo.getMyShips().toString());
                    System.out.println("playerTwo Shot Board:");
                    System.out.println(playerTwo.getMyShots().toString());
        
                    Position shotOne = playerTwo.chooseShot();
                    
                    playerOne.setClone(playerOne.getMyShips());
                    playerOne.getClone().shoot(shotOne);
                    playerOne.setMyShips(playerOne.getClone());
                    
                    playerTwo.shotResult(shotOne);
                    
                    System.out.println("playerTwo Shot Board:");
                    System.out.println(playerTwo.getMyShots().toString());
                    System.out.println("-------------------------------");
                    
                    break;
                }
                catch(InvalidPositionException e){
                    System.out.println(e);
                    System.out.println("Please try again.");
                }
                catch(PauseException e){
                    System.out.println(e);
                    while(true){
                        System.out.println("Type 'RESUME' to resume, or 'EXIT' to exit.");
                        String temp = input.nextLine();
                        if(temp.equals("RESUME")){
                            break;
                        }
                        else if(temp.equals("EXIT")){
                            return playerOne;
                        }
                    }
                }
            }
            
            turn++;
            if(playerOne.getMyShips().allSunk()){
                return playerOne;
            }
            else if(playerTwo.getMyShips().allSunk()){
                return playerTwo;
            }
        }
    }
    
    /**
     * Save the current state of the game (including the boards and players) into a file so it can be 
     * re-loaded and game play continued. You choose what the format of the file is. Java object 
     * serialization is not the preferred solution.
     *
     * @param filename the name of the file in which to save the game state
     *
     * @throws IOException when an I/O problem occurs while saving
     **/
    public void saveGame(String filename) throws IOException{
    
    }

    /**
     * Load the game state from the given file
     *
     * @param filename  the name of the file from which to load the game state
     *
     * @throws IOException when an I/O problem occurs or the file is not in the correct format (as used by saveGame())
     **/
    public void loadGame(String filename) throws IOException{
        
    }

}
