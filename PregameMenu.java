import java.awt.*;
import chn.util.*;
import java.awt.event.*;
import javax.swing.SpringLayout;
public class PregameMenu extends GameFrame implements ActionListener, ItemListener
{
	private Label mapLabel;
	private Label numPlayerLabel;
	private Label numLivesLabel;
	private Label p1label;
	private Label p2label;
	private Label p3label;
	private Label p4label;
	private Label name1label;
	private Label name2label;
	private Label name3label;
	private Label name4label;
	private Choice maps;
	private Choice numPlayers;
	private Choice numLives;
	private Choice player1;
	private Choice player2;
	private Choice player3;
	private Choice player4;
	private TextField player1name;
	private TextField player2name;
	private TextField player3name;
	private TextField player4name;
	private Button ready;
	private Panel mapsPanel;
	private Panel numPlayerPanel;
	private Panel playerModelsPanel;
	private Panel playerNamesPanel;
	private Panel readyPanel;
	private boolean p1pass;
	private boolean p2pass;
	private boolean p3pass;
	private boolean p4pass;
	public final String MAPFOLDER = "";
	public final String MODELFOLDER = "";
	
	public PregameMenu(String title, String mapFile, String modelFile)
	{
		super(title);
		setBackground(Color.RED);
		setLayout(new GridLayout(2, 3));
		p1pass = false;
		p2pass = false;
		p3pass = false;
		p4pass = false;
		mapLabel = new Label("Choose your map");
		numPlayerLabel = new Label("Choose number of players");
		numLivesLabel = new Label("Choose the number of lives per player");
		p1label = new Label("First player's model");
		p2label = new Label("Second player's model");
		p3label = new Label("Third player's model");
		p4label = new Label("Fourth player's model");
		name1label = new Label("First player's name");
		name2label = new Label("Second player's name");
		name3label = new Label("Third player's name");
		name4label = new Label("Fourth player's name");
		maps = new Choice();
		numPlayers = new Choice();
		numPlayers.add("One Player: Test");
		numPlayers.add("Two Players: Duel");
		numPlayers.add("Three Players: Skirmish");
		numPlayers.add("Four Players: All-out War");
		numPlayers.addItemListener(this);
		numLives = new Choice();
		for(int i = 0; i < 11; i++)
		{
			numLives.add(String.valueOf(i));
		}
		player1 = new Choice();
		player2 = new Choice();
		player3 = new Choice();
		player4 = new Choice();
		player1name = new TextField(15);
		player2name = new TextField(15);
		player3name = new TextField(15);
		player4name = new TextField(15);
		ready = new Button("Press When Ready");
		mapsPanel = new Panel();
		numPlayerPanel = new Panel();
		playerModelsPanel = new Panel();
		playerNamesPanel = new Panel();
		readyPanel = new Panel();
		setSize(800, 600);
		FileInput loadMaps = new FileInput(mapFile);
		while(loadMaps.hasMoreLines())
		{
			String map = loadMaps.readLine();
			maps.add(map);
		}
		FileInput loadPlayers = new FileInput(modelFile);
		while(loadPlayers.hasMoreLines())
		{
			String model = loadPlayers.readLine();
			player1.add(model);
			player2.add(model);
			player3.add(model);
			player4.add(model);
		}
		player1.addItemListener(this);
		player2.addItemListener(this);
		player3.addItemListener(this);
		player4.addItemListener(this);
		mapsPanel.add(mapLabel);
		mapsPanel.add(maps);
		mapsPanel.add(numLivesLabel);
		mapsPanel.add(numLives);
		numPlayerPanel.add(numPlayerLabel);
		numPlayerPanel.add(numPlayers);
		playerModelsPanel.add(p1label);
		playerModelsPanel.add(player1);
		playerModelsPanel.add(p2label);
		playerModelsPanel.add(player2);
		playerModelsPanel.add(p3label);
		playerModelsPanel.add(player3);
		playerModelsPanel.add(p4label);
		playerModelsPanel.add(player4);
		playerNamesPanel.setLayout(new GridLayout(8, 0));
		playerNamesPanel.add(name1label);
		playerNamesPanel.add(player1name);
		playerNamesPanel.add(name2label);
		playerNamesPanel.add(player2name);
		playerNamesPanel.add(name3label);
		playerNamesPanel.add(player3name);
		playerNamesPanel.add(name4label);
		playerNamesPanel.add(player4name);
		ready.addActionListener(this);
		readyPanel.add(ready);
		add(mapsPanel);
		add(numPlayerPanel);
		add(playerModelsPanel);
		add(playerNamesPanel);
		add(readyPanel);
		player2.setEnabled(false);
		player3.setEnabled(false);
		player4.setEnabled(false);
		player2name.setEnabled(false);
		player3name.setEnabled(false);
		player4name.setEnabled(false);
		setVisible(true);	
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(numPlayers.getSelectedIndex() == 0)
		{
         int lives = numLives.getSelectedIndex();
			String[] playersInfo = new String[2];
			playersInfo[0] = MODELFOLDER + player1.getSelectedItem();
			playersInfo[1] = player1name.getText();
         GameFrame frame = new GameFrame("Onslaught");
         Environment env = new Environment(MAPFOLDER + maps.getSelectedItem(), "sounds.txt", playersInfo, lives, frame);
         env.setBackground(new Color(255, 255, 255));
			frame.add(env, BorderLayout.CENTER);
			frame.pack();
			frame.setResizable(false);
			frame.setVisible(true);
			env.playGame();	
			this.dispose();
   	}
		else if(numPlayers.getSelectedIndex() == 1)
		{
			int lives = numLives.getSelectedIndex();
			String[] playersInfo = new String[4];
			playersInfo[0] = MODELFOLDER + player1.getSelectedItem();
			playersInfo[1] = player1name.getText();
			playersInfo[2] = MODELFOLDER + player2.getSelectedItem();
			playersInfo[3] = player2name.getText();
			GameFrame frame = new GameFrame("Onslaught");
			Environment env = new Environment(MAPFOLDER + maps.getSelectedItem(), "sounds.txt", playersInfo, lives, frame);
			env.setBackground(new Color(255, 255, 255));
			frame.add(env, BorderLayout.CENTER);
			frame.pack();
			frame.setResizable(false);
			frame.setVisible(true);
			env.playGame();	
			this.dispose();	
		}
		else if(numPlayers.getSelectedIndex() == 2)
		{
			int lives = numLives.getSelectedIndex();
			String[] playersInfo = new String[6];
			playersInfo[0] = MODELFOLDER + player1.getSelectedItem();
			playersInfo[1] = player1name.getText();
			playersInfo[2] = MODELFOLDER + player2.getSelectedItem();
			playersInfo[3] = player2name.getText();
			playersInfo[4] = MODELFOLDER + player3.getSelectedItem();
			playersInfo[5] = player3name.getText();
			GameFrame frame = new GameFrame("Onslaught");
			Environment env = new Environment(MAPFOLDER + maps.getSelectedItem(), "sounds.txt", playersInfo, lives, frame);
			env.setBackground(new Color(255, 255, 255));
			frame.add(env, BorderLayout.CENTER);
			frame.pack();
			frame.setResizable(false);
			frame.setVisible(true);
			env.playGame();
			this.dispose();
			
		}
		else
		{
			int lives = numLives.getSelectedIndex();
			String[] playersInfo = new String[8];
			playersInfo[0] = MODELFOLDER + player1.getSelectedItem();
			playersInfo[1] = player1name.getText();
			playersInfo[2] = MODELFOLDER + player2.getSelectedItem();
			playersInfo[3] = player2name.getText();
			playersInfo[4] = MODELFOLDER + player3.getSelectedItem();
			playersInfo[5] = player3name.getText();
			playersInfo[6] = MODELFOLDER + player4.getSelectedItem();
			playersInfo[7] = player4name.getText();
			GameFrame frame = new GameFrame("Onslaught");
			Environment env = new Environment(MAPFOLDER + maps.getSelectedItem(), "sounds.txt", playersInfo, lives, frame);
			env.setBackground(new Color(255, 255, 255));
			frame.add(env, BorderLayout.CENTER);
			frame.pack();
			frame.setResizable(false);
			frame.setVisible(true);
			env.playGame();
			this.dispose();
		}
	}
	
	public void itemStateChanged(ItemEvent e)
	{
		if(player1.getSelectedItem().equals("admin.txt") && !p1pass)
		{
			PassCheck pass = new PassCheck("Please enter a password", player1, this, 1);
		}
		else if(player2.getSelectedItem().equals("admin.txt") && !p2pass)
		{
			PassCheck pass = new PassCheck("Please enter a password", player2, this, 2);
		}
		else if(player3.getSelectedItem().equals("admin.txt") && !p3pass)
		{
			PassCheck pass = new PassCheck("Please enter a password", player3, this, 3);
		}
		else if(player4.getSelectedItem().equals("admin.txt") && !p4pass)
		{
			PassCheck pass = new PassCheck("Please enter a password", player4, this, 4);
		}
		else
		{
			if(numPlayers.getSelectedIndex() == 0)
			{
				player2.setEnabled(false);
				player3.setEnabled(false);
				player4.setEnabled(false);
				player2name.setEnabled(false);
				player3name.setEnabled(false);
				player4name.setEnabled(false);
			}
			else if(numPlayers.getSelectedIndex() == 1)
			{
				player2.setEnabled(true);
				player3.setEnabled(false);
				player4.setEnabled(false);
				player2name.setEnabled(true);
				player3name.setEnabled(false);
				player4name.setEnabled(false);
			}
			else if(numPlayers.getSelectedIndex() == 2)
			{
				player2.setEnabled(true);
				player3.setEnabled(true);
				player4.setEnabled(false);
				player2name.setEnabled(true);
				player3name.setEnabled(true);
				player4name.setEnabled(false);
			}
			else if(numPlayers.getSelectedIndex() == 3)
			{
				player2.setEnabled(true);
				player3.setEnabled(true);
				player4.setEnabled(true);
				player2name.setEnabled(true);
				player3name.setEnabled(true);
				player4name.setEnabled(true);
			}
		}
	}
	
	public void passCorrect(int player)
	{
		if(player == 1)
			p1pass = true;
		else if(player == 2)
			p2pass = true;
		else if(player == 3)
			p3pass = true;
		else if(player == 4)
			p4pass = true;
	}
	
	
	
}
