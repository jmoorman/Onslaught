import java.awt.*;

class SingleSprite extends Sprite
{
	protected Image image;	//The SingleSprite's Image.
	private String spriteType = "single";	//The Sprite's type.
	
	//Creates a SingleSprite at the given Point and with the given Image, deltaPoint, Rectangle, and dimensions.
	public SingleSprite(Image pic, Point loc, Point delta, Rectangle ends, int wide, int tall)
	{
		super(loc, delta, ends, wide, tall);
		image = pic;
	}
	
	//Creates a SingleSprite at the given Point and with the given Image.
	public SingleSprite(Image pic, Point loc)
	{
		super(loc);
		image = pic;
	}
	
	//Returns the Sprite's type.
	public String getSpriteType()
	{
		return spriteType;
	}
	
	//Returns the Sprite's current Image.
	public Image getCurrentImage()
	{
		return image;
	}
	
	//Sets the Sprite's Image to the passed Image.
	public void setImage(Image pic)
	{
		image = pic;
	}
}
