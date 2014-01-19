package monopoly.proprietes;

import monopoly.jeu.Game;
import monopoly.jeu.ISlot;
import monopoly.util.FormatUtil;
import monopoly.util.ICSVSerializable;

/** Terrain */
public class Plot extends Property implements ICSVSerializable
{

	//
	// ATTRIBUTES
	//
	/** Prix de construction */
	private final int buildingPrice;

	/** Montant des loyers */
	private int[] rents;

	/** Nombre de constructions */
	private int buildings;



	//
	// CONSTRUCTOR
	//
	/** Construit un nouveau terrain */
	public Plot(ISlot slot, String name, int price, IGroup group, int buildingPrice, int[] rents)
	{
		super(slot, name, price, group);
		this.buildingPrice = buildingPrice;
		this.rents = rents;
		this.buildings = 0;
		this.buildable = true;
	}


	//
	// METHODS
	//
	@Override
	public boolean build()
	{
		if (! this.isBuildable())
			return false;

		if (this.owner.take(this.buildingPrice))
		{
			this.buildings++;
			return true;
		}
		else
			return false;
	}

	@Override
	public boolean unbuild()
	{
		if (! this.isUnbuildable())
			return false;

		this.buildings--;
		this.owner.give(this.buildingPrice / 2);
		Game.getInstance().addMessage("Une maison a été détruite sur " + this.getName());

		return false;
	}

	@Override
	public int calcRent()
	{
		if (this.mortgaged || this.owner == null)
			return 0;
		
		int fact = 1;

		if (this.group.hasUniqueOwner())
			fact = 2;
		
		return this.rents[this.buildings] * fact;
	}

	@Override
	public boolean isBuildable()
	{
		return this.buildable && this.owner != null && ! this.mortgaged && this.buildings < 5;
	}

	public boolean isUnbuildable()
	{
		return this.buildable && this.owner != null && this.buildings <= 5 && this.buildings > 0;
	}



	//
	// GET & SET
	//
	@Override
	public int getBuildings()
	{
		return this.buildings;
	}

	/** Retourne le montant des loyers */
	public int[] getRents()
	{
		return this.rents;
	}

	/** Retourne le prix de construction */
	public int getBuildingPrice()
	{
		return this.buildingPrice;
	}



	//
	// UTIL
	//
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("Property: " + this.name).append("\n")
		.append("id: " + this.getSlot().getId()).append("\n")
		.append(this.group == null ? "group: null" : "group: " + this.group.getName()).append("\n")
		.append(this.owner == null ? "owner: null" : "owner: "+ this.owner.getName()).append("\n")
		.append("mortgaged: " + this.mortgaged).append("\n")
		.append("buildable: " + this.buildable).append("\n")
		.append("price: " + this.price).append("\n")
		.append("buildingprice: " + this.buildingPrice).append("\n")
		.append("buildings: " + this.buildings).append("\n")
		.append("rent: " + FormatUtil.join(this.rents, ","));

		return sb.toString();
	}

	@Override
	public String[] serialize()
	{
		String[] ret = new String[3];
		ret[0] = "" + this.getSlot().getId();
		ret[1] = "" + FormatUtil.booleanToString(this.mortgaged);
		ret[2] = "" + this.buildings;

		return ret;
	}

}