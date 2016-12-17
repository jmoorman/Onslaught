import java.awt.*;

public abstract class Static extends Locatable
{
	private final String TYPE = "static";	//The locatables type.
	
	//Creates a static int the given environment.
	public Static(Environment theEnv)
	{
		super(theEnv);
		theEnv.addStatic(this);
	}
	
	//Creates a Static with the given Sprite and Rectangle
	public Static(Environment env, Sprite spr, Rectangle rect)
	{
		super(env, spr, rect);
	}

	
	//returns the locatable's type.
	public String getType()
	{
		return TYPE;
	}
	
	//Returns the locatable's subType.
	public abstract String getSubType();
}
