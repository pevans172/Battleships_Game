import java.util.ArrayList;
public class Board
{
    private String[][] boardList;
    private ArrayList<Ship> shipList;
    
    public Board()
    {
        createEmptyBoard();
        shipList = new ArrayList<Ship>();
    }
    
    public ArrayList<Ship> getShipList(){
        return shipList;
    }
    
    public void setBoardList(String[][] i){
        this.boardList = i;
    }
    public void setShipList(ArrayList<Ship> i){
        this.shipList = i;
    }
    
    public void createEmptyBoard(){
        boardList = new String[11][11];
        // fill first row with numbers for x-axis
        for(int i = 0; i < 11; i++){
            boardList[0][i] = Integer.toString(i);
        }
        
        // fill first entry of each following row with numbers for y-axis
        for(int row = 1; row < 11; row++){
            boardList[row][0] = Integer.toString(row);
        }
        
        // fill the remaining spots with empty symbol '-'
        for(int row = 1; row < 11; row++){
            for(int col = 1; col < 11; col++){
                boardList[row][col] = "-";
            }
        }
    }
    
    public void placeShip(Ship ship, Position position, boolean isVertical) throws InvalidPositionException, ShipOverlapException
    {
        int size = ship.getSize();
        int row = position.getY();
        int col = position.getX();
        String symbol = "";
        ArrayList<Position> temp = new ArrayList<Position>();
        
        if(shipList.size() == 0){
            symbol = "A";
        }
        else if(shipList.size() == 1){
            symbol = "B";
        }
        else if(shipList.size() == 2){
            symbol = "C";
        }
        else if(shipList.size() == 3){
            symbol = "D";
        }
        else if(shipList.size() == 4){
            symbol = "E";
        }
        
        if(isVertical == true)
        {
            if((row + size)>10){
                throw new InvalidPositionException("Ship at position (" + col + ","+ row + ") goes off the edge of the board");
            }
            
            for(int i = 0; i<size; i++){
                Position p = new Position(col, row + i);
                if(getStatus(p) != ShipStatus.NONE){
                    throw new ShipOverlapException("At position (" + p.getX() + ","+ p.getY() + ") there is an overlap.");
                }
                temp.add(p);
            }
            
            for(int i = 0; i<size; i++){
                boardList[row+i][col] = symbol;
            }
        }
        
        if(isVertical == false)
        {
            if((col + size)>10){
                throw new InvalidPositionException("Ship at position (" + col + ","+ row + ") goes off the edge of the board");
            }
            
            for(int i = 0; i<size; i++){
                Position p = new Position(col + i, row);
                if(getStatus(p) != ShipStatus.NONE){
                    throw new ShipOverlapException("At position (" + p.getX() + ","+ p.getY() + ") there is an overlap.");
                }
                temp.add(p);
            }
            
            for(int i = 0; i<size; i++){
                boardList[row][col+i] = symbol;
            }
        }
        ship.setCoords(temp);
        shipList.add(ship);
    }

    public void shoot(Position position) throws InvalidPositionException{
        int row = position.getY();
        int col = position.getX();
        if(row<1 || row>10 || col<1 || col>10){
            throw new InvalidPositionException("Position (" + col + ","+ row + ") not on board");
        }
        
        // search for a match of the position given to any of those occupied by ships
        boardList[row][col] = "m";
        boolean match = false;
        for(int i = 0; i < shipList.size(); i++){
            Ship ship = shipList.get(i);
            ArrayList<Position> coords = ship.getCoords();
            for(int j = 0; j < ship.getSize(); j++){
                if(position.getX() == coords.get(j).getX() && position.getY() == coords.get(j).getY()){
                   ship.shoot(j);
                   if(ship.isSunk()){
                       for(Position k : coords){
                           int r = k.getY();
                           int c = k.getX();
                           boardList[r][c] = "#";
                       }
                   }
                   else{
                       boardList[row][col] = "*";
                   }
                   shipList.set(i, ship);
                   match = true;
                   break;
                }
                if(match){break;}
            }
        }
    }

    public ShipStatus getStatus(Position position) throws InvalidPositionException{
        int row = position.getY();
        int col = position.getX();
        if(row<1 || row>10 || col<1 || col>10){
            throw new InvalidPositionException("Position (" + col + ","+ row + ") not on board");
        }
        String i = boardList[row][col];
        ShipStatus out = ShipStatus.NONE;
        if(i == "-"){out = ShipStatus.NONE;}
        if(i == "A" || i == "B" || i == "C" || i == "D" || i == "E"){out = ShipStatus.INTACT;}
        if(i == "*"){out = ShipStatus.HIT;}
        if(i == "#"){out = ShipStatus.SUNK;}
        return out;
    }

    public boolean allSunk(){
        boolean out = true;
        for(Ship i : shipList){
            if(i.isSunk() != true){
                out = false;
                break;
            }
        }
        return out;
    }

    public String toString(){
        String out = "";
        for(String[] row : boardList){
            for(String col : row){
                out += col;
                out += " ";
            }
            out += "\n";
        }
        return out;
    }

    public Board clone(){
            Board clone = new Board();
            clone.setBoardList(this.boardList);
            clone.setShipList(this.shipList);
            return clone;
    }
}