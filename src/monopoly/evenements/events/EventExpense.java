package monopoly.evenements.events;

import monopoly.evenements.EEventTypes;
import monopoly.evenements.Event;
import monopoly.jeu.Player;

public class EventExpense extends Event
{

	//
	// ATTRIBUTES
	//
	/** Montant de la depense */
	private int amount;



	//
	// CONSTRUCTOR
	//
	public EventExpense(int amount)
	{
		this();
		this.amount = amount;
	}

	public EventExpense()
	{
		super(EEventTypes.EXPENSE.getName());
	}



	//
	// METHODS
	//
	@Override
	public boolean run()
	{
		if (this.target.take(this.amount))
			this.succeeded = true;
		else
			this.succeeded = ((Player)(this.target)).mortgageUntilMoneyIsBack(this.amount);

		return this.succeeded;
	}

	@Override
	public void clear()
	{
		super.clear();
		this.amount = 0;
	}



	//
	// GET & SET
	//
	public int getAmount()
	{
		return this.amount;
	}

	public void setAmount(int amount)
	{
		this.amount = amount;
	}



	//
	// UTIL
	//	
	@Override
	public String toString()
	{
		if (this.target == null)
			return "";
		else if (! this.succeeded)
			return this.target.getName() + " ne peut pas dépenser " + this.amount;
		else
			return this.target.getName() + " dépense " + this.amount;
	}
}
