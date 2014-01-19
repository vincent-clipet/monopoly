package monopoly.proprietes;

/** Contient la liste des types de propriete */
public enum EPropertyTypes
{

	//
	// ATTRIBUTES
	//
	NONE(0, ""),
	PLOT(1, "terrain"),
	TRAINSTATION(2, "gares"),
	COMPANY(3, "compagnies");

	private int type;
	private String name;



	//
	// CONSTRUCTOR
	//
	EPropertyTypes(int type, String name)
	{
		this.type = type;
		this.name = name;
	}



	//
	// GET & SET
	//
	public int getType()
	{
		return this.type;
	}

	public String getName()
	{
		return this.name;
	}

	/** Retourne le type de propriete en fonction de son nom */
	public static EPropertyTypes getTypeFromString(String type, String group)
	{
		if (type.equalsIgnoreCase("terrain"))
			return EPropertyTypes.PLOT;
		else if (type.equalsIgnoreCase("monopole"))
		{
			if (group.equalsIgnoreCase("compagnies"))
				return EPropertyTypes.COMPANY;
			else if (group.equalsIgnoreCase("gares"))
				return EPropertyTypes.TRAINSTATION;
		}

		return EPropertyTypes.NONE;
	}

}