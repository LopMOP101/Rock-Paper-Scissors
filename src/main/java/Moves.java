import java.util.HashSet;
import java.util.Set;

public class Moves {

    public String[] moves;
    private int wincount;

    private boolean isRightMoves(String[] moves)
    {
        if(moves.length>=3)
        {
            if (moves.length % 2 != 0)
                if (reiterationinArray(moves))
                    return true;
                else
                    System.out.println("Moves should not be repeated ");
            else
                System.out.println("The number of moves must be odd.");
        }
        else
            System.out.println("The number of moves must be more than three");
        return false;
    }

    private boolean reiterationinArray(String[]myArray)
    {
        boolean result = true;
        Set set = new HashSet();
        for(int i=0; i<myArray.length; i++) {
            if(!set.add(myArray[i]))
                result=false;
        }
        return result;
    }

    public boolean iswingame(int computermove,int usermove)
    {
        if ((usermove>computermove) && (usermove<=(computermove+wincount)))
            return true;
        else
            if (usermove<=(wincount-(moves.length-computermove)))
                return true;
        return false;
    }

    public Moves(String[]movesfromconsol)
    {
        if (isRightMoves(movesfromconsol))
        {
            moves = movesfromconsol.clone();
            wincount = Math.floorDiv(moves.length,2);
        }
    }
}
