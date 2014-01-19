package monopoly.proprietes;

import monopoly.jeu.ISlot;

/** Station de train */
public class TrainStation extends PublicService
{

	//
	// ATTRIBUTES
	//
	/** Loyer */
	private final int rent;



	//
	// CONSTRUCTOR
	//
	/** Construit une nouvelle station de train */
	public TrainStation(ISlot slot, String name, int price, IGroup group, int rent)
	{
		super(slot, name, price, group);
		this.rent = rent;
	}



	//
	// METHODS
	//	
	@Override
	public int calcRent()
	{
		if (this.mortgaged || this.owner == null)
			return 0;

		int count = 0;

		for (IProperty prop : this.group.getProperties())
		{
			if (prop != null && prop == this.owner)
				count++;
		}

		int x = (int) Math.pow(2, count);

		return x * this.rent;
	}

}
