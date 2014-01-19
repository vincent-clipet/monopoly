package monopoly.evenements.events;

import monopoly.evenements.EEventTypes;
import monopoly.evenements.Event;
import monopoly.evenements.IEvent;
import monopoly.jeu.IPlayer;
import monopoly.jeu.ISlot;

public class EventMoveRelative extends Event
{

	//
	// ATTRIBUTES
	//
	/** Portee du deplacement */
	private int slots;



	//
	// CONSTRUCTOR
	//
	public EventMoveRelative(int slots, IPlayer p)
	{
		super(EEventTypes.MOVE_RELATIVE.getName(), p);
		this.slots = slots;
	}

	public EventMoveRelative(int slots)
	{
		this();
		this.slots = slots;
	}

	public EventMoveRelative()
	{
		super(EEventTypes.MOVE_RELATIVE.getName());
	}



	//
	// METHODS
	//
	@Override
	public boolean run()
	{
		ISlot slot = this.target.getSlot();

		int newSlot = ((slot.getId() + this.getMove()) - 1) % 40 + 1;
		ISlot slot2 = slot.getSlot(newSlot);
		this.target.setSlot(slot2);

		IEvent slotEvent = slot2.getEvent();
		slotEvent.setTarget(this.target);
		this.target.getTodo().push(slotEvent);

		this.succeeded = true;
		return this.succeeded;
	}

	@Override
	public void clear()
	{
		super.clear();
		this.slots = 0;
	}



	//
	// GET & SET
	//
	public int getMove()
	{
		return this.slots;
	}

	public void setMove(int slots)
	{
		this.slots = slots;
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
			return this.target.getName() + " se déplace de " + this.slots + " cases";
	}
}
