import java.awt.*;

public abstract class Active extends Locatable
{
	private final String TYPE = "active";	//The Locatable's type.
	
	//Creates a Active in the given Environment.
	public Active(Environment theEnv)
	{
		super(theEnv);
	}
	
	//Creates a Active with the given Sprite and Rectangle in the given Environment.
	public Active(Environment env, Sprite spr, Rectangle bounds)
	{
		super(env, spr, bounds);
	}
	
	//Returns the object's type.
	public String getType()
	{
		return TYPE;
	}
	
	//Returns a dealta Point that is MOVERATIO pixels down.
	//	If the delta Point is out of bounds, then the old Point is returned.
	public Point toDown(int MOVERATIO)
	{
		Sprite spr = this.getSprite();
		Point delta = spr.getDeltaPoint();
		Point newDelta = (new Point(0, (MOVERATIO)));
		if(environment().isInEnv(new Point((newDelta.x + spr.getLocation().x), (newDelta.y + spr.getLocation().y)  )) )
			return newDelta;
		return delta;
	}
	
	//Returns a dealta Point that is MOVERATIO pixels up.
	//	If the delta Point is out of bounds, then the old Point is returned.
	public Point toUp(int MOVERATIO)
	{	
		Sprite spr = this.getSprite();
		Point delta = spr.getDeltaPoint();
		Point newDelta = ( new Point(0, (-MOVERATIO)));
		if(environment().isInEnv(new Point((newDelta.x + spr.getLocation().x), (newDelta.y + spr.getLocation().y)  )) )
			return newDelta;
		return delta;
	}
	
	//Returns a dealta Point that is MOVERATIO pixels left.
	//	If the delta Point is out of bounds, then the old Point is returned.
	public Point toLeft(int MOVERATIO)
	{
		Sprite spr = this.getSprite();
		Point delta = spr.getDeltaPoint();
		Point newDelta = (new Point( (-MOVERATIO), 0)  );
		if(environment().isInEnv(new Point((newDelta.x + spr.getLocation().x), (newDelta.y + spr.getLocation().y)  )) )
			return newDelta;
		return delta;
	}
	
	//Returns a dealta Point that is MOVERATIO pixels right.
	//	If the delta Point is out of bounds, then the old Point is returned.
	public Point toRight(int MOVERATIO)
	{
		Sprite spr = this.getSprite();
		Point delta = spr.getDeltaPoint();
		Point newDelta = ( new Point( (MOVERATIO), 0) );
		if(environment().isInEnv(new Point((newDelta.x + spr.getLocation().x), (newDelta.y + spr.getLocation().y)  )) )
			return newDelta;
		return delta;
	}
	
	
	//Returns the subtype of the Active. 
	public abstract String getSubType();
	
	//The Active object acts. iD is the acitves spot in the active ArrayList.
	public abstract void run(int iD);
}
