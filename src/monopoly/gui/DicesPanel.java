package monopoly.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import monopoly.jeu.Game;

/** JPanel contenant la valeur du dernier lance de des */
public class DicesPanel extends JPanel
{

	//
	// ATTRIBUTES
	//
	/** Valeur du dé de gauche */
	private JTextArea left;
	
	/** Valeur du dé de droite */
	private JTextArea right;
	
	/** Bouton tous suivant */
	private JButton button;
	
	/** Bouton sauvegarde */
	private JButton save;



	//
	// CONSTRUCTOR
	//
	/** Construit un nouveau DicesPanel */
	public DicesPanel()
	{
		this.left = new JTextArea();
		left.setEditable(false);

		this.right = new JTextArea();
		right.setEditable(false);

		updateValues();

		this.button = new JButton();
		button.setText("Tour suivant");
		button.setEnabled(false);
		button.addActionListener(new NextTurnAction());

		this.save = new JButton();
		save.setText("Sauvegarde");
		save.setEnabled(false);
		save.addActionListener(new SaveAction());

		this.add(this.left);
		this.add(this.right);
		this.add(this.button);
		this.add(this.save);
	}



	//
	// METHODS
	//
	/** Met a jour la valeur des dés */
	public void updateValues()
	{
		left.setText("  " + Game.getInstance().getDices().getValue(0) + "  ");
		right.setText("  " + Game.getInstance().getDices().getValue(1) + "  ");
	}



	//
	// GET & SET
	//
	/** Retourne le bouton tour suivant */
	public JButton getDicesButton()
	{
		return this.button;
	}

	/** Retourne le bouton de sauvegarde */
	public JButton getSaveButton()
	{
		return this.save;
	}



	//
	// CLASSES
	//
	/** ActionListener permettant de passer ou non au tour suivant */
	class NextTurnAction implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			JButton jb = (JButton) e.getSource();

			if (jb.isEnabled())
				Game.getInstance().runTurn();
		}
	}

	/** ActionListener permettant de sauvegarder ou non la partie */
	class SaveAction implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			JButton jb = (JButton) e.getSource();

			if (jb.isEnabled())
			{
				Game.getInstance().save();
				jb.setEnabled(false);
			}
		}
	}
}
