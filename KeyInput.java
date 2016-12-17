import javax.swing.*;
import java.awt.event.*;

public class KeyInput implements KeyListener
{
	
	//KeySet 1
	private final int W = 87;	//W
	private final int A = 65;	//A
	private final int S = 83;	//S
	private final int D = 68;	//D
	private final int SPACE = 32;	//Space bar
	
	//KeySet 2
	private final int UPARROW = 38;	//Up arrow
	private final int DOWNARROW = 40;	//Down arrow
	private final int RIGHTARROW = 39;	//Right arrow
	private final int LEFTARROW = 37;	//Left arrow
	private final int NUM0 = 96;	//numpad 0
	
	//KeySet 3
	private final int O = 79; //O
	private final int L = 76; //L
	private final int K = 75; //K
	private final int COLON = 59; //;
	private final int M = 77; //M
	
	//KeySet 4
	private final int EIGHT = 104; //numpad 8
	private final int FIVE = 101; //numpad 5
	private final int FOUR = 100; //numpad 4
	private final int SIX = 102; //numpad 6
	private final int PLUS = 107; //numpad +
	
	
	
	private final int QUIT = 81;	//Q
	
	//These publics will be the player's buttons.
	//Each player will have these values initiated to one of the above keysets.
	public int up;
	public int down;
	public int left;
	public int right;
	public int fire;
	
	private GameFrame frame;	//The game's gameframe
	private Player guy;	//The KeyInput's player
	
	//Note: Each player must have their own KeySet to allow different players 
	//	to use differnt keys.
	public KeyInput(Player person, GameFrame f, int keySet)
	{
		frame = f;
		guy = person;
		initiateKeys(keySet);
	}
	
	//Initiate the player's buttons to the passed keySet.
	public void initiateKeys(int keySet)
	{
		if(keySet == 2)
		{
			up = UPARROW;
			down = DOWNARROW;
			right = RIGHTARROW;
			left = LEFTARROW;
			fire = NUM0;
		}
		else if(keySet == 1)
		{
			up = W;
			down = S;
			right = D;
			left = A;
			fire = SPACE;
		}
		
		else if(keySet == 3)
		{
			up = O;
			down = L;
			left = K;
			right = COLON;
			fire = M;
		}
		
		else if(keySet == 4)
		{
			up = EIGHT;
			down = FIVE;
			left = FOUR;
			right = SIX;
			fire = PLUS;
			
		}
		else
		{
			//A player was attempted to be created out of an invalid keySet.
			System.out.println("KeyInput Error: No such KeySet!");
			System.exit(0);
		}
	}
	
	//gets the keyInput from the user.
	public void keyPressed(KeyEvent e)
	{
		process(e.getKeyCode() );
	}
	
	public void keyReleased(KeyEvent e)
	{
	}
	
	public void keyTyped(KeyEvent e)
	{
	}
	
	//Process weither or not the keyCode should be handled by by the player or gameFrame.
	public void process(int keyCode)
	{
		if(keyCode == up || keyCode == down || keyCode == right || keyCode == left || keyCode == fire)
			guy.processKey(keyCode);
		else if(keyCode == QUIT)
		{
			frame.dispose();
			PregameMenu menu = new PregameMenu("Onsalught Menu", "maps.txt", "models.txt");
		}
			
	}
	
	//Returns the keyInput's keySet.
	public int getKeySet()
	{
		if (up == UPARROW)
			return 1;
		else if(up == W)
			return 2;
		else if(up == O)
			return 3;
		return 4;
			
	}
}
