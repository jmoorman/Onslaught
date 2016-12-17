import java.awt.*;
import chn.util.*;
import java.awt.event.*;
import javax.swing.SpringLayout;
import java.net.*;
import java.applet.*;
class PassCheck extends GameFrame implements ActionListener
{
	private final String PASSWORD = "cheerio";
	
	private TextField passBox;
	private Button checkPass;
	private Choice model;
	private PregameMenu frame;
	private int playerNum;
	private AudioClip denied;
	private AudioClip accepted;
	
	PassCheck(String title, Choice player, PregameMenu f, int num)
	{
		super(title);
		try
		{
			denied = Applet.newAudioClip(new URL ("file:DENIED.wav"));
		}
		catch(MalformedURLException e)
		{
			System.out.println("no");
		}
		try
		{
			accepted = Applet.newAudioClip(new URL ("file:tada.wav"));
		}
		catch(MalformedURLException e)
		{
			System.out.println("no");
		}
		frame = f;
		model = player;
		playerNum = num;
		setBackground(Color.BLUE);
		setSize(300, 75);
		passBox = new TextField(25);
		passBox.setEchoChar('$');
		checkPass = new Button("OK");
		checkPass.addActionListener(this);
		setLayout(new FlowLayout());
		add(passBox);
		add(checkPass);
		setVisible(true);
		
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(!passBox.getText().equals(PASSWORD))
		{
			denied.play();
			model.select(0);
			dispose();
		}
		else
		{
			accepted.play();
			frame.passCorrect(playerNum);	
			dispose();	
		}
				
	}	
}
