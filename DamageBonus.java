import java.awt.*;

public class DamageBonus extends Drop
{
	private final String DROPTYPE = "damagebonus";	//The Drop's type (dropType).
	private final String DAMAGEIMAGE = "damageUp.gif";	//The Drop's image name.
	private final int BONUS = 2;	//The amount of bonus.
	
	//Creates a DamageBonus at the given location, int the given Environemnt, and with the given specifications.
	public DamageBonus(Point start, Environment theEnv, int width, int height)
	{
		super(theEnv);
		Image img = Toolkit.getDefaultToolkit().getImage( theEnv.IMAGEPATH.concat(DAMAGEIMAGE) );
		img = img.getScaledInstance(width, height, 4);
		theEnv.mt.addImage(img, theEnv.mediaCount);
		theEnv.mediaCount++;
		
		Rectangle r = new Rectangle(start.x, start.y, width, height);
		
		setSprite(new SingleSprite(img, start, new Point(0, 0 ), r, width, height));
	}
	
	//Creates a DamageBonus in the given Environment with the given Rectangle.
	public DamageBonus(Rectangle rect, Environment theEnv)
	{
		super(theEnv);
		Image img = Toolkit.getDefaultToolkit().getImage( theEnv.IMAGEPATH.concat(DAMAGEIMAGE) );
		img = img.getScaledInstance((int)(rect.getWidth()), (int)(rect.getHeight()), 4);
		theEnv.mt.addImage(img, theEnv.mediaCount);
		theEnv.mediaCount++;
		
		setSprite(new SingleSprite(img, rect.getLocation(), new Point(0, 0 ), rect, (int)(rect.getWidth()), (int)(rect.getHeight()) ));
	}
	
	//Returns the Drop's dropType.
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