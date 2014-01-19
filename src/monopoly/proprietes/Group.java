package monopoly.proprietes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import monopoly.jeu.IPlayer;

/** Groupe de propriete */
public class Group implements IGroup
{

	//
	// ATTRIBUTES
	//
	/** Nom du groupe */
	private final String name;
	
	/** Liste des proprietes du groupe */
	private final List<IProperty> properties;
	
	/** Map nom_groupe/groupe */
	private final static HashMap<String, IGroup> groups = new HashMap<String, IGroup>();



	//
	// CONSTRUCTOR
	//
	/** Construit un nouveau groupe */
	public Group(String name)
	{
		this.name = name;
		this.properties = new ArrayList<IProperty>();

		if (! Group.groups.containsKey(name))
			Group.groups.put(name, this);
	}



	//
	// METHODS
	//
	@Override
	public boolean hasUniqueOwner()
	{
		IPlayer owner = null;

		for (IProperty prop : this.properties)
		{
			IPlayer player = prop.getOwner();

			if (player == null)
				return false;

			if (owner == null)
				owner = player;
			else if (owner != prop.getOwner())
				return false;
		}

		return true;
	}

	@Override
	public int calcTotalCost()
	{
		int total = 0;

		for (IProperty prop : this.getProperties())
			total += prop.getPrice();

		return total;
	}



	//
	// GET & SET
	//
	@Override
	public String getName()
	{
		return this.name;
	}

	@Override
	public List<IProperty> getProperties()
	{
		return this.properties;
	}

	@Override
	public IGroup getGroup(String nom)
	{
		return Group.groups.get(nom);
	}



	//
	// UTIL
	//
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("Group: " + this.name).append("\n")
		.append("properties: ");

		for (IProperty p : this.properties)
			sb.append(p.getName()).append(", ");

		return sb.toString();
	}

}