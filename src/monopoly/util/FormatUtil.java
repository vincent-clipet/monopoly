package monopoly.util;

/** Classe utilitaire permettant de formatter des chaines de caracteres */
public class FormatUtil
{

	//
	// METHODS
	//
	/** Convertit une chaine de caractere en tableau d'entier,
	 * avec un separateur specifique */
	public static int[] stringToIntArray(String input, String sep)
	{
		String[] cut = input.split(sep);
		int[] ret = new int[cut.length];

		for (int i = 0; i < cut.length; i ++)
			ret[i] = stringToInt(cut[i]);

		return ret;
	}

	/** Convertit une chaine de caractere en entier */
	public static int stringToInt(String input)
	{
		try { return Integer.valueOf(input); }
		catch (NumberFormatException exc) { return -1; }
	}

	/** Convertit une chaine de caractere en booleen */
	public static boolean stringToBoolean(String input)
	{
		if (input.equalsIgnoreCase("true") || input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("oui"))
			return true;
		else
			return false;
	}

	/** Convertit un booleen en une chaine de caractere particuliere */
	public static String booleanToString(boolean b)
	{
		return b ? "oui" : "non";
	}

	/** Convertit un tableau de chaines de caracteres en 1 seule chaine,
	 * avec un separateur specifique */
	public static String join(String[] toJoin, String sep)
	{
		if (toJoin.length == 0)
			return "";
		else if (toJoin.length == 1)
			return toJoin[0];
		else
		{
			StringBuilder sb = new StringBuilder(toJoin[0]);

			for (int i = 1; i < toJoin.length; i++)
				sb.append(sep).append(toJoin[i]);

			return sb.toString();
		}
	}

	/** Convertit un tableau d'entiers en 1 seule chaine de caracteres,
	 * avec un separateur specifique */
	public static String join(int[] toJoin, String sep)
	{
		String[] toTransform = new String[toJoin.length];
		for (int i = 0; i < toJoin.length; i++)
			toTransform[i] = "" + toJoin[i];

		return FormatUtil.join(toTransform, sep);
	}

}