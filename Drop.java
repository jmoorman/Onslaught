import java.awt.*;

public abstract class Drop extends Destructible
{
	private final String DESTYPE = "drop";	//The Destructible's desType.
	
	//Creates a Drop in the given Environment.
	public Drop(Environment env)
	{
		super(env);
	}
	
	//Creates a Drop in the given Environment and with the given rectangle and Sprite.
	public Drop(Environment env, Sprite spr, Rectangle bounds)
	{
		super(env, spr, bounds);
		env.addStatic(this);
	}
	
	//Returns the Destructible's desType.
	public String getDesType()
	{
		return DESTYPE;
	}
	
	//If the Drop is hit an any way, it is destroyed.
	public void destroy(int dmg)
	{
		environment().removeDrop(this);
	}
	
   //Returns a String representation of the drop.
   public String toString()
   {
      return getClass().getName();
   }
   
	//Returns the Drop's dropType.
	public abstract String getDropType();
}
