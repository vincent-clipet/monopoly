package monopoly.proprietes;

import monopoly.jeu.ISlot;
import monopoly.util.FormatUtil;

/** Service Public */
public abstract class PublicService extends Property
{

	//
	// CONSTRUCTOR
	//
	/** Construit un nouveau service public */
	public PublicService(ISlot slot, String name, int price, IGroup group)
	{
		super(slot, name, price, group);
		this.buildable = false;
	}



	//
	// METHODS
	//	
	@Override
	public abstract int calcRent();



	//
	// UTIL
	//
	@Override
	public String[] serialize()
	{
		String[] ret = new String[3];
		ret[0] = "" + this.getSlot().getId();
		ret[1] = "" + FormatUtil.booleanToString(this.mortgaged);
		ret[2] = "0";

		return ret;
	}

}
