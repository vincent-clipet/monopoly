package monopoly.gui;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JTextArea;

/** JPanel contenant les messages pour les joueurs */
public class MessagesPanel extends JPanel
{

	//
	// ATTRIBUTES
	//

	/** Liste des derniers messages affiches */
	private ArrayList<String> list;
	

	/** Zone de texte contenant les derniers messages */
	private JTextArea text;



	//
	// CONSTRUCTOR
	//
	/** Construit un nouveau MessagesPanel */
	public MessagesPanel()
	{
		this.text = new JTextArea(19, 32);
		this.text.setMaximumSize(new Dimension(350, 300));

		this.list = new ArrayList<String>();

		this.add(this.text);
	}



	//
	// METHODS
	//

	/** Ajoute un message a l'ecran */
	public void addMessage(String s)
	{
		if (s.length() == 0 || s.equalsIgnoreCase(""))
			return;

		this.text.setText(formatText(s));
		this.revalidate();
		this.repaint();
	}


	/** Enleve les lignes en surplus dans la liste */
	private String formatText(String toAdd)
	{
		this.list.add(toAdd);

		if (this.list.size() > 18)
			this.list.remove(0);

		StringBuilder sb = new StringBuilder();

		for (String s : this.list)
			sb.append("\n").append(s);

		return sb.toString();
	}

}
