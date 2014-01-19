package monopoly.gui;

import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import monopoly.jeu.Game;
import monopoly.jeu.IPlayer;
import monopoly.jeu.Player;

/** JPanel contenant la liste des joueurs */
public class PlayersPanel extends JPanel
{

	//
	// ATTRIBUTES
	//
	/** JTable contenant quelques infos sur les joueurs */
	private JTable table;
	
	/** En-tetes de la JTable */
	private final String[] headers = new String[] { "Tour", "Nom", "$", "Slot" };
	
	/** Donnees de la JTable */
	private Object[][] data;

	/** Nombre de lignes */
	private int rows;
	
	/** Nombre de colonnes */
	private int cols;
	
	/** Index du joueur actuel */
	private int currentIndex;
	
	/** Liste des joueurs */
	private ArrayList<IPlayer> players;



	//
	// METHODS
	//
	/** Remplit la JTable avec des donnees fraiches */
	public void fillData()
	{
		initPlayers();
		this.rows = players.size();
		this.cols = 4;
		this.currentIndex = 0;
		initData();

		this.table = new PlayersTable();
		this.table.getColumnModel().getColumn(0).setPreferredWidth(30);
		this.table.getColumnModel().getColumn(1).setPreferredWidth(130);
		this.table.getColumnModel().getColumn(2).setPreferredWidth(70);
		this.table.getColumnModel().getColumn(3).setPreferredWidth(30);
		this.add(this.table);

		this.revalidate();
		this.repaint();
	}

	/** Initialise la liste des joueurs */
	private void initPlayers()
	{
		players = new ArrayList<IPlayer>();

		for (IPlayer p : Player.players)
			players.add(p);
	}

	/** Initialise le tableau de donnees */
	private void initData()
	{
		this.data = new Object[this.rows][this.cols];

		for (int j = 0; j < this.rows; j++)
		{
			IPlayer ip = players.get(j);
			this.data[j][0] = ip == Game.getInstance().getCurrentPlayer();
			this.data[j][1] = ip.getName();
			this.data[j][2] = ip.getMoney();
			this.data[j][3] = ip.getSlot().getId();
		}
	}



	//
	// GET & SET
	//
	/** Passe au joueur suivant, en cochant la case correspondante dans le tableau */
	public void nextPlayer()
	{
		this.data[this.currentIndex][0] = false;
		this.currentIndex = (this.currentIndex + 1) % this.data.length;
		this.data[this.currentIndex][0] = true;

		initData();
		this.revalidate();
		this.repaint();
	}



	//
	// CLASSES
	//
	class PlayersTable extends JTable
	{
		PlayersTable()
		{
			super(new PlayersTableModel());
		}


	}

	class PlayersTableModel extends AbstractTableModel
	{
		PlayersTableModel()
		{

		}

		public int getRowCount()
		{
			return rows;
		}

		public int getColumnCount()
		{
			return cols;
		}

		public Object getValueAt(int rowIndex, int columnIndex)
		{
			if (columnIndex == 0)
			{
				return (players.get(rowIndex) == (Game.getInstance().getCurrentPlayer()));
			}
			else if (columnIndex == 1)
			{
				return players.get(rowIndex).getName();
			}
			else if (columnIndex == 2)
			{
				return players.get(rowIndex).getMoney();
			}
			else if (columnIndex == 3)
			{
				return players.get(rowIndex).getSlot().getId();
			}
			else
				return null;
		}

		public Class getColumnClass(int i) 
		{ 
			return (data[0][i]).getClass() ; 
		}

		public boolean isCellEditable(int row, int col)
		{
			return false;
		}

		public String getColumnName(int col) 
		{ 
			return headers[col] ; 
		}
	}

}
