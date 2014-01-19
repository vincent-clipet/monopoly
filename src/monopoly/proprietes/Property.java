package monopoly.proprietes;

import monopoly.evenements.events.EventPayRent;
import monopoly.jeu.Game;
import monopoly.jeu.IPlayer;
import monopoly.jeu.ISlot;
import monopoly.util.ICSVSerializable;

/** Propriete */
public abstract class Property implements IProperty, ICSVSerializable
{

	//
	// ATTRIBUTES
	//
	/** Nom de la propriete */
	protected final String name;
	
	/** Propriete hypothequee ou non */
	protected boolean mortgaged;
	
	/** Prix d'achat */
	protected final int price;
	
	/** Groupe */
	protected final IGroup group;
		
	/** Constructible ou non */
	protected boolean buildable;
	
	/** Proprietaite */
	protected IPlayer owner;
	
	/** Case correspondante */
	protected ISlot slot;



	//
	// CONSTRUCTOR
	//
	/** Construit une nouvelle propriete */
	public Property(ISlot slot, String name, int price, IGroup group)
	{
		this.slot = slot;
		this.name = name;
		this.price = price;
		this.group = group;

		this.mortgaged = false;
		this.owner = null;

		group.getProperties().add(this);
	}



	//
	// METHODS
	//
	@Override
	public boolean build()
	{
		if (this.owner == null)
			return true;

		return false;
	}

	@Override
	public boolean unbuild()
	{
		return false;
	}

	@Override
	public void setMortgaged()
	{
		if (this.mortgaged || this.owner == null)
			return;

		this.mortgaged = true;
		this.owner.give(this.price / 2);
		Game.getInstance().addMessage("La propriété " + this.name + " de " + this.owner.getName() + " a été hypothéquée");
	}

	@Override
	public boolean setUnmortgaged()
	{
		if (! mortgaged || this.owner == null)
			return false;

		if (this.owner.take(this.price / 2 + this.price / 10))
		{
			this.mortgaged = false;
			Game.getInstance().addMessage("La propriété " + this.name + " de " + this.owner.getName() + " a été déshypothéquée");
			return true;
		}

		return false;
	}

	@Override
	public abstract int calcRent();

	@Override
	public boolean isBuildable()
	{
		return this.buildable;
	}



	//
	// GET & SET
	//
	@Override
	public ISlot getSlot()
	{
		return this.slot;
	}

	@Override
	public void setSlot(ISlot slot)
	{
		this.slot = slot;
	}

	@Override
	public String getName()
	{
		return this.name;
	}

	@Override
	public boolean isMortgaged()
	{
		return this.mortgaged;
	}

	@Override
	public int getPrice()
	{
		return this.price;
	}

	@Override
	public IGroup getGroup()
	{
		return this.group;
	}

	@Override
	public IPlayer getOwner()
	{
		return this.owner;
	}

	@Override
	public void setOwner(IPlayer p)
	{
		if (p == null)
			this.owner = null;
		else
		{
			this.owner = p;
			this.slot.setEvent(new EventPayRent(this));
		}
	}

	@Override
	public int getBuildings()
	{
		return 0;
	}



	//
	// UTIL
	//
	@Override
	public abstract String[] serialize();

}