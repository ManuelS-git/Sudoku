import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;

public class NewGameActionListener implements ActionListener 
{
	public void actionPerformed(ActionEvent e) 
	{
		System.out.println(((JMenuItem)e.getSource()).getText());
	}
}
