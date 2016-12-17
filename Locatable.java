import java.awt.*;

public abstract class Locatable 
{
	private Environment env;	//The environment that the Locatable is in.
	private Sprite sprite;	//The locatable's sprite (picture)
	private Rectangle bounds;	//The locatable's dimensions and position.
	
	//Creates a Locatable in the given Environment
	public Locatable(Environment theEnv)
	{
		env = theEnv;
	}
	
	//Creates a Locatable with the given Sprite and Rectangle in the given Environment.
	public Locatable(Environment theEnv, Sprite spr, Rectangle perimeter)
	{
		env = theEnv;
		sprite = spr;
		bounds = perimeter;
	}
	
	//Returns the Locatable's location.
	public Point location()
	{
		return sprite.getLocation();
	}
	
	//Returns the current environemnt
	public Environment environment()
	{
		return env;
	}
	
	//Sets the environment to the passed environment
	public void setEnv(Environment newEnv)
	{
		env = newEnv;
	}
	
	//Returns the Locatable's Sprite.
	public Sprite getSprite()
	{
		return sprite;
	}
	
	//Sets the Locatable's Sprite to the passed Sprite.
	public void setSprite(Sprite spr)
	{
		sprite = spr;
	}
	
	//Returns the Locatable's rectangle.
	public Rectangle getBounds()
	{
		return bounds;
	}
	
	//Sets the Locatable's rectangle to the passed Rectangle.
	public void setBounds(Rectangle r)
	{
		bounds = r;
	}
	
	//Returns the type of the Locatable. "static" or "active"
	public abstract String getType();
	
	//Returns the subtype of the Locatable. 
	public abstract String getSubType();
	
	
	
	
}
