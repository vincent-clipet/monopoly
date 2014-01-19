package monopoly.jeu;

import monopoly.evenements.Event;
import monopoly.evenements.IEvent;
import monopoly.proprietes.IProperty;

/** Case */
public class Slot implements ISlot
{

	//
	// ATTRIBUTES
	//
	/** Nom de la case */
	private final String name;
	
	/** ID de la case */
	private final int id;
	
	/** Propriete de la case */
	private final IProperty property;
	
	/** Evenement de la case */
	private IEvent event;
	
	/** Liste des cases */
	private final static Slot[] slots = new Slot[40];



	//
	// CONSTRUCTOR
	//	
	/** Construit une nouvelle case */
	public Slot(int id, String name, Event event, IProperty property)
	{
		this.name = name;
		this.id = id;
		this.event = event;
		this.property = property;
		Slot.slots[id-1] = this;
	}



	//
	// GET & SET
	//
	@Override
	public int getId()
	{
		return this.id;
	}

	@Override
	public String getName()
	{
		return this.name;
	}

	@Override
	public ISlot getSlot(int id)
	{
		return Slot.slots[id-1];
	}

	@Override
	public IProperty getProperty()
	{
		return this.property;
	}

	@Override
	public IEvent getEvent()
	{
		return this.event;
	}

	@Override
	public void setEvent(IEvent event)
	{
		this.event = event;
	}



	//
	// UTIL
	//	
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("Slot: " + this.name).append("\n")
		.append("id: " + this.id).append("\n")
		.append(this.property == null ? "property: null" : "property: " + this.property.getName() + "\n" + (this.property.getOwner() == null ? "owner: null" : "owner: " + this.property.getOwner().getName())).append("\n")
		.append("event: " + this.event.getName());

		return sb.toString();
	}
}