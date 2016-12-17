import java.awt.*;

public class Health extends Drop
{
	private final String DROPTYPE = "health";	//The Drop's dropType.
	private final String HEALTHIMAGE = "health.jpg";	//The name of the Health image.
	private final int RECOVERY = 50;	//The amount that the health recovers.
	
	//Creattes a DamageBonus at the given location, int the given Environemnt, and with the given specifications.
	public Health(Point start, Environment theEnv, int width, int height)
	{
		super(theEnv);
		Image img = Toolkit.getDefaultToolkit().getImage( theEnv.IMAGEPATH.concat(HEALTHIMAGE) );
		img = img.getScaledInstance(width, height, 4);
		theEnv.mt.addImage(img, theEnv.mediaCount);
		theEnv.mediaCount++;
		
		Rectangle r = new Rectangle(start.x, start.y, width, height);
		
		setSprite(new SingleSprite(img, start, new Point(0, 0 ), r, width, height));
	}
	
	//Creates a DamageBonus in the given Environment with the given Rectangle.
	public Health(Rectangle rect, Environment theEnv)
	{
		super(theEnv);
		Image img = Toolkit.getDefaultToolkit().getImage( theEnv.IMAGEPATH.concat(HEALTHIMAGE) );
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
	
	//Returns the amount recovered.
	public int getRecovery()
	{
		return RECOVERY;
	}
}
