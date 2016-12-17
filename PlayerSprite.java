import java.awt.*;
import java.awt.Point;

class PlayerSprite extends Sprite
{
	protected Image[] movementUp;
	protected Image[] movementDown;
	protected Image[] movementLeft;
	protected Image[] movementRight;
	
	protected Image[] actionUp;
	protected Image[] actionDown;
	protected Image[] actionLeft;
	protected Image[] actionRight;
	
	private int currentImgIndex;
	private boolean act;
	private String spriteType = "Player";
	
	public PlayerSprite(Image[] moveUp, Image[] moveDown, Image[] moveLeft, 
			Image[] moveRight, Image[] actUp, Image[] actDown, Image[] actLeft, 
			Image[] actRight, Point loc)
	{
		super(loc);
		
		movementUp = moveUp;
		movementDown = moveDown;
		movementLeft = moveLeft;
		movementRight = moveRight;
		
		actionUp = actUp;
		actionDown = actDown;
		actionLeft = actLeft;
		actionRight = actRight;
		
		currentImgIndex = 0;
		act = false;
		setActives();
	}

	public PlayerSprite(Image[] moveUp, Image[] moveDown, Image[] moveLeft, 
			Image[] moveRight, Image[] actUp, Image[] actDown, Image[] actLeft, 
			Image[] actRight, Point loc, Point delta, Rectangle ends, int wide, int tall)
	{
		super(loc, delta, ends, wide, tall);
		
		movementUp = moveUp;
		movementDown = moveDown;
		movementLeft = moveLeft;
		movementRight = moveRight;
		
		actionUp = actUp;
		actionDown = actDown;
		actionLeft = actLeft;
		actionRight = actRight;
		
		currentImgIndex = 0;
		act = false;
		setActives();
	}
	
	private void setActives()
	{
		if(actionUp.length <= 0)
		{
			actionUp = new Image[1];
			actionUp[0] = movementUp[0];
		}
		if(actionDown.length <= 0)
		{
			actionDown = new Image[1];
			actionDown[0] = movementDown[0];
		}
		if(actionLeft.length <= 0)
		{
			actionLeft = new Image[1];
			actionLeft[0] = movementLeft[0];
		}
		if(actionRight.length <= 0)
		{
			actionRight = new Image[1];
			actionRight[0] = movementRight[0];
		}
	}
	
	public Image getCurrentImage()
	{
		if(act)
			return ( getCurrentAction(myDir() ) );
		return ( getCurrentMovement(myDir() ) );
	}
	
	private Image getCurrentMovement(String direction)
	{
		if(direction.equals("up") )
			return (movementUp[ (currentImgIndex % movementUp.length)]  );
		else if(direction.equals("down") )
			return (movementDown[ (currentImgIndex % movementDown.length)]  );
		else if(direction.equals("left") )
			return (movementLeft[ (currentImgIndex % movementLeft.length)]  );
		else
			return (movementRight[ (currentImgIndex % movementRight.length)]  );
	}
	
	private Image getCurrentAction(String direction)
	{
		if(direction.equals("up") )
			return (actionUp[ (currentImgIndex % actionUp.length)]  );
		else if(direction.equals("down") )
			return (actionDown[ (currentImgIndex % actionDown.length)]  );
		else if(direction.equals("left") )
			return (actionLeft[ (currentImgIndex % actionLeft.length)]  );
		else
			return (actionRight[ (currentImgIndex % actionRight.length)]  );
	}
	
	public void update()
	{	
		if (!act)
		{
			super.update();
			String dir = myDir();
			if(dir.equals("up"))
				currentImgIndex = (currentImgIndex + 1) % movementUp.length;
			else if(dir.equals("down"))
				currentImgIndex = (currentImgIndex + 1) % movementDown.length;
			else if(dir.equals("left"))
				currentImgIndex = (currentImgIndex + 1) % movementLeft.length;
			else
				currentImgIndex = (currentImgIndex + 1) % movementRight.length;
		}
		else
		{
			String dir = myDir();
			if(dir.equals("up"))
				currentImgIndex = (currentImgIndex + 1) % actionUp.length;
			else if(dir.equals("down"))
				currentImgIndex = (currentImgIndex + 1) % actionDown.length;
			else if(dir.equals("left"))
				currentImgIndex = (currentImgIndex + 1) % actionLeft.length;
			else
				currentImgIndex = (currentImgIndex + 1) % actionRight.length;
		}
	}
	
	public void setAct(boolean state)
	{
		act = state;
	}
	
	public String getSpriteType()
	{
		return spriteType;
	}
	
	private Rectangle generateLocation(Environment theEnv)
	{
		Rectangle bounds = theEnv.getBounds();
		int envX = (int)(bounds.getWidth());
		int envY = (int)(bounds.getHeight());
		
		int ranX = (int)(Math.random() * envX);
		int ranY = (int)(Math.random() * envY);
		Rectangle rect = new Rectangle(ranX, ranY, getWidth(), getHeight());
		
		while (!(theEnv.collision(rect) == null))
		{
			ranX = (int)(Math.random() * envX);
			ranY = (int)(Math.random() * envY);
			rect = new Rectangle(ranX, ranY, getWidth(), getHeight());
		}
		
		return rect;
	}
	
	public void respawn(Environment env)
	{
		Rectangle r = generateLocation(env);
		setBounds(r);
		setLocation(r.getLocation());
	}
	
	
}
