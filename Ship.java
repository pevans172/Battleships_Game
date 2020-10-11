import java.util.ArrayList;
public class Ship
{
    private int size;
    private ArrayList<ShipStatus> stats;
    private ArrayList<Position> coords;
    
    public Ship(int size)
    {
        this.size = size;
        stats = new ArrayList<ShipStatus>();
        coords = new ArrayList<Position>();
        
        for(int i = 0; i < size; i++){
            stats.add(ShipStatus.INTACT);
        }
    }

    public int getSize()
    {
        return size;
    }
    
    public ArrayList<Position> getCoords(){
        return coords;
    }
    
    public void setCoords(ArrayList<Position> i){
        coords = i;
    }
    
    public ShipStatus getStatus(int offset) throws InvalidPositionException
    {
        if(offset < size && offset >= 0){
            return stats.get(offset);
        }
        else {
            throw new InvalidPositionException("This ship does not occupy offset " + offset);
        }
    }
    
    public void shoot(int offset) throws InvalidPositionException
    {
        if(offset < size && offset >= 0){
            if (stats.get(offset) != ShipStatus.SUNK){
                stats.set(offset, ShipStatus.HIT);     
                if(isSunk() == true){
                    for(int i = 0; i < size; i++){
                        stats.set(i, ShipStatus.SUNK);
                    }
                }
            }
        }
        else {
            throw new InvalidPositionException("This ship does not occupy offset " + offset);
        }
    }
    
    public boolean isSunk()
    {
        boolean check = true;
        for(ShipStatus i : stats){
            if(i == ShipStatus.INTACT){
                check = false;
                break;
            }
        }
        return check;
    }
    
}