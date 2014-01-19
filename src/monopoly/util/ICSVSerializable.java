package monopoly.util;

/** Defini si une classe est serialisable pour le jeu */
public interface ICSVSerializable
{
	/** Serialise l'objet pour une sauvegarde */
	public String[] serialize();
}
