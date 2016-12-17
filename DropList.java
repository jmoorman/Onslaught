import java.util.*;
import java.awt.*;

public class DropList implements Runnable
{
	private ArrayList drops;	//drops is an ArrayList of Drop objects.
	private Environment theEnv; //The Environment that will hold this DropList.
	private final int NUMDROPS = 3; //The number of types of drops to help make the distribution of drops random.
	private final int DIMENSIONS = 10; //Drops will all be square and this is its height and width.
	
	//Creates a DropList with the given Environment
	public DropList(Environment env)
	{
		theEnv = env;
		drops = new ArrayList();
	}
	
	//Pre:stuff is an ArrayList of Drops.
	//Creates a DropList with the given Environment and ArrayList of Drops
	public DropList(Environment env, ArrayList stuff)
	{
		theEnv = env;
		drops = (ArrayList)(stuff.clone());
	}
	
	//Adds the given Drop to drops.
	public void addDrop(Drop drp)
	{
		drops.add(drp);
	}
	
	//Removes the given Drop from drops.
	public Drop removeDrop(Drop item)
	{
		for(int count = 0; count < drops.size(); count++ )
		{
			if(item == (Drop)(drops.get(count) )  )	//Same adresses so, same Object.
				return (Drop)(drops.remove(count));
		}
		return null;	//The Drop wassn't found.
	}
	
	//Generates a random Drop at a random location.
	public void generateDrop()
	{
		int ranType = ((int) (Math.random() * NUMDROPS + 1));
		Rectangle rect = generateLocation();
		if (ranType == 1)	//Health
		{
			drops.add(new Health(rect, theEnv)  );
		}
		else if(ranType == 2)	//Speed Bonus
		{
			drops.add(new SpeedBonus(rect, theEnv) );
		}
		else if(ranType == 3)	//Damage Bonus
		{
			drops.add(new DamageBonus(rect, theEnv) );
		}
	}
	
	//Generates a Rectangle at a given location where it is not colliding with anyhting.
	private Rectangle generateLocation()
	{
		Rectangle bounds = theEnv.getBounds();
		int envX = (int)(bounds.getWidth());
		int envY = (int)(bounds.getHeight());
		
		int ranX = (int)(Math.random() * envX);
		int ranY = (int)(Math.random() * envY);
		Rectangle rect = new Rectangle(ranX, ranY, DIMENSIONS, DIMENSIONS);
		
		while (!(theEnv.collision(rect) == null))
		{
			ranX = (int)(Math.random() * envX);
			ranY = (int)(Math.random() * envY);
			rect = new Rectangle(ranX, ranY, DIMENSIONS, DIMENSIONS);
		}
		
		return rect;
	}
	
	//While there are players in the game, this will generate a random drop every once in a while.
	public void run()
	{
		while (theEnv.hasPlayers() )
		{
			generateDrop();
			try
			{
				Thread.sleep(25000);	//A long sleep to space out the time between drops.
			}
			catch(InterruptedException e)
			{
			}
		}
	}
}
