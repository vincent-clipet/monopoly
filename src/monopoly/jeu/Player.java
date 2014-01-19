package monopoly.jeu;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import monopoly.evenements.IEvent;
import monopoly.proprietes.IProperty;
import monopoly.util.FormatUtil;
import monopoly.util.ICSVSerializable;

/** Joueur */
public class Player implements IPlayer, ICSVSerializable
{

	//
	// ATTRIBUTES
	//
	/** Nom du joueur */
	private final String name;
	
	/** ID du joueur */
	private final int id;
	
	/** Indique si le joueur est elimine */
	private boolean eliminated;
	
	/** Indique si le joueur est en prison */
	private boolean jailed;
	
	/** Argent du joueur */
	private int money;
	
	/** Case ou est le joueur */
	private ISlot slot;
	
	/** Liste des proprietes du joueur */
	private List<IProperty> properties;
	
	/** Liste des cartes du joueur */
	private List<IEvent> cards;
	
	/** Liste des evenements du tour actuel */
	private Stack<IEvent> todo;
	
	/** Liste des joueurs. Devrait etre 'private' mais tant pis... */
	public final static ArrayList<Player> players = new ArrayList<Player>(); //TODO: private



	//
	// CONSTRUCTOR
	//
	/** Construit un nouveau joueur */
	public Player(int uid, String name)
	{
		this.name = name;
		this.id = uid;
		this.eliminated = false;
		this.jailed = false;
		this.money = 150000;
		this.slot = Game.getInstance().getFirstSlot();
		this.properties = new ArrayList<IProperty>();
		this.cards = new ArrayList<IEvent>();
		this.todo = new Stack<IEvent>();
		Player.players.add(this);
	}

	/** Construit un nouveau joueur */
	public Player(int uid, String name, int money, ISlot slot, boolean jailed, List<IProperty> properties, List<IEvent> cards)
	{
		this.name = name;
		this.id = uid;
		this.eliminated = false;
		this.jailed = jailed;
		this.money = money;
		this.slot = slot;
		this.properties = properties;
		this.cards = cards;
		this.todo = new Stack<IEvent>();
		Player.players.add(this);
	}



	//
	// METHODS
	//
	@Override
	public boolean take(int somme)
	{
		int newMoney = this.money - somme;

		if (newMoney > 0)
		{
			this.money -= somme;
			return true;
		}
		else
			return false;
	}

	@Override
	public void give(int somme)
	{
		this.money += somme;
	}

	@Override
	public List<IPlayer> getOpponents()
	{
		List<IPlayer> ret = new ArrayList<IPlayer>(Player.players); //TODO: test
		ret.remove(this);

		for (IPlayer p : ret)
			if (p.isEliminated())
				ret.remove(p);

		return ret;
	}

	/** Hypotheque toutes les proprietes du joueur jusqu'a ce qu'il puisse payer.
	 * S'il ne peut toujours pas, il est elimine. */
	public boolean mortgageUntilMoneyIsBack(int moneyWanted)
	{
		boolean isPositive = this.money > moneyWanted;

		if (isPositive)
		{
			take(moneyWanted);
			return true;
		}

		for (IProperty prop : this.properties)
		{
			if (isPositive)
			{
				take(moneyWanted);
				return true;
			}

			while (prop.getBuildings() > moneyWanted)
			{
				if (isPositive)
				{
					take(moneyWanted);
					return true;
				}

				prop.unbuild(); //TODO: test
				isPositive = this.money > moneyWanted;
			}

			prop.setMortgaged();
			isPositive = this.money > moneyWanted;
		}

		if (isPositive)
		{
			take(moneyWanted);
			return true;
		}
		else
		{
			setEliminated();
			return false;
		}
	}

	/*private IProperty getBiggestProperty()
	{
		if (this.properties.size() == 0)
			return null;

		IProperty ret = null;

		for (IProperty prop : this.properties)
		{
			if (ret == null)
				ret = prop;
			else
			{
				if (! prop.isMortgaged())
				{
					if (prop.getPrice() > ret.getPrice())
						ret = prop;
				}
			}
		}

		return ret;
	}*/

	@Override
	public void setSlot(ISlot c)
	{
		this.slot = c;
	}



	//
	// GET & SET
	//
	@Override
	public List<IProperty> getProperties()
	{
		return this.properties;
	}

	@Override
	public List<IEvent> getCards()
	{
		return this.cards;
	}

	@Override
	public Stack<IEvent> getTodo()
	{
		return this.todo;
	}

	@Override
	public int getId()
	{
		return this.id;
	}

	@Override
	public String getName()
	{
		return this.name;
	}

	@Override
	public boolean isJailed()
	{
		return this.jailed;
	}

	@Override
	public int getMoney()
	{
		return this.money;
	}

	@Override
	public ISlot getSlot()
	{
		return this.slot;
	}

	@Override
	public void setJailed()
	{
		this.jailed = true;
	}

	@Override
	public void setFree()
	{
		this.jailed = false;
	}

	@Override
	public boolean isEliminated()
	{
		return this.eliminated;
	}

	@Override
	public void setEliminated()
	{
		this.eliminated = true;

		for (IProperty prop : this.properties)
		{
			prop.setUnmortgaged();
			prop.setOwner(null);
		}

		Game.getInstance().addMessage("Le joueur " + this.name + " a été éliminé");
	}



	//
	// UTIL
	//
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("Player: " + this.name).append("\n")
		.append("id: " + this.id).append("\n")
		.append("jailed: " + this.jailed).append("\n")
		.append("eliminated: " + this.eliminated).append("\n")
		.append("money: " + this.money).append("\n")
		.append("slot: " + this.slot.getName()).append("\n")
		.append("properties: ");

		for (IProperty p : this.properties)
			sb.append(p.getName()).append(", ");

		//TODO: add cards

		return sb.toString();
	}

	@Override
	public String[] serialize()
	{
		String[] ret = new String[7];
		ret[0] = "" + this.id;
		ret[1] = this.name;
		ret[2] = "" + this.money;
		ret[3] = "" + this.slot.getId();
		ret[4] = "" + FormatUtil.booleanToString(this.jailed);

		int[] props = new int[this.properties.size()];
		for (int i = 0; i < props.length; i++)
			props[i] = this.properties.get(i).getSlot().getId();

		ret[5] = FormatUtil.join(props, ",");
		ret[6] = ""; // TODO: replace "" by cards

		return ret;
	}

}