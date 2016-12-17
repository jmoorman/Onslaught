import java.awt.*;

public class SpeedBonus extends Drop
{
	private final String DROPTYPE = "speedbonus";	//The Drop's type
	private final String SPEEDIMAGE = "speedUp.gif";	//The SpeedBonus' Image name.
	private final int BONUS = 2;	//The bonus offered by the SpeedBonus.
	
	//Creates a SpeedBonus at the given location, int the given Environemnt, and with the given specifications.
	public SpeedBonus(Point start, Environment theEnv, int width, int height)
	{
		super(theEnv);
		Image img = Toolkit.getDefaultToolkit().getImage( theEnv.IMAGEPATH.concat(SPEEDIMAGE) );
		img = img.getScaledInstance(width, height, 4);
		theEnv.mt.addImage(img, theEnv.mediaCount);
		theEnv.mediaCount++;
		
		Rectangle r = new Rectangle(start.x, start.y, width, height);
		
		setSprite(new SingleSprite(img, start, new Point(0, 0 ), r, width, height));
	}
	
	//Creates a SpeedBonus in the given Environment with the given Rectangle.
	public SpeedBonus(Rectangle rect, Environment theEnv)
	{
		super(theEnv);
		Image img = Toolkit.getDefaultToolkit().getImage( theEnv.IMAGEPATH.concat(SPEEDIMAGE) );
		img = img.getScaledInstance((int)(rect.getWidth()), (int)(rect.getHeight()), 4);
		theEnv.mt.addImage(img, theEnv.mediaCount);
		theEnv.mediaCount++;
		
		setSprite(new SingleSprite(img, rect.getLocation(), new Point(0, 0 ), rect, (int)(rect.getWidth()), (int)(rect.getHeight()) ));
	}
	
	//Returns the Drop's type.
	public String getDropType()
	{
		return DROPTYPE;
	}
	
	//Returns the Drop's bonus.
	public int getBonus()
	{
		return BONUS;
	}
}