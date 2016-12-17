import java.util.*;
import java.awt.*;
import java.net.*;
import java.applet.*;

public class Player extends Active implements Runnable
{
	private final String SUBTYPE = "player";	//The actives subtype.
	
	private String myName;	//The player's name
	private int health;	//When 0 the player is dead.
	
	private Image grave1;	//The first image of the player's grave
	private Image grave2;	//The second image of the player's grave
	private Image grave3;	//The third image of the player's grave
	
	private AudioClip fireSound;	//The sound played when a bullet is fired
	private AudioClip deathSound;	//The sound played when the player dies.

	//Booleans to show the player's intent to move and act.
	private boolean moveRight;
	private boolean moveLeft;
	private boolean moveUp;
	private boolean moveDown;
	private boolean fire;
	
	private boolean dead;	//A boolean to see if the player is dead of not.
	
	private KeyInput keys;	//The players key listiner
	//Each player must have their own keyListiner so that multiple people can play at once.
	
	private final int MOVERATIO = 10;	//The number of Points the sprite will move per action.
	
	private int moveModifier;	//The amout that MOVERATIO is multiplied by if the player has a speed modifier.
	private final int SPEEDUPCAP = 40;	//The number of moves that the moveModifier is not 1;
	private int speedTimer; //When 0, moveModifier == 0;
	
	private int damageModifier;	//The amout that fired bullets' damageModifier is set to. 
	private final int DAMAGEUPCAP = 10;	//The number of bullets fired that the damageModifier is not 1;
	private int damageTimer; //When 0, damageModifier == 0;
	
	private int myLives;	//The number of lives the player has.

	
	//Constructs a new player with a player sprite.
	public Player(Point start, Image[] movementUp, Image[] movementDown, Image[] movementLeft, 
			Image[] movementRight, Image[] actionUp, Image[] actionDown, Image[] actionLeft, 
			Image[] actionRight, Environment env, GameFrame f, int width, int height, int keyCode, 
			String name, Image death1, Image death2, Image death3, int lives)
	{
		super(env);
		Rectangle r = new Rectangle(start.x, start.y, width, height);
		setSprite(new PlayerSprite(movementUp, movementDown, movementLeft, movementRight,
			actionUp, actionDown, actionLeft, actionRight, start, new Point(start), r, width, height));

		myLives = lives;
		myName = name;
		grave1 = death1;
		grave2 = death2;
		grave3 = death3;
		health = 100;
		dead = false;
		moveRight = false;
		moveLeft = false;
		moveUp = false;
		moveDown = false;
		fire = false;
		moveModifier = 1;
		damageModifier = 1;
		speedTimer = 0;
		damageTimer = 0;
		keys = new KeyInput(this, f, keyCode);
		f.addKeyListener(keys);
	}
	
	//Returns the player's name
	public String getName()
	{
		return myName;
	}
	
	//Returns the players ID (keySet)
	//	Keyset is used for an ID because no two player's can have the same keySet.
	public int getID()
	{
		return (keys.getKeySet());
	}
	
	//Retuns the Active's subType.
	public String getSubType()
	{
		return SUBTYPE;
	}
	
	//Returns the amount of health that the player has left after being hit.
	public int takeDamage(int dmg)
	{
		health -= dmg;
		if (health <= 0)
		{
			deathSound.play();
			if(myLives == 0)	//If the player has no move health or lives.
			{
				dead = true;
			}
			else	//If the player has no health, but has move lives.
			{
				myLives--;
				Sprite spr = getSprite();
				Box g = new Box(location(), environment(), grave1, grave2, grave3, spr.getWidth(), spr.getHeight() );
				try
				{
					Thread.sleep(10);
				}
				catch(InterruptedException e)
				{
				}
				respawn();
			}
		}
		return health;
	}
	
	//Process the typed key by setting the player's intent to move.
	public void processKey(int keyCode)
	{
		if(keyCode == keys.up)
			moveUp = true;
		else if(keyCode == keys.down)
			moveDown = true;
		else if(keyCode == keys.left)
			moveLeft = true;
		else if(keyCode == keys.right)
			moveRight = true;
		else if(keyCode == keys.fire)
		{
			fire = true;
			Sprite spr = this.getSprite();
			spr.setAct(true);
		}
	}
	
	//A defualt run required by actives.
	public void run(int iD)
	{
	}
	
	//A run to implement Runnable.
	//This will cause this player Thread to run until the player is dead and out of lives.
	public void run()
	{
		while (!dead)
		{
			move();
			try	//A small sleep to let the rest of the program catch up with this player
			{
				Thread.sleep(10);
			}
			catch(InterruptedException e)
			{
			}
		}
		
		//Makes a  grave and removes the player from the environment.	
		System.out.println("dead");	
		Sprite spr = getSprite();
		Box g = new Box(location(), environment(), grave1, grave2, grave3, spr.getWidth(), spr.getHeight() );
		environment().removePlayer(this);
	}
	
	//Move the player according to the players intent to move.
	private void move()
	{
		if(moveUp == true)	//Move Up
		{
			moveUp();
			if(speedTimer <= 0)
				moveModifier = 1;
			else
				speedTimer--;
		}
		else if(moveDown == true)	//Move Down
		{
			moveDown();
			if(speedTimer <= 0)
				moveModifier = 1;
			else
				speedTimer--;
		}
		
		if(moveLeft == true)	//Move left
		{
			moveLeft();
			if(speedTimer <= 0)
				moveModifier = 1;
			else
				speedTimer--;
		}
		else if(moveRight == true)	//Move Rigth
		{
			moveRight();
			if(speedTimer <= 0)
				moveModifier = 1;
			else
				speedTimer--;
		}
		
		if(fire == true)	//Fire
		{
			if(damageTimer <= 0)
				damageModifier = 1;
			else
				damageTimer--;
			
			Sprite spr = getSprite();
			fire();
			spr.setAct(true);
			spr.update();
		}
	}
	
	//Moves the Player up by (MOVERATIO * moveModifier)
	private void moveUp()
	{
		Sprite spr = getSprite();
		Environment env = environment();
		Point delta = toUp(MOVERATIO * moveModifier);
		Point loc = spr.getLocation();
		if(!collision(delta, loc, spr, env) )
		{
			spr.setDeltaPoint(delta);
		}
		else
			spr.setDeltaPoint(new Point(0, 0));
		moveUp = false;
		
		spr.setAct(false);
		spr.update();
	}
	
	//Moves the Player down by (MOVERATIO * moveModifier)
	private void moveDown()
	{
		Sprite spr = getSprite();
		Environment env = environment();
		Point delta = toDown(MOVERATIO * moveModifier);
		Point loc = spr.getLocation();
		if(!collision(delta, loc, spr, env) )
		{
			spr.setDeltaPoint(delta);
		}
		else
			spr.setDeltaPoint(new Point(0, 0));
		moveDown = false;
		
		spr.setAct(false);
		spr.update();
	}
	
	//Moves the Player left by (MOVERATIO * moveModifier)
	private void moveLeft()
	{
		Sprite spr = getSprite();
		Environment env = environment();
		Point delta = toLeft(MOVERATIO * moveModifier);
		Point loc = spr.getLocation();
		if(!collision(delta, loc, spr, env) )
		{
			spr.setDeltaPoint(delta);
		}
		else
			spr.setDeltaPoint(new Point(0, 0));
		moveLeft = false;
		
		spr.setAct(false);
		spr.update();
	}
	
	//Moves the Player right by (MOVERATIO * moveModifier)
	private void moveRight()
	{
		Sprite spr = getSprite();
		Environment env = environment();
		Point delta = toRight(MOVERATIO * moveModifier);
		Point loc = spr.getLocation();
		if(!collision(delta, loc, spr, env) )
		{
			spr.setDeltaPoint(delta);
		}
		else
			spr.setDeltaPoint(new Point(0, 0));
		moveRight = false;
		
		spr.setAct(false);
		spr.update();
	}
	
	//Returns weither the player collides with something that it cannot move through and
	//	deals with collisions with drops.
	private boolean collision(Point delta, Point loc, Sprite spr, Environment env)
	{
		Locatable collision = env.playerCollision(new Rectangle( (delta.x + loc.x), (delta.y + loc.y), spr.getWidth(), spr.getHeight() ), getID()  );
		if(collision == null || collision.getSubType().equals("destructible") )
		{
			if(collision == null)
				return false;
			else
			{
				if( ((Destructible)(collision)).getDesType().equals("drop"))	//The player collided with a drop
				{
					String dropType = ((Drop)(collision)).getDropType();
					if( dropType.equals("health") )	//The drop is health
						takeDamage( -( ((Health)(collision)).getRecovery() ) );
					else if(dropType.equals("speedbonus"))	//The drop is a speed bonus
					{
						moveModifier = ((SpeedBonus)(collision)).getBonus();
						speedTimer = SPEEDUPCAP;
					}
					else if(dropType.equals("damagebonus"))	//The drop is a damagae bonus.
					{
						damageModifier = ((DamageBonus)(collision)).getBonus();
						damageTimer = DAMAGEUPCAP;
					}
					env.removeDrop( (Drop)(collision) );	//Removes the drop that the player collided with.
					return false;
				}
			}	
		}
		return true;
	}
	
	//Takes a bullet from the environment and activates it in the correct direction.
	private void fire()
	{
		fire = false;
		Bullet bullet = environment().getBullet();
		Sprite bulletSpr = bullet.getSprite();
		Sprite spr = this.getSprite();
		String direction = spr.myDir();
		Point location = spr.getLocation();
		
		if(fireSound != null)
			fireSound.play();
		
		if(direction.equals("up") )
		{
			bulletSpr.setLocation(new Point(location.x + (spr.getWidth() / 2) , location.y));
		}
		else if(direction.equals("down") )
		{
			bulletSpr.setLocation(new Point(location.x + (spr.getWidth() / 2) , (location.y + spr.getHeight() )));
		}
		else if(direction.equals("right") )
		{
			bulletSpr.setLocation(new Point(location.x + (spr.getWidth()) , (location.y + (spr.getHeight() / 2))));
		}
		else
		{
			bulletSpr.setLocation(new Point(location.x, (location.y + (spr.getHeight() / 2))));
		}
		
		bullet.setDamageModifier(damageModifier);
		bullet.activate();
		bullet.setDirection(direction );
		environment().addActive(bullet);
	}
	
	//The IDs (keySets) were compared since no two players can have the same keySet.
	public boolean equals(Player other)
	{
		if (other.getID() == this.getID() )
			return true;
		return false;
	}
	
	//Sets the players sounds to audio clips
	public void setSound(String type, String file)
	{
		if(type.equals("fire"))
		{
			try
			{
				fireSound = Applet.newAudioClip(new URL ("file:" + file));
			}
			catch(MalformedURLException m)
			{
				System.out.println("Error loading " + file);
			}
		}
		else if(type.equals("death"))
		{
			try
			{
				deathSound = Applet.newAudioClip(new URL ("file:" + file));
			}
			catch(MalformedURLException m)
			{
				System.out.println("Error loading " + file);
			}
		}
	}
	
	//Returns the player's health
	public int getHP()
	{
		return health;		
	}
	
	//Respawns the player at a random (non-occupied) location.
	public void respawn()
	{
		setHealth(100);
		PlayerSprite spr = (PlayerSprite)(getSprite());
		spr.respawn(environment());
		
	}
	
	//Sets the player's health
	public void setHealth(int hp)
	{
		health = hp;
	}
	
	//Returns the player's lives left.
	public int getLives()
	{
		return myLives;
	}
}
	
