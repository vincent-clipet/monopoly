package monopoly.proprietes;

import monopoly.jeu.Game;
import monopoly.jeu.IPlayer;
import monopoly.jeu.ISlot;

/** Compagnie */
public class Company extends PublicService
{

	//
	// CONSTRUCTOR
	//
	/** Construit une nouvelle compagnie */
	public Company(ISlot slot, String name, int price, IGroup group)
	{
		super(slot, name, price, group);
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
			IPlayer p = prop.getOwner();

			if (p != null && p == this.owner)
				count++;
		}

		int rate = (count == 1 ? 400 : 1000);
		int d = Game.getInstance().getDices().getTotal();

		return d * rate;
	}

}