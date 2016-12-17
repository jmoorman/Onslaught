import java.awt.*;

public class Bullet extends Active 
{
	private final String SUBTYPE = "bullet";	//The Active's subType.
	private final int DMG = 10; //base damage of the bullet
	private int dmgModifier;	//An int that the bullets base damage is multiplied by.
	
	private boolean live;	//A boolean to see weither ot not the bullet is moving.
	
	private int direction;	//An int to hold the bullet's direction.
	private final int UP = 0;
	private final int DOWN = 1;
	private final int LEFT = 2;
	private final int RIGHT = 3;
	
	private final int MOVERATIO = 30;	//The number of Points the sprite will move per action.

	//Creates a dead(non-moving) bullet at the given location and with the given specifacations.
	public Bullet(Point start, Image movement, Environment env, int width, int height)
	{
		super(env);
		Rectangle r = new Rectangle(start.x, start.y, width, height);
		setSprite(new SingleSprite(movement, start, new Point(0, 0), r, width, height));
		direction = 0;
		dmgModifier = 1;
		live = false;
	}
	
	//Creates an active (moving) bullet
	public Bullet(Point start, Image movement, Environment env, int width, int height, boolean alive)
	{
		super(env);
		Rectangle r = new Rectangle(start.x, start.y, width, height);
		setSprite(new SingleSprite(movement, start, new Point(0, 0), r, width, height));
		direction = 0;
		dmgModifier = 1;
		live = alive;
	}
	
	//Creates a new dead bullet that is a copy of the given bullet..
	public Bullet(Bullet bullet)
	{
		super(bullet.environment() );
		SingleSprite spr = (SingleSprite)(bullet.getSprite());
		Rectangle r = spr.getBounds();
		Image movement = spr.getCurrentImage();
		int width = spr.getWidth();
		int height = spr.getHeight();
		Point start = spr.getLocation();
		setSprite(new SingleSprite(movement, start, new Point(0, 0), r, width, height) );
		setDirection( bullet.getDirection() );
		live = false;
	}
	
	//Activates the bullet.
	public void activate()
	{
		live = true;
	}
	
	//returns the damage value of a bullet
	public int getDamage()
	{
		return (DMG * dmgModifier );	
	}
	
	//Sets the bullet's damage modifier.
	public void setDamageModifier(int modifier)
	{
		dmgModifier = modifier;
	}
	
	//Returns the bullet's direction as a string.
	public String getDirection()
	{
		if (direction == UP)
			return "up";
		else if (direction == DOWN)
			return "down";
		else if (direction == LEFT)
			return "left";
		else
			return "right";
	}
	
	//Sets the bullet's direction from the given String.
	public void setDirection(String dir)
	{
		if (dir.equals("up"))
			direction = UP;
		else if(dir.equals("down") )
			direction = DOWN;
		else if (dir.equals("left") )
			direction = LEFT;
		else
			direction = RIGHT;
			
	}
	
	//Moves the bullet if it is alive.
	public void run(int iD)
	{
		if (live)
			move(iD);
	}
	
	//Move the bullet in its current direction
	//If the bullet collides with someting, it is removed and whatever it collides with takes damage 
	//	if it is a player or destructible.
	private void move(int iD)
	{
		Sprite spr = this.getSprite();
		if (direction == UP)
			spr.setDeltaPoint(toUp(MOVERATIO) );
		else if (direction == DOWN)
			spr.setDeltaPoint(toDown(MOVERATIO));
		else if (direction == LEFT)
			spr.setDeltaPoint(toLeft(MOVERATIO));
		else
			spr.setDeltaPoint(toRight(MOVERATIO));	
			
		if(live)
			spr.update();
		
		Environment env = environment();
		Point loc = spr.getLocation();
		Locatable collide = env.collision(spr.getBounds() )	;
		if (collide != null)
		{
			if ((collide.getSubType()).equals("player") )	//The bullet collided with a player
				((Player)(collide)).takeDamage(getDamage() );
			else if( (collide.getSubType()).equals("destructible") )	//The bulllet collided with a a destructible.
				((Destructible)(collide)).destroy(getDamage() );
				
			env.removeActive(iD);	//Removes the bullet.
		}
	}
	
	//Returns the active's subType;
	public String getSubType()
	{
		return SUBTYPE;
	}
}
