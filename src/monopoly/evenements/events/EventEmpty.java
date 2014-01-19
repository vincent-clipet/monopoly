package monopoly.evenements.events;

import monopoly.evenements.EEventTypes;
import monopoly.evenements.Event;

public class EventEmpty extends Event
{
	
	//
	// CONSTRUCTOR
	//
	public EventEmpty()
	{
		super(EEventTypes.EMPTY.getName());
	}



	//
	// METHODS
	//
	@Override
	public boolean run()
	{
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
		return "EMPTY"; //TODO: remove
	}
}
