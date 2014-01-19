package monopoly.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/** Fenetre du jeu */
public class GameWindow extends JFrame
{

	//
	// ATTRIBUTES
	//
	/** Panel inutile servant de fond */
	private JPanel background;
	
	private BoardPanel board;
	private DicesPanel dices;
	private MessagesPanel messages;
	private PlayersPanel players;
	private CreatePlayersPanel createPlayers;
	private PropertyPanel property;
	
	/** ArrayList contenant tous les JPanel a cacher lors de l'affichage du CreatePlayersPanel */
	private ArrayList<JPanel> gamePanels;



	//
	// CONSTRUCTOR
	//
	/** Construit une nouvelle GameWindow */
	public GameWindow()
	{
		setResizable(false);
		setLocation(100, 100);
		setTitle("Monopoly");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setPreferredSize(new Dimension(800, 600));
		pack();

		this.gamePanels = new ArrayList<JPanel>();

		this.setBackground(Color.WHITE);

		this.property = new PropertyPanel();
		property.setLocation(420, 10);
		property.setSize(370, 250);
		property.setBackground(Color.CYAN);

		this.board = new BoardPanel(this.property);
		board.setLocation(10, 10);
		board.setSize(400, 400);
		board.setBackground(Color.RED);

		this.dices = new DicesPanel();
		dices.setLocation(290, 420);
		dices.setSize(120, 90);
		dices.setBackground(Color.GREEN);

		this.messages = new MessagesPanel();
		messages.setLocation(420, 270);
		messages.setSize(370, 320);
		messages.setBackground(Color.BLUE);

		this.players = new PlayersPanel();
		players.setLocation(10, 420);
		players.setSize(270, 170);
		players.setBackground(Color.PINK);

		this.createPlayers = new CreatePlayersPanel();
		createPlayers.setLocation(200, 200);
		createPlayers.setSize(400, 200);
		createPlayers.setBackground(Color.ORANGE);
		createPlayers.setVisible(false);

		this.background = new JPanel();
		background.setBackground(Color.WHITE);

		add(this.board);
		add(this.dices);
		add(this.messages);
		add(this.players);
		add(this.property);
		add(this.createPlayers);
		add(this.background);

		this.gamePanels.add(this.board);
		this.gamePanels.add(this.dices);
		this.gamePanels.add(this.property);
		this.gamePanels.add(this.messages);
		this.gamePanels.add(this.players);
		this.gamePanels.add(this.background);

		setVisible(true);
	}



	//
	// METHODS
	//
	/** Pose une question oui/non au joueur, avec un titre pour la fenetre, et un message */
	public boolean askQuestion(String msg, String title)
	{
		int reply = JOptionPane.showConfirmDialog(null, msg, title, JOptionPane.YES_NO_OPTION);
		return (reply == JOptionPane.YES_OPTION);
	}

	/** Active les boutons de sauvegarde et de tour suivant a la fin de chaque tour */
	public void setEndOfTurn()
	{
		this.dices.getDicesButton().setEnabled(true);
		this.dices.getSaveButton().setEnabled(true);
	}

	/** Desactive les boutons de sauvegarde et de tour suivant au debut de chaque tour;
	 * met a jour les valeurs des dés;
	 * change le joueur courant */
	public void setBeginningOfTurn()
	{
		this.dices.updateValues();
		this.dices.getDicesButton().setEnabled(false);
		this.dices.getSaveButton().setEnabled(false);
		this.players.nextPlayer();
		this.messages.addMessage("------------------------------------");
	}


	/** Permet de montrer/cacher tous les JPanels necessaire au deroulement du jeu */
	public void setVisibility(boolean b)
	{
		for (JPanel label : this.gamePanels)
			label.setVisible(b);
	}



	//
	// GET & SET
	//
	
	public BoardPanel getBoardPanel()
	{
		return this.board;
	}

	public DicesPanel getDicesPanel()
	{
		return this.dices;
	}

	public MessagesPanel getMessagesPanel()
	{
		return this.messages;
	}

	public PlayersPanel getPlayersPanel()
	{
		return this.players;
	}

	public CreatePlayersPanel getCreatePlayersPanel()
	{
		return this.createPlayers;
	}

	public PropertyPanel getPropertyPanel()
	{
		return this.property;
	}

}