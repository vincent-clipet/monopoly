package monopoly.evenements.events;

import monopoly.evenements.EEventTypes;
import monopoly.evenements.Event;
import monopoly.jeu.Game;
import monopoly.proprietes.IProperty;

public class EventBuyProperty extends Event
{

	//
	// ATTRIBUTES
	//
	/** Propriete concernee */
	private IProperty prop;
	
	/** Sert a savoir si le joueur manquait d'argent */
	private boolean wasPoor;



	//
	// CONSTRUCTOR
	//
	public EventBuyProperty(IProperty prop)
	{
		this();
		this.prop = prop;
		this.wasPoor = false;
	}

	public EventBuyProperty()
	{
		super(EEventTypes.BUY.getName());
	}



	//
	// METHODS
	//
	@Override
	public boolean run()
	{
		boolean ans = Game.getInstance().getWindow().askQuestion("(" + this.target.getName() + ") Acheter la propriété " + this.prop.getName() + " pour " + this.prop.getPrice() + " ?", "Achat");

		if (ans)
		{
			if (this.target.take(this.prop.getPrice()))
			{
				this.prop.setOwner(this.target);
				this.target.getProperties().add(this.prop);
				this.succeeded = true;
			}
			else
			{
				this.succeeded = false;
				this.wasPoor = true;
			}
		}
		else
			this.succeeded = false;

		return this.succeeded;
	}

	@Override
	public void clear()
	{
		super.clear();
		this.prop = null;
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
		if (this.target == null || this.prop == null)
			return "";
		else if (this.wasPoor)
			return this.target.getName() + " n'a pas assez d'argent pour acheter " + this.prop.getName();
		else if (! this.succeeded)
			return "";
		else
			return this.target.getName() + " achète " + this.prop.getName() + " pour " + this.prop.getPrice();
	}
}
