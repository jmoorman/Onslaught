import java.awt.*;

public abstract class Sprite 
{
	private Point location;	//The Sprite's location
	private Point deltaPoint;	//The Sprite's deltaPoint. 
	//NOTE: A delta Point is a point that the Sprite translates to.
	private boolean act;	//Wither or not the sprite is using act images.
	private Rectangle bounds;	//The Sprite's rectangle.
	private int width;	//The width of the Sprite.
	private int height;	//The height of the Sprite.
	
	//Creates A Sprite at the given location with the given deltaPoint, Rectangle, and dimensions.
	public Sprite(Point loc, Point delta, Rectangle ends, int wide, int tall)
	{
		act = false;
		location = new Point(loc.x, loc.y);
		deltaPoint = new Point(delta.x, delta.y);
		bounds = ends;
		width = wide;
		height = tall;
	}
	
	//Creates a Sprite at the given Point.
	public Sprite(Point loc)
	{
		act = false;
		location = new Point(loc.x, loc.y);
		deltaPoint = new Point(0, 0);
	}
	
	//Returns the Sprite's width.
	public int getWidth()
	{
		return width;
	}
	
	//Returns the Sprite;s height.
	public int getHeight()
	{
		return height;
	}
	
	//Sets the Sprite's location to the given Point.
	public void setLocation(Point loc)
	{
		location = new Point(loc.x, loc.y);
	}
	
	//Returns the Sprite's location.
	public Point getLocation()
	{
		return new Point(location.x, location.y);
	}
	
	//Sets the Sprite's deltaPoint to the given Point.
	public void setDeltaPoint(Point dest)
	{
		deltaPoint = new Point(dest.x, dest.y);
	}
	
	//Returns the Sprite's deltaPoint.
	public Point getDeltaPoint()
	{
		return new Point(deltaPoint.x, deltaPoint.y);
	}
	
	//Sets the act state to the passed value.
	public void setAct(boolean state)
	{
		act = state;
	}
	
	//Sets the Sprite's bounds to the passed Rectangle.
	public void setBounds(Rectangle r)
	{
		bounds = r;	
	}
	
	//Retutrns the Sprite's Rectabgle.
	public Rectangle getBounds()
	{
		return bounds;
	}
	
	//pre: deltapoint is in one axis moving along one axis
	//Returns the Sprite's direction.
	public String myDir()
	{
		if(deltaPoint.x < 0 && deltaPoint.y == 0)
			return "left";
		else if(deltaPoint.x > 0 && deltaPoint.y == 0)
			return "right";
		else if(deltaPoint.x == 0 && deltaPoint.y < 0)
			return "up";
		else
			return "down";
	}
	
	//Updates the Sprite's bounds and location.
	public void update()
	{
		setBounds(new Rectangle((location.x + deltaPoint.x), (location.y + deltaPoint.y), getWidth(), getHeight() ));
		location.translate(deltaPoint.x, deltaPoint.y);
	}
	
	//Returns the Sprite's type.
	public abstract String getSpriteType();
	
	//Retutns the Sprite's current Image.
	public abstract Image getCurrentImage();
}
