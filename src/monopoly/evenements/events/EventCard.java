package monopoly.evenements.events;

import monopoly.evenements.EEventTypes;
import monopoly.evenements.Event;

public class EventCard extends Event
{

	//
	// ATTRIBUTES
	//
	//TODO



	//
	// CONSTRUCTOR
	//
	public EventCard()
	{
		this(null);
	}

	public EventCard(Event event)
	{
		super(EEventTypes.CARD.getName());
	}



	//
	// METHODS
	//
	@Override
	public boolean run()
	{
		//TODO
		this.succeeded = true;
		return this.succeeded;
	}

	@Override
	public void clear()
	{
		super.clear();
	}



	//
	// UTIL
	//
	@Override
	public String toString()
	{
		return "CARD"; //TODO: remove
	}
}
