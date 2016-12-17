import java.awt.*;
import java.awt.event.*;

public class GameFrame extends Frame
{
	public GameFrame(String name)
	{
		super(name);
		setBackground(Color.white);
		
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				dispose();
				System.exit(0);
			} 
		});	
	}
	
	public void setVisible(boolean visible)
	{
		if(visible)
		{
			Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
			setLocation((d.width - getWidth()) / 2, (d.height - getHeight()) / 2);
		}
		super.setVisible(visible);
	}
}
