package monopoly.evenements;

import monopoly.jeu.IPlayer;

public abstract class Event implements IEvent
{

	//
	// ATTRIBUTES
	//
	/** Joueur cible de l'evenement */
	protected IPlayer target;
	
	/** Nom de l'evenement */
	protected final String name;
	
	/** Reussite ou non de l'evenement */
	protected boolean succeeded;



	//
	// CONSTRUCTOR
	//
	/** Construit un nouvel evenement */
	public Event(String name, IPlayer p)
	{
		this.name = name;
		this.target = p;
		this.succeeded = false;
	}

	/** Construit un nouvel evenement */
	public Event(String name)
	{
		this(name, null);
	}



	//
	// METHODS
	//
	@Override
	public abstract boolean run();

	@Override
	public void clear()
	{
		this.target = null;
		this.succeeded = false;
	}



	//
	// GET & SET
	//
	@Override
	public String getName()
	{
		return this.name;
	}

	@Override
	public IPlayer getTarget()
	{
		return this.target;
	}

	@Override
	public void setTarget(IPlayer p)
	{
		this.target = p;
	}



	//
	// UTIL
	//
	public String toString()
	{
		return "Event " + this.name + " : " + this.target.getName();
	}

}