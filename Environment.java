import java.util.*;
import java.awt.*;
import chn.util.*;
import java.awt.Point;
import java.net.*;
public class Environment extends Canvas implements Runnable 
{
	private ArrayList actives;
	private ArrayList statics;
	private ArrayList bullets;
	private ArrayList players;
	private Image offImg;
	protected Thread runner;
	private boolean gameOver;
	private Rectangle boundaries;
	private GameFrame frame;
	private DropList myDrops;
	
	public final String IMAGEPATH = "";
	public MediaTracker mt;
	public int mediaCount;
	
	
	public Environment()
	{
		actives = new ArrayList();
		statics = new ArrayList();
		bullets = new ArrayList();
		players = new ArrayList();
		offImg = null;
		runner = new Thread();
		gameOver = false;
		boundaries = new Rectangle();
	}
	
	/*Environment Text files must be formatted as follows:
		 *Type ("static" or "active")	//See below for Players
		 *SubType
		 *Starting X coordinate
		 *Starting Y coordinate
		 *Width of object
		 *Height of object
		 *Picture's state ("movement" or "action")
		 *Name of picture file
		 * //Continue for all pictures
		 *		//Note:Statics should only have one movement picture.
		 *		//	Only one bullet needs to be created because copies will be made.
		 * //and astrisk to show the end of an object
		 *
		 *For Players:
		 *	They Type is "player"
		 *	Starting X coordinate
		 *	Starting Y coordinate
		 *	Followed by the name of the text file for that player.
		 *	//an astrisk to show the end of an object
		 
	 *Player Text files must be formatted as follows:
	 	*Player's Name
	 	*Width of Player
	 	*Height of Player
	 	*Picture's State ("movement" of "action")
	 	*Picture's Orientation ("up", "down", "left", or "right").
	 	*Name of picture file
	 	*	//Continue for all pictures.
	 	* //An astrisk to show the end of the player.
		
	 */
	
	Environment(String file, String soundFile, String[] playersFile, int lives, GameFrame f)
	{
		frame = f;
		actives = new ArrayList();
		statics = new ArrayList();
		bullets = new ArrayList();
		players = new ArrayList();
		
		mt = new MediaTracker(this);
		mediaCount = 0;
		
		FileInput load = new FileInput(file);
		int count = 0;
		int playercount = 0;
		setSize(load.readInt(), load.readInt());
		while(load.hasMoreLines())
		{
			int tempCount = 0;
			String type = load.readLine();
			if (type.equals("player") )
			{
				int xStart = load.readInt();
				int yStart = load.readInt();
				String modelDir;
				String playerName;
				if(playercount == 0)
				{
               modelDir = playersFile[0];
               playerName = playersFile[1];
               playercount++;
               readPlayer(modelDir, xStart, yStart, playerName, lives);
            }
				else if(playercount == 1 && playersFile.length > 2)
				{
					modelDir = playersFile[2];
					playerName = playersFile[3];
					playercount++;
					readPlayer(modelDir, xStart, yStart, playerName, lives);
				}
				else if(playercount == 2 && playersFile.length > 4)
				{
					modelDir = playersFile[4];
					playerName = playersFile[5];
					playercount++;
					readPlayer(modelDir, xStart, yStart, playerName, lives);
				}
				else if(playercount == 3 && playersFile.length > 6)
				{
					modelDir = playersFile[6];
					playerName = playersFile[7];
					playercount++;
					readPlayer(modelDir, xStart, yStart, playerName, lives);
				}
				else
				{
					modelDir = null;
					playerName = null;
				}
				load.readLine();	//The ending astrisk
			}
			else	//Statics
			{
				String subType = load.readLine();
				int xStart = load.readInt();
				int yStart = load.readInt();
				int width = load.readInt();
				int height = load.readInt();
				Point start = new Point(xStart, yStart);
				boolean stop = false;
				ArrayList imgDirs = new ArrayList();
				while (!stop)
				{
					String temp = load.readLine();
					if (temp.equals("*"))
						stop = true;
					else
					{
						imgDirs.add(temp);
					}
				}
				
				if(type.equals("active") )
					initateActive(subType, start, imgDirs, width, height);
				else if (type.equals("static") )
					initateStatic(subType, start, imgDirs, width, height);
			}
			
			mediaCount += tempCount;
		}
		
		FileInput load2 = new FileInput(soundFile);
		while(load2.hasMoreLines())
		{
			String type = load2.readLine();
			String subType = load2.readLine();
			String soundDir = load2.readLine();
			if(type.equals("player"))
			{
				for(int i = 0; i < players.size(); i++)
				{
					Player p = (Player)(players.get(i));
					p.setSound(subType, soundDir);
				}
			}
			else if(type.equals("environment"))
			{
				//setSound(subType, soundDir);
			}
		
		boundaries = new Rectangle(0, 0, getSize().width, getSize().height);
		try
		{
			mt.waitForAll();
		}
		catch (InterruptedException e)
		{	
		}
		
		
		}
	}
	
	private void readPlayer(String dir, int xStart, int yStart, String name, int lives)
	{
		int tempCount = 0;
		FileInput load = new FileInput(dir);
		int width = load.readInt();
		int height = load.readInt();
		//Array Lists to hold the player's various movements and actions at different orientations.
		ArrayList movementUp = new ArrayList();
		ArrayList actionUp = new ArrayList();
		ArrayList movementDown = new ArrayList();
		ArrayList actionDown = new ArrayList();
		ArrayList movementLeft = new ArrayList();
		ArrayList actionLeft = new ArrayList();
		ArrayList movementRight = new ArrayList();
		ArrayList actionRight = new ArrayList();
		ArrayList deathImage = new ArrayList();	//The path of the death image, if there is not a death image than it will remain null.
		
		boolean stop = false;
		while (!stop)
		{
			String temp = load.readLine();
			if (temp.equals("*"))
				stop = true;
			else
			{
				if(temp.equals("movement") )
				{
					temp = load.readLine();
					if (temp.equals("up") )
						movementUp.add(load.readLine() );
					else if (temp.equals("down") )
						movementDown.add(load.readLine() );
					else if (temp.equals("left") )
						movementLeft.add(load.readLine() );
					else if (temp.equals("right") )
						movementRight.add(load.readLine() );
					else
					{
						System.out.println("Syntax Error: Player movement");
						System.exit(0);
					}
						
					tempCount++;
				}
				else if(temp.equals("action") )
				{
					temp = load.readLine();
					if (temp.equals("up") )
						actionUp.add(load.readLine() );
					else if (temp.equals("down") )
						actionDown.add(load.readLine() );
					else if (temp.equals("left") )
						actionLeft.add(load.readLine() );
					else if (temp.equals("right") )
						actionRight.add(load.readLine() );
					else
					{
						System.out.println("Syntax Error: Player action");
						System.exit(0);
					}
					
					tempCount++;
				}
				else if(temp.equals("death") )
				{
					deathImage.add(load.readLine());
				}
				else
				{
					System.out.println("Syntax Error: Player; invalid state!");
					System.exit(0);
				}
			}
		}
		initiatePlayer(new Point(xStart, yStart), width, height, name, deathImage, 
			movementUp, movementDown, movementLeft, movementRight,
			actionUp, actionDown, actionLeft, actionRight, lives);
		
		System.out.println(name + " loading complete!");
	}
	
	private void initiatePlayer(Point start, int width, int height, String name, ArrayList deathImage, 
			ArrayList movementUp, ArrayList movementDown, ArrayList movementLeft, ArrayList movementRight,
			ArrayList actionUp, ArrayList actionDown, ArrayList actionLeft, ArrayList actionRight, int lives)
	{
		Image[] moveUp = getImages(movementUp, width, height);
		updateMT(moveUp);
		
		Image[] moveDown = getImages(movementDown, width, height);
		updateMT(moveDown);
		
		Image[] moveLeft = getImages(movementLeft, width, height);
		updateMT(moveLeft);
		
		Image[] moveRight = getImages(movementRight, width, height);
		updateMT(moveRight);
		
		Image[] actUp = getImages(actionUp, width, height);
		updateMT(actUp);
		
		Image[] actDown = getImages(actionDown, width, height);
		updateMT(actDown);
		
		Image[] actLeft = getImages(actionLeft, width, height);
		updateMT(actLeft);
		
		Image[] actRight = getImages(actionRight, width, height);
		updateMT(actRight);
		
		Image death1 = Toolkit.getDefaultToolkit().getImage( IMAGEPATH.concat( (String)(deathImage.get(0)) ) );
		death1 = death1.getScaledInstance(width, height, 4);
		mt.addImage(death1, mediaCount);
		mediaCount++;
		
		if (deathImage.size() == 3)	//A player with multiple graves.
		{
			Image death2 = Toolkit.getDefaultToolkit().getImage( IMAGEPATH.concat((String)(deathImage.get(1)) ) );
			death2 = death2.getScaledInstance(width, ((height * 2) / 3), 4);
			mt.addImage(death2, mediaCount);
			mediaCount++;
		
			Image death3 = Toolkit.getDefaultToolkit().getImage( IMAGEPATH.concat((String)(deathImage.get(2)) ) );
			death3 = death3.getScaledInstance(width, (height / 3), 4);
			mt.addImage(death3, mediaCount);
			mediaCount++;
			
			addPlayer(new Player(start, moveUp, moveDown, moveLeft, moveRight,
				actUp, actDown, actLeft, actRight, this, frame, width, height, (players.size() + 1) , name, death1, death2, death3, lives));
		}
		else
			addPlayer(new Player(start, moveUp, moveDown, moveLeft, moveRight,
				actUp, actDown, actLeft, actRight, this, frame, width, height, (players.size() + 1) , name, death1, death1, death1, lives));
	}
	
	//Pre:Dirs is an arrayList of Strings that are the names of picture files.
	private Image[] getImages(ArrayList dirs, int width, int height)
	{
		Image[] images = new Image[dirs.size() ];
		for (int i = 0; i < dirs.size(); i++ )
		{
			String dir = (String)(dirs.get(i));
			Image temp = Toolkit.getDefaultToolkit().getImage( IMAGEPATH.concat(dir) );
			temp = temp.getScaledInstance(width, height, 4);
			images[i] = temp;
		}
		return images;
	}
	
	private void updateMT(Image[] images)
	{
		for(int i = 0; i < images.length; i++)
		{
			Image temp = images[i];
			mt.addImage(temp, (mediaCount) );
			mediaCount++;
		}
	}
	
	private void initateStatic(String subType, Point start, ArrayList moveDirs, int width, int height)
	{
		Image img1 = Toolkit.getDefaultToolkit().getImage( IMAGEPATH.concat((String)(moveDirs.get(0)) ));
		img1 = img1.getScaledInstance(width, height, 4);
		mt.addImage(img1, mediaCount);
		mediaCount++;
		
		if(subType.equals("box"))	//These types are destructibles and need multiple images.
		{
			Image img2 = Toolkit.getDefaultToolkit().getImage( IMAGEPATH.concat((String)(moveDirs.get(1)) ) );
			img2 = img2.getScaledInstance(width, ((height * 2) / 3), 4);
			mt.addImage(img2, mediaCount);
			mediaCount++;
		
			Image img3 = Toolkit.getDefaultToolkit().getImage( IMAGEPATH.concat((String)(moveDirs.get(2)) ) );
			img3 = img3.getScaledInstance(width, (height / 3), 4);
			mt.addImage(img3, mediaCount);
			mediaCount++;
			
			new Box(start, this, img1, img2, img3, width, height);
		}
		else if(subType.equals("wall"))
		{
			new Wall(start, img1, this, width, height);
		}
		else
		{
			System.out.println("Error: No Such SubType!");
			System.exit(0);
		}
	}
	
	private void initateActive(String subType, Point start, ArrayList image, int width, int height)
	{
		String dir = (String)(image.get(0));
		Image temp = Toolkit.getDefaultToolkit().getImage(IMAGEPATH.concat(dir));
		temp = temp.getScaledInstance(width, height, 4);
		
		if( subType.equals("bullet") )
			initiateBullets(100, start, temp, this, width, height);
		else
		{
			System.out.println("Error: " + subType + " is not a valid SubType!");
			System.exit(0);
		}
	}
	
	public Rectangle getBounds()
	{
		return boundaries;
	}
	
	public ArrayList getActives()
	{
		return actives;
	}
	
	public ArrayList getStatics()
	{
		return statics;
	}
	
	public Bullet getBullet()
	{
		if(bullets.size() <= 1)
		{
			Bullet bullet = (Bullet)(bullets.get(0));
			initiateBullets(100, bullet);
		}
		return ( (Bullet)(bullets.remove(0) ) );
	}
	
	private void initiateBullets(int num, Point start, Image movement, Environment env, int width, int height)
	{
		for (int count = 0; count < num; count++)
		{
			addBullet(new Bullet(start, movement, this, width, height, false));
		}
		mt.addImage(movement, mediaCount);
		mediaCount++;
	}
	
	private void initiateBullets(int num, Bullet bullet)
	{
		for (int count = 0; count < num; count++)
		{
			addBullet(new Bullet(bullet) );
		}
	}
	
	public void addBullet(Locatable obj)
	{
		bullets.add(obj);
	}
	
	public void addPlayer(Locatable obj)
	{
		players.add(obj);
	}
	
	//adds a static object to the Map and updates the Set of used locations
	public void addStatic(Locatable obj)
	{
		statics.add(obj);
	}
	
	//adds an active object to the Map and updates the Set of used locations
	public void addActive(Locatable obj)
	{
		actives.add(obj);
	}
	
	//removes an active object from the actives arrayList
	//the removed object is then returned
	public Active removeActive(int iD)
	{
		return (Active)(actives.remove(iD));
	}
	
	public Static removeStatic(Point loc)
	{
		Locatable remove = objectAt(loc);
		if( remove == null || !remove.getType().equals("static") )	//The static at loc doesn't exist.
			return null;
		
		for (int count = 0; count < statics.size(); count++ )
		{
			Locatable temp = ( (Locatable)(statics.get(count) ) );
			if(remove == temp)	//They have the same adress and are therefore the same object.
				return (Static)(statics.remove(count) );
		}
		return null;	//No match was found.
	}
	
	public Drop removeDrop(Drop item)
	{
		System.out.println("Drop: " + item + " was removed");
		removeStatic(item.location() );
		return myDrops.removeDrop(item);
	}
	
	//NOTE:Tis does NOT return any object at the specified location, 
	//	only the locatable with the SAME location().
	public Locatable objectAt(Point loc)
	{
		//Statics
		for (int count = 0; count < statics.size(); count++ )
		{
			Locatable temp = ((Locatable)(statics.get(count)));
			if(temp.location().equals(loc) )
				return temp;
		}
		
		//Players
		for (int count = 0; count < players.size(); count++ )
		{
			Locatable temp = ((Locatable)(players.get(count)));
			if(temp.location().equals(loc) )
				return temp;
		}
		
		//Other Actives
		for (int count = 0; count < actives.size(); count++ )
		{
			Locatable temp = ((Locatable)(actives.get(count)));
			if(temp.location().equals(loc) )
				return temp;
		}
		
		return null;	//No matches were found.
	}
	
	//removes an active object from the players arrayList
	//the removed object is then returned
	public Player removePlayer(Player guy)
	{
		for (int count = 0; count < players.size(); count++ )
		{
			if(guy.equals( (Player)(players.get(count) ) ))
				return ( (Player)players.remove(count) );
		}
		return null;	//no player was removed
	}
	
	//Returns the specific Player with the given ID.
	public Player getPlayer(int ID)
	{
		for(int count = 0; count < players.size(); count++)
		{
			Player temp = (Player)(players.get(count));
			if(temp.getID() == ID)
				return temp;
		}
		return null;
	}
	
	
	//returns whether or not the given point is in the environment
	public boolean isInEnv(Rectangle bounds)
	{
		if(bounds.intersects(boundaries))
			return true;
		
		else
		{
			return false;
		}
	}
	
	public boolean isInEnv(Point pnt)
	{
		return (boundaries.contains(pnt) );
	}
	
	//If rect is intersection an object, the object that it is intersection is returned
	//	otherwise, null is returned.
	//NOTE: To help eficiency, the objects in this environment are traversed in order of 
	//	probability of intersection. First Statics, Players, then other actives.
	public Locatable collision(Rectangle rect)
	{
		
		//Statics
		for (int count = 0; count < statics.size(); count++ )
		{
			
			Locatable temp = ((Locatable)(statics.get(count)));
			Rectangle rectTemp = (( (temp).getSprite()).getBounds() );
			if (rectTemp != rect)//rect and temp don't have the same adress (are not the same locatable).
				if (rect.intersects(rectTemp) )
					return temp;
		}
		
		//Players
		for (int count = 0; count < players.size(); count++ )
		{
			Locatable temp = ((Locatable)(players.get(count)));
			Rectangle rectTemp = (( (temp).getSprite()).getBounds() );
			if (rectTemp != rect)//rect and temp don't have the same adress (are not the same locatable).
				if (rect.intersects(rectTemp) )
					return temp;
		}
		
		//Other Actives
		for (int count = 0; count < actives.size(); count++ )
		{
			Locatable temp = ((Locatable)(actives.get(count)));
			Rectangle rectTemp = (( (temp).getSprite()).getBounds() );
			if (rectTemp != rect)//rect and temp don't have the same adress (are not the same locatable).
				if (rect.intersects(rectTemp) )
					return temp;
		}
		return null;	//No collisions were found.
	}
	
	//NOTE: To help efficiency, a second collision method is used for players. 
	//	Because players do not run into bullets, actives, (bullets run into players)
	//		Actives is not checked for intersections.
	//Pre: rect is not already in the static or active ArrayLists.
	//	Id is the player's keySet (since no two players can have the same KeySet).
	//		0 (zero) is a defualt ID that is never used by players and may be used for other 
	//			objects not wishing to use the collision with actives (like drops).
	//Returns the objects that there is a collision, null otherwise.
	public Locatable playerCollision(Rectangle rect, int iD)
	{
		//Statics
		for (int count = 0; count < statics.size(); count++ )
		{
			Locatable temp = ((Locatable)(statics.get(count)));
			Rectangle rectTemp = (( (temp).getSprite()).getBounds() );
			if (rect.intersects(rectTemp) )
				return temp;
		}
		
		//Players (discluding the player with the same ID (keySet)).
		for (int count = 0; count < players.size(); count++ )
		{
			Locatable temp = ((Locatable)(players.get(count)));
			Rectangle rectTemp = (( (temp).getSprite()).getBounds() );
			if ( ((Player)(temp)).getID() != iD )	//If it is not the same player
				if (rect.intersects(rectTemp) )
					return temp;
		}
		
		return null;	//No collisions were found.
	}
	
	//returns whether or not the given point has an object at it.
	public boolean isEmpty()
	{
		return (actives.isEmpty() && statics.isEmpty() && players.isEmpty() );
	}
	
	public boolean hasPlayers()
	{
		return ( !players.isEmpty() );
	}
	
	public void playGame()
	{
		if (runner == null || !runner.isAlive())
		{
			for(int count = 0; count < players.size(); count++ )
			{
				Player temp = (Player)(players.get(count) );
				Thread playerThread = new Thread( temp );
				playerThread.start();
			}
			myDrops = new DropList(this);
			Thread dropThread = new Thread (myDrops);
			dropThread.start();
			
			runner = new Thread(this);
			runner.start();
		}
	}
	
	public void run()
	{
		gameOver = false;
		
		for(int i = 0; i < statics.size(); i++)
		{
			Locatable temp = (Locatable)(statics.get(i));
		}
			
		while(!gameOver)
		{
			repaint();
			if(players.size() <= 0)
			{
				System.out.println("GAME OVER\nYou All LOSE!!!");
				gameOver = true;
			}
			for(int j = 0; j < actives.size(); j++)
			{
				
				Active temp = (Active)(actives.get(j));
				if(isInEnv(temp.getSprite().getBounds()))
					temp.run(j);
				else
					removeActive(j);
			}
			try
			{
				Thread.sleep(10);
			}
			catch(InterruptedException e)
			{
			}
		}
	}
	
	public void update(Graphics g)
	{
		paint(g);
	}
	
	public void paint(Graphics g)
	{
		offImg = createImage(getSize().width, getSize().height);
		Graphics offg = offImg.getGraphics();
		paintOffScreen(offg);
		g.drawImage(offImg, 0, 0, null);
	}
	
	public void paintOffScreen(Graphics g)
	{
		g.setColor(getBackground());
		g.fillRect(0, 0, getSize().width, getSize().height);
		if(!gameOver)
		{
			for(int i = 0; i < statics.size(); i++)
			{
				Locatable temp = (Locatable)(statics.get(i));
				Sprite spr = temp.getSprite();
				g.drawImage(spr.getCurrentImage(), spr.getLocation().x, spr.getLocation().y, this);
			}
			for(int j = 0; j < actives.size(); j++)
			{
				Locatable temp = (Locatable)(actives.get(j));
				if(isInEnv(temp.getSprite().getBounds()))
				{
					Sprite spr = temp.getSprite();
					g.drawImage(spr.getCurrentImage(), spr.getLocation().x, spr.getLocation().y, this);
				}
			}
			for(int i = 0; i < players.size(); i++)
			{
				Player temp = (Player)(players.get(i));
				Sprite spr = temp.getSprite();
				g.drawImage(spr.getCurrentImage(), spr.getLocation().x, spr.getLocation().y, this);
				
				drawHealth(temp, g);
				
			}
			g.setColor(getForeground());
		}
	}
	
	public void drawHealth(Player guy, Graphics g)
	{
		int tempID = guy.getID();
		Font f1 = new Font("Helvetica", Font.BOLD, 24);
		g.setFont(f1);
		g.setColor(Color.GREEN);
		if(tempID == 2)
		{
			g.drawString(guy.getName() + ": " + String.valueOf(guy.getHP()) + ": " + String.valueOf(guy.getLives()), 20, getHeight() - 10);
		}
		else if(tempID == 1)
		{
			g.drawString(guy.getName() + ": " + String.valueOf(guy.getHP()) + ": " + String.valueOf(guy.getLives()), getWidth() - 200, getHeight() - 10);
		}
		else if(tempID == 3)
		{
			g.drawString(guy.getName() + ": " + String.valueOf(guy.getHP()) + ": " + String.valueOf(guy.getLives()), 20, 30);
		}
		else if(tempID == 4)
		{
			g.drawString(guy.getName() + ": " + String.valueOf(guy.getHP()) + ": " + String.valueOf(guy.getLives()), getWidth() - 200, 30);
		}
			
	}
	
}

