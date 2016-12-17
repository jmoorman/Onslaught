import java.awt.*;

public class Box extends Destructible
{
	//NOTE: A box is any square 
	private final String DESTYPE = "box";	//The Destructible's type (desType).
	
	private int desPhase; //The phase of destruction that the object is in. Either 1, 2, 3 (3 being the last phase before being destroyed..)
	
	private final int HPCAP = 300;	//The maxium health of the box.
	private int health;
	
	//The first box image need not  to be stored because the box's original
	//	Sprite contains the initial grave image.
	private Image desBox2;
	private Image desBox3;
	
	//Boxes are created with their initial image (graves are not created partially destroyed.)
	public Box(Point start, Environment theEnv, Image box1, Image box2, Image box3, int width, int height)
	{
		super(theEnv);
		Rectangle r = new Rectangle(start.x, start.y, width, height);
		setSprite(new SingleSprite(box1, start, new Point(0, 0 ), r, width, height));
		
		desBox2 = box2;
		desBox3 = box3;
		
		desPhase = 1;
		health = HPCAP;
	}
	
	//Returns the desType of the Destructible
	public String getDesType()
	{
		return DESTYPE;
	}
	
	//The Destructible takes damage, switches image if it is at a certain point, 
	//	or removes itself if it has 0 or less health.
	public void destroy(int dmg)
	{
		health -= dmg;
		if(health <= 0)	//The box is destroyed and removed from the environment.
		{
			environment().removeStatic(location() );
		}
		else if(health <= 100 && desPhase == 2)	//The box switches to its third image.
		{
			desPhase = 3;
			Sprite spr = (getSprite());
			Point loc = location();
			spr.setLocation(new Point(loc.x, loc.y + (spr.getHeight() / 2)));
			loc = location();
			spr.setBounds(new Rectangle(loc.x, loc.y, spr.getWidth(), (spr.getHeight() / 2)) );
			((SingleSprite)(spr)).setImage(desBox3);
		}
		else if(health <= 200 && desPhase == 1)	//The box switches to its second image.
		{
			desPhase = 2;
			Sprite spr = (getSprite());
			Point loc = location();
			spr.setLocation(new Point(loc.x, loc.y + ((spr.getHeight()) /3) ));
			loc = location();
			spr.setBounds(new Rectangle(loc.x, loc.y, spr.getWidth(), ((spr.getHeight()* 2 ) / 3)) );
			((SingleSprite)(spr)).setImage(desBox2);
		}
	}
}

