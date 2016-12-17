import java.awt.event.*;
import java.util.*;
import java.awt.*;
public class GUIFrame extends Frame
{	
	public GUIFrame(String title)
	{
		super(title);
		setBackground(Color.white);
		
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				dispose();
				System.exit(0);
			} 
		});
		super.setResizable(false);
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

