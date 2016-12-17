import java.awt.*;

public abstract class Destructible extends Static
{
	public final String SUBTYPE = "destructible";	//The static's subType.
	
	//Creates a destructible in the given Environment
	public Destructible(Environment env)
	{
		super(env);
	}
	
	//Creates a destructible with the given Sprite and Rectangle in the given Environment.
	public Destructible(Environment env, Sprite spr, Rectangle rect)
	{
		super(env, spr, rect);
	}
	
	//Returns the Static's subType.
	public String getSubType()
	{
		return SUBTYPE;
	}
	
	//Returns the Destructible's desType.
	public abstract String getDesType();
	
	//The destructible takes the given damage.
	public abstract void destroy(int dmg);
}
