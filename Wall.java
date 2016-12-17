import java.awt.*;

public class Wall extends Static
{
	private final String SUBTYPE = "wall";	//The Static's subType
	
	//Creates a Wall at the given Point, in the given Environment, with the given Image, height, and width.
	public Wall(Point start, Image img, Environment theEnv, int width, int height)
	{
		super(theEnv);
		Rectangle r = new Rectangle(start.x, start.y, width, height);
		setSprite(new SingleSprite(img, start, new Point(0, 0), r, width, height));
	}
	
	//Returns the Static's subType.
	public String getSubType()
	{
		return SUBTYPE;
	}
}
