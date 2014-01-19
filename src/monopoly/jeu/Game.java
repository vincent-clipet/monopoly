package monopoly.jeu;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import monopoly.Core;
import monopoly.evenements.EEventTypes;
import monopoly.evenements.Event;
import monopoly.evenements.IEvent;
import monopoly.evenements.events.EventBuyProperty;
import monopoly.evenements.events.EventCard;
import monopoly.evenements.events.EventEmpty;
import monopoly.evenements.events.EventExpense;
import monopoly.evenements.events.EventJail;
import monopoly.evenements.events.EventMoveRelative;
import monopoly.evenements.events.EventPayRent;
import monopoly.evenements.events.EventReceipt;
import monopoly.gui.CreatePlayersPanel;
import monopoly.gui.GameWindow;
import monopoly.proprietes.Company;
import monopoly.proprietes.EPropertyTypes;
import monopoly.proprietes.Group;
import monopoly.proprietes.IGroup;
import monopoly.proprietes.IProperty;
import monopoly.proprietes.Plot;
import monopoly.proprietes.Property;
import monopoly.proprietes.TrainStation;
import monopoly.util.CSVManager;
import monopoly.util.FormatUtil;

/** Partie */
public class Game
{

	//
	// ATTRIBUTES
	//
	/** Bloque l'instanciation de plusieurs parties */
	private static Game singleton = null;

	/** Case depart */
	private ISlot firstSlot;

	/** Joueur actuel */
	private IPlayer currentPlayer;

	/** Ne sert à rien */
	private IGroup emptyGroup;

	/** Fenetre de jeu */
	private GameWindow gw;

	/** Paire de dés utilisee */
	private final DicePair dices = new DicePair();



	//
	// CONSTRUCTOR
	//
	private Game()
	{

	}

	/** Singleton */
	public static Game getInstance()
	{
		if (Game.singleton == null)
			Game.singleton = new Game();

		return Game.singleton;
	}



	//
	// INIT
	//
	/** Initialise les donnes du jeu */
	public void init()
	{
		this.gw = new GameWindow();
		boolean mustLoad = askLoad();

		// Groups & Slots
		initGroupsSlots();

		// Players
		initPlayers(mustLoad);

		// Cards
		initCards();
	}

	/** Demande a l'utiisateur s'il veut charger une partie sauvegardee precedemment */
	private boolean askLoad()
	{
		boolean load = this.gw.askQuestion("Voulez-vous charger la partie précédente ?", "Chargement de sauvegarde");

		if (! load)
			return false;

		File f1 = new File("config/partie.joueurs");
		File f2 = new File("config/partie.titres");

		if (! f1.exists() || ! f2.exists())
		{
			JOptionPane.showMessageDialog(null, "Impossible de charger la sauvegarde. Une nouvelle partie va être lancée.");
			return false;
		}

		return true;
	}

	/** Initialise les cases et les groupes */
	private void initGroupsSlots()
	{
		ArrayList<String> slotsToLoad = CSVManager.read("config/monopoly.csv", 1);

		boolean needFirstSlot = true;
		boolean needFirstGroup = true;

		for (String line : slotsToLoad)
		{
			String groupName = line.split(";", -1)[4];

			if (! groupName.equals("")) //TODO: clear
			{
				IGroup g = new Group(groupName);

				if (needFirstGroup && g != null)
				{
					this.emptyGroup = g;
					needFirstGroup = false;
				}
			}

			if (needFirstSlot)
			{
				this.firstSlot = unserializeSlot(line);
				needFirstSlot = false;
			}
			else
				unserializeSlot(line);
		}
	}

	/** Initialise les cartes */
	private void initCards()
	{
		//TODO
	}

	/** Initialise ou cree les joueurs */
	private void initPlayers(boolean load)
	{
		if (load)
		{
			ArrayList<String> playersToLoad = CSVManager.read("config/partie.joueurs", 0);

			for (String line : playersToLoad)
			{
				Player p = unserializePlayer(line);

				for (IProperty iprop : p.getProperties())
				{
					iprop.setOwner(p);
					EventPayRent event = new EventPayRent(iprop);
					iprop.getSlot().setEvent(event);
				}
			}

			loadProperties();
		}
		else
		{
			CreatePlayersPanel panel = Game.getInstance().getWindow().getCreatePlayersPanel();
			panel.askPlayers();

			while (! panel.getCanContinue()) // Permet d'attendre que le joueur aie cliqué sur 'oui' ou 'non'
			{
				try
				{
					Thread.sleep(100);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}

			ArrayList<String> names = panel.getPlayersName();

			for (int i = 1; i <= names.size(); i++)
				new Player(i, names.get(i-1));
		}

		this.gw.getPlayersPanel().fillData();
	}

	/** Charge les proprietes sauvegardees */
	private void loadProperties()
	{
		ArrayList<String[]> propToLoad = CSVManager.read("config/partie.titres", ";");

		for (String[] fields : propToLoad)
		{
			//NUMERO;HYPOTHEQUE;NIVEAU CONSTRUCTION 
			int id = FormatUtil.stringToInt(fields[0]);
			boolean mortgaged = (fields[1].length() == 0 ? false : FormatUtil.stringToBoolean(fields[1]));
			int buildings = FormatUtil.stringToInt(fields[2]);

			ISlot slot = this.firstSlot.getSlot(id);
			IProperty prop = slot.getProperty();

			for (int i = 0; i < buildings; i++)
			{
				prop.build(); // FIX
			}

			if (mortgaged)
			{
				prop.setMortgaged();
				prop.getOwner().take(prop.getPrice() / 2);
			}
		}
	}

	/** Sauvegarde la partie actuelle */
	public void save()
	{
		// Players
		ArrayList<String[]> playersCSV = new ArrayList<String[]>();
		for (Player p : Player.players)
			playersCSV.add(p.serialize());
		CSVManager.write("config/partie.joueurs", playersCSV, ";");

		// Properties
		ArrayList<String[]> propertiesCSV = new ArrayList<String[]>();
		for (int i = 1; i <= 40; i++)
		{ 
			IProperty prop = this.firstSlot.getSlot(i).getProperty();

			if (prop != null && prop.getOwner() != null)
				propertiesCSV.add(((Property)prop).serialize());
		}
		CSVManager.write("config/partie.titres", propertiesCSV, ";");
	}



	//
	// RUN
	//
	/** Lance la partie */
	public void start()
	{
		this.currentPlayer = Player.players.get(Player.players.size() - 1);
		runTurn();
	}

	/** Lance un nouveau tour */
	public void runTurn()
	{
		IPlayer winner = isThereAWinner();
		
		if (winner != null)
		{
			JOptionPane.showMessageDialog(null, winner.getName() + " gagne la partie !");
			System.exit(0);
		}
		
		this.currentPlayer = Player.players.get((this.currentPlayer.getId()) % Player.players.size());

		if (this.currentPlayer.isEliminated())
			runTurn();

		Game.getInstance().getDices().roll();

		if (Core.debug)
		{
			System.out.println("\n/////////////////////////////////////////////////////////////////////////////////////////\n");
			System.out.println(Game.getInstance().getDices().toString());
			System.out.println(this.currentPlayer);
		}

		this.gw.setBeginningOfTurn();

		if (this.currentPlayer.isJailed()) //TODO : free if player has a "jail-free" card
		{
			if (! Game.getInstance().getDices().isDouble())
			{
				this.addMessage(this.currentPlayer.getName() + " passe son tour (prison)");
				this.gw.setEndOfTurn();
				return;
			}
			else
			{
				this.addMessage(this.currentPlayer.getName() + " est maintenant libre !");
				this.currentPlayer.setFree();
			}
		}

		IEvent moveEvent = new EventMoveRelative(Game.getInstance().getDices().getTotal(), this.currentPlayer);
		this.currentPlayer.getTodo().push(moveEvent);

		while (! this.currentPlayer.getTodo().isEmpty())
		{
			IEvent event = this.currentPlayer.getTodo().pop();
			event.setTarget(this.currentPlayer);
			event.run();
			this.addMessage(event.toString());
		}

		this.gw.setEndOfTurn();
		this.gw.repaint();

		if (Core.debug)
			System.out.println(this.currentPlayer);
	}

	/** Ajoute un message a l'ecran */
	public void addMessage(String s)
	{
		this.getWindow().getMessagesPanel().addMessage(s);
	}
	
	/** Retourne le gagnant s'il y en a un, null sinon */
	private IPlayer isThereAWinner()
	{
		int count = 0;
		IPlayer ret = null;
		
		for (IPlayer p : Player.players)
		{
			if (! p.isEliminated())
			{
				count++;
				ret = p;
			}
		}
		
		if (count == 1)
			return ret;
		else
			return null;
	}



	//
	// GET & SET
	//
	/** Retourne la fenetre du jeu */
	public GameWindow getWindow()
	{
		return this.gw;
	}

	/** Retourne la case depart */
	public ISlot getFirstSlot()
	{
		return this.firstSlot;
	}

	/** Retourne le joueur actuel */
	public IPlayer getCurrentPlayer()
	{
		return this.currentPlayer;
	}

	/** Retourne la paire de dés */
	public DicePair getDices()
	{
		return this.dices;
	}



	//
	// UTIL
	//
	/** Deserialise un joueur */
	private Player unserializePlayer(String line)
	{
		//NUMÉRO;NOM;ESPÈCES;POSITION;EMPRISONNÉ;TITRES;CARTES
		String[] s = line.split(";", -1);
		int uid = FormatUtil.stringToInt(s[0]);
		String name = s[1];
		int money = FormatUtil.stringToInt(s[2]);
		ISlot slot = this.firstSlot.getSlot(FormatUtil.stringToInt(s[3]));
		slot.setEvent(new EventPayRent(slot.getProperty()));
		boolean jailed = FormatUtil.stringToBoolean(s[4]);
		int[] numProp = FormatUtil.stringToIntArray(s[5], ",");

		List<IProperty> props = new ArrayList<IProperty>();

		for (int i : numProp)
			props.add(this.firstSlot.getSlot(i).getProperty());

		return new Player(uid, name, money, slot, jailed, props, null); // TODO: replace 'null' by cards
	}

	/** Deserialise une case */
	private Slot unserializeSlot(String line)
	{
		//NUM:NOM:EVENT:TYPE_PROP:GROUP:PRICE:BUILDINGPRICE:RENTS
		String[] s = line.split(";", -1);
		int id = FormatUtil.stringToInt(s[0]);
		String name = s[1];
		Event event = unserializeEvent(s[2]);
		EPropertyTypes type = EPropertyTypes.getTypeFromString(s[3], s[4]);

		if (type == EPropertyTypes.NONE)
			return new Slot(id, name, event, null);

		IGroup group = this.emptyGroup.getGroup(s[4]);
		int price = FormatUtil.stringToInt(s[5]);

		Slot slot = null;
		IProperty property = null;

		if (type == EPropertyTypes.PLOT) // Plot
		{
			int buildingPrice = FormatUtil.stringToInt(s[6]);
			int[] rents = FormatUtil.stringToIntArray(s[7], ",");
			property = new Plot(null, name, price, group, buildingPrice, rents);
		}
		else if (type == EPropertyTypes.COMPANY) // companies
		{
			property = new Company(null, name, price, group);
		}
		else if (type == EPropertyTypes.TRAINSTATION) // stations
		{
			int rent = FormatUtil.stringToInt(s[7]);
			property = new TrainStation(null, name, price, group, rent);
		}

		EventBuyProperty eventBuy = new EventBuyProperty(property);
		slot = new Slot(id, name, eventBuy, property);
		property.setSlot(slot);

		return slot;
	}

	/** Deserialise un evenement */
	private Event unserializeEvent(String line)
	{
		String[] fields = line.split(",");
		EEventTypes type = EEventTypes.getTypeFromString(fields[0]);

		if (type == EEventTypes.RECEIPT)
			return new EventReceipt(FormatUtil.stringToInt(fields[1]));
		else if (type == EEventTypes.EXPENSE)
			return new EventExpense(FormatUtil.stringToInt(fields[1]));
		else if (type == EEventTypes.CARD)
			return new EventCard(); //TODO
		else if (type == EEventTypes.JAIL)
			return new EventJail();
		else
			return new EventEmpty();
	}

}