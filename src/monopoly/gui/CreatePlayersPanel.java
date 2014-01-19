package monopoly.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import monopoly.jeu.Game;

/** JPanel contenant le menu d'entree des noms des joueurs */
public class CreatePlayersPanel extends JPanel
{

	//
	// ATTRIBUTES
	//
	/** Zone de texte */
	private JTextArea text;
	
	/** Bouton de validation */
	private JButton ok;
	
	/** Liste des noms des joueurs */
	private ArrayList<String> players;
	
	/** Permet de bloquer le ju pendant l'attente d'une entree utilisateur */
	private boolean canContinue;



	//
	// CONSTRUCTOR
	//
	/** Construit un nouveau CreatePlayersPanel */
	public CreatePlayersPanel()
	{
		this.text = new JTextArea(1, 30);
		this.ok = new JButton("OK");
		this.ok.addActionListener(new OkButtonListener());
		this.canContinue = false;

		this.add(this.text);
		this.add(this.ok);
	}

	/** Demande l'entree des noms des joueurs (2 a 8) */
	public void askPlayers()
	{
		Game.getInstance().getWindow().setVisibility(false);
		this.text.setText("");
		this.setVisible(true);
		JOptionPane.showMessageDialog(null, "Entrez le nom des joueurs (1 par ligne)");
	}



	//
	// GET & SET
	//
	/** Retourne la liste des noms des joueurs */
	public ArrayList<String> getPlayersName()
	{
		return this.players;
	}

	/** Retourne true si le jeu est non-bloque, false sinon */
	public boolean getCanContinue()
	{
		return this.canContinue;
	}



	//
	// CLASSES
	//
	/** ActionListener permettant de valider les noms des joueurs */
	class OkButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			String s = text.getText();
			String error = null;

			if (s.length() == 0)
				error = "nom trop court";
			else
			{
				int count = countValidLines(s);

				if (count <= 1)	
					error = "pas assez de joueurs";
				else if (count > 8)
					error = "trop de joueurs";
			}

			if (error != null)
				JOptionPane.showMessageDialog(null, error);
			else
			{
				players = new ArrayList<String>();
				String[] cut = s.split("\n");

				for (String str : cut)
				{
					if (str.length() != 0 && ! str.equals(""))
						players.add(str);
				}

				setVisible(false);
				Game.getInstance().getWindow().setVisibility(true);

				canContinue = true;
			}
		}

		/** Compte le nombre de noms valides */
		private int countValidLines(String s)
		{
			String[] cut = s.split("\n");
			int ret = 0;

			for (String str : cut)
			{
				if (str.length() != 0 && ! str.equals(""))
					ret++;
			}

			return ret;
		}
	}

}