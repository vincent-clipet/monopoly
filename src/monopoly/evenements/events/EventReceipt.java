package monopoly.evenements.events;

import monopoly.evenements.EEventTypes;
import monopoly.evenements.Event;

public class EventReceipt extends Event
{

	//
	// ATTRIBUTES
	//
	/** Montant de la recette */
	private int amount;



	//
	// CONSTRUCTOR
	//
	public EventReceipt(int amount)
	{
		this();
		this.amount = amount;
	}

	public EventReceipt()
	{
		super(EEventTypes.RECEIPT.getName());
	}



	//
	// METHODS
	//
	@Override
	public boolean run()
	{
		this.target.give(this.amount);
		this.succeeded = true;
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
		else
			return this.target.getName() + " reçoit " + this.amount;
	}


}
