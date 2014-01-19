package monopoly.gui;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import monopoly.jeu.Game;
import monopoly.jeu.ISlot;

/** JPanel contenant la description de la derniere case selectionnee */
public class PropertyPanel extends JPanel
{

	//
	// ATTRIBUTES
	//
	/** Zone de texte contenant la description de la propriete selectionnee */
	private JTextArea desc;
	
	/** Slot selectionne */
	private ISlot slot;



	//
	// CONSTRUCTOR
	//
	/** Construit un nouveau PropertyPanel */
	public PropertyPanel()
	{
		this.slot = Game.getInstance().getFirstSlot();
		this.desc = new JTextArea();
		desc.setEditable(false);
		desc.setLocation(10, 10);
		desc.setSize(200, 200);

		this.add(this.desc);
	}



	//
	// METHODS
	//
	/** Change le slot selectionne */
	public void setSlot(int id)
	{
		this.slot = Game.getInstance().getFirstSlot().getSlot(id + 1);
		this.desc.setText(this.slot.toString());
	}

}
