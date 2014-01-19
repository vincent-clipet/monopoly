package monopoly.evenements.events;

import monopoly.evenements.EEventTypes;
import monopoly.evenements.Event;

public class EventJail extends Event
{

	//
	// CONSTRUCTOR
	//
	public EventJail()
	{
		super(EEventTypes.JAIL.getName());
	}



	//
	// METHODS
	//
	@Override
	public boolean run()
	{
		this.target.setJailed();
		this.target.setSlot(this.target.getSlot().getSlot(11));
		/*IEvent event = this.target.getSlot().getEvent();
		event.setTarget(this.target);
		this.target.getTodo().push(event);*/

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
		if (this.target == null)
			return "";
		else
			return this.target.getName() + " va en prison ! ";
	}

}
