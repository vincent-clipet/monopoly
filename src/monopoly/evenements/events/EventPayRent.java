package monopoly.evenements.events;

import monopoly.evenements.EEventTypes;
import monopoly.evenements.Event;
import monopoly.jeu.Player;
import monopoly.proprietes.IProperty;

public class EventPayRent extends Event
{

	//
	// ATTRIBUTES
	//
	/** Propriete concernee */
	private IProperty prop;
	
	/** Sert a savoir si le proprietaire == joueur cible */
	private boolean samePlayer;
	
	/** Sert a savoir si la propriete est hypothequee */
	private boolean mortgaged;



	//
	// CONSTRUCTOR
	//
	public EventPayRent(IProperty prop)
	{
		this();
		this.prop = prop;
	}

	public EventPayRent()
	{
		super(EEventTypes.PAY_RENT.getName());
		this.samePlayer = false;
		this.mortgaged = false;
	}



	//
	// METHODS
	//
	@Override
	public boolean run()
	{
		if (this.prop == null)
		{
			this.succeeded = false;
			return this.succeeded;
		}
		
		if (this.prop.isMortgaged())
		{
			this.mortgaged = true;
			this.succeeded = false;
			return this.succeeded;
		}
		
		if (this.prop.getOwner() == this.target)
		{
			this.samePlayer = true;
			this.succeeded = false;
			return this.succeeded;
		}

		int rent = this.prop.calcRent();
		boolean hasPaid = this.target.take(rent);

		if (hasPaid)
		{
			this.prop.getOwner().give(rent);
			this.succeeded = true;
		}
		else
			this.succeeded = ((Player)(this.target)).mortgageUntilMoneyIsBack(rent);
		
		return this.succeeded;
	}

	@Override
	public void clear()
	{
		super.clear();
	}



	//
	// GET & SET
	//
	public IProperty getProperty()
	{
		return this.prop;
	}

	public void setProperty(IProperty prop)
	{
		this.prop = prop;
	}



	//
	// UTIL
	//
	@Override
	public String toString()
	{
		if (this.target == null || this.prop == null || this.prop.getOwner() == null)
			return "";
		else if (this.mortgaged)
			return "";
		else if (this.samePlayer)
			return "";
		else if (! this.succeeded)
			return this.target.getName() + " n'a pas pu payer le loyer (" + this.prop.calcRent() + ") de " + this.prop.getName();
		else
			return this.target.getName() + " paye " + this.prop.calcRent() + " de loyer à " + this.prop.getOwner().getName();
	}
}
