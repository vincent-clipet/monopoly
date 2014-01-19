package monopoly.evenements;

/** Enumeration contenant tous les types d'evenements */
public enum EEventTypes
{

	//
	// ATTRIBUTES
	//
	EMPTY(""),
	JAIL("prison"),
	CARD("carte"),
	EXPENSE("dépense"),
	RECEIPT("recette"),
	MOVE_RELATIVE("aller"),
	BUY("achat"),
	PAY_RENT("loyer");

	/** Nom du type d'evenement */
	private final String name;



	//
	// CONSTRUCTOR
	//
	EEventTypes(String name)
	{
		this.name = name;
	}



	//
	// METHODS
	//
	/** Donne un type d'evenement en fonction de son nom */
	public static EEventTypes getTypeFromString(String toFind)
	{
		for (EEventTypes type : EEventTypes.values())
			if (type.getName().equals(toFind))
				return type;

		return EEventTypes.EMPTY;
	}



	//
	// GET & SET
	//
	/** Retourne le nom de ce type d'evenement */
	public String getName()
	{
		return this.name;
	}
}
